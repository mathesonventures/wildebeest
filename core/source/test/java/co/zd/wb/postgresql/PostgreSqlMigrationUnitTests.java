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

import co.zd.wb.AssertionResponse;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.zd.wb.plugin.database.DatabaseExistsAssertion;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit tests for plugins as applied to PostgreSQL databases.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlMigrationUnitTests
{
	@Test public void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException
	{
		
		//
		// Setup
		//
		
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null);
		
		AnsiSqlCreateDatabaseMigration m = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());

		try
		{

			//
			// Execute
			//

			m.perform(db);

			//
			// Verify
			//

			Assert.assertEquals("databaseExists", true, db.databaseExists());

		}
		finally
		{
			try
			{
				DatabaseHelper.execute(
					db.getAdminDataSource(),
					"DROP DATABASE skyfalltest");
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
		}
		
	}
	
	@Test public void databaseExistsAssertionSucceeds() throws MigrationFailedException
	{
		
		//
		// Setup
		//
		
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null);
		
		AnsiSqlCreateDatabaseMigration m = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());

		DatabaseExistsAssertion databaseExists = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);
		
		try
		{
			// Use the migration to create the database
			m.perform(db);
		
			//
			// Execute
			//

			AssertionResponse response = databaseExists.perform(db);

			//
			// Verify
			//

			Assert.assertNotNull("response", response);
			Assert.assertEquals("response.message", "Database SkyfallTest exists", response.getMessage());
			Assert.assertTrue("respnse.result", response.getResult());
			
		}
		finally
		{
			try
			{
				DatabaseHelper.execute(
					db.getAdminDataSource(),
					"DROP DATABASE skyfalltest");
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
		}
		
	}
}