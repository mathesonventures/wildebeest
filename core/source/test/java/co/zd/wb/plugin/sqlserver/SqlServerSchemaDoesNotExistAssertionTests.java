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

import co.zd.wb.AssertExtensions;
import co.zd.wb.plugin.base.FakeInstance;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.AssertionResponse;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;
import co.zd.wb.plugin.database.DatabaseFixtureHelper;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class SqlServerSchemaDoesNotExistAssertionTests
{
	 @Test public void applyForExistingSchemaFails() throws
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

		// Instance
		String databaseName = DatabaseFixtureHelper.databaseName();
		SqlServerDatabaseInstance instance = new SqlServerDatabaseInstance(
			properties.getHostName(),
			properties.hasInstanceName() ? properties.getInstanceName() : null,
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			null);

		// CreateDatabase
		Migration createDatabase = new SqlServerCreateDatabaseMigration(UUID.randomUUID(), null, UUID.randomUUID());
		createDatabase.perform(instance);
		 
		// CreateSchema
		Migration createSchema = new SqlServerCreateSchemaMigration(UUID.randomUUID(), null, null, "prd");
		createSchema.perform(instance);
		 
		SqlServerSchemaDoesNotExistAssertion schemaDoesNotExist = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = schemaDoesNotExist.apply(instance);
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);

			//
			// Assert Results
			//

			Assert.assertNotNull("response", response);
			AssertExtensions.assertAssertionResponse(false, "Schema prd exists", response, "response");
		}
		
	 }
	 
	 @Test public void applyForNonExistentSchemaSucceeds() throws
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
		 
		// CreateDatabase
		Migration createDatabase = new SqlServerCreateDatabaseMigration(UUID.randomUUID(), null, UUID.randomUUID());
		createDatabase.perform(instance);
		
		SqlServerSchemaDoesNotExistAssertion schemaDoesNotExist = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		//
		// Execute
		//
		
		AssertionResponse response = null;
		
		try
		{
			response = schemaDoesNotExist.apply(instance);
		}
		finally
		{
			SqlServerUtil.tryDropDatabase(instance);

			//
			// Assert Results
			//

			Assert.assertNotNull("response", response);
			AssertExtensions.assertAssertionResponse(true, "Schema prd does not exist", response, "response");
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
		 
		SqlServerSchemaDoesNotExistAssertion assertion = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
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
		 
		SqlServerSchemaDoesNotExistAssertion assertion = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
		
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
		 
		SqlServerSchemaDoesNotExistAssertion assertion = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
		
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