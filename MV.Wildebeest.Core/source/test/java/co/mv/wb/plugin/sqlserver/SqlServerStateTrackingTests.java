package co.mv.wb.plugin.sqlserver;

import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Asserts;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigrationPlugin;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class SqlServerStateTrackingTests
{

	@Test
	public void checkIsStateInstantTracked() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{

		//
		// Setup
		//

		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryPluginGroups()
			.withFactoryResourcePlugins()
			.withFactoryMigrationPlugins()
			.get();

		SqlServerProperties properties = SqlServerProperties.get();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			Wildebeest.SqlServerDatabase,
			"Database",
			Optional.empty());

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Schema Loaded
		State schemaLoaded = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(schemaLoaded);

		// Migrate -> created
		Migration migration1 = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(created.getStateId().toString()));
		resource.getMigrations().add(migration1);


		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SqlServerCreateDatabaseMigration.class, new SqlServerCreateDatabaseMigrationPlugin());
		migrationPlugins.put(SqlScriptMigration.class, new SqlScriptMigrationPlugin());

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			null);

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(created.getStateId().toString()));

		//
		// Execute and Verify
		//

		AssertionResponse response = null;

		try
		{
			DatabaseHelper.execute(instance.getAppDataSource(),
				String.format("SELECT LastMigrationInstant from %s",
					instance.getStateTableName()));

		}
		catch (SQLException e)
		{
			e.getCause();
			Assert.fail();
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}

	}

}
