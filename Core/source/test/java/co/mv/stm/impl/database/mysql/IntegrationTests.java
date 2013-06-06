package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.SqlScriptTransition;
import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.State;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionNotPossibleException;
import co.mv.stm.model.TransitionType;
import co.mv.stm.model.impl.ImmutableState;
import java.util.UUID;
import org.junit.Test;

public class IntegrationTests
{
	@Test public void createDatabaseeAddTableInsertRows() throws
		IndeterminateStateException,
		AssertionFailedException,
		TransitionNotPossibleException,
		TransitionFailedException
	{
	
		//
		// Fixture Setup
		//

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
			TransitionType.DatabaseSqlScript,
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

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm_test");
		
		//
		// Execute
		//
		
		resource.transition(instance, populated.getStateId());
		
		//
		// Assert Results
		//
		
		
	}
}
