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

import co.mv.wb.MigrationFailedException;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MySqlCreateDatabaseMigrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(AnsiSqlCreateDatabaseMigrationPlugin.class);

	@Test
	public void performForNonExistantDatabaseSucceeds() throws
		MigrationFailedException
	{
		MySqlProperties mySqlProperties = MySqlProperties.get();

		MySqlCreateDatabaseMigration migration = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		MySqlCreateDatabaseMigrationPlugin migrationPlugin = new MySqlCreateDatabaseMigrationPlugin();

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		// Execute
		migrationPlugin.perform(
			new LoggingEventSink(LOG),
			migration,
			instance);

		// Verify

		// (none)

		// Tear-Down
		MySqlUtil.dropDatabase(mySqlProperties, databaseName);
	}

	@Test
	public void performForExistantDatabaseFails()
	{
		MySqlProperties mySqlProperties = MySqlProperties.get();

		String databaseName = MySqlUtil.createDatabase(
			mySqlProperties,
			"stm_test",
			"");

		MySqlCreateDatabaseMigration migration = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString());

		MySqlCreateDatabaseMigrationPlugin migrationPlugin = new MySqlCreateDatabaseMigrationPlugin();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		// Execute
		MigrationFailedException caught = null;

		try
		{
			migrationPlugin.perform(
				new LoggingEventSink(LOG),
				migration,
				instance);

			Assert.fail("MigrationFailedException expected");
		}
		catch (MigrationFailedException e)
		{
			caught = e;
		}
		finally
		{
			MySqlUtil.dropDatabase(mySqlProperties, databaseName);
		}

		// Verify
		Assert.assertEquals(
			"caught.message",
			String.format("database \"%s\" already exists", databaseName),
			caught.getMessage());
	}
}
