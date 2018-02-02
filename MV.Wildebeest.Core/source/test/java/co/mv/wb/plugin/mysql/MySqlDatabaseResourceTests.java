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

package co.mv.wb.plugin.mysql;

import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Resource;
import co.mv.wb.ResourceHelper;
import co.mv.wb.State;
import co.mv.wb.WildebeestFactory;
import co.mv.wb.impl.ResourceHelperImpl;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

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
		// Setup
		ResourceHelper resourceHelper = new ResourceHelperImpl();

		MySqlProperties mySqlProperties = MySqlProperties.get();

		MySqlDatabaseResourcePlugin resourcePlugin = new MySqlDatabaseResourcePlugin(
			resourceHelper);

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			WildebeestFactory.MySqlDatabase,
			"Database",
			Optional.empty());

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"non_existent_schema",
			null);

		// Execute
		State state = resourcePlugin.currentState(
			resource,
			instance);
		
		// Verify
		Assert.assertEquals("state", null, state);
	}
	
	@Test public void currentStateForExistentDatabaseSucceds() throws IndeterminateStateException, SQLException
	{
		// Setup
		ResourceHelper resourceHelper = new ResourceHelperImpl();

		MySqlProperties mySqlProperties = MySqlProperties.get();

		UUID resourceId = UUID.randomUUID();
		UUID knownStateId = UUID.randomUUID();

		String databaseName = MySqlUtil.createDatabase(
			mySqlProperties,
			"stm",
			"");

		try
		{
			MySqlStateHelper.setStateId(
				resourceId,
				MySqlUtil.getDataSource(mySqlProperties, databaseName),
				"wb_state",
				knownStateId);

			MySqlDatabaseResourcePlugin resourcePlugin = new MySqlDatabaseResourcePlugin(
				resourceHelper);

			Resource resource = new ResourceImpl(
				resourceId,
				WildebeestFactory.MySqlDatabase,
				"Database",
				Optional.empty());

			resource.getStates().add(new ImmutableState(knownStateId));

			MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
				mySqlProperties.getHostName(),
				mySqlProperties.getPort(),
				mySqlProperties.getUsername(),
				mySqlProperties.getPassword(),
				databaseName,
			null);

			// Execute
			State state = resourcePlugin.currentState(resource, instance);

			// Verify
			Assert.assertEquals("state.stateId", knownStateId, state.getStateId());
		}
		finally
		{
			// Tear-Down
			MySqlUtil.dropDatabase(mySqlProperties, databaseName);
		}
	}
	
	@Test public void currentStateForDatabaseWithUnknownStateIdDeclaredFails() throws SQLException
	{
		// Setup
		ResourceHelper resourceHelper = new ResourceHelperImpl();

		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		UUID resourceId = UUID.randomUUID();
		UUID knownStateId = UUID.randomUUID();
		
		String databaseName = MySqlUtil.createDatabase(mySqlProperties, "stm", "");

		MySqlStateHelper.setStateId(
			resourceId,
			MySqlUtil.getDataSource(mySqlProperties, databaseName),
			"wb_state",
			knownStateId);
		
		MySqlDatabaseResourcePlugin resourcePlugin = new MySqlDatabaseResourcePlugin(
			resourceHelper);

		Resource resource = new ResourceImpl(
			resourceId,
			WildebeestFactory.MySqlDatabase,
			"Database",
			Optional.empty());

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);

		// Execute and Verify
		try
		{
			resourcePlugin.currentState(resource, instance);
			
			Assert.fail("IndeterminateStateException expected");
		}
		catch(IndeterminateStateException e)
		{
			Assert.assertTrue(
				"exception message",
				e.getMessage().startsWith("The resource is declared to be in state"));
		}
		
		// Tear-Down
		MySqlUtil.dropDatabase(mySqlProperties, databaseName);
	}
	
	@Ignore @Test public void currentStateForDatabaseWithInvalidStateTableSchemaFaults()
	{
		throw new UnsupportedOperationException();
	}
}
