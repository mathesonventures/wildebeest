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

public class SqlServerDropSchemaMigrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(SqlServerDropSchemaMigrationTests.class);

	@Test
	public void performForExistantSchemaSucceeds() throws
		MigrationFailedException
	{
		// Setup
		EventSink eventSink = new LoggingEventSink(LOG);

		SqlServerProperties p = SqlServerProperties.get();

		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		SqlServerCreateDatabaseMigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			p.getHostName(),
			p.hasInstanceName() ? p.getInstanceName() : null,
			p.getPort(),
			p.getUsername(),
			p.getPassword(),
			databaseName,
			null);

		createDatabaseRunner.perform(
			eventSink,
			createDatabase,
			instance);

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

		SqlServerDropSchemaMigration dropSchema = new SqlServerDropSchemaMigration(
			UUID.randomUUID(),
			null,
			null,
			"prd");

		SqlServerDropSchemaMigrationPlugin dropSchemaRunner = new SqlServerDropSchemaMigrationPlugin();

		try
		{
			// Execute
			dropSchemaRunner.perform(
				eventSink,
				dropSchema,
				instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}

	@Test
	public void performForNonExistantSchemaFails() throws SQLException, MigrationFailedException
	{
		// Setup
		EventSink eventSink = new LoggingEventSink(LOG);

		SqlServerProperties p = SqlServerProperties.get();

		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		SqlServerCreateDatabaseMigrationPlugin createDatabaseRunner = new SqlServerCreateDatabaseMigrationPlugin();

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			p.getHostName(),
			p.hasInstanceName() ? p.getInstanceName() : null,
			p.getPort(),
			p.getUsername(),
			p.getPassword(),
			databaseName,
			null);

		createDatabaseRunner.perform(
			eventSink,
			createDatabase,
			instance);

		SqlServerDropSchemaMigration dropSchema = new SqlServerDropSchemaMigration(
			UUID.randomUUID(),
			null,
			null,
			"prd");

		SqlServerDropSchemaMigrationPlugin dropSchemaRunner = new SqlServerDropSchemaMigrationPlugin();

		try
		{
			// Execute
			dropSchemaRunner.perform(
				eventSink,
				dropSchema,
				instance);

			fail("MigrationFailedException expected");
		}
		catch (MigrationFailedException e)
		{
			assertEquals(
				"e.message",
				"Cannot drop the schema 'prd', because it does not exist or you do not have permission.",
				e.getMessage());
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}
}
