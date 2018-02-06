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

import co.mv.wb.PluginGroup;
import co.mv.wb.PluginManager;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.framework.ArgumentNullException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fluent builder for WildebeestApi implementation.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class WildebeestApiBuilder
{
	private final WildebeestApiImpl _wildebeestApi;
	private final Map<ResourceType, ResourcePlugin> _resourcePlugins;
	private final PluginManager _pluginManager;

	public static WildebeestApiBuilder build(
		PrintStream output)
	{
		if (output == null) throw new ArgumentNullException("output");

		return new WildebeestApiBuilder(
			new WildebeestApiImpl(output),
			new HashMap<>(),
			new PluginManagerImpl(
				new ArrayList<>(),
				new ArrayList<>()));
	}

	private WildebeestApiBuilder(
		WildebeestApiImpl wildebeestApi,
		Map<ResourceType, ResourcePlugin> resourcePlugins,
		PluginManager pluginManager)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");
		if (resourcePlugins == null) throw new ArgumentNullException("resourcePlugins");
		if (pluginManager == null) throw new ArgumentNullException("pluginManager");

		_wildebeestApi = wildebeestApi;
		_resourcePlugins = resourcePlugins;
		_pluginManager = pluginManager;
	}

	public WildebeestApiBuilder withFactoryResourcePlugins()
	{
		Map<ResourceType, ResourcePlugin> resourcePlugins = new HashMap<>(_resourcePlugins);
		_resourcePlugins.putAll(Wildebeest.getResourcePlugins());

		return new WildebeestApiBuilder(
			_wildebeestApi,
			resourcePlugins,
			_pluginManager);
	}

	public WildebeestApiBuilder withFactoryPluginManager()
	{
		return this.withPluginManager(new PluginManagerImpl(
			Wildebeest.getPluginGroups(),
			Wildebeest.getMigrationPlugins(_wildebeestApi)));
	}

	public WildebeestApiBuilder withPluginManager(PluginManager pluginManager)
	{
		if (pluginManager == null) throw new ArgumentNullException("pluginManager");

		return new WildebeestApiBuilder(
			_wildebeestApi,
			_resourcePlugins,
			pluginManager);
	}

	public WildebeestApi get()
	{
		_wildebeestApi.setResourcePlugins(_resourcePlugins);
		_wildebeestApi.setPluginManager(_pluginManager);

		return _wildebeestApi;
	}
}
