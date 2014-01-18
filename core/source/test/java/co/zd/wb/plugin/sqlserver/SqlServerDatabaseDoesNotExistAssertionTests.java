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

import co.zd.wb.AssertExtensions;
import co.zd.wb.fake.FakeInstance;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.AssertionResponse;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.State;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;
import co.zd.wb.plugin.base.ImmutableState;
import co.zd.wb.plugin.database.DatabaseFixtureHelper;
import co.zd.wb.PrintStreamLogger;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class SqlServerDatabaseDoesNotExistAssertionTests
{
	 @Test public void applyForExistingDatabaseFails() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 MigrationNotPossibleException,
		 MigrationFailedException,
		 SQLException
	 {
		 
		//
		// Fixture Setup
		//

		SqlServerProperties properties = SqlServerProperties.get();
		 
		SqlServerDatabaseResource resource = new SqlServerDatabaseResource(UUID.randomUUID(), "Database");
		 
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);
		 
		Migration tran1 = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(), null, created.getStateId());
		resource.getMigrations().add(tran1);
		 
		String databaseName = DatabaseFixtureHelper.databaseName();
		
		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			null);
		 
		resource.migrate(new PrintStreamLogger(System.out), instance, created.getStateId());
		
		SqlServerDatabaseDoesNotExistAssertion assertion = new SqlServerDatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);
 
		//
		// Execute
		//
		
		try
		{
			AssertionResponse response = assertion.perform(instance);

			//
			// Assert Results
			//

			Assert.assertNotNull("response", response);
			AssertExtensions.assertAssertionResponse(false, "Database " + databaseName + " exists", response, "response");
			
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);
		}
		
	 }
	 
	 @Test public void applyForNonExistentDatabaseSucceeds() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 MigrationNotPossibleException,
		 MigrationFailedException,
		 SQLException
	 {
		 
		//
		// Fixture Setup
		//

		SqlServerProperties properties = SqlServerProperties.get();

		String databaseName = DatabaseFixtureHelper.databaseName();

		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			null);
		
		SqlServerDatabaseDoesNotExistAssertion assertion = new SqlServerDatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);
 
		//
		// Execute
		//
		
		AssertionResponse response = assertion.perform(instance);

		//
		// Assert Results
		//

		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(
			true, "Database " + databaseName + " does not exist",
			response, "response");
		
	 }
	 
	 @Test public void applyForNullInstanceFails()
	 {
		 
		//
		// Fixture Setup
		//

		SqlServerDatabaseDoesNotExistAssertion assertion = new SqlServerDatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);
		
		//
		// Execute
		//
		
		IllegalArgumentException caught = null;
		
		try
		{
			AssertionResponse response = assertion.perform(null);
			
			Assert.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
			caught = e;
		}

		//
		// Assert Results
		//

		Assert.assertEquals("caught.message", "instance cannot be null", caught.getMessage());
		
	 }
	 
	 @Test public void applyForIncorrectInstanceTypeFails()
	 {
		 
		//
		// Fixture Setup
		//
		 
		SqlServerDatabaseDoesNotExistAssertion assertion = new SqlServerDatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);
		
		FakeInstance instance = new FakeInstance();
		
		//
		// Execute
		//
		
		IllegalArgumentException caught = null;
		
		try
		{
			AssertionResponse response = assertion.perform(instance);
			
			Assert.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
			caught = e;
		}

		//
		// Assert Results
		//

		Assert.assertEquals("caught.message", "instance must be a SqlServerDatabaseInstance", caught.getMessage());
		
	 }
}