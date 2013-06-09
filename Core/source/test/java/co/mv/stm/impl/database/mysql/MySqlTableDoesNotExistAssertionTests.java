package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.AssertExtensions;
import co.mv.stm.impl.FakeResourceInstance;
import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.impl.database.SqlScriptTransition;
import co.mv.stm.AssertionFailedException;
import co.mv.stm.AssertionResponse;
import co.mv.stm.IndeterminateStateException;
import co.mv.stm.State;
import co.mv.stm.Transition;
import co.mv.stm.TransitionFailedException;
import co.mv.stm.TransitionNotPossibleException;
import co.mv.stm.impl.ImmutableState;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class MySqlTableDoesNotExistAssertionTests
{
	 @Test public void applyForExistingTableFails() throws
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
		 
		MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		 
		// Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);
		 
		// Schema Loaded
		State schemaLoaded = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(schemaLoaded);
		 
		// Transition -> created
		Transition tran1 = new MySqlCreateDatabaseTransition(
			UUID.randomUUID(), created.getStateId());
		resource.getTransitions().add(tran1);
		 
		// Transition created -> schemaLoaded
		Transition tran2 = new SqlScriptTransition(
			UUID.randomUUID(),
			created.getStateId(),
			schemaLoaded.getStateId(),
			MySqlElementFixtures.realmTypeRefCreateTableStatement());
		resource.getTransitions().add(tran2);

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test");
		 
		resource.transition(instance, schemaLoaded.getStateId());
		
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			"RealmTypeRef does not exists",
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
		AssertExtensions.assertAssertionResponse(false, "Table RealmTypeRef exists", response, "response");
		
	 }
	 
	 @Test public void applyForNonExistentTableSucceeds() throws
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

		 MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		 
		 // Created
		 State created = new ImmutableState(UUID.randomUUID());
		 resource.getStates().add(created);
		 
		 // Transition -> created
		 Transition tran1 = new MySqlCreateDatabaseTransition(
			 UUID.randomUUID(), created.getStateId());
		 resource.getTransitions().add(tran1);
		 
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test");
		 
		resource.transition(instance, created.getStateId());
		
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
			UUID.randomUUID(),
			"RealmTypeRef does not exist",
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
		AssertExtensions.assertAssertionResponse(true, "Table RealmTypeRef does not exist", response, "response");
		
	 }
	 
	 @Test public void applyForNonExistentDatabaseFails()
	 {
		 
		//
		// Fixture Setup
		//
		 
		MySqlProperties mySqlProperties = MySqlProperties.get();
		 
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test");
		 
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
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
		 
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
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
		 
		MySqlTableDoesNotExistAssertion assertion = new MySqlTableDoesNotExistAssertion(
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