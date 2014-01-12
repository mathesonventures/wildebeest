// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.plugin.base;

import co.mv.helium.testframework.Expect;
import co.zd.wb.AssertExtensions;
import co.zd.wb.Assertion;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.AssertionResult;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.JumpStateFailedException;
import co.zd.wb.State;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;
import co.zd.wb.PrintStreamLogger;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class BaseResourceTests
{
	
	//
	// assertState()
	//
	
	@Test public void assertStateWithNoAssertionsSuccessful() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
				
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");
		
		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);
		
		FakeInstance instance = new FakeInstance(state.getStateId());

		//
		// Execute
		//

		List<AssertionResult> results = resource.assertState(new PrintStreamLogger(System.out), instance);

		//
		// Assert Results
		//

		assertNotNull("results", results);
		assertEquals("results.size", 0, results.size());
			
	}
	
	@Test public void assertStateWithOneAssertionSuccessful() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
		
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");
		
		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);
		
		Assertion assertion1 = new FakeAssertion(
			UUID.randomUUID(),
			0,
			"Foo");
		state.getAssertions().add(assertion1);
		
		FakeInstance instance = new FakeInstance(state.getStateId());
		instance.setTag("Foo");

		//
		// Execute
		//

		List<AssertionResult> results = resource.assertState(new PrintStreamLogger(System.out), instance);

		//
		// Assert Results
		//

		assertNotNull("results", results);
		assertEquals("results.size", 1, results.size());
		AssertExtensions.assertAssertionResult(
			assertion1.getAssertionId(), true, "Tag is \"Foo\"", results.get(0), "results[0]");
	}
	
	@Test public void assertStateWithMultipleAssertionsSuccessful() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
		
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);

		UUID assertion1Id = UUID.randomUUID();
		state.getAssertions().add(new FakeAssertion(
			assertion1Id,
			0,
			"Foo"));
		
		UUID assertion2Id = UUID.randomUUID();
		state.getAssertions().add(new FakeAssertion(
			assertion2Id,
			1,
			"Bar"));
		
		FakeInstance instance = new FakeInstance(state.getStateId());
		instance.setTag("Foo");

		//
		// Execute
		//

		List<AssertionResult> results = resource.assertState(new PrintStreamLogger(System.out), instance);

		//
		// Assert Results
		//

		assertNotNull("results", results);
		assertEquals("results.size", 2, results.size());
		AssertExtensions.assertAssertionResult(
			assertion1Id, true, "Tag is \"Foo\"",
			results.get(0), "results[0]");
		AssertExtensions.assertAssertionResult(
			assertion2Id, false, "Tag expected to be \"Bar\" but was \"Foo\"",
			results.get(1), "results[1]");
	}

	/**
	 * Verifies that when the internal call to currentState() results in an IndeterminateStateException, assertState
	 * handles that properly.
	 */
	@Ignore @Test public void assertStateForResourceIndeterminateState()
	{
		throw new UnsupportedOperationException();
	}

	@Ignore @Test public void assertStateStateForFaultingAssertion()
	{
		throw new UnsupportedOperationException();
	}
	
	//
	// migrate()
	//

	@Test public void migrateFromNullToFirstState() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException
	{
		
		//
		// Fixture Setup
		//

		// The resource
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		// State 1
		UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state);
		
		// Migration 1
		UUID migration1Id = UUID.randomUUID();
		Migration tran1 = new FakeMigration(migration1Id, null, state1Id, "foo");
		resource.getMigrations().add(tran1);
		
		// Instance
		FakeInstance instance = new FakeInstance();
		
		//
		// Execute
		//
		
		resource.migrate(new PrintStreamLogger(System.out), instance, state1Id);
		
		//
		// Assert Results
		//
		
		assertEquals("instance.tag", "foo", instance.getTag());
		
	}
	
	@Test public void migrateFromNullToDeepState() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException
	{
		
		//
		// Fixture Setup
		//

		// The resource
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		// State 1
		State state1 = new ImmutableState(UUID.randomUUID(), "State 1");
		state1.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state1);

		// State 2
		State state2 = new ImmutableState(UUID.randomUUID(), "State 2");
		state2.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "bar"));
		resource.getStates().add(state2);

		// State 3
		State state3 = new ImmutableState(UUID.randomUUID(), "State 3");
		state3.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "bup"));
		resource.getStates().add(state3);
		
		// Migrate null -> State1
		Migration tran1 = new FakeMigration(UUID.randomUUID(), null, state1.getStateId(), "foo");
		resource.getMigrations().add(tran1);
		
		// Migrate State1 -> State2
		Migration tran2 = new FakeMigration(UUID.randomUUID(), state1.getStateId(), state2.getStateId(), "bar");
		resource.getMigrations().add(tran2);
		
		// Migrate State2 -> State3
		Migration tran3 = new FakeMigration(UUID.randomUUID(), state2.getStateId(), state3.getStateId(), "bup");
		resource.getMigrations().add(tran3);
		
		// Instance
		FakeInstance instance = new FakeInstance();
		
		//
		// Execute
		//
		
		resource.migrate(new PrintStreamLogger(System.out), instance, state3.getStateId());
		
		//
		// Assert Results
		//
		
		assertEquals("instance.tag", "bup", instance.getTag());
		
	}
	
	@Test public void migrateFromNullToDeepStateWithMultipleBranches() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException
	{
		
		//
		// Fixture Setup
		//

		// The resource
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		// State 1
		UUID state1Id = UUID.randomUUID();
		State state1 = new ImmutableState(state1Id);
		state1.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "state1"));
		resource.getStates().add(state1);

		// State B2
		UUID stateB2Id = UUID.randomUUID();
		State stateB2 = new ImmutableState(stateB2Id);
		stateB2.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "stateB2"));
		resource.getStates().add(stateB2);

		// State B3
		UUID stateB3Id = UUID.randomUUID();
		State stateB3 = new ImmutableState(stateB3Id);
		stateB3.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "stateB3"));
		resource.getStates().add(stateB3);

		// State C2
		UUID stateC2Id = UUID.randomUUID();
		State stateC2 = new ImmutableState(stateC2Id);
		stateC2.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "stateC2"));
		resource.getStates().add(stateC2);

		// State C3
		UUID stateC3Id = UUID.randomUUID();
		State stateC3 = new ImmutableState(stateC3Id);
		stateC3.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "stateC3"));
		resource.getStates().add(stateC3);
		
		// Migrate null -> State1
		UUID migration1Id = UUID.randomUUID();
		Migration migration1 = new FakeMigration(migration1Id, null, state1Id, "state1");
		resource.getMigrations().add(migration1);
		
		// Migrate State1 -> StateB2
		UUID migration2Id = UUID.randomUUID();
		Migration migration2 = new FakeMigration(migration2Id, state1Id, stateB2Id, "stateB2");
		resource.getMigrations().add(migration2);
		
		// Migrate StateB2 -> StateB3
		UUID migration3Id = UUID.randomUUID();
		Migration migration3 = new FakeMigration(migration3Id, stateB2Id, stateB3Id, "stateB3");
		resource.getMigrations().add(migration3);
		
		// Migrate State1 -> StateC2
		UUID migration4Id = UUID.randomUUID();
		Migration migration4 = new FakeMigration(migration4Id, state1Id, stateC2Id, "stateC2");
		resource.getMigrations().add(migration4);
		
		// Migrate StateC2 -> StateC3
		UUID migration5Id = UUID.randomUUID();
		Migration migration5 = new FakeMigration(migration5Id, stateC2Id, stateC3Id, "stateC3");
		resource.getMigrations().add(migration5);
		
		// Instance
		FakeInstance instance = new FakeInstance();
		
		//
		// Execute
		//
		
		resource.migrate(new PrintStreamLogger(System.out), instance, stateB3Id);
		
		//
		// Assert Results
		//
		
		assertEquals("instance.tag", "stateB3", instance.getTag());
		
	}
	
	@Ignore @Test public void migrateFromStateToState()
	{
		throw new UnsupportedOperationException();
	}
	
	@Ignore @Test public void migrateFromStateToDeepState()
	{
		throw new UnsupportedOperationException();
	}
	
	@Test public void migrateToSameState() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException
	{
		
		//
		// Fixture Setup
		//

		// The resource
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		// State 1
		UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state);
		
		// Migration 1
		UUID migration1Id = UUID.randomUUID();
		Migration tran1 = new FakeMigration(migration1Id, null, state1Id, "foo");
		resource.getMigrations().add(tran1);
		
		// Instance
		FakeInstance instance = new FakeInstance();
		
		PrintStreamLogger logger = new PrintStreamLogger(System.out);
		
		resource.migrate(logger, instance, state1Id);
		
		//
		// Execute
		//
		
		resource.migrate(logger, instance, state1Id);
		
		//
		// Assert Results
		//
		
		assertEquals("instance.tag", "foo", instance.getTag());
		
	}
	
	@Ignore @Test public void migrateFromStateToNull() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException
	{
		
		//
		// Setup
		//

		// The resource
		FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		// State 1
		UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state);
		
		// Migration 1
		UUID migration1Id = UUID.randomUUID();
		Migration tran1 = new FakeMigration(migration1Id, null, state1Id, "foo");
		resource.getMigrations().add(tran1);
		
		// Instance
		FakeInstance instance = new FakeInstance();

		resource.migrate(new PrintStreamLogger(System.out), instance, state1Id);

		//
		// Execute
		//
		
		resource.migrate(new PrintStreamLogger(System.out), instance, null);
		
		//
		// Verify
		//
		
		assertEquals("instance.tag", "foo", instance.getTag());
		
	}
	
	@Ignore @Test public void migrateFromDeepStateToNull()
	{
		throw new RuntimeException("not implemented");
	}

	@Ignore @Test public void migrateWithCircularDependencyFails()
	{
		throw  new UnsupportedOperationException();
	}
	
	//
	// jumpstate()
	//
	
	@Test public void jumpstateForAssertionFailThrows()
	{
		
		//
		// Setup
		//

		// Resource
		final FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");
		
		// State 1
		final UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "Foo"));
		resource.getStates().add(state);

		// Instance
		final FakeInstance instance = new FakeInstance();
		instance.setTag("Bar");
		
		//
		// Execute and Verify
		//

		new Expect<AssertionFailedException>()
		{
			@Override public void invoke() throws Throwable
			{
				resource.jumpstate(new PrintStreamLogger(System.out), instance, state1Id);
			}

			@Override public void verify(AssertionFailedException te)
			{
				assertEquals("te.assertionResults.size", 1, te.getAssertionResults().size());
				assertEquals("te.assertionResults[0].message", "Tag not as expected", te.getAssertionResults().get(0).getMessage());
			}
		}.perform();

	}
	
	@Test public void jumpstateForNonExistentStateThrows()
	{
		
		//
		// Setup
		//

		// Resource
		final FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		// Instance
		final FakeInstance instance = new FakeInstance();
		
		// Target State ID
		final UUID targetStateId = UUID.randomUUID();
		
		//
		// Execute and Verify
		//

		new Expect<JumpStateFailedException>()
		{
			@Override public void invoke() throws Throwable
			{
				resource.jumpstate(new PrintStreamLogger(System.out), instance, targetStateId);
			}

			@Override public void verify(JumpStateFailedException te)
			{
				assertEquals(
					"e.message",
					"This resource does not have a state with ID " + targetStateId.toString(),
					te.getMessage());
			}
		}.perform();
		
	}
	
	@Test public void jumpstateForExistentStateSucceeds() throws
		AssertionFailedException,
		JumpStateFailedException
	{
		
		//
		// Setup
		//

		// Resource
		final FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");
		
		// State 1
		final UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "Foo"));
		resource.getStates().add(state);

		// Instance
		final FakeInstance instance = new FakeInstance();
		instance.setTag("Foo");
		
		//
		// Execute
		//

		resource.jumpstate(new PrintStreamLogger(System.out), instance, state1Id);

		//
		// Verify
		//
		
		assertEquals("instance.tag", "Foo", instance.getTag());
		
	}
	
	@Test public void jumpstateForNullLoggerThrows()
	{
		// The resource
		final FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");
		
		// Instance
		final FakeInstance instance = new FakeInstance();

		new Expect<IllegalArgumentException>()
		{
			@Override public void invoke() throws Throwable
			{
				resource.jumpstate(null, instance, UUID.randomUUID());
			}

			@Override public void verify(IllegalArgumentException te)
			{
				assertEquals("e.message", "logger cannot be null", te.getMessage());
			}
		}.perform();
	}
	
	@Test public void jumpstateForNullInstanceThrows()
	{
		// The resource
		final FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");

		new Expect<IllegalArgumentException>()
		{
			@Override public void invoke() throws Throwable
			{
				resource.jumpstate(new PrintStreamLogger(System.out), null, UUID.randomUUID());
			}

			@Override public void verify(IllegalArgumentException te)
			{
				assertEquals("e.message", "instance cannot be null", te.getMessage());
			}
		}.perform();
	}
	
	@Test public void jumpstateForNullTargetStateIdThrows()
	{
		// The resource
		final FakeResource resource = new FakeResource(UUID.randomUUID(), "Resource");
		final FakeInstance instance = new FakeInstance();

		new Expect<IllegalArgumentException>()
		{
			@Override public void invoke() throws Throwable
			{
				resource.jumpstate(new PrintStreamLogger(System.out), instance, null);
			}

			@Override public void verify(IllegalArgumentException te)
			{
				assertEquals("e.message", "targetStateId cannot be null", te.getMessage());
			}
		}.perform();
	}
}
