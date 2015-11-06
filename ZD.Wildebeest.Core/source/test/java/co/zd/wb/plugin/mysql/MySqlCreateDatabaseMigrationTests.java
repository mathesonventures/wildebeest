// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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
import java.sql.SQLException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class MySqlCreateDatabaseMigrationTests
{
	@Test public void performForNonExistantDatabaseSucceeds() throws
		MigrationFailedException,
		SQLException
	{
		// Setup
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlCreateDatabaseMigration tr = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		// Execute
		tr.perform(instance);
		
		// Verify

		// (none)
		
		// Tear-Down
		MySqlUtil.dropDatabase(mySqlProperties, databaseName);
	}

	@Test public void performForExistantDatabaseFails()
	{
		// Setup
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		String databaseName = MySqlUtil.createDatabase(
			mySqlProperties,
			"stm_test",
			"");
		
		MySqlCreateDatabaseMigration tr = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

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
			tr.perform(instance);
			
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
			String.format("database \"%s\" already exists",	databaseName),
			caught.getMessage());
	}
}