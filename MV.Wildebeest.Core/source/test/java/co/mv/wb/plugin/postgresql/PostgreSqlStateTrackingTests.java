package co.mv.wb.plugin.postgresql;

import co.mv.wb.AssertionFailedException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.PluginNotFoundException;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.WildebeestApi;
import co.mv.wb.WildebeestApiBuilder;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.UUID;

public class PostgreSqlStateTrackingTests
{
	private static final Logger LOG = LoggerFactory.getLogger(PostgreSqlStateTrackingTests.class);

	@Test
	public void checkIsStateInstantTracked() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidReferenceException,
		MigrationNotPossibleException,
		MigrationFailedException,
		PluginNotFoundException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.withFactoryPluginGroups()
			.withPostgreSqlSupport()
			.get();

		String databaseName = DatabaseFixtureHelper.databaseName().toLowerCase();

		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			15432,
			"postgres",
			"Password123!",
			databaseName,
			"wb",
			"wb_state");

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			PostgreSqlConstants.PostgreSqlDatabase,
			"Database",
			null);

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Migrate -> created
		Migration migration1 = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			created.getStateId().toString());
		resource.getMigrations().add(migration1);

		wildebeestApi.migrate(
			resource,
			instance,
			created.getStateId().toString());

		//

		try
		{
			DatabaseHelper.execute(
				instance.getAppDataSource(),
				String.format(
					"SELECT LastMigrationInstant FROM %s.%s ",
					instance.getMetaSchemaName(),
					instance.getStateTableName()),
				false);

		}
		catch (SQLException e)
		{
			e.getCause();
			Assert.fail();
		}
		finally
		{
			//TODO drop database
		}

	}
}
