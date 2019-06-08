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

import co.mv.wb.Instance;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigrationPlugin;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class SqlScriptMigrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(SqlScriptMigrationTests.class);

	public SqlScriptMigrationTests()
	{
	}

	@Test
	public void performSuccessfully() throws MigrationFailedException
	{
		MySqlProperties mySqlProperties = MySqlProperties.get();

		String databaseName = MySqlUtil.createDatabase(mySqlProperties, "stm_test", null);

		Migration migration = new SqlScriptMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID().toString(),
			MySqlElementFixtures.productCatalogueDatabase(),
			true);

		MigrationPlugin migrationPlugin = new SqlScriptMigrationPlugin();

		Instance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		// Execute and Verify
		try
		{
			migrationPlugin.perform(
				new LoggingEventSink(LOG),
				migration,
				instance);
		}
		finally
		{
			MySqlUtil.dropDatabase(mySqlProperties, databaseName);
		}
	}
}
