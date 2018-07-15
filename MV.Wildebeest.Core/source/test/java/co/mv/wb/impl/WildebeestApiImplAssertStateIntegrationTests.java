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
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.event.EventSink;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import co.mv.wb.plugin.fake.TagAssertion;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for the {@link WildebeestApi#assertState(Resource, Instance)} implementation on
 * {@link WildebeestApiImpl}.
 *
 * @since 4.0
 */
public class WildebeestApiImplAssertStateIntegrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(WildebeestApiImplAssertStateIntegrationTests.class);

	@Test
	public void assertState_noAssertions_succeeds() throws
		IndeterminateStateException,
		AssertionFailedException
	{
		// Setup
		EventSink eventSink = (event) ->
		{if (event.getMessage().isPresent()) LOG.info(event.getMessage().get());};
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			null);

		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);

		FakeInstance instance = new FakeInstance(state.getStateId());

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(eventSink)
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			resource,
			instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 0, results.size());
	}

	@Test
	public void assertState_oneAssertion_succeeds() throws
		IndeterminateStateException,
		AssertionFailedException
	{
		// Setup
		EventSink eventSink = (event) ->
		{if (event.getMessage().isPresent()) LOG.info(event.getMessage().get());};
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			null);

		State state = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(state);

		Assertion assertion1 = new TagAssertion(
			UUID.randomUUID(),
			0,
			"Foo");
		state.getAssertions().add(assertion1);

		FakeInstance instance = new FakeInstance(state.getStateId());
		instance.setTag("Foo");

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(eventSink)
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			resource,
			instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 1, results.size());
		Asserts.assertAssertionResult(
			assertion1.getAssertionId(),
			true,
			"Tag is \"Foo\" as expected",
			results.get(0),
			"results[0]");
	}

	@Test
	public void assertState_multipleAssertions_succeeds() throws
		IndeterminateStateException,
		AssertionFailedException
	{
		// Setup
		EventSink eventSink = (event) ->
		{if (event.getMessage().isPresent()) LOG.info(event.getMessage().get());};
		FakeResourcePlugin resourcePlugin = new FakeResourcePlugin();
		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Resource",
			null);

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

		FakeInstance instance = new FakeInstance(state.getStateId());
		instance.setTag("Foo");

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(eventSink)
			.withResourcePlugin(FakeConstants.Fake, resourcePlugin)
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			resource,
			instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 2, results.size());
		Asserts.assertAssertionResult(
			assertion1Id, true, "Tag is \"Foo\" as expected",
			results.get(0), "results[0]");
		Asserts.assertAssertionResult(
			assertion2Id, false, "Tag expected to be \"Bar\" but was \"Foo\"",
			results.get(1), "results[1]");
	}

	/**
	 * Verifies that when the internal call to currentState() results in an IndeterminateStateException, assertState
	 * handles that properly.
	 */
	@Ignore
	@Test
	public void assertState_resourceIndeterminateState_throws()
	{
		throw new UnsupportedOperationException();
	}

	@Ignore
	@Test
	public void assertState_faultingAssertion_throws()
	{
		throw new UnsupportedOperationException();
	}
}
