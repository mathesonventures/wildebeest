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

import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.impl.WildebeestApiImpl;
import co.mv.wb.plugin.composite.CompositeConstants;
import co.mv.wb.plugin.composite.ExternalResourceMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlTableDoesNotExistAssertionPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlTableExistsAssertionPlugin;
import co.mv.wb.plugin.generaldatabase.DatabaseDoesNotExistAssertionPlugin;
import co.mv.wb.plugin.generaldatabase.DatabaseExistsAssertionPlugin;
import co.mv.wb.plugin.generaldatabase.GeneralDatabaseConstants;
import co.mv.wb.plugin.generaldatabase.RowDoesNotExistAssertionPlugin;
import co.mv.wb.plugin.generaldatabase.RowExistsAssertionPlugin;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlConstants;
import co.mv.wb.plugin.mysql.MySqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlDatabaseResourcePlugin;
import co.mv.wb.plugin.mysql.MySqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlTableDoesNotExistAssertionPlugin;
import co.mv.wb.plugin.mysql.MySqlTableExistsAssertionPlugin;
import co.mv.wb.plugin.postgresql.PostgreSqlConstants;
import co.mv.wb.plugin.postgresql.PostgreSqlDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerConstants;
import co.mv.wb.plugin.sqlserver.SqlServerCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerCreateSchemaMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropSchemaMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerSchemaDoesNotExistAssertionPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerSchemaExistsAssertionPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerTableDoesNotExistAssertionPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerTableExistsAssertionPlugin;

