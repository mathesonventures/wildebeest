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

package co.mv.wb.impl;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResult;
import co.mv.wb.Asserts;
import co.mv.wb.ExpectException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import co.mv.wb.plugin.fake.SetTagMigration;
import co.mv.wb.plugin.fake.SetTagMigrationPlugin;
import co.mv.wb.plugin.fake.TagAssertion;
import org.junit.Ignore;
import org.junit.Test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceHelperUnitTests
{
	
	//
	// assertState()
	//
	
	@Test public void assertState_noAssertions_succeeds() throws IndeterminateStateException
	{
		// Setup
		PrintStream output = System.out;

		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		State state = new ImmutableState(UUID.randomUUID());

		resource.getStates().add(state);
		
		FakeInstance instance = new FakeInstance(state.getStateId().toString());

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			resource,
			instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 0, results.size());
	}
	
	@Test public void assertState_oneAssertion_succeeds() throws IndeterminateStateException
	{
		// Setup
		PrintStream output = System.out;
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);
		
		Assertion assertion1 = new TagAssertion(
			UUID.randomUUID(),
			0,
			"Foo");
		state.getAssertions().add(assertion1);
		
		FakeInstance instance = new FakeInstance(state.getStateId().toString());
		instance.setTag("Foo");

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			resource,
			instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 1, results.size());
		Asserts.assertAssertionResult(
			assertion1.getAssertionId(), true, "Tag is \"Foo\"", results.get(0), "results[0]");
	}
	
	@Test public void assertState_multipleAssertions_succeeds() throws IndeterminateStateException
	{
		// Setup
		PrintStream output = System.out;
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);

		UUID assertion1Id = UUID.randomUUID();
		state.getAssertions().add(new TagAssertion(
			assertion1Id,
			0,
			"Foo"));
		
		UUID assertion2Id = UUID.randomUUID();
		state.getAssertions().add(new TagAssertion(
			assertion2Id,
			1,
			"Bar"));
		
		FakeInstance instance = new FakeInstance(state.getStateId().toString());
		instance.setTag("Foo");

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			resource,
			instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 2, results.size());
		Asserts.assertAssertionResult(
			assertion1Id, true, "Tag is \"Foo\"",
			results.get(0), "results[0]");
		Asserts.assertAssertionResult(
			assertion2Id, false, "Tag expected to be \"Bar\" but was \"Foo\"",
			results.get(1), "results[1]");
	}

	/**
	 * Verifies that when the internal call to currentState() results in an IndeterminateStateException, assertState
	 * handles that properly.
	 */
	@Ignore @Test public void assertState_resourceIndeterminateState_throws()
	{
		throw new UnsupportedOperationException();
	}

	@Ignore @Test public void assertState_faultingAssertion_throws()
	{
		throw new UnsupportedOperationException();
	}
	
	//
	// migrate()
	//

	@Test public void migrate_nonExistentToFirstState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		// Setup
		PrintStream output = System.out;
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state);
		
		UUID migration1Id = UUID.randomUUID();
		Migration tran1 = new SetTagMigration(migration1Id, Optional.empty(), Optional.of(state1Id.toString()), "foo");
		resource.getMigrations().add(tran1);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SetTagMigration.class, new SetTagMigrationPlugin());
		
		FakeInstance instance = new FakeInstance();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		// Execute
		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(state1Id.toString()));
		
		// Verify
		assertEquals("instance.tag", "foo", instance.getTag());
		
	}
	
	@Test public void migrate_nonExistentToDeepState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// The resource
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		// State 1
		State state1 = new ImmutableState(UUID.randomUUID(), Optional.of("State 1"));
		state1.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state1);

		// State 2
		State state2 = new ImmutableState(UUID.randomUUID(), Optional.of("State 2"));
		state2.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "bar"));
		resource.getStates().add(state2);

		// State 3
		State state3 = new ImmutableState(UUID.randomUUID(), Optional.of("State 3"));
		state3.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "bup"));
		resource.getStates().add(state3);
		
		// Migrate null -> State1
		Migration tran1 = new SetTagMigration(
			UUID.randomUUID(),
			Optional.empty(),
			Optional.of(state1.getStateId().toString()),
			"foo");
		resource.getMigrations().add(tran1);
		
		// Migrate State1 -> State2
		Migration tran2 = new SetTagMigration(
			UUID.randomUUID(),
			Optional.of(state1.getStateId().toString()),
			Optional.of(state2.getStateId().toString()),
			"bar");

		resource.getMigrations().add(tran2);
		
		// Migrate State2 -> State3
		Migration tran3 = new SetTagMigration(
			UUID.randomUUID(),
			Optional.of(state2.getStateId().toString()),
			Optional.of(state3.getStateId().toString()),
			"bup");
		resource.getMigrations().add(tran3);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SetTagMigration.class, new SetTagMigrationPlugin());

		// Instance
		FakeInstance instance = new FakeInstance();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		//
		// Execute
		//

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(state3.getStateId().toString()));
		
		//
		// Verify
		//
		
		assertEquals("instance.tag", "bup", instance.getTag());
		
	}
	
	@Test public void migrate_nonExistentToDeepStateWithMultipleBranches_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// The resource
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

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
		Migration migration1 = new SetTagMigration(
			migration1Id,
			Optional.empty(),
			Optional.of(state1Id.toString()),
			"state1");
		resource.getMigrations().add(migration1);
		
		// Migrate State1 -> StateB2
		UUID migration2Id = UUID.randomUUID();
		Migration migration2 = new SetTagMigration(
			migration2Id,
			Optional.of(state1Id.toString()),
			Optional.of(stateB2Id.toString()),
			"stateB2");
		resource.getMigrations().add(migration2);
		
		// Migrate StateB2 -> StateB3
		UUID migration3Id = UUID.randomUUID();
		Migration migration3 = new SetTagMigration(
			migration3Id,
			Optional.of(stateB2Id.toString()),
			Optional.of(stateB3Id.toString()),
			"stateB3");
		resource.getMigrations().add(migration3);
		
		// Migrate State1 -> StateC2
		UUID migration4Id = UUID.randomUUID();
		Migration migration4 = new SetTagMigration(
			migration4Id,
			Optional.of(state1Id.toString()),
			Optional.of(stateC2Id.toString()),
			"stateC2");
		resource.getMigrations().add(migration4);
		
		// Migrate StateC2 -> StateC3
		UUID migration5Id = UUID.randomUUID();
		Migration migration5 = new SetTagMigration(
			migration5Id,
			Optional.of(stateC2Id.toString()),
			Optional.of(stateC3Id.toString()),
			"stateC3");
		resource.getMigrations().add(migration5);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SetTagMigration.class, new SetTagMigrationPlugin());

		// Instance
		FakeInstance instance = new FakeInstance();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		//
		// Execute
		//

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(stateB3Id.toString()));
		
		//
		// Verify
		//
		
		assertEquals("instance.tag", "stateB3", instance.getTag());
		
	}
	
	@Ignore @Test public void migrate_stateToState_succeeds()
	{
		throw new UnsupportedOperationException();
	}
	
	@Ignore @Test public void migrate_stateToDeepState_succeeds()
	{
		throw new UnsupportedOperationException();
	}
	
	@Test public void migrate_toSameState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// The resource
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		// State 1
		UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state);
		
		// Migration 1
		UUID migration1Id = UUID.randomUUID();
		Migration tran1 = new SetTagMigration(
			migration1Id,
			Optional.empty(),
			Optional.of(state1Id.toString()),
			"foo");
		resource.getMigrations().add(tran1);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SetTagMigration.class, new SetTagMigrationPlugin());

		// Instance
		FakeInstance instance = new FakeInstance();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(state1Id.toString()));
		
		//
		// Execute
		//

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(state1Id.toString()));
		
		//
		// Verify
		//
		
		assertEquals("instance.tag", "foo", instance.getTag());
		
	}
	
	@Ignore @Test public void migrate_stateToNonExistent_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// The resource
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		// State 1
		UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "foo"));
		resource.getStates().add(state);
		
		// Migration 1
		UUID migration1Id = UUID.randomUUID();
		Migration tran1 = new SetTagMigration(
			migration1Id,
			Optional.empty(),
			Optional.of(state1Id.toString()),
			"foo");
		resource.getMigrations().add(tran1);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(SetTagMigration.class, new SetTagMigrationPlugin());

		// Instance
		FakeInstance instance = new FakeInstance();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.of(state1Id.toString()));

		//
		// Execute
		//

		wildebeestApi.migrate(
			resource,
			instance,
			Optional.empty());

		//
		// Verify
		//
		
		assertEquals("instance.tag", "foo", instance.getTag());
		
	}
	
	@Ignore @Test public void migrate_deepStateToNonExistent_succeeds()
	{
		throw new RuntimeException("not implemented");
	}

	@Ignore @Test public void migrate_circularDependency_throws()
	{
		throw  new UnsupportedOperationException();
	}
	
	//
	// jumpstate()
	//
	
	@Test public void jumpstate_assertionFail_throws()
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// Resource
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		final Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());
		
		// State 1
		final UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "Foo"));
		resource.getStates().add(state);

		// Instance
		final FakeInstance instance = new FakeInstance();
		instance.setTag("Bar");

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		//
		// Execute and Verify
		//

		new ExpectException(AssertionFailedException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.jumpstate(
					resource,
					instance,
					state1Id.toString());
			}

			@Override public void verify(Exception e)
			{
				AssertionFailedException te = (AssertionFailedException)e;
				
				assertEquals("te.assertionResults.size", 1, te.getAssertionResults().size());
				assertEquals("te.assertionResults[0].message", "Tag not as expected", te.getAssertionResults().get(0).getMessage());
			}
		}.perform();

	}
	
	@Test public void jumpstate_nonExistentState_throws()
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// Resource
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		final Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());

		// Instance
		final FakeInstance instance = new FakeInstance();
		
		// Target State ID
		final UUID targetStateId = UUID.randomUUID();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		//
		// Execute and Verify
		//

		new ExpectException(JumpStateFailedException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.jumpstate(
					resource,
					instance,
					targetStateId.toString());
			}

			@Override public void verify(Exception e)
			{
				JumpStateFailedException te = (JumpStateFailedException)e;

				assertEquals(
					"e.message",
					"This resource does not have a state with ID " + targetStateId.toString(),
					te.getMessage());
			}
		}.perform();
		
	}
	
	@Test public void jumpstate_existentState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		JumpStateFailedException,
		UnknownStateSpecifiedException
	{
		
		//
		// Setup
		//

		PrintStream output = System.out;

		// Resource
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			Optional.empty());
		
		// State 1
		final UUID state1Id = UUID.randomUUID();
		State state = new ImmutableState(state1Id);
		state.getAssertions().add(new TagAssertion(UUID.randomUUID(), 0, "Foo"));
		resource.getStates().add(state);

		// Instance
		final FakeInstance instance = new FakeInstance();
		instance.setTag("Foo");

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		//
		// Execute
		//

		wildebeestApi.jumpstate(
			resource,
			instance,
			state1Id.toString());

		//
		// Verify
		//
		
		assertEquals("instance.tag", "Foo", instance.getTag());
		
	}

}


