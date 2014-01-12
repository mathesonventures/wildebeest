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

package co.zd.wb.plugin.mysql;

import co.mv.helium.testframework.MySqlDatabaseFixture;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.State;
import co.zd.wb.plugin.base.ImmutableState;
import java.sql.SQLException;
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
	
	@Test public void currentStateForExistentDatabaseSucceds() throws IndeterminateStateException, SQLException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();

		UUID resourceId = UUID.randomUUID();
		UUID knownStateId = UUID.randomUUID();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm",
			"");

		try
		{
			database.setUp();
			
			MySqlStateHelper.setStateId(
				resourceId,
				database.getDataSource(),
				"wb_state",
				knownStateId);

			MySqlDatabaseResource resource = new MySqlDatabaseResource(
				resourceId,
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
	
	@Test public void currentStateForDatabaseWithUnknownStateIdDeclaredFails() throws SQLException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		UUID resourceId = UUID.randomUUID();
		UUID knownStateId = UUID.randomUUID();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm",
			"");
		database.setUp();

		MySqlStateHelper.setStateId(
			resourceId,
			database.getDataSource(),
			"wb_state",
			knownStateId);
		
		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			resourceId,
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