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
import co.mv.wb.AssertionFaultException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.AssertionResult;
import co.mv.wb.Asserts;
import co.mv.wb.ExpectException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.ResourceType;
import co.mv.wb.State;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.fixture.TestContext_ResourceAndInstance;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withInitialState(0, "Foo")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			context.resource,
			context.instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 0, results.size());
	}

	/**
	 * A resource that has been migrated to a certain state which has one assertion defined passes its assertions.
	 */
	@Test
	public void assertState_oneAssertion_succeeds() throws
		IndeterminateStateException,
		AssertionFailedException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(0, "Foo")
			.withInitialState(0, "Foo")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			context.resource,
			context.instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 1, results.size());

		Assertion assertion1 = context.getState(0).getAssertions().get(0);
		Asserts.assertAssertionResult(
			assertion1.getAssertionId(),
			true,
			"Tag is \"Foo\" as expected",
			results.get(0),
			"results[0]");
	}

	/**
	 * A resource that has been migrated to a certain state which has multiple assertion defined passes all assertions.
	 */
	@Test
	public void assertState_multipleAssertions_succeeds() throws
		IndeterminateStateException,
		AssertionFailedException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(0, "Foo")
			.withAssertion(0, "Bar")
			.withInitialState(0, "Foo")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute
		List<AssertionResult> results = wildebeestApi.assertState(
			context.resource,
			context.instance);

		// Verify
		assertNotNull("results", results);
		assertEquals("results.size", 2, results.size());

		UUID assertion0Id = context.getState(0).getAssertions().get(0).getAssertionId();
		Asserts.assertAssertionResult(
			assertion0Id, true, "Tag is \"Foo\" as expected",
			results.get(0), "results[0]");

		UUID assertion1Id = context.getState(0).getAssertions().get(1).getAssertionId();
		Asserts.assertAssertionResult(
			assertion1Id, false, "Tag expected to be \"Bar\" but was \"Foo\"",
			results.get(1), "results[1]");
	}

	/**
	 * A resource is declared to be in a state that is not defined, and calling assertState triggers an
	 * IndeterminateStateException.
	 */
	@Test
	public void assertState_resourceIndeterminateState_throws()
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.build();

		UUID nonExistantStateId = UUID.randomUUID();
		context.instance.setStateId(nonExistantStateId);

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute and Verify
		new ExpectException(IndeterminateStateException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.assertState(
					context.resource,
					context.instance);
			}

			@Override public void verify(Exception e)
			{
				assertEquals(
					"e.message",
					e.getMessage(),
					String.format(
						"The resource is declared to be in state %s, but this state is not defined for this resource",
						nonExistantStateId));
			}
		}.perform();
	}

	/**
	 * A resource that has been migrated to a certain state which has one assertion encounters an
	 * AssertionFaultException when applying the assertion.
	 */
	@Test
	public void assertState_faultingAssertion_throws()
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withInitialState(0, "Foo")
			.build();

		UUID assertionId = UUID.randomUUID();

		Assertion faultingAssertion = new Assertion()
		{
			@Override public UUID getAssertionId()
			{
				return assertionId;
			}

			@Override public String getDescription()
			{
				return "Faulting Assertion";
			}

			@Override public int getSeqNum()
			{
				return 0;
			}

			@Override public List<ResourceType> getApplicableTypes()
			{
				return Arrays.asList();
			}

			@Override public AssertionResponse perform(Instance instance)
			{
				if (instance == null) throw new ArgumentNullException("instance");

				throw new AssertionFaultException(assertionId, new Exception("root cause"));
			}
		};

		State state = context.resource.getStates().get(0);
		state.getAssertions().add(faultingAssertion);

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute and Verify
		new ExpectException(AssertionFaultException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.assertState(
					context.resource,
					context.instance);
			}

			@Override public void verify(Exception e)
			{
				assertEquals("e.cause.message", "root cause", e.getCause().getMessage());
			}
		}.perform();
	}
}
