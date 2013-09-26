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

package co.zd.wb.model.mysql;

import co.zd.helium.fixture.MySqlDatabaseFixture;
import co.zd.wb.model.IndeterminateStateException;
import co.zd.wb.model.State;
import co.zd.wb.model.base.ImmutableState;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MySqlDatabaseResourceTests
{
	public MySqlDatabaseResourceTests()
	{
	}
	
	//
	// currentState()
	//
	
	@Test public void currentStateForNonExistentDatabaseSucceds() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//

		MySqlProperties mySqlProperties = MySqlProperties.get();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			UUID.randomUUID(),
			"Database");

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"non_existent_schema",
			null);

		//
		// Execute
		//
		
		State state = resource.currentState(instance);
		
		//
		// Assert Results
		//
		
		Assert.assertEquals("state", null, state);
		
	}
	
	@Test public void currentStateForExistentDatabaseSucceds() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();

		UUID knownStateId = UUID.randomUUID();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm",
			MySqlElementFixtures.stateCreateTableStatement() +
			MySqlElementFixtures.stateInsertRow(knownStateId));
		
		try
		{
			database.setUp();

			MySqlDatabaseResource resource = new MySqlDatabaseResource(
				UUID.randomUUID(),
				"Database");
			resource.getStates().add(new ImmutableState(knownStateId));

			MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
				mySqlProperties.getHostName(),
				mySqlProperties.getPort(),
				mySqlProperties.getUsername(),
				mySqlProperties.getPassword(),
				database.getDatabaseName(),
			null);

			//
			// Execute
			//

			State state = resource.currentState(instance);

			//
			// Assert Results
			//

			Assert.assertEquals("state.stateId", knownStateId, state.getStateId());
		}
		finally
		{
		
			//
			// Fixture Tear-Down
			//

			database.tearDown();
			
		}
	}
	
	@Test public void currentStateForDatabaseWithMultipleStateRowsFails()
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm",
			MySqlElementFixtures.stateCreateTableStatement() +
			MySqlElementFixtures.stateInsertRow(UUID.randomUUID()) +
			MySqlElementFixtures.stateInsertRow(UUID.randomUUID()));
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			UUID.randomUUID(),
			"Database");
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			database.getDatabaseName(),
			null);

		//
		// Execute
		//
		
		IndeterminateStateException caught = null;
		
		try
		{
			resource.currentState(instance);
			
			Assert.fail("IndeterminateStateException expected");
		}
		catch(IndeterminateStateException e)
		{
			caught = e;
		}
		
		//
		// Assert Results
		//
		
		Assert.assertTrue("exception message", caught.getMessage().startsWith("Multiple rows found"));
		
		//
		// Fixture Tear-Down
		//
		
		database.tearDown();
		
	}
	
	@Test public void currentStateForDatabaseWithUnknownStateIdDeclaredFails()
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		UUID knownStateId = UUID.randomUUID();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm",
			MySqlElementFixtures.stateCreateTableStatement() +
			MySqlElementFixtures.stateInsertRow(knownStateId));
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			UUID.randomUUID(),
			"Database");
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			database.getDatabaseName(),
			null);

		//
		// Execute
		//
		
		IndeterminateStateException caught = null;
		try
		{
			resource.currentState(instance);
			
			Assert.fail("IndeterminateStateException expected");
		}
		catch(IndeterminateStateException e)
		{
			caught = e;
		}
		
		//
		// Assert Results
		//
		
		Assert.assertTrue(
			"exception message",
			caught.getMessage().startsWith("The resource is declared to be in state"));
		
		//
		// Fixture Tear-Down
		//
		
		database.tearDown();
		
	}
	
	@Ignore @Test public void currentStateForDatabaseWithInvalidStateTableSchemaFaults()
	{
		throw new UnsupportedOperationException();
	}
}