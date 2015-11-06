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

public class SqlServerCreateDatabaseMigrationTests
{
	@Test public void performForNonExistantDatabaseSucceeds() throws
		MigrationFailedException
	{
		// Setup
		SqlServerProperties p = SqlServerProperties.get();
		
		SqlServerCreateDatabaseMigration m = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			p.getHostName(),
			p.hasInstanceName() ? p.getInstanceName() : null,
			p.getPort(),
			p.getUsername(),
			p.getPassword(),
			databaseName,
			null);
		
		// Execute
		try
		{
			m.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}
	}

	@Test public void performForExistantDatabaseFails() throws SQLException
	{
		// Setup
		SqlServerProperties properties = SqlServerProperties.get();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			DatabaseFixtureHelper.databaseName(),
			null);
		
		SqlServerUtil.createDatabase(instance);
		
		SqlServerCreateDatabaseMigration tr = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

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
			SqlServerUtil.tryDropDatabase(instance);
		}
		
		// Verify
		Assert.assertEquals(
			"caught.message",
			String.format("Database '%s' already exists. Choose a different database name.",	instance.getDatabaseName()),
			caught.getMessage());
	}
}
