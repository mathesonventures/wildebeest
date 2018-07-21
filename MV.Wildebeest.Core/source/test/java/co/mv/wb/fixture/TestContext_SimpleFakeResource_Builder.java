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

package co.mv.wb.fixture;

import co.mv.wb.Migration;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentException;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.SetTagMigration;
import co.mv.wb.plugin.fake.TagAssertion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A test context builder that builds a FakeResource and FakeInstance with the desired configuration.
 *
 * @since 4.0
 */
public class TestContext_SimpleFakeResource_Builder
{
	private List<State> states;
	private List<Migration> migrations;
	private String defaultTarget;

	private UUID initialStateId;
	private String initialTag;

	private TestContext_SimpleFakeResource_Builder()
	{
		this.defaultTarget = null;
		this.states = Arrays.asList();
		this.migrations = Arrays.asList();

		this.initialStateId = null;
		this.initialTag = null;
	}

	public static TestContext_SimpleFakeResource_Builder create()
	{
		return new TestContext_SimpleFakeResource_Builder();
	}

	public TestContext_SimpleFakeResource_Builder withFooBarStatesAndMigrations()
	{
		State fooState = new ImmutableState(
			UUID.randomUUID(),
			"foo");

		State barState = new ImmutableState(
			UUID.randomUUID(),
			"bar");

		this.states = Arrays.asList(fooState, barState);

		this.migrations = Arrays.asList(

			// Migrate non-existant to Foo
			new SetTagMigration(
				UUID.randomUUID(),
				null,
				fooState.getStateId().toString(),
				"Foo"),

			// Migrate Foo to Bar
			new SetTagMigration(
				UUID.randomUUID(),
				fooState.getStateId().toString(),
				barState.getStateId().toString(),
				"Bar")

		);

		return this;
	}

	public TestContext_SimpleFakeResource_Builder withAssertion(
		int stateIndex,
		String tag)
	{
		if (tag == null) throw new ArgumentNullException("tag");

		State state = this.states.get(stateIndex);

		state.getAssertions().add(
			new TagAssertion(UUID.randomUUID(), 0, tag));

		return this;
	}

	public TestContext_SimpleFakeResource_Builder withDummyStates(int count)
	{
		if (count < 1) throw new ArgumentException("count", "count must be 1 or greater");

		List<State> states = new ArrayList<>();

		for (int i = 1; i <= count; i++)
		{
			states.add(new ImmutableState(
				UUID.randomUUID(),
				"state" + i));
		}

		this.states = states;

		return this;
	}

	public TestContext_SimpleFakeResource_Builder withMigration(
		Integer fromStateIndex,
		Integer toStateIndex)
	{
		String fromStateRef = fromStateIndex == null
			? null
			: this.states.get(fromStateIndex).getStateId().toString();
		String toStateRef = toStateIndex == null
			? null
			: this.states.get(toStateIndex).getStateId().toString();

		List<Migration> newMigrations = new ArrayList<>(this.migrations);

		newMigrations.add(new SetTagMigration(
			UUID.randomUUID(),
			fromStateRef,
			toStateRef,
			String.format("state %d -> state %d", fromStateIndex, toStateIndex)));

		this.migrations = newMigrations;

		return this;
	}

	public TestContext_SimpleFakeResource_Builder withDefaultTarget(
		String defaultTarget)
	{
		if (defaultTarget == null) throw new ArgumentNullException("defaultTarget");

		this.defaultTarget = defaultTarget;

		return this;
	}

	public TestContext_SimpleFakeResource_Builder withInitialState(
		int stateIndex,
		String tag)
	{
		if (tag == null) throw new ArgumentNullException("tag");

		State state = this.states.get(stateIndex);
		this.initialStateId = state.getStateId();
		this.initialTag = tag;

		return this;
	}

	public TestContext_SimpleFakeResource_Builder withInitialTag(
		String tag)
	{
		if (tag == null) throw new ArgumentNullException("tag");

		this.initialTag = tag;

		return this;
	}

	public TestContext_ResourceAndInstance build()
	{
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			defaultTarget);

		for (State state : this.states)
		{
			resource.getStates().add(state);
		}

		for (Migration migration : this.migrations)
		{
			resource.getMigrations().add(migration);
		}

		FakeInstance instance = new FakeInstance();

		if (this.initialStateId != null)
		{
			instance.setStateId(this.initialStateId);
		}

		if (this.initialTag != null)
		{
			instance.setTag(this.initialTag);
		}

		return new TestContext_ResourceAndInstance(
			resource,
			instance);
	}
}
