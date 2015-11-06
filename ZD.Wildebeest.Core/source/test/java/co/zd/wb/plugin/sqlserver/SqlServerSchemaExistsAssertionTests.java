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
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;
import co.zd.wb.plugin.database.DatabaseFixtureHelper;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class SqlServerSchemaExistsAssertionTests
{
	 @Test public void applyForExistingSchemaSucceeds() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 MigrationNotPossibleException,
		 MigrationFailedException,
		 SQLException
	 {
		// Setup
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

		Migration createDatabase = new SqlServerCreateDatabaseMigration(UUID.randomUUID(), null, UUID.randomUUID());
		createDatabase.perform(instance);
		 
		Migration createSchema = new SqlServerCreateSchemaMigration(UUID.randomUUID(), null, null, "prd");
		createSchema.perform(instance);
		 
		SqlServerSchemaExistsAssertion schemaExists = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		AssertionResponse response = null;
		
		try
		{
			// Execute
			response = schemaExists.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}

		// Verify
		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(true, "Schema prd exists", response, "response");
	 }
	 
	 @Test public void applyForNonExistentSchemaFails() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 MigrationNotPossibleException,
		 MigrationFailedException,
		 SQLException
	 {
		// Setup
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
		 
		Migration createDatabase = new SqlServerCreateDatabaseMigration(UUID.randomUUID(), null, UUID.randomUUID());
		createDatabase.perform(instance);
		
		SqlServerSchemaExistsAssertion schemaExists = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		AssertionResponse response = null;
		
		try
		{
			// Execute
			response = schemaExists.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}

		// Verify
		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(false, "Schema prd does not exist", response, "response");
	 }
	 
	 @Test public void applyForNonExistentDatabaseFails()
	 {
		// Setup
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
		 
		SqlServerSchemaExistsAssertion assertion = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
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
		SqlServerSchemaExistsAssertion assertion = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");
		
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
		SqlServerSchemaExistsAssertion assertion = new SqlServerSchemaExistsAssertion(
			UUID.randomUUID(),
			0,
			"prd");
		
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
		Assert.assertEquals("caught.message", "instance must be a SqlServerDatabaseInstance", caught.getMessage());
	}
}
