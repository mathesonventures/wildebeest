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

package co.mv.wb.plugin.postgresql;

import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.BaseDatabasePluginUnitTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Unit tests for Database plugins as applied to PostgreSQL databases.
 *
 * @since 1.0
 */
public class PostgreSqlDatabasePluginUnitTests extends BaseDatabasePluginUnitTests
{
	private static final Logger LOG = LoggerFactory.getLogger(PostgreSqlDatabasePluginUnitTests.class);

	@Override
	@Test
	public void databaseExistsAssertionForExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			15432,
			"postgres",
			"Password123!",
			"WildebeestTest",
			null,
			null);

		Migration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString());

		MigrationPlugin createRunner = new AnsiSqlCreateDatabaseMigrationPlugin();

		Migration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString());

		MigrationPlugin dropRunner = new AnsiSqlDropDatabaseMigrationPlugin();

		this.databaseExistsAssertionForExistentDatabase(
			new LoggingEventSink(LOG),
			instance,
			create,
			createRunner,
			drop,
			dropRunner);
	}

	@Override
	@Test
	public void databaseExistsAssertionForNonExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			15432,
			"postgres",
			"Password123!",
			"WildebeestTest",
			null,
			null);

		this.databaseExistsAssertionForNonExistentDatabase(instance);
	}

	@Override
	@Test
	public void databaseDoesNotExistAssertionForExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			15432,
			"postgres",
			"Password123!",
			"WildebeestTest",
			null,
			null);

		Migration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString());

		MigrationPlugin createRunner = new AnsiSqlCreateDatabaseMigrationPlugin();

		Migration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID().toString(),
			UUID.randomUUID().toString());

		MigrationPlugin dropRunner = new AnsiSqlDropDatabaseMigrationPlugin();

		this.databaseDoesNotExistAssertionForExistentDatabase(
			new LoggingEventSink(LOG),
			instance,
			create,
			createRunner,
			drop,
			dropRunner);
	}

	@Override
	@Test
	public void databaseDoesNotExistAssertionForNonExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			15432,
			"postgres",
			"Password123!",
			"WildebeestTest",
			null,
			null);

		this.databaseDoesNotExistAssertionForNonExistentDatabase(instance);
	}
}
