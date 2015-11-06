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
import co.zd.wb.plugin.database.DatabasePluginUnitTestsTemplate;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import java.util.UUID;
import org.junit.Test;

/**
 * Unit tests for Database plugins as applied to PostgreSQL databases.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlDatabasePluginUnitTests extends DatabasePluginUnitTestsTemplate
{
	@Override @Test public void databaseExistsAssertionForExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null,
			null);
		
		AnsiSqlCreateDatabaseMigration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		AnsiSqlDropDatabaseMigration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());

		this.databaseExistsAssertionForExistentDatabase(db, create, drop);
	}
	
	@Override @Test public void databaseExistsAssertionForNonExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null,
			null);

		this.databaseExistsAssertionForNonExistentDatabase(db);
	}
	
	@Override @Test public void databaseDoesNotExistAssertionForExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null,
			null);
		
		AnsiSqlCreateDatabaseMigration create = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());
		
		AnsiSqlDropDatabaseMigration drop = new AnsiSqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());

		this.databaseDoesNotExistAssertionForExistentDatabase(db, create, drop);
	}
	
	@Override @Test public void databaseDoesNotExistAssertionForNonExistentDatabase() throws MigrationFailedException
	{
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null,
			null);
		
		this.databaseDoesNotExistAssertionForNonExistentDatabase(db);
	}
}
