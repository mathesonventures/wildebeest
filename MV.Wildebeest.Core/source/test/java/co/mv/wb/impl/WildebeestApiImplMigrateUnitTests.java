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
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.fixture.TestContext_ResourceAndInstance;
import co.mv.wb.framework.ExpectException;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import co.mv.wb.plugin.fake.SetTagMigrationPlugin;
import co.mv.wb.plugin.fake.TagAssertion;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static co.mv.wb.Asserts.assertFakeInstance;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for WildebeestApiImpl.
 *
 * @since 4.0
 */
public class WildebeestApiImplMigrateUnitTests
{
	private static final Logger LOG = LoggerFactory.getLogger(WildebeestApiImplMigrateUnitTests.class);

	/**
	 * A call to migrate specified a target and the resource does not have a default.  WildebeestApiImpl correctly
	 * resolves the specified target and passes it to ResourceHelperImpl.
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_targetSpecifiedNoDefault_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withMigrationPlugin(new SetTagMigrationPlugin(context.resource))
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			"foo");

		// Verify
		assertFakeInstance(
			"Foo",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate specified a target and the resource has a default.  WildebeestApiImpl correctly resolves the
	 * specified target and passes it to ResourceHelperImpl.
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_targetSpecifiedWithDefault_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withDefaultTarget("bar")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withMigrationPlugin(new SetTagMigrationPlugin(context.resource))
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			"foo");

		// Verify
		assertFakeInstance(
			"Foo",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate did not specify a target and the resource does not have a default.  WildebeestApiImpl raises
	 * the error by throwing a TargetNotSpecifiedException.
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_targetNotSpecifiedNoDefault_throws()
	{
		// Setup
		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.get();

		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.build();

		// Execute and Verify
		new ExpectException(TargetNotSpecifiedException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.migrate(
					context.resource,
					context.instance,
					null);
			}

			@Override public void verify(Exception e)
			{
				// No additional verification needed
			}
		}.perform();
	}

	/**
	 * A call to migrate did not specify a target but the resource has a default.  WildebeestApiImpl correctly resolves
	 * the default target and passes it to ResourceHelperImpl
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_targetNotSpecifiedWithDefault_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withDefaultTarget("bar")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withMigrationPlugin(new SetTagMigrationPlugin(context.resource))
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			null);

		// Verify
		assertFakeInstance(
			"Bar",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate from non existent with assertions that is expected to pass
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_withAssertionsOnToStateStartingFromNonExistentState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(1, "Bar")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withMigrationPlugin(new SetTagMigrationPlugin(context.resource))
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			"bar");

		// Verify
		assertFakeInstance(
			"Bar",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate with assertions prior to migration that is expected to pass
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_withAssertionsOnToStateStartingFromExistantState_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(1, "Bar")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withMigrationPlugin(new SetTagMigrationPlugin(context.resource))
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			"bar");

		TagAssertion initialStateAssertionResult =
			(TagAssertion)context.resource.getStates().get(1).getAssertions().get(0);
		assertEquals(1, initialStateAssertionResult.getCalledNTimes());

		// Verify
		assertFakeInstance(
			"Bar",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate with assertions prior to migration that is expected to fail
	 *
	 * @since 4.0
	 */
	@Test
	public void migrate_withAssertionsPriorToMigration_withCurrentState_fails()
	{
		// Setup
		TestContext_ResourceAndInstance context = TestContext_ResourceAndInstance.Builder
			.create()
			.withFooBarStatesAndMigrations()
			.withAssertion(0, "Bup")
			.withInitialState(0, "Foo")
			.build();

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withResourcePlugin(FakeConstants.Fake, new FakeResourcePlugin())
			.withMigrationPlugin(new SetTagMigrationPlugin(context.resource))
			.get();

		// Execute and Verify
		new ExpectException(AssertionFailedException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.migrate(
					context.resource,
					context.instance,
					"bar");
			}

			@Override public void verify(Exception e)
			{
				AssertionFailedException te = (AssertionFailedException)e;

				TagAssertion initialStateAssertionResult =
					(TagAssertion)context.resource.getStates().get(0).getAssertions().get(0);
				assertEquals(1, initialStateAssertionResult.getCalledNTimes());

				FakeInstance instanceT = (FakeInstance)context.instance;

				Assert.assertTrue("instance.tag", !instanceT.getTag().equals("NewTag"));
			}
		}.perform();
	}
}
