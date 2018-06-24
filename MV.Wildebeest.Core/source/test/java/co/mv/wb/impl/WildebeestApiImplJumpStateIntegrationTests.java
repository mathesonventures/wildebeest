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

import co.mv.wb.AssertionFailedException;
import co.mv.wb.ExpectException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import co.mv.wb.plugin.fake.TagAssertion;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Integration tests for the {@link WildebeestApi#jumpstate(Resource, Instance, String)} implementation on
 * {@link WildebeestApiImpl}.
 *
 * @since 4.0
 */
public class WildebeestApiImplJumpStateIntegrationTests
{
	@Test
	public void jumpstate_assertionFail_throws()
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
				assertEquals(
					"te.assertionResults[0].message",
					"Tag not as expected",
					te.getAssertionResults().get(0).getMessage());
			}
		}.perform();

	}

	@Test
	public void jumpstate_nonExistentState_throws()
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

	@Test
	public void jumpstate_existentState_succeeds() throws
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


