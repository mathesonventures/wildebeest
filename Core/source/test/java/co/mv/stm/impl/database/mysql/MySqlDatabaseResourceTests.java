package co.mv.stm.impl.database.mysql;

import co.zd.helium.fixture.MySqlDatabaseFixture;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.State;
import co.mv.stm.model.impl.ImmutableState;
import java.util.UUID;
import org.junit.Assert;
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
		
		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			UUID.randomUUID(),
			"Database");

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"non_existent_schema");

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
		
		UUID knownStateId = UUID.randomUUID();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm",
			MySqlElementFixtures.stmStateCreateTableStatement() +
			MySqlElementFixtures.stmStateInsertRow(knownStateId));
		
		try
		{
			database.setUp();

			MySqlDatabaseResource resource = new MySqlDatabaseResource(
				UUID.randomUUID(),
				"Database");
			resource.getStates().add(new ImmutableState(knownStateId));

			MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
				"127.0.0.1",
				3306,
				"root",
				"password",
				database.getDatabaseName());

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
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm",
			MySqlElementFixtures.stmStateCreateTableStatement() +
			MySqlElementFixtures.stmStateInsertRow(UUID.randomUUID()) +
			MySqlElementFixtures.stmStateInsertRow(UUID.randomUUID()));
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			UUID.randomUUID(),
			"Database");
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			database.getDatabaseName());

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
		
		UUID knownStateId = UUID.randomUUID();
		
		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm",
			MySqlElementFixtures.stmStateCreateTableStatement() +
			MySqlElementFixtures.stmStateInsertRow(knownStateId));
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource(
			UUID.randomUUID(),
			"Database");
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			database.getDatabaseName());

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
	
	@Test public void currentStateForDatabaseWithInvalidStateTableSchemaFaults()
	{
		throw new UnsupportedOperationException();
	}
}