import java.util.ArrayList;
import java.util.Arrays;
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
		updated.addAll(Arrays.asList(
			CompositeConstants.CompositeResourcePluginGroup,
			GeneralDatabaseConstants.GeneralDatabasePluginGroup,
			MySqlConstants.MySqlPluginGroup,
			PostgreSqlConstants.PostgreSqlPluginGroup,
			SqlServerConstants.SqlServerPluginGroup));

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			updated,
			this.resourcePlugins,
			this.migrationPlugins,
			this.assertionPlugins);
	}

	/**
	 * Fluently configures the API with the required plugins for working with PostgreSQL databases.  A new builder is
	 * returned and the original builder is left unmutated.
	 *
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withPostgreSqlSupport()
	{
		return this
			.withResourcePlugin(PostgreSqlConstants.PostgreSqlDatabase, new PostgreSqlDatabaseResourcePlugin())
			.withAssertionPlugins(WildebeestApiBuilder.getAssertionPlugins_GeneralDatabase())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_GeneralDatabase())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_External(this.wildebeestApi));
	}

	/**
	 * Fluently configures the API with the required plugins for working with MySQL databases.  A new builder is
	 * returned and the original builder is left unmutated.
	 *
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withMySqlSupport()
	{
		return this
			.withResourcePlugin(MySqlConstants.MySqlDatabase, new MySqlDatabaseResourcePlugin())
			.withAssertionPlugins(WildebeestApiBuilder.getAssertionPlugins_GeneralDatabase())
			.withAssertionPlugins(WildebeestApiBuilder.getAssertionPlugins_MySql())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_GeneralDatabase())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_MySql())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_External(this.wildebeestApi));
	}

	/**
	 * Fluently configures the API with the required plugins for working with SQL Server databases.  A new builder is
	 * returned and the original builder is left unmutated.
	 *
	 * @return a new WildebeestApiBuilder with the state of the original plus the new state
	 * @since 4.0
	 */
	public WildebeestApiBuilder withSqlServerSupport()
	{
		return this
			.withResourcePlugin(SqlServerConstants.SqlServerDatabase, new SqlServerDatabaseResourcePlugin())
			.withAssertionPlugins(WildebeestApiBuilder.getAssertionPlugins_GeneralDatabase())
			.withAssertionPlugins(WildebeestApiBuilder.getAssertionPlugins_SqlServer())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_GeneralDatabase())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_SqlServer())
			.withMigrationPlugins(WildebeestApiBuilder.getMigrationPlugins_External(this.wildebeestApi));
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

		if (!updated.containsKey(resourceType))
		{
			updated.put(resourceType, resourcePlugin);
		}

		return new WildebeestApiBuilder(
			this.wildebeestApi,
			this.pluginGroups,
			updated,
			this.migrationPlugins,
			this.assertionPlugins);
	}

	public WildebeestApiBuilder withAssertionPlugins(List<AssertionPlugin> assertionPlugins)
	{
		if (assertionPlugins == null) throw new ArgumentNullException("assertionPlugins");

		List<AssertionPlugin> updated = new ArrayList<>(this.assertionPlugins);

		// Add any plugins that we don't already know about
		for (AssertionPlugin assertionPlugin : assertionPlugins)
		{
			if (!updated.stream().anyMatch(x -> x.getClass().equals(assertionPlugin.getClass())))
			{
				updated.add(assertionPlugin);
			}
		}

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

	public WildebeestApiBuilder withMigrationPlugins(List<MigrationPlugin> migrationPlugins)
	{
		if (migrationPlugins == null) throw new ArgumentNullException("migrationPlugins");

		Map<String, MigrationPlugin> updated = new HashMap<>(this.migrationPlugins);

		Map<String, MigrationPlugin> factory = migrationPlugins
			.stream()
			.collect(Collectors.toMap(
				x -> Wildebeest.getPluginHandlerUri(x),
				x -> x));

		for (String key : factory.keySet())
		{
			if (!updated.containsKey(key))
			{
				updated.put(key, factory.get(key));
			}
		}

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

		String key = Wildebeest.getPluginHandlerUri(migrationPlugin);

		if (!updated.containsKey(key))
		{
			updated.put(
				key,
				migrationPlugin);
		}

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

	private static List<AssertionPlugin> getAssertionPlugins_GeneralDatabase()
	{
		return Arrays.asList(
			new AnsiSqlTableDoesNotExistAssertionPlugin(),
			new AnsiSqlTableExistsAssertionPlugin(),
			new DatabaseDoesNotExistAssertionPlugin(),
			new DatabaseExistsAssertionPlugin(),
			new RowDoesNotExistAssertionPlugin(),
			new RowExistsAssertionPlugin());
	}

	private static List<AssertionPlugin> getAssertionPlugins_MySql()
	{
		return Arrays.asList(
			new MySqlTableDoesNotExistAssertionPlugin(),
			new MySqlTableExistsAssertionPlugin());
	}

	private static List<AssertionPlugin> getAssertionPlugins_SqlServer()
	{
		return Arrays.asList(
			new SqlServerSchemaDoesNotExistAssertionPlugin(),
			new SqlServerSchemaExistsAssertionPlugin(),
			new SqlServerTableDoesNotExistAssertionPlugin(),
			new SqlServerTableExistsAssertionPlugin());
	}

	private static List<MigrationPlugin> getMigrationPlugins_External(
		WildebeestApi wildebeestApi)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");

		return Arrays.asList(
			new ExternalResourceMigrationPlugin(wildebeestApi));
	}

	private static List<MigrationPlugin> getMigrationPlugins_GeneralDatabase()
	{
		return Arrays.asList(
			new AnsiSqlCreateDatabaseMigrationPlugin(),
			new AnsiSqlDropDatabaseMigrationPlugin(),
			new SqlScriptMigrationPlugin());
	}

	private static List<MigrationPlugin> getMigrationPlugins_MySql()
	{
		return Arrays.asList(
			new MySqlCreateDatabaseMigrationPlugin(),
			new MySqlDropDatabaseMigrationPlugin());
	}

	private static List<MigrationPlugin> getMigrationPlugins_SqlServer()
	{
		return Arrays.asList(
			new SqlServerCreateDatabaseMigrationPlugin(),
			new SqlServerCreateSchemaMigrationPlugin(),
			new SqlServerDropDatabaseMigrationPlugin(),
			new SqlServerDropSchemaMigrationPlugin());
	}
}
