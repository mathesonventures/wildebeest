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

import co.zd.wb.AssertExtensions;
import co.zd.wb.fake.FakeInstance;
import co.zd.wb.plugin.database.SqlScriptMigration;
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

public class MySqlTableDoesNotExistAssertionTests
{
	 @Test public void applyForExistingTableFails() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 MigrationNotPossibleException,
		 MigrationFailedException,
		 SQLException
	 {
		 
		//
		// Setup
		//
		 
		MySqlProperties mySqlProperties = MySqlProperties.get();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		 
		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);
		 
		// Schema Loaded
		State schemaLoaded = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(schemaLoaded);
		 
		// Migrate -> created
		Migration tran1 = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(), null, created.getStateId());
		resource.getMigrations().add(tran1);
		 
		// Migrate created -> schemaLoaded
		Migration tran2 = new SqlScriptMigration(
			UUID.randomUUID(),
			created.getStateId(),
			schemaLoaded.getStateId(),
			MySqlElementFixtures.productCatalogueDatabase());
		resource.getMigrations().add(tran2);

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);
		 
		resource.migrate(new PrintStreamLogger(System.out), instance, schemaLoaded.getStateId());
		
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"ProductType");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = assertion.perform(instance);
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}

		//
		// Verify
		//

		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(false, "Table ProductType exists", response, "response");
		
	 }
	 
	 @Test public void applyForNonExistentTableSucceeds() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 MigrationNotPossibleException,
		 MigrationFailedException,
		 SQLException
	 {
		 
		 //
		 // Setup
		 //

		MySqlProperties mySqlProperties = MySqlProperties.get();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");

		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Migrate -> created
		Migration tran1 = new MySqlCreateDatabaseMigration(
			UUID.randomUUID(), null, created.getStateId());
		resource.getMigrations().add(tran1);
		
		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);
		 
		resource.migrate(new PrintStreamLogger(System.out), instance, created.getStateId());
		
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"ProductType");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = assertion.perform(instance);
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}

		//
		// Verify
		//

		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(true, "Table ProductType does not exist", response, "response");
		
	 }
	 
	 @Test public void applyForNonExistentDatabaseFails() throws SQLException
	 {
		// Setup
		MySqlProperties mySqlProperties = MySqlProperties.get();
	
		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);
		 
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"ProductType");
 
		// Execute
		AssertionResponse response = assertion.perform(instance);

		// Verify
		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(
			false, "Database " + databaseName + " does not exist",
			response, "response");
	 }
	 
	 @Test public void applyForNullInstanceFails()
	 {
		// Setup
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"TableName");
		
		// Execute
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

		// Verify
		Assert.assertEquals("caught.message", "instance cannot be null", caught.getMessage());
	 }
	 
	 @Test public void applyForIncorrectInstanceTypeFails()
	 {
		// Setup
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"TableName");
		
		FakeInstance instance = new FakeInstance();
		
		// Execute
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

		// Verify
		Assert.assertEquals("caught.message", "instance must be a MySqlDatabaseInstance", caught.getMessage());
	 }
}
