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
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.impl.ResourceHelperImpl;
import co.mv.wb.impl.WildebeestApiImpl;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.ansisql.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.composite.ExternalResourceMigration;
import co.mv.wb.plugin.composite.ExternalResourceMigrationPlugin;
import co.mv.wb.plugin.database.SqlScriptMigration;
import co.mv.wb.plugin.database.SqlScriptMigrationPlugin;
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
import co.mv.wb.plugin.sqlserver.SqlServerDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropSchemaMigration;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Factory
{
	public static WildebeestApi wildebeestApi(
		PrintStream output)
	{
		if (output == null) throw new ArgumentNullException("output");

		ResourceHelper resourceHelper = new ResourceHelperImpl();

		WildebeestApiImpl wildebeestApi = new WildebeestApiImpl(
			output,
			resourceHelper);

		wildebeestApi.setResourcePlugins(Factory.getResourcePlugins(resourceHelper));
		wildebeestApi.setMigrationPlugins(Factory.getMigrationPlugins(wildebeestApi));

		return wildebeestApi;
	}

	private static Map<ResourceType, ResourcePlugin> getResourcePlugins(
		ResourceHelper resourceHelper)
	{
		if (resourceHelper == null) { throw new IllegalArgumentException("resourceHelper cannot be null"); }

		Map<ResourceType, ResourcePlugin> result = new HashMap<>();

		result.put(FactoryResourceTypes.MySqlDatabase, new MySqlDatabaseResourcePlugin(
			resourceHelper));
		result.put(FactoryResourceTypes.PostgreSqlDatabase, new PostgreSqlDatabaseResourcePlugin(
			resourceHelper));
		result.put(FactoryResourceTypes.SqlServerDatabase, new SqlServerDatabaseResourcePlugin(
			resourceHelper));

		return result;
	}

	private static Map<Class, MigrationPlugin> getMigrationPlugins(
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
		result.put(SqlServerDropSchemaMigration.class, new SqlServerDropDatabaseMigrationPlugin());

		return result;
	}
}
