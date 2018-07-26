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

package co.mv.wb.cli;

import co.mv.wb.AssertionFailedException;
import co.mv.wb.Asserts;
import co.mv.wb.FileLoadException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.PluginBuildException;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.XmlValidationException;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.fixture.Fixtures;
import co.mv.wb.fixture.TestContext_WildebeestCommandUnit;
import co.mv.wb.framework.PredicateMatcher;
import co.mv.wb.plugin.fake.FakeConstants;
import org.junit.Test;
import org.mockito.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Unit tests for WildebeestCommand.
 *
 * @since 1.0
 */
public class WildebeestCommandUnitTests
{
	private static final Logger LOG = LoggerFactory.getLogger(WildebeestCommandUnitTests.class);

	@Test public void noCommand_noOperationsCalled()
	{
		// Setup
		TestContext_WildebeestCommandUnit context = TestContext_WildebeestCommandUnit.create();

		String[] args = new String[]{};

		// Execute
		context.wildebeestCommand.run(args);

		// Verify
		verifyZeroInteractions(context.wildebeestApi);
	}

	@Test public void helpDoubleDash_validRequest_noOperationsCalled()
	{
		// Setup
		TestContext_WildebeestCommandUnit context = TestContext_WildebeestCommandUnit.create();

		String[] args = new String[]
			{
				"help"
			};

		// Execute
		context.wildebeestCommand.run(args);

		// Verify
		verifyZeroInteractions(context.wildebeestApi);
	}


	@Test public void migrate_validRequest_migrateOperationCalled() throws
		AssertionFailedException,
		FileLoadException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		LoaderFault,
		MigrationFailedException,
		MigrationNotPossibleException,
		PluginBuildException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		XmlValidationException,
		InvalidReferenceException
	{
		// Setup
		TestContext_WildebeestCommandUnit context = TestContext_WildebeestCommandUnit.create();

		String[] args = new String[]
			{
				"migrate",
				"--resource MySqlDatabase/database.wbresource.xml",
				"--instance MySqlDatabase/staging_db.wbinstance.xml",
				"--target-state Core Schema Loaded"
			};

		// Execute
		context.wildebeestCommand.run(args);

		// Verify
		verify(context.wildebeestApi).loadResource(any());

		verify(context.wildebeestApi).loadInstance(any());

		verify(context.wildebeestApi).migrate(
			Matchers.argThat(new PredicateMatcher<>(
				resource -> Asserts.verifyFakeResource(
					context.fakeResource.getResourceId(),
					FakeConstants.Fake,
					context.fakeResource.getName(),
					resource))),
			Matchers.argThat(new PredicateMatcher<>(Asserts::verifyFakeInstance)),
			eq("Core Schema Loaded"));

		verifyNoMoreInteractions(context.wildebeestApi);
	}

	@Test public void migrate_missingInstanceArg_fails()
	{
		// Setup
		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Fixtures
			.wildebeestApi()
			.get();

		WildebeestCommand wb = new WildebeestCommand(
			output,
			wildebeestApi);

		// Execute
		wb.run(new String[]
			{
				"migrate",
				"--resource MySqlDatabase/database.wbresource.xml",
				"--targetState Core Schema Loaded"
			});

		// Verify
		// TODO: Refactor WildebeestCommand for testability - currently nothing to verify because all calls from run are private
	}

	@Test public void migrate_missingResourceArg_fails()
	{
		// Setup
		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Fixtures
			.wildebeestApi()
			.get();

		WildebeestCommand wb = new WildebeestCommand(
			output,
			wildebeestApi);

		// Execute
		wb.run(new String[]
			{
				"migrate",
				"--instance MySqlDatabase/staging_db.wbinstance.xml",
				"--targetState Core Schema Loaded"
			});

		// Verify
		// TODO: Refactor WildebeestCommand for testability - currently nothing to verify because all calls from run are private
	}

	@Test public void plugins_succeeds()
	{
		// Setup
		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withFactoryResourcePlugins()
			.get();

		WildebeestCommand wb = new WildebeestCommand(
			output,
			wildebeestApi);

		// Execute
		wb.run(new String[]
			{
				"--plugins"
			});

		// Verify

		// (none)
	}
}
