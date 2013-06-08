package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.FakeResourceInstance;
import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.impl.database.SqlScriptTransition;
import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.State;
import co.mv.stm.model.Transition;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionNotPossibleException;
import co.mv.stm.model.TransitionType;
import co.mv.stm.model.impl.ImmutableState;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class MySqlTableExistsAssertionTests
{
	 @Test public void applyForExistingTableSucceeds() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 TransitionNotPossibleException,
		 TransitionFailedException,
		 SQLException
	 {
		 
		 //
		 // Fixture Setup
		 //
		 
		 MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		 
		 // Created
		 State created = new ImmutableState(UUID.randomUUID());
		 resource.getStates().add(created);
		 
		 // Schema Loaded
		 State schemaLoaded = new ImmutableState(UUID.randomUUID());
		 resource.getStates().add(schemaLoaded);
		 
		 // Transition -> created
		 Transition tran1 = new MySqlCreateDatabaseTransition(
			 UUID.randomUUID(), TransitionType.DatabaseSqlScript, created.getStateId());
		 resource.getTransitions().add(tran1);
		 
		 // Transition created -> schemaLoaded
		 Transition tran2 = new SqlScriptTransition(
			 UUID.randomUUID(),
			 created.getStateId(),
			 schemaLoaded.getStateId(),
			 MySqlElementFixtures.realmTypeRefCreateTableStatement());
		 resource.getTransitions().add(tran2);

		 MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			 "127.0.0.1", 3306, "root", "password", "stm_test");
		 
		resource.transition(instance, schemaLoaded.getStateId());
		
		MySqlTableExistsAssertion assertion = new MySqlTableExistsAssertion(
			UUID.randomUUID(),
			"RealmTypeRef Exists",
			0,
			"RealmTypeRef");
 
		//
		// Execute
		//
		
		AssertionResponse response = assertion.apply(instance);
		
		DatabaseHelper.execute(instance.getInfoDataSource(), "DROP DATABASE stm_test;");

		//
		// Assert Results
		//

		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(true, "Table RealmTypeRef exists", response, "response");
		
	 }
	 
	 @Test public void applyForNonExistentTableFails() throws
		 IndeterminateStateException,
		 AssertionFailedException,
		 TransitionNotPossibleException,
		 TransitionFailedException,
		 SQLException
	 {
		 
		 //
		 // Fixture Setup
		 //
		 
		 MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		 
		 // Created
		 State created = new ImmutableState(UUID.randomUUID());
		 resource.getStates().add(created);
		 
		 // Transition -> created
		 Transition tran1 = new MySqlCreateDatabaseTransition(
			 UUID.randomUUID(), TransitionType.DatabaseSqlScript, created.getStateId());
		 resource.getTransitions().add(tran1);
		 
		 MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			 "127.0.0.1", 3306, "root", "password", "stm_test");
		 
		resource.transition(instance, created.getStateId());
		
		MySqlTableExistsAssertion assertion = new MySqlTableExistsAssertion(
			UUID.randomUUID(),
			"RealmTypeRef Exists",
			0,
			"RealmTypeRef");
 
		//
		// Execute
		//
		
		AssertionResponse response = assertion.apply(instance);
		
		DatabaseHelper.execute(instance.getInfoDataSource(), "DROP DATABASE stm_test;");

		//
		// Assert Results
		//

		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(false, "Table RealmTypeRef does not exist", response, "response");
		
	 }
	 
	 @Test public void applyForNonExistentDatabaseFails()
	 {
		 
		 //
		 // Fixture Setup
		 //
		 
		 MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			 "127.0.0.1", 3306, "root", "password", "stm_test");
		 
		MySqlTableExistsAssertion assertion = new MySqlTableExistsAssertion(
			UUID.randomUUID(),
			"RealmTypeRef Exists",
			0,
			"RealmTypeRef");
 
		//
		// Execute
		//
		
		AssertionResponse response = assertion.apply(instance);

		//
		// Assert Results
		//

		Assert.assertNotNull("response", response);
		AssertExtensions.assertAssertionResponse(false, "Database stm_test does not exist", response, "response");
		
	 }
	 
	 @Test public void applyForNullInstanceFails()
	 {
		 
		//
		// Fixture Setup
		//
		 
		MySqlTableExistsAssertion assertion = new MySqlTableExistsAssertion(
			UUID.randomUUID(),
			"Database does not exist",
			0,
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
		 
		MySqlTableExistsAssertion assertion = new MySqlTableExistsAssertion(
			UUID.randomUUID(),
			"Database does not exist",
			0,
			"TableName");
		
		FakeResourceInstance instance = new FakeResourceInstance();
		
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

		Assert.assertEquals("caught.message", "instance must be a MySqlDatabaseResourceInstance", caught.getMessage());
		
	 }
}