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

import co.mv.wb.MigrationFailedException;
import co.mv.wb.event.EventSink;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SqlServerCreateSchemaMigrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(SqlServerCreateSchemaMigrationTests.class);

	@Test
	public void performForNonExistantSchemaSucceeds() throws
		MigrationFailedException
	{
		// Setup
		EventSink eventSink = new LoggingEventSink(LOG);
		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = SqlServerProperties.get().toInstance(databaseName);

		// Create the database
		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		SqlServerCreateDatabaseMigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		createDatabaseRunner.perform(
			eventSink,
			createDatabase,
			instance);

		// Setup the migration
		SqlServerCreateSchemaMigration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			null,
			null,
			"prd");

		SqlServerCreateSchemaMigrationPlugin createSchemaRunner = new SqlServerCreateSchemaMigrationPlugin();

		try
		{
			// Execute
			createSchemaRunner.perform(
				eventSink,
				createSchema,
				instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}

	@Test
	public void performForExistantSchemaFails() throws SQLException, MigrationFailedException
	{
		// Setup
		EventSink eventSink = new LoggingEventSink(LOG);

		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = SqlServerProperties.get().toInstance(databaseName);

		// Create the database
		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		SqlServerCreateDatabaseMigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		createDatabaseRunner.perform(
			eventSink,
			createDatabase,
			instance);

		// Setup the migration
		SqlServerCreateSchemaMigration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			null,
			null,
			"prd");

		SqlServerCreateSchemaMigrationPlugin createSchemaRunner = new SqlServerCreateSchemaMigrationPlugin();

		createSchemaRunner.perform(
			eventSink,
			createSchema,
			instance);

		// Execute
		MigrationFailedException caught = null;

		try
		{
			createSchemaRunner.perform(
				eventSink,
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
