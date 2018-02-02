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
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.Resource;
import co.mv.wb.ResourceHelper;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.ResourceType;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.fixture.Fixtures;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeResourcePlugin;
import org.junit.Test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for WildebeestApiImpl.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class WildebeestApiImplUnitTests
{
	/**
	 * A call to migrate specified a target and the resource does not have a default.  WildebeestApiImpl correctly resolves the
	 * specified target and passes it to ResourceHelperImpl.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetSpecifiedNoDefault_succeeds() throws
		MigrationNotPossibleException,
		IndeterminateStateException,
		MigrationFailedException,
		AssertionFailedException,
		InvalidStateSpecifiedException,
		UnknownStateSpecifiedException,
		TargetNotSpecifiedException
	{
		// Setup
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(FakeConstants.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			Optional.empty());

		UUID fooId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			fooId,
			Optional.of("foo")));

		ResourceHelper resourceHelper = Fixtures
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.get();

		WildebeestApiImpl iface = new WildebeestApiImpl(
			mock(PrintStream.class),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.of("foo"));

		// Verify
		verify(resourceHelper).migrate(
			any(PrintStream.class),
			any(Resource.class),
			any(ResourcePlugin.class),
			any(Instance.class),
			any(),
			eq(fooId));
	}

	/**
	 * A call to migrate specified a target and the resource has a default.  WildebeestApiImpl correctly resolves the specified
	 * target and passes it to ResourceHelperImpl.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetSpecifiedWithDefault_succeeds() throws
		MigrationNotPossibleException,
		IndeterminateStateException,
		MigrationFailedException,
		AssertionFailedException,
		InvalidStateSpecifiedException,
		UnknownStateSpecifiedException,
		TargetNotSpecifiedException
	{
		// Setup
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(FakeConstants.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			Optional.of("bar"));

		UUID fooId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			fooId,
			Optional.of("foo")));

		UUID barId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			barId,
			Optional.of("bar")));

		ResourceHelper resourceHelper = Fixtures
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.get();

		WildebeestApiImpl iface = new WildebeestApiImpl(
			mock(PrintStream.class),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.of("foo"));

		// Verify
		verify(resourceHelper).migrate(
			any(PrintStream.class),
			any(Resource.class),
			any(ResourcePlugin.class),
			any(Instance.class),
			any(),
			eq(fooId));
	}

	/**
	 * A call to migrate did not specify a target and the resource does not have a default.  WildebeestApiImpl raises the error
	 * by throwing a TargetNotSpecifiedException.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetNotSpecifiedNoDefault_throws() throws
		MigrationNotPossibleException,
		AssertionFailedException,
		UnknownStateSpecifiedException,
		TargetNotSpecifiedException,
		MigrationFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException
	{
		// Setup
		PrintStream output = System.out;
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(FakeConstants.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			Optional.empty());

		UUID fooId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			fooId,
			Optional.of("foo")));

		ResourceHelper resourceHelper = Fixtures
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.get();

		WildebeestApiImpl iface = new WildebeestApiImpl(
			output,
			resourceHelper);

		// Execute and Verify
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.empty());
		// TODO: Verify target not specified
	}

	/**
	 * A call to migrate did not specify a target but the resource has a default.  WildebeestApiImpl correctly resolves the
	 * default target and passes it to ResourceHelperImpl
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetNotSpecifiedWithDefault_succeeds() throws
		MigrationNotPossibleException,
		IndeterminateStateException,
		MigrationFailedException,
		AssertionFailedException,
		InvalidStateSpecifiedException,
		UnknownStateSpecifiedException,
		TargetNotSpecifiedException
	{
		// Setup
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(FakeConstants.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"MyResource",
			Optional.of("bar"));

		UUID fooId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			fooId,
			Optional.of("foo")));

		UUID barId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			barId,
			Optional.of("bar")));

		ResourceHelper resourceHelper = Fixtures
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.withStateIdForLabel("bar", barId)
			.get();

		WildebeestApiImpl iface = new WildebeestApiImpl(
			mock(PrintStream.class),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.empty());

		// Verify
		verify(resourceHelper).migrate(
			any(PrintStream.class),
			any(Resource.class),
			any(ResourcePlugin.class),
			any(Instance.class),
			any(),
			eq(barId));
	}
}
