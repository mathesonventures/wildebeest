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
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SqlServerCreateDatabaseMigrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(SqlServerCreateDatabaseMigrationTests.class);

	@Test
	public void performForNonExistantDatabaseSucceeds() throws
		MigrationFailedException
	{
		SqlServerProperties p = SqlServerProperties.get();

		SqlServerCreateDatabaseMigration migration = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		SqlServerCreateDatabaseMigrationPlugin migrationPlugin = new SqlServerCreateDatabaseMigrationPlugin();

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			p.getHostName(),
			p.hasInstanceName() ? p.getInstanceName() : null,
			p.getPort(),
			p.getUsername(),
			p.getPassword(),
			databaseName,
			null);

		// Execute
		try
		{
			migrationPlugin.perform(
				new LoggingEventSink(LOG),
				migration,
				instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}

	@Test
	public void performForExistantDatabaseFails() throws SQLException
	{
		SqlServerProperties properties = SqlServerProperties.get();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			DatabaseFixtureHelper.databaseName(),
			null);

		SqlServerUtil.createDatabase(instance);

		SqlServerCreateDatabaseMigration migration = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		SqlServerCreateDatabaseMigrationPlugin migrationPlugin = new SqlServerCreateDatabaseMigrationPlugin();

		// Execute
		try
		{
			migrationPlugin.perform(
				new LoggingEventSink(LOG),
				migration,
				instance);

			fail("MigrationFailedException expected");
		}
		catch (MigrationFailedException e)
		{
			assertEquals(
				"e.message",
				String.format(
					"Database '%s' already exists. Choose a different database name.",
					instance.getDatabaseName()),
				e.getMessage());
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}
	}
}
