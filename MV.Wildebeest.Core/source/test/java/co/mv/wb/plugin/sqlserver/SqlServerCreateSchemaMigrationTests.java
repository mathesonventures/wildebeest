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

import co.mv.wb.FakeLogger;
import co.mv.wb.Logger;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.plugin.database.DatabaseFixtureHelper;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SqlServerCreateSchemaMigrationTests
{
	@Test public void performForNonExistantSchemaSucceeds() throws
		MigrationFailedException
	{
		// Setup
		Logger logger = new FakeLogger();

		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = SqlServerProperties.get().toInstance(databaseName);

		// Create the database
		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(UUID.randomUUID()));

		SqlServerCreateDatabaseMigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();
		
		createDatabaseRunner.perform(
			logger,
			createDatabase,
			instance);
		
		// Setup the migration
		SqlServerCreateSchemaMigration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.empty(),
			"prd");

		SqlServerCreateSchemaMigrationPlugin createSchemaRunner = new SqlServerCreateSchemaMigrationPlugin();
		
		try
		{
			// Execute
			createSchemaRunner.perform(
				logger,
				createSchema,
				instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}

	@Test public void performForExistantSchemaFails() throws SQLException, MigrationFailedException
	{
		// Setup
		Logger logger = new FakeLogger();

		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = SqlServerProperties.get().toInstance(databaseName);

		// Create the database
		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(UUID.randomUUID()));

		SqlServerCreateDatabaseMigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		createDatabaseRunner.perform(
			logger,
			createDatabase,
			instance);
		
		// Setup the migration
		SqlServerCreateSchemaMigration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.empty(),
			"prd");

		SqlServerCreateSchemaMigrationPlugin createSchemaRunner = new SqlServerCreateSchemaMigrationPlugin();
		
		createSchemaRunner.perform(
			logger,
			createSchema,
			instance);
		
		// Execute
		MigrationFailedException caught = null;
		
		try
		{
			createSchemaRunner.perform(
				logger,
				createSchema,
				instance);
			
			fail("MigrationFailedException expected");
		}
		catch (MigrationFailedException e)
		{
			caught = e;
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}
		
		// Verify
		assertEquals(
			"caught.message",
			"There is already an object named 'prd' in the database.",
			caught.getMessage());
	}
}
