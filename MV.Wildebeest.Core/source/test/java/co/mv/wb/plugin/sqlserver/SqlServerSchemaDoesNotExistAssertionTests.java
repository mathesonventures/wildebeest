// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

package co.mv.wb.plugin.sqlserver;

import co.mv.wb.Asserts;
import co.mv.wb.fake.FakeInstance;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.plugin.database.DatabaseFixtureHelper;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import static org.junit.Assert.*;
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

		Migration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(UUID.randomUUID()));
		createDatabase.perform(instance);
		 
		Migration createSchema = new SqlServerCreateSchemaMigration(UUID.randomUUID(), null, null, "prd");
		createSchema.perform(instance);
		 
		SqlServerSchemaDoesNotExistAssertion schemaDoesNotExist = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		AssertionResponse response = null;
		
		try
		{
			// Execute
			response = schemaDoesNotExist.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}

		// Verify
		assertNotNull("response", response);
		Asserts.assertAssertionResponse(false, "Schema prd exists", response, "response");
	 }
	 
	 @Test public void applyForNonExistentSchemaSucceeds() throws
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
		 
		Migration createDatabase = new SqlServerCreateDatabaseMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(UUID.randomUUID()));
		createDatabase.perform(instance);
		
		SqlServerSchemaDoesNotExistAssertion schemaDoesNotExist = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		AssertionResponse response = null;
		
		try
		{
			// Execute
			response = schemaDoesNotExist.perform(instance);
		}
		finally
		{
			// Tear-Down
			SqlServerUtil.tryDropDatabase(instance);
		}

		// Verify
		assertNotNull("response", response);
		Asserts.assertAssertionResponse(true, "Schema prd does not exist", response, "response");
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
		 
		SqlServerSchemaDoesNotExistAssertion assertion = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
 
		// Execute
		AssertionResponse response = assertion.perform(instance);

		// Verify
		assertNotNull("response", response);
		Asserts.assertAssertionResponse(
			false, "Database " + databaseName + " does not exist",
			response, "response");
	 }
	 
	 @Test public void applyForNullInstanceFails()
	 {
		// Setup
		SqlServerSchemaDoesNotExistAssertion assertion = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
		
		// Execute and Verify
		try
		{
			AssertionResponse response = assertion.perform(null);
			
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals("e.message", "instance cannot be null", e.getMessage());
		}
	 }
	 
	 @Test public void applyForIncorrectInstanceTypeFails()
	 {
		// Setup
		SqlServerSchemaDoesNotExistAssertion assertion = new SqlServerSchemaDoesNotExistAssertion(
			UUID.randomUUID(),
			0,
			"prd");
		
		FakeInstance instance = new FakeInstance();
		
		// Execute
		try
		{
			AssertionResponse response = assertion.perform(instance);
			
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
			assertEquals("e.message", "instance must be a SqlServerDatabaseInstance", e.getMessage());
		}
	}
}
