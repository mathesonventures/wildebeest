// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

import co.zd.wb.plugin.mysql.MySqlDatabaseInstance;
import co.zd.wb.plugin.database.SqlScriptMigration;
import co.zd.wb.MigrationFailedException;
import co.mv.helium.testframework.MySqlDatabaseFixture;
import java.util.UUID;
import org.junit.Test;

public class SqlScriptMigrationTests
{
	public SqlScriptMigrationTests()
	{
	}
	
	@Test
	public void performSuccessfully() throws MigrationFailedException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test",
			MySqlElementFixtures.stateCreateTableStatement());
		f.setUp();
		
		SqlScriptMigration migration = new SqlScriptMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID(),
			MySqlElementFixtures.productCatalogueDatabase());
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName(),
			null);
		
		//
		// Execute
		//

		try
		{
			migration.perform(instance);
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//
		
	}
}