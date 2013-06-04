package co.mv.stm.impl;

import co.mv.stm.impl.database.mysql.MySqlDatabaseResource;
import co.mv.stm.impl.database.mysql.MySqlDatabaseResourceInstance;
import co.mv.stm.impl.database.mysql.MySqlElementFixtures;
import co.mv.stm.model.AssertionResult;
import co.mv.stm.model.AssertionType;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.State;
import co.mv.stm.model.impl.ImmutableState;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class BaseResourceTests
{
	
	//
	// assertState()
	//
	
	@Test public void assertStateWithNoAssertionsSuccessful() throws IndeterminateStateException
	{
		throw new UnsupportedOperationException();
	}
	
	@Test public void assertStateWithOneAssertionSuccessful() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
		
		UUID knownStateId = UUID.randomUUID();
		
		State state = new ImmutableState(knownStateId);
		FakeResource resource = new FakeResource(
			UUID.randomUUID(),
			"Resource",
			state);
		UUID assertionId = UUID.randomUUID();
		state.getAssertions().add(new FakeAssertion(
			assertionId,
			"Fake1",
			0,
			AssertionType.DatabaseRowExists,
			true,
			"Fake1 passed"));
		resource.getStates().add(state);
		
		FakeResourceInstance instance = new FakeResourceInstance();

		//
		// Execute
		//

		List<AssertionResult> results = resource.assertState(instance);

		//
		// Assert Results
		//

		Assert.assertNotNull("results", results);
		Assert.assertEquals("results.size", 1, results.size());
		Assert.assertEquals("results[0].assertionId", assertionId, results.get(0).getAssertionId());
		Assert.assertEquals("results[0].result", true, results.get(0).getResult());
		Assert.assertEquals("results[0].message", "Fake1 passed", results.get(0).getMessage());
			
	}
	
	@Test public void assertStateWithMultipleAssertionsSuccessful() throws IndeterminateStateException
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Verifies that when the internal call to currentState() results in an IndeterminateStateException, assertState
	 * handles that properly.
	 */
	@Test public void assertStateForResourceIndeterminateState()
	{
		throw new UnsupportedOperationException();
	}

	@Test public void assertStateStateForFaultingAssertion()
	{
		throw new UnsupportedOperationException();
	}
	
	//
	// transitionTo()
	//

}