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

import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.impl.WildebeestApiBuilder;
import co.mv.wb.plugin.composite.ExternalResourceMigration;
import co.mv.wb.plugin.composite.ExternalResourceMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlCreateDatabaseMigration;
import co.mv.wb.plugin.mysql.MySqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlDatabaseResourcePlugin;
import co.mv.wb.plugin.mysql.MySqlDropDatabaseMigration;
import co.mv.wb.plugin.postgresql.PostgreSqlDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerCreateDatabaseMigration;
import co.mv.wb.plugin.sqlserver.SqlServerCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerCreateSchemaMigration;
import co.mv.wb.plugin.sqlserver.SqlServerCreateSchemaMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropDatabaseMigration;
import co.mv.wb.plugin.sqlserver.SqlServerDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropSchemaMigration;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Global definitions and functions for Wildebeest.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class Wildebeest
{

	//
	// PluginGroup
	//

	public static final PluginGroup CompositeResourcePluginGroup = new PluginGroup(
		"co.mv.wb:Composite",
		"Composite Resource",
		"Works with higher-order resources composed of multiple other Wildebeest resources");
	public static final PluginGroup GeneralDatabasePluginGroup = new PluginGroup(
		"co.mv.wb:GeneralDatabase",
		"General Database",
		"Plugins that can be used for most relational database management systems");
	public static final PluginGroup MySqlPluginGroup = new PluginGroup(
		"co.mv.wb:MySqlDatabase",
		"MySQL",
		"Plugins for MySQL database resources");
	public static final PluginGroup PostgreSqlPluginGroup = new PluginGroup(
		"co.mv.wb:PostgreSqlDatabase",
		"PostgreSQL",
		"Plugins for PostgreSQL database resources");
	public static final PluginGroup SqlServerPluginGroup = new PluginGroup(
		"co.mv.wb:SqlServerDatabase",
		"SQL Server",
		"Plugins for SQL Server database resources");

	public static List<PluginGroup> getPluginGroups()
	{
		return Arrays.asList(
			Wildebeest.CompositeResourcePluginGroup,
			Wildebeest.GeneralDatabasePluginGroup,
			Wildebeest.MySqlPluginGroup,
			Wildebeest.PostgreSqlPluginGroup,
			Wildebeest.SqlServerPluginGroup);
	}

	//
	// ResourceType
	//

	public static final ResourceType MySqlDatabase = new ResourceType(
		"co.mv.wb.MySqlDatabase",
		"MySQL Database");
	public static final ResourceType PostgreSqlDatabase = new ResourceType(
		"co.mv.wb.PostgreSqlDatabase",
		"PostgreSQL Database");
	public static final ResourceType SqlServerDatabase = new ResourceType(
		"co.mv.wb.SqlServerDatabase",
		"SQL Server Database");

	public static List<ResourceType> getResourceTypes()
	{
		return Arrays.asList(
			Wildebeest.MySqlDatabase,
			Wildebeest.PostgreSqlDatabase,
			Wildebeest.SqlServerDatabase);
	}

	public static Map<ResourceType, ResourcePlugin> getResourcePlugins()
	{
		Map<ResourceType, ResourcePlugin> result = new HashMap<>();

		result.put(Wildebeest.MySqlDatabase, new MySqlDatabaseResourcePlugin());
		result.put(Wildebeest.PostgreSqlDatabase, new PostgreSqlDatabaseResourcePlugin());
		result.put(Wildebeest.SqlServerDatabase, new SqlServerDatabaseResourcePlugin());

		return result;
	}

	//
	// Migration
	//

	public static Map<Class, MigrationPlugin> getMigrationPlugins(
		WildebeestApi wildebeestApi)
	{
		if (wildebeestApi == null) { throw new IllegalArgumentException("wildebeestApi cannot be null"); }

		Map<Class, MigrationPlugin> result = new HashMap<>();

		// ansisql
		result.put(AnsiSqlCreateDatabaseMigration.class, new AnsiSqlCreateDatabaseMigrationPlugin());
		result.put(AnsiSqlDropDatabaseMigration.class, new AnsiSqlDropDatabaseMigrationPlugin());

		// composite
		result.put(ExternalResourceMigration.class, new ExternalResourceMigrationPlugin(wildebeestApi));

		// database
		result.put(SqlScriptMigration.class, new SqlScriptMigrationPlugin());

		// mysql
		result.put(MySqlCreateDatabaseMigration.class, new MySqlCreateDatabaseMigrationPlugin());
		result.put(MySqlDropDatabaseMigration.class, new MySqlCreateDatabaseMigrationPlugin());

		// sqlserver
		result.put(SqlServerCreateDatabaseMigration.class, new SqlServerCreateDatabaseMigrationPlugin());
		result.put(SqlServerCreateSchemaMigration.class, new SqlServerCreateSchemaMigrationPlugin());
		result.put(SqlServerDropDatabaseMigration.class, new SqlServerDropDatabaseMigrationPlugin());
		result.put(SqlServerDropSchemaMigration.class, new SqlServerDropDatabaseMigrationPlugin());

		return result;
	}

	//
	// Services
	//

	public static WildebeestApiBuilder wildebeestApi(
		PrintStream output)
	{
		if (output == null) throw new ArgumentNullException("output");

		return WildebeestApiBuilder.build(output);
	}

	//
	// Global Functions
	//

	public static State stateForId(
		Resource resource,
		UUID stateId)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }

		State result = null;

		for(State check : resource.getStates())
		{
			if (stateId.equals(check.getStateId()))
			{
				result = check;
				break;
			}
		}

		return result;
	}

}
