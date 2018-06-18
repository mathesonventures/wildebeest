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
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.BaseAnsiPluginUnitTests;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigrationPlugin;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Optional;
import java.util.UUID;

/**
 * Unit tests for AnsiSql plugins as applied to PostgreSQL databases.
 *
 * @since 1.0
 */
public class PostgreSqlAnsiPluginUnitTests extends BaseAnsiPluginUnitTests
{
	@Override
	@Test
	public void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException
	{
		PrintStream output = System.out;

		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"WildebeestTest",
			null,
			null);

		Migration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()));

		MigrationPlugin createRunner = new AnsiSqlCreateDatabaseMigrationPlugin();

		Migration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()));

		MigrationPlugin dropRunner = new AnsiSqlDropDatabaseMigrationPlugin();

		this.ansiSqlCreateDatabaseMigrationSucceeds(
			output,
			instance,
			create,
			createRunner,
			drop,
			dropRunner);
	}

	@Override
	@Test
	public void tableExistsForExistentTable() throws MigrationFailedException
	{
		PrintStream output = System.out;

		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"WildebeestTest",
			null,
			null);

		Migration createDatabase = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()));

		MigrationPlugin createDatabaseRunner = new AnsiSqlCreateDatabaseMigrationPlugin();

		Migration createTable = new SqlScriptMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()),
			"CREATE SCHEMA sch; CREATE TABLE sch.tbl ( tblId INTEGER );");

		MigrationPlugin createTableRunner = new SqlScriptMigrationPlugin();

		Migration dropDatabase = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()));

		MigrationPlugin dropDatabaseRunner = new AnsiSqlDropDatabaseMigrationPlugin();

		this.tableExistsForExistentTable(
			output,
			instance,
			createDatabase,
			createDatabaseRunner,
			createTable,
			createTableRunner,
			dropDatabase,
			dropDatabaseRunner);
	}

	@Override
	@Test
	public void tableExistsForNonExistentTable() throws MigrationFailedException
	{
		PrintStream output = System.out;

		PostgreSqlDatabaseInstance instance = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"WildebeestTest",
			null,
			null);

		Migration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()));

		MigrationPlugin createRunner = new AnsiSqlCreateDatabaseMigrationPlugin();

		Migration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID().toString()),
			Optional.of(UUID.randomUUID().toString()));

		MigrationPlugin dropRunner = new AnsiSqlDropDatabaseMigrationPlugin();

		this.tableExistsForNonExistentTable(
			output,
			instance,
			create,
			createRunner,
			drop,
			dropRunner);
	}
}
