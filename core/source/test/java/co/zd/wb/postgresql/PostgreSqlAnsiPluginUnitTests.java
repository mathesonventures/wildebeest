// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.postgresql;

import co.zd.wb.MigrationFailedException;
import co.zd.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.zd.wb.plugin.ansisql.AnsiSqlDropDatabaseMigration;
import co.zd.wb.plugin.database.AnsiPluginUnitTestsTemplate;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.plugin.database.SqlScriptMigration;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit tests for AnsiSql plugins as applied to PostgreSQL databases.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlAnsiPluginUnitTests extends AnsiPluginUnitTestsTemplate
{
	@Override @Test public void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null);
		
		AnsiSqlCreateDatabaseMigration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		AnsiSqlDropDatabaseMigration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		this.ansiSqlCreateDatabaseMigrationSucceeds(db, create, drop);
	}

	@Override @Test public void tableExistsForExistentTable() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null);
		
		AnsiSqlCreateDatabaseMigration createDatabase = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		SqlScriptMigration createTable = new SqlScriptMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			"CREATE SCHEMA sch; CREATE TABLE sch.tbl ( tblId INTEGER );");
		
		AnsiSqlDropDatabaseMigration dropDatabase = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		this.tableExistsForExistentTable(db, createDatabase, createTable, dropDatabase);
	}

	@Override @Test public void tableExistsForNonExistentTable() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null);
		
		AnsiSqlCreateDatabaseMigration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		AnsiSqlDropDatabaseMigration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		this.tableExistsForNonExistentTable(db, create, drop);
	}
}
