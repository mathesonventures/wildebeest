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

import co.mv.wb.Assertion;
import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.SetTagMigration;
import co.mv.wb.plugin.fake.TagAssertion;

import java.util.UUID;

public class TestContext_SimpleFakeResource_Builder
{
	private String defaultTarget;

	private TestContext_SimpleFakeResource_Builder()
	{
		defaultTarget = null;
	}

	public static TestContext_SimpleFakeResource_Builder create()
	{
		return new TestContext_SimpleFakeResource_Builder();
	}

	public TestContext_SimpleFakeResource_Builder withDefaultTarget(
		String defaultTarget)
	{
		if (defaultTarget == null) throw new ArgumentNullException("defaultTarget");

		this.defaultTarget = defaultTarget;

		return this;
	}

	public TestContext_SimpleFakeResource getResourceWithNonExistantInitialState()
	{
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			defaultTarget);

		// Foo State
		UUID fooStateId = UUID.randomUUID();
		State fooState = new ImmutableState(
			fooStateId,
			"foo");
		resource.getStates().add(fooState);

		// Bar State
		UUID barStateId = UUID.randomUUID();
		State barState = new ImmutableState(
			barStateId,
			"bar");
		resource.getStates().add(barState);

		// Migrate non-existant to Foo
		resource.getMigrations().add(new SetTagMigration(
			UUID.randomUUID(),
			null,
			fooStateId.toString(),
			"Foo"));

		// Migrate non-existant to Foo
		resource.getMigrations().add(new SetTagMigration(
			UUID.randomUUID(),
			null,
			barStateId.toString(),
			"Bar"));

		Instance instance = new FakeInstance();

		return new TestContext_SimpleFakeResource(
			resource,
			fooStateId,
			fooState,
			barStateId,
			barState,
			instance);
	}

	public TestContext_SimpleFakeResource getResourceWithInitialState()
	{
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			defaultTarget);

		UUID finalStateId = UUID.randomUUID();
		State finalState = new ImmutableState(
			finalStateId,
			"finalState");
		resource.getStates().add(finalState);
		Assertion finalAssertion1 = new TagAssertion(
			UUID.randomUUID(),
			0,
			"finalState");
		finalState.getAssertions().add(finalAssertion1);

		UUID initialStateId = UUID.randomUUID();
		State initialState = new ImmutableState(
			initialStateId,
			"initialState");
		resource.getStates().add(initialState);

		resource.getMigrations().add(new SetTagMigration(
			UUID.randomUUID(),
			initialStateId.toString(),
			finalStateId.toString(),
			"finalState"));

		Instance instance = new FakeInstance(initialStateId);
		((FakeInstance)instance).setTag("initialState");
		return new TestContext_SimpleFakeResource(
			resource,
			finalStateId,
			finalState,
			initialStateId,
			initialState,
			instance);
	}

	public TestContext_SimpleFakeResource getResourceAndInstanceOnly()
	{
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			defaultTarget);

		Instance instance = new FakeInstance();

		return new TestContext_SimpleFakeResource(
			resource,
			null,
			null,
			null,
			null,
			instance);
	}
}
