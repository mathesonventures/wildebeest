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

import co.mv.wb.AssertionResponse;
import co.mv.wb.Asserts;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import co.mv.wb.plugin.fake.FakeInstance;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link SqlServerSchemaExistsAssertion}.
 *
 * @since                                       2.0
 */
public class SqlServerSchemaExistsAssertionTests
{
	@Test
	public void applyForExistingSchemaSucceeds() throws
		MigrationFailedException
	{
		// Setup
		PrintStream output = System.out;

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

		Migration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(UUID.randomUUID()));

		MigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		createDatabaseRunner.perform(
			output,
			createDatabase,
			instance);

		Migration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.empty(),
			"prd");

		MigrationPlugin createSchemaRunner = new SqlServerCreateSchemaMigrationPlugin();

		createSchemaRunner.perform(
			output,
			createSchema,
			instance);

		SqlServerSchemaExistsAssertion schemaExists = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");

		AssertionResponse response = null;

		try
		{
			// Execute
			response = schemaExists.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}

		// Verify
		assertNotNull("response", response);
		Asserts.assertAssertionResponse(
			true,
			"Schema prd exists",
			response,
			"response");
	}

	@Test
	public void applyForNonExistentSchemaFails() throws
		MigrationFailedException
	{
		// Setup
		PrintStream output = System.out;

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

		Migration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(UUID.randomUUID()));

		MigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		createDatabaseRunner.perform(
			output,
			createDatabase,
			instance);

		SqlServerSchemaExistsAssertion schemaExists = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");

		AssertionResponse response = null;

		try
		{
			// Execute
			response = schemaExists.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}

		// Verify
		assertNotNull("response", response);
		Asserts.assertAssertionResponse(
			false,
			"Schema prd does not exist",
			response,
			"response");
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

		SqlServerSchemaExistsAssertion assertion = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");

		// Execute
		AssertionResponse response = assertion.perform(instance);

		// Verify
		assertNotNull("response", response);

		Asserts.assertAssertionResponse(
			false,
			"Database " + databaseName + " does not exist",
			response,
			"response");
	}

	@Test
	public void applyForNullInstanceFails()
	{
		// Setup
		SqlServerSchemaExistsAssertion assertion = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");

		// Execute and Verify
		try
		{
			AssertionResponse response = assertion.perform(null);

			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals("e.message", "instance cannot be null", e.getMessage());
		}
	}

	@Test
	public void applyForIncorrectInstanceTypeFails()
	{
		// Setup
		SqlServerSchemaExistsAssertion assertion = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");

		FakeInstance instance = new FakeInstance();

		// Execute and Verify
		try
		{
			AssertionResponse response = assertion.perform(instance);

			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals("e.message", "instance must be a SqlServerDatabaseInstance", e.getMessage());
		}
	}
}
