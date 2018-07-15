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

package co.mv.wb.plugin.mysql;

import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Asserts;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
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

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link MySqlTableDoesNotExistAssertion}.
 *
 * @since 1.0
 */
public class MySqlTableDoesNotExistAssertionTests
{
	private static final Logger LOG = LoggerFactory.getLogger(MySqlTableDoesNotExistAssertionTests.class);

	@Test
	public void applyForExistingTableFails() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		InvalidReferenceException,
		SQLException, TargetNotSpecifiedException, UnknownStateSpecifiedException, InvalidStateSpecifiedException
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

		MySqlProperties mySqlProperties = MySqlProperties.get();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			Wildebeest.MySqlDatabase,
			"Database",
			null);

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Schema Loaded
		State schemaLoaded = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(schemaLoaded);

		// Migrate -> created
		Migration tran1 = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			created.getStateId().toString());
		resource.getMigrations().add(tran1);

		// Migrate created -> schemaLoaded
		Migration tran2 = new SqlScriptMigration(
			UUID.randomUUID(),
			created.getStateId().toString(),
			schemaLoaded.getStateId().toString(),
			MySqlElementFixtures.productCatalogueDatabase());
		resource.getMigrations().add(tran2);

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		wildebeestApi.migrate(
			resource,
			instance,
			schemaLoaded.getStateId().toString());

		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
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
			MySqlUtil.dropDatabase(instance, databaseName);
		}

		//
		// Verify
		//

		assertNotNull("response", response);
		Asserts.assertAssertionResponse(false, "Table ProductType exists", response, "response");

	}

	@Test
	public void applyForNonExistentTableSucceeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		SQLException,
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
			.withMigrationPlugin(new MySqlCreateDatabaseMigrationPlugin())
			.get();

		MySqlProperties mySqlProperties = MySqlProperties.get();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			Wildebeest.MySqlDatabase,
			"Database",
			null);

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Migrate -> created
		Migration tran1 = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			created.getStateId().toString());
		resource.getMigrations().add(tran1);

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		wildebeestApi.migrate(
			resource,
			instance,
			created.getStateId().toString());

		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
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
			MySqlUtil.dropDatabase(instance, databaseName);
		}

		//
		// Verify
		//

		assertNotNull("response", response);
		Asserts.assertAssertionResponse(true, "Table ProductType does not exist", response, "response");
	}

	@Test
	public void applyForNonExistentDatabaseFails() throws SQLException
	{
		// Setup
		MySqlProperties mySqlProperties = MySqlProperties.get();

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
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
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"TableName");

		FakeInstance instance = new FakeInstance();

		// Execute and Verify
		try
		{
			AssertionResponse response = assertion.perform(instance);

			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e)
		{
			assertEquals("e.message", "instance must be a MySqlDatabaseInstance", e.getMessage());
		}
	}
}
