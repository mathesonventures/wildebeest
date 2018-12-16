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

import co.mv.wb.AssertionPlugin;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.PluginGroup;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A fluent builder for WildebeestApi implementation.
 *
 * @since 4.0
 */
public class WildebeestApiBuilder
{
	private final WildebeestApiImpl wildebeestApi;
	private final List<PluginGroup> pluginGroups;
	private final Map<ResourceType, ResourcePlugin> resourcePlugins;
	private final Map<String, MigrationPlugin> migrationPlugins;
	private final List<AssertionPlugin> assertionPlugins;

	/**
	 * Creates a new WildebeestApiBuilder with the specified PrintStream for Wildebeest to output to.
	 *
	 * @param eventSink the EventSink that Wildebeest should output to.
	 * @return a new WildebeestApiBuilder.
	 * @since 4.0
	 */
	public static WildebeestApiBuilder create(
		EventSink eventSink)
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");

		return new WildebeestApiBuilder(
			new WildebeestApiImpl(eventSink),
			new ArrayList<>(),
			new HashMap<>(),
			new HashMap<>(),
			new ArrayList<>());
	}

	private WildebeestApiBuilder(
		WildebeestApiImpl wildebeestApi,
		List<PluginGroup> pluginGroups,
		Map<ResourceType, ResourcePlugin> resourcePlugins,
		Map<String, MigrationPlugin> migrationPlugins,
		List<AssertionPlugin> assertionPlugins)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");
		if (pluginGroups == null) throw new ArgumentNullException("pluginGroups");
		if (resourcePlugins == null) throw new ArgumentNullException("resourcePlugins");
		if (migrationPlugins == null) throw new ArgumentNullException("migrationPlugins");
		if (assertionPlugins == null) throw new ArgumentNullException("assertionPlugins");

		this.wildebeestApi = wildebeestApi;
		this.pluginGroups = pluginGroups;
		this.resourcePlugins = resourcePlugins;
		this.migrationPlugins = migrationPlugins;
		this.assertionPlugins = assertionPlugins;
	}

	/**
	 * Fluently adds the factory set of PluginGroups to the builder.
	 *
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withFactoryPluginGroups()
	{
		List<PluginGroup> updated = new ArrayList<>(this.pluginGroups);
		updated.addAll(Wildebeest.getPluginGroups());

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			updated,
			this.resourcePlugins,
			this.migrationPlugins,
			this.assertionPlugins);
	}

	/**
	 * Fluently adds the factory-preset {@link ResourcePlugin}'s to the builder.  A new builder is returned and the
	 * original builder is left unmutated.
	 *
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withFactoryResourcePlugins()
	{
		Map<ResourceType, ResourcePlugin> updated = new HashMap<>(this.resourcePlugins);
		updated.putAll(Wildebeest.getResourcePlugins());

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			updated,
			this.migrationPlugins,
			this.assertionPlugins);
	}

	/**
	 * Fluently adds the supplied ResourceType -> ResourcePlugin mapping to the builder.  A new builder is returned and
	 * the original builder is left unmutated.
	 *
	 * @param resourceType   the new ResourceType being mapped.
	 * @param resourcePlugin the ResourcePlugin being mapped.
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withResourcePlugin(
		ResourceType resourceType,
		ResourcePlugin resourcePlugin)
	{
		Map<ResourceType, ResourcePlugin> updated = new HashMap<>(this.resourcePlugins);
		updated.put(resourceType, resourcePlugin);

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			updated,
			this.migrationPlugins,
			this.assertionPlugins);
	}

	/**
	 * Fluently adds the factory set of AssertionPlugins to the builder.  A new builder is returned and the original
	 * builder is left unmutated.
	 *
	 * @returna new WildebeestApiBuilder with the state of the original plus the new state.
	 * @since 4.0
	 */
	public WildebeestApiBuilder withFactoryAssertionPlugins()
	{
		return this.withAssertionPlugins(Wildebeest.getAssertionPlugins());
	}

	public WildebeestApiBuilder withAssertionPlugins(List<AssertionPlugin> assertionPlugins)
	{
		if (assertionPlugins == null) throw new ArgumentNullException("assertionPlugins");

		List<AssertionPlugin> updated = new ArrayList<>(this.assertionPlugins);

		updated.addAll(assertionPlugins);

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			this.resourcePlugins,
			this.migrationPlugins,
			updated);
	}

	/**
	 * Fluently adds the supplied {@link AssertionPlugin} to the builder.  A new builder is returned and the original
	 * builder is left unmutated.
	 *
	 * @param assertionPlugin the AssertionPlugin to be added to the buidler
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withAssertionPlugin(AssertionPlugin assertionPlugin)
	{
		if (assertionPlugin == null) throw new ArgumentNullException("assertionPlugin");

		List<AssertionPlugin> updated = new ArrayList<>(this.assertionPlugins);

		updated.add(assertionPlugin);

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			this.resourcePlugins,
			this.migrationPlugins,
			updated);
	}

	/**
	 * Fluently adds the factory set of MigrationPlugins to the builder.  A new builder is returned and the original
	 * builder is left unmutated.
	 *
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withFactoryMigrationPlugins()
	{
		return this.withMigrationPlugins(Wildebeest.getMigrationPlugins(
			this.wildebeestApi));
	}

	public WildebeestApiBuilder withMigrationPlugins(List<MigrationPlugin> migrationPlugins)
	{
		if (migrationPlugins == null) throw new ArgumentNullException("migrationPlugins");

		Map<String, MigrationPlugin> updated = new HashMap<>(this.migrationPlugins);

		Map<String, MigrationPlugin> factory = migrationPlugins
			.stream()
			.collect(Collectors.toMap(
				x -> Wildebeest.getPluginHandlerUri(x),
				x -> x));

		updated.putAll(factory);

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			this.resourcePlugins,
			updated,
			this.assertionPlugins);
	}


	/**
	 * Fluently adds the supplied {@link MigrationPlugin} to the builder.  A new builder is returned and the
	 * original builder is left unmutated.
	 *
	 * @param migrationPlugin the MigrationPlugin to be added to the builder
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withMigrationPlugin(MigrationPlugin migrationPlugin)
	{
		if (migrationPlugin == null) throw new ArgumentNullException("migrationPlugin");

		Map<String, MigrationPlugin> updated = new HashMap<>(this.migrationPlugins);

		updated.put(
			Wildebeest.getPluginHandlerUri(migrationPlugin),
			migrationPlugin);

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			this.resourcePlugins,
			updated,
			this.assertionPlugins);
	}

	/**
	 * Builds a new {@link WildebeestApi} with the plugins that were registered to this builder.
	 *
	 * @return a new WildebeestApi instance with the plugins that were registered to
	 * this builder.
	 */
	public WildebeestApi get()
	{
		this.wildebeestApi.setPluginGroups(pluginGroups);
		this.wildebeestApi.setResourcePlugins(this.resourcePlugins);
		this.wildebeestApi.setMigrationPlugins(this.migrationPlugins);
		this.wildebeestApi.setAssertionPlugins(this.assertionPlugins);

		return this.wildebeestApi;
	}
}
