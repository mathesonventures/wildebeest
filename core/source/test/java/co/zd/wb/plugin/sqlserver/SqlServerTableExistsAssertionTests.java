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

package co.zd.wb.plugin.sqlserver;

import co.zd.wb.plugin.sqlserver.SqlServerCreateDatabaseMigration;
import co.zd.wb.plugin.sqlserver.SqlServerTableExistsAssertion;
import co.zd.wb.plugin.sqlserver.SqlServerDatabaseInstance;
import co.zd.wb.plugin.sqlserver.SqlServerDatabaseResource;
import co.zd.wb.AssertExtensions;
import co.zd.wb.plugin.base.FakeInstance;
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
import co.zd.wb.service.PrintStreamLogger;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class SqlServerTableExistsAssertionTests
{
	 @Test public void applyForExistingTableSucceeds() throws
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
		 
		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);

		// Schema Loaded
		State schemaLoaded = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(schemaLoaded);
		 
		// Migrate -> created
		Migration migration1 = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(), null, created.getStateId());
		resource.getMigrations().add(migration1);
		 
		// Migrate created -> schemaLoaded
		Migration migration2 = new SqlScriptMigration(
			UUID.randomUUID(),
			created.getStateId(),
			schemaLoaded.getStateId(),
			SqlServerElementFixtures.productCatalogueDatabase());
		resource.getMigrations().add(migration2);

		String databaseName = DatabaseFixtureHelper.databaseName();
		
		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			null);
		 
		resource.migrate(new PrintStreamLogger(System.out), instance, schemaLoaded.getStateId());
		
		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			"ProductType Exists",
			0,
			"dbo",
			"ProductType");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = assertion.apply(instance);
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);

			//
			// Assert Results
			//

			Assert.assertNotNull("response", response);
			AssertExtensions.assertAssertionResponse(true, "Table ProductType exists", response, "response");
		}
		
	 }
	 
	 @Test public void applyForNonExistentTableFails() throws
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
		 
		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);
		 
		// Migrate -> created
		Migration migration1 = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(), null, created.getStateId());
		resource.getMigrations().add(migration1);

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
		
		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			"ProductType Exists",
			0,
			"dbo",
			"ProductType");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = assertion.apply(instance);
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);

			//
			// Assert Results
			//

			Assert.assertNotNull("response", response);
			AssertExtensions.assertAssertionResponse(false, "Table ProductType does not exist", response, "response");
		}

	 }
	 
	 @Test public void applyForNonExistentDatabaseFails()
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
		 
		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			"ProductType Exists",
			0,
			"dbo",
			"ProductType");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = assertion.apply(instance);
		}
		finally
		{

			//
			// Assert Results
			//

			Assert.assertNotNull("response", response);
			AssertExtensions.assertAssertionResponse(
				false, "Database " + databaseName + " does not exist",
				response, "response");

		}

	 }
	 
	 @Test public void applyForNullInstanceFails()
	 {
		 
		//
		// Fixture Setup
		//
		 
		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			"Database does not exist",
			0,
			"dbo",
			"TableName");
		
		//
		// Execute
		//
		
		IllegalArgumentException caught = null;
		
		try
		{
			AssertionResponse response = assertion.apply(null);
			
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
		 
		SqlServerTableExistsAssertion assertion = new SqlServerTableExistsAssertion(
			UUID.randomUUID(),
			"Database does not exist",
			0,
			"dbo",
			"TableName");
		
		FakeInstance instance = new FakeInstance();
		
		//
		// Execute
		//
		
		IllegalArgumentException caught = null;
		
		try
		{
			AssertionResponse response = assertion.apply(instance);
			
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