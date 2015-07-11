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

package co.zd.wb.plugin.mysql;

import co.zd.wb.MigrationFailedException;
import co.zd.wb.plugin.database.DatabaseFixtureHelper;
import co.zd.wb.plugin.database.DatabasePluginUnitTestsTemplate;
import java.util.UUID;
import org.junit.Test;

/**
 * Unit tests for plugins as applied to MySql databases.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class MySqlPluginUnitTests extends DatabasePluginUnitTestsTemplate
{
	@Override @Test public void databaseExistsAssertionForExistentDatabase() throws MigrationFailedException
	{
		String databaseName = DatabaseFixtureHelper.databaseName();
		MySqlDatabaseInstance db = MySqlProperties.get().toInstance(databaseName);
		
		MySqlCreateDatabaseMigration create = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());
		
		MySqlDropDatabaseMigration drop = new MySqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			null);

		this.databaseExistsAssertionForExistentDatabase(db, create, drop);
	}
	
	@Override @Test public void databaseExistsAssertionForNonExistentDatabase() throws MigrationFailedException
	{
		String databaseName = DatabaseFixtureHelper.databaseName();
		MySqlDatabaseInstance db = MySqlProperties.get().toInstance(databaseName);
		
		this.databaseExistsAssertionForNonExistentDatabase(db);
	}
	
	@Override @Test public void databaseDoesNotExistAssertionForExistentDatabase() throws MigrationFailedException
	{
		String databaseName = DatabaseFixtureHelper.databaseName();
		MySqlDatabaseInstance db = MySqlProperties.get().toInstance(databaseName);
		
		MySqlCreateDatabaseMigration create = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());
		
		MySqlDropDatabaseMigration drop = new MySqlDropDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			null);

		this.databaseDoesNotExistAssertionForExistentDatabase(db, create, drop);
	}
	
	@Override @Test public void databaseDoesNotExistAssertionForNonExistentDatabase() throws MigrationFailedException
	{
		String databaseName = DatabaseFixtureHelper.databaseName();
		MySqlDatabaseInstance db = MySqlProperties.get().toInstance(databaseName);
		
		this.databaseDoesNotExistAssertionForNonExistentDatabase(db);
	}
}