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

package co.zd.wb.plugin.sqlserver;

import co.zd.wb.MigrationFailedException;
import co.zd.wb.plugin.database.DatabaseFixtureHelper;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class SqlServerCreateSchemaMigrationTests
{
	@Test public void performForNonExistantSchemaSucceeds() throws
		MigrationFailedException
	{
		// Setup
		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = SqlServerProperties.get().toInstance(databaseName);

		// Create the database
		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());
		
		createDatabase.perform(instance);
		
		// Setup the migration
		SqlServerCreateSchemaMigration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			null,
			null,
			"prd");
		
		try
		{
			// Execute
			createSchema.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}

	@Test public void performForExistantSchemaFails() throws SQLException, MigrationFailedException
	{
		// Setup
		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = SqlServerProperties.get().toInstance(databaseName);

		// Create the database
		SqlServerCreateDatabaseMigration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		createDatabase.perform(instance);
		
		// Setup the migration
		SqlServerCreateSchemaMigration createSchema = new SqlServerCreateSchemaMigration(
			UUID.randomUUID(),
			null,
			null,
			"prd");
		
		createSchema.perform(instance);
		
		// Execute
		MigrationFailedException caught = null;
		
		try
		{
			createSchema.perform(instance);
			
			Assert.fail("MigrationFailedException expected");
		}
		catch (MigrationFailedException e)
		{
			caught = e;
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}
		
		// Verify
		Assert.assertEquals(
			"caught.message",
			"There is already an object named 'prd' in the database.",
			caught.getMessage());
	}
}
