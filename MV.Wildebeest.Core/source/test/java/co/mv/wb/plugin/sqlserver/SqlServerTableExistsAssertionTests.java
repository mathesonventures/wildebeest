// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

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
import co.mv.wb.event.EventSink;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link SqlServerTableExistsAssertion}.
 *
 * @since 2.0
 */
public class SqlServerTableExistsAssertionTests
{
	private static final Logger LOG = LoggerFactory.getLogger(SqlServerTableExistsAssertionTests.class);

	@Test
	public void applyForExistingTableSucceeds() throws
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
		EventSink eventSink = (event) ->
		{if (event.getMessage().isPresent()) LOG.info(event.getMessage().get());};

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(eventSink)
			.withFactoryResourcePlugins()
			.withFactoryMigrationPlugins()
			.get();

		SqlServerProperties properties = SqlServerProperties.get();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			Wildebeest.SqlServerDatabase,
			"Database",
			null);

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Schema Loaded
		State schemaLoaded = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(schemaLoaded);

		// Migrate -> created
		Migration migration1 = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			created.getStateId().toString());
		resource.getMigrations().add(migration1);

		// Migrate created -> schemaLoaded
		Migration migration2 = new SqlScriptMigration(
			UUID.randomUUID(),
			created.getStateId().toString(),
			schemaLoaded.getStateId().toString(),
			SqlServerElementFixtures.productCatalogueDatabase());
		resource.getMigrations().add(migration2);

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
			schemaLoaded.getStateId().toString());

		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"dbo",
			"ProductType");

		//
		// Execute
		//

		AssertionResponse response = null;

		try
		{
			response = assertion.perform(instance);
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}

		//
		// Verify
		//

		assertNotNull("response", response);
		Asserts.assertAssertionResponse(true, "Table ProductType exists", response, "response");
	}

	@Test
	public void applyForNonExistentTableFails() throws
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
		EventSink eventSink = (event) ->
		{if (event.getMessage().isPresent()) LOG.info(event.getMessage().get());};

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(eventSink)
			.withFactoryResourcePlugins()
			.withFactoryMigrationPlugins()
			.get();

		SqlServerProperties properties = SqlServerProperties.get();

		SqlServerDatabaseResourcePlugin resourcePlugin = new SqlServerDatabaseResourcePlugin();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			Wildebeest.SqlServerDatabase,
			"Database",
			null);

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Migrate -> created
		Migration migration1 = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			created.getStateId().toString());
		resource.getMigrations().add(migration1);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SqlServerCreateDatabaseMigration.class, new SqlServerCreateDatabaseMigrationPlugin());

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
			created.getStateId().toString());

		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"dbo",
			"ProductType");

		//
		// Execute
		//

		AssertionResponse response = null;

		try
		{
			response = assertion.perform(instance);
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}

		//
		// Verify
		//

		assertNotNull("response", response);
		Asserts.assertAssertionResponse(false, "Table ProductType does not exist", response, "response");
	}

	@Test
	public void applyForNonExistentDatabaseFails()
	{
		// Setup
		SqlServerProperties properties = SqlServerProperties.get();

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			null);

		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"dbo",
			"ProductType");

		// Execute
		AssertionResponse response = assertion.perform(instance);

		// Verify
		assertNotNull("response", response);
		Asserts.assertAssertionResponse(
			false, "Database " + databaseName + " does not exist",
			response, "response");
	}

	@Test
	public void applyForIncorrectInstanceTypeFails()
	{
		// Setup
		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"dbo",
			"TableName");

		FakeInstance instance = new FakeInstance();

		try
		{
			AssertionResponse response = assertion.perform(instance);

			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e)
		{
			assertEquals("e.message", "instance must be a SqlServerDatabaseInstance", e.getMessage());
		}
	}
}
