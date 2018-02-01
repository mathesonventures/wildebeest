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

package co.mv.wb;

import co.mv.wb.fake.FakeResourcePlugin;
import co.mv.wb.fake.TestResourceTypes;
import co.mv.wb.impl.ImmutableState;
import co.mv.wb.impl.ResourceImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for Interface.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class InterfaceUnitTests
{
	/**
	 * A call to migrate specified a target and the resource does not have a default.  Interface correctly resolves the
	 * specified target and passes it to ResourceHelperImpl.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetSpecifiedNoDefault_succeeds() throws
		MigrationNotPossibleException,
		IndeterminateStateException,
		MigrationFailedException,
		AssertionFailedException
	{
		// Setup
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(TestResourceTypes.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			TestResourceTypes.Fake,
			"MyResource",
			Optional.empty());

		UUID fooId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			fooId,
			Optional.of("foo")));

		ResourceHelper resourceHelper = Mocks
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.get();

		Interface iface = new Interface(
			mock(Logger.class),
			resourcePlugins,
			new HashMap<>(),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.of("foo"));

		// Verify
		verify(resourceHelper).migrate(
			any(Logger.class),
			any(Resource.class),
			any(ResourcePlugin.class),
			any(Instance.class),
			any(),
			eq(fooId));
	}

	/**
	 * A call to migrate specified a target and the resource has a default.  Interface correctly resolves the specified
	 * target and passes it to ResourceHelperImpl.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetSpecifiedWithDefault_succeeds() throws
		MigrationNotPossibleException,
		IndeterminateStateException,
		MigrationFailedException,
		AssertionFailedException
	{
		// Setup
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(TestResourceTypes.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			TestResourceTypes.Fake,
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

		ResourceHelper resourceHelper = Mocks
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.get();

		Interface iface = new Interface(
			mock(Logger.class),
			resourcePlugins,
			new HashMap<>(),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.of("foo"));

		// Verify
		verify(resourceHelper).migrate(
			any(Logger.class),
			any(Resource.class),
			any(ResourcePlugin.class),
			any(Instance.class),
			any(),
			eq(fooId));
	}

	/**
	 * A call to migrate did not specify a target and the resource does not have a default.  Interface raises the error
	 * by throwing a TargetNotSpecifiedException.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetNotSpecifiedNoDefault_throws()
	{
		// Setup
		Logger logger = mock(Logger.class);
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(TestResourceTypes.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			TestResourceTypes.Fake,
			"MyResource",
			Optional.empty());

		UUID fooId = UUID.randomUUID();
		resource.getStates().add(new ImmutableState(
			fooId,
			Optional.of("foo")));

		ResourceHelper resourceHelper = Mocks
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.get();

		Interface iface = new Interface(
			logger,
			resourcePlugins,
			new HashMap<>(),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.empty());

		// Verify
		verify(logger).targetNotSpecified(any(TargetNotSpecifiedException.class));
	}

	/**
	 * A call to migrate did not specify a target but the resource has a default.  Interface correctly resolves the
	 * default target and passes it to ResourceHelperImpl
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetNotSpecifiedWithDefault_succeeds() throws
		MigrationNotPossibleException,
		IndeterminateStateException,
		MigrationFailedException,
		AssertionFailedException
	{
		// Setup
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>();
		resourcePlugins.put(TestResourceTypes.Fake, new FakeResourcePlugin());

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			TestResourceTypes.Fake,
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

		ResourceHelper resourceHelper = Mocks
			.resourceHelper()
			.withStateIdForLabel("foo", fooId)
			.withStateIdForLabel("bar", barId)
			.get();

		Interface iface = new Interface(
			mock(Logger.class),
			resourcePlugins,
			new HashMap<>(),
			resourceHelper);

		// Execute
		iface.migrate(
			resource,
			mock(Instance.class),
			Optional.empty());

		// Verify
		verify(resourceHelper).migrate(
			any(Logger.class),
			any(Resource.class),
			any(ResourcePlugin.class),
			any(Instance.class),
			any(),
			eq(barId));
	}
}
