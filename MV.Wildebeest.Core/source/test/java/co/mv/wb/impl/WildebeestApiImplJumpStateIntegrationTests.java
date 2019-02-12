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
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.PluginNotFoundException;
import co.mv.wb.Resource;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.WildebeestApiBuilder;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.fixture.TestContext_ResourceAndInstance;
import co.mv.wb.framework.ExpectException;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import co.mv.wb.plugin.fake.TagAssertionPlugin;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger LOG = LoggerFactory.getLogger(WildebeestApiImplJumpStateIntegrationTests.class);

	@Test
	public void jumpstate_assertionFail_throws()
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(0, "Foo")
			.withInitialTag("Bar")
			.build();

		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withAssertionPlugin(new TagAssertionPlugin())
			.get();

		// Execute and Verify
		new ExpectException(AssertionFailedException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.jumpstate(
					context.resource,
					context.instance,
					context.getStateId(0).toString());
			}

			@Override public void verify(Exception e)
			{
				AssertionFailedException te = (AssertionFailedException)e;

				assertEquals("e.assertionResults.size", 1, te.getAssertionResults().size());
				assertEquals(
					"e.assertionResults[0].message",
					"Tag expected to be \"Foo\" but was \"Bar\"",
					te.getAssertionResults().get(0).getMessage());
			}
		}.perform();
	}

	@Test
	public void jumpstate_nonExistentState_throws()
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.build();

		final UUID targetStateId = UUID.randomUUID();

		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		// Execute and Verify
		new ExpectException(UnknownStateSpecifiedException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.jumpstate(
					context.resource,
					context.instance,
					targetStateId.toString());
			}

			@Override public void verify(Exception e)
			{
				UnknownStateSpecifiedException te = (UnknownStateSpecifiedException)e;

				assertEquals("e.specifiedState", targetStateId.toString(), te.getSpecifiedState());
			}
		}.perform();
	}

	@Test
	public void jumpstate_existentState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		JumpStateFailedException,
		PluginNotFoundException,
		UnknownStateSpecifiedException
	{
		// Setup

		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(0, "Foo")
			.withInitialTag("Foo")
			.build();

		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withAssertionPlugin(new TagAssertionPlugin())
			.get();

		// Execute
		wildebeestApi.jumpstate(
			context.resource,
			context.instance,
			context.getStateId(0).toString());

		// Verify
		assertEquals("instance.tag", "Foo", context.instance.getTag());
	}
}


