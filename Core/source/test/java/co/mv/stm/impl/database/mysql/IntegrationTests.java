package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.SqlScriptTransition;
import co.mv.stm.AssertionFailedException;
import co.mv.stm.IndeterminateStateException;
import co.mv.stm.State;
import co.mv.stm.TransitionFailedException;
import co.mv.stm.TransitionNotPossibleException;
import co.mv.stm.impl.ImmutableState;
import java.sql.SQLException;
import java.util.UUID;
import org.junit.Test;

public class IntegrationTests
{
	@Test public void createDatabaseAddTableInsertRows() throws
		IndeterminateStateException,
		AssertionFailedException,
		TransitionNotPossibleException,
		TransitionFailedException,
		SQLException
	{
	
		//
		// Fixture Setup
		//

		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		// Resource
		MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		
		// State: Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);
		
		// State: Initial Schema
		State initialSchema = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(initialSchema);
		
		// State: Populated
		State populated = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(populated);
		
		// Transition: to Created
		resource.getTransitions().add(new MySqlCreateDatabaseTransition(
			UUID.randomUUID(),
			null,
			created.getStateId()));
		
		// Transition: Created to Initial Schema
		resource.getTransitions().add(new SqlScriptTransition(
			UUID.randomUUID(),
			created.getStateId(),
			initialSchema.getStateId(),
			MySqlElementFixtures.realmTypeRefCreateTableStatement()));
		
		// Transition: Initial Schema to Populated
		resource.getTransitions().add(new SqlScriptTransition(
			UUID.randomUUID(),
			initialSchema.getStateId(),
			populated.getStateId(),
			MySqlElementFixtures.realmTypeRefInsertUserBaseRow()));

		String databaseName = MySqlElementFixtures.databaseName("StmTest");

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName);
		
		//
		// Execute
		//
		
		try
		{
			resource.transition(instance, populated.getStateId());
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}
		
		//
		// Assert Results
		//
		
	}
}
