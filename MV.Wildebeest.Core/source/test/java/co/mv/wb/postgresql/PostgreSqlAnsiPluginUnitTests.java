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

package co.mv.wb.postgresql;

import co.mv.wb.MigrationFailedException;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.database.BaseAnsiPluginUnitTests;
import co.mv.wb.plugin.database.SqlScriptMigration;
import co.mv.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;

/**
 * Unit tests for AnsiSql plugins as applied to PostgreSQL databases.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlAnsiPluginUnitTests extends BaseAnsiPluginUnitTests
{
	@Override @Test public void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"WildebeestTest",
			null,
			null);
		
		AnsiSqlCreateDatabaseMigration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()));
		
		AnsiSqlDropDatabaseMigration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()));
		
		this.ansiSqlCreateDatabaseMigrationSucceeds(db, create, drop);
	}

	@Override @Test public void tableExistsForExistentTable() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"WildebeestTest",
			null,
			null);
		
		AnsiSqlCreateDatabaseMigration createDatabase = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()));
		
		SqlScriptMigration createTable = new SqlScriptMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()),
			"CREATE SCHEMA sch; CREATE TABLE sch.tbl ( tblId INTEGER );");
		
		AnsiSqlDropDatabaseMigration dropDatabase = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()));
		
		this.tableExistsForExistentTable(db, createDatabase, createTable, dropDatabase);
	}

	@Override @Test public void tableExistsForNonExistentTable() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"WildebeestTest",
			null,
			null);
		
		AnsiSqlCreateDatabaseMigration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()));
		
		AnsiSqlDropDatabaseMigration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			Optional.of(UUID.randomUUID()),
			Optional.of(UUID.randomUUID()));
		
		this.tableExistsForNonExistentTable(db, create, drop);
	}
}
