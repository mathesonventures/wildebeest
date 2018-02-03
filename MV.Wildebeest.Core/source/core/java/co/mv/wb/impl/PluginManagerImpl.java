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

import co.mv.wb.MigrationPlugin;
import co.mv.wb.MigrationPluginType;
import co.mv.wb.MigrationType;
import co.mv.wb.MigrationTypeInfo;
import co.mv.wb.PluginManager;
import co.mv.wb.framework.ArgumentNullException;
import org.reflections.Reflections;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PluginManagerImpl implements PluginManager
{
	private final Map<String, MigrationPlugin> _migrationPlugins;

	public PluginManagerImpl(
		List<MigrationPlugin> migrationPlugins)
	{
		if (migrationPlugins == null) throw new ArgumentNullException("migrationPlugins");

		_migrationPlugins = migrationPlugins
			.stream()
			.collect(Collectors.toMap(
				x ->
				{
					MigrationPluginType migrationPluginType = x.getClass().getAnnotation(MigrationPluginType.class);

					if (migrationPluginType == null)
					{
						throw new RuntimeException(String.format(
							"MigrationPlugin %s doesn't have a MigrationPluginType",
							x.getClass().getName()));
					}

					return migrationPluginType.uri();
				},
				x -> x));
	}

	@Override public List<MigrationTypeInfo> getMigrationTypeInfos()
	{
		Reflections reflections = new Reflections("co.mv.wb");

		List<MigrationTypeInfo> result = reflections
			.getTypesAnnotatedWith(MigrationType.class)
			.stream()
			.map(
				migrationClass ->
				{
					MigrationType migrationType = migrationClass.getAnnotation(MigrationType.class);

					return new MigrationTypeInfo(
						migrationType.pluginGroupUri(),
						migrationType.uri(),
						migrationClass);
				})
			.collect(Collectors.toList());

		return result;
	}

	@Override public MigrationPlugin getMigrationPlugin(
		String uri)
	{
		if (uri == null) throw new ArgumentNullException("uri");

		if (_migrationPlugins.containsKey(uri))
		{
			throw new RuntimeException(String.format("no MigrationPlugin found for uri: %s", uri));
		}

		return _migrationPlugins.get(uri);
	}
}
