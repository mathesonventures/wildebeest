// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.service.dom;

import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.InstanceBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.MigrationBuilder;
import co.zd.wb.service.dom.ansisql.AnsiSqlCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.ansisql.AnsiSqlDropDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.database.DatabaseDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.database.DatabaseExistsDomAssertionBuilder;
import co.zd.wb.service.dom.database.RowDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.database.RowExistsDomAssertionBuilder;
import co.zd.wb.service.dom.mysql.MySqlCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.database.SqlScriptDomMigrationBuilder;
import co.zd.wb.service.dom.mysql.MySqlDatabaseDomInstanceBuilder;
import co.zd.wb.service.dom.mysql.MySqlDatabaseDomResourceBuilder;
import co.zd.wb.service.dom.mysql.MySqlTableDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.mysql.MySqlTableExistsDomAssertionBuilder;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomInstanceBuilder;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomResourceBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerCreateSchemaDomMigrationBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDatabaseDomInstanceBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDatabaseDomResourceBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDropSchemaDomMigrationBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerSchemaDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerSchemaExistsDomAssertionBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerTableDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerTableExistsDomAssertionBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides the set of pre-configured core plugins that are supported by Wildebeest.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class DomPlugins
{
	public static Map<String, ResourceBuilder> resourceBuilders()
	{
		Map<String, ResourceBuilder> result = new HashMap<String, ResourceBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomResourceBuilder());
		result.put("SqlServerDatabase", new SqlServerDatabaseDomResourceBuilder());
		result.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomResourceBuilder());
		
		return result;
	}

	public static Map<String, AssertionBuilder> assertionBuilders()
	{
		Map<String, AssertionBuilder> result = new HashMap<String, AssertionBuilder>();
		
		// Database
		result.put("DatabaseExists", new DatabaseExistsDomAssertionBuilder());
		result.put("DatabaseDoesNotExist", new DatabaseDoesNotExistDomAssertionBuilder());
		result.put("RowExists", new RowExistsDomAssertionBuilder());
		result.put("RowDoesNotExist", new RowDoesNotExistDomAssertionBuilder());
		
		// MySql
		result.put("MySqlTableDoesNotExist", new MySqlTableDoesNotExistDomAssertionBuilder());
		result.put("MySqlTableExists", new MySqlTableExistsDomAssertionBuilder());

		// SqlServer
		result.put("SqlServerSchemaDoesNotExist", new SqlServerSchemaDoesNotExistDomAssertionBuilder());
		result.put("SqlServerSchemaExists", new SqlServerSchemaExistsDomAssertionBuilder());
		result.put("SqlServerTableDoesNotExist", new SqlServerTableDoesNotExistDomAssertionBuilder());
		result.put("SqlServerTableExists", new SqlServerTableExistsDomAssertionBuilder());

		return result;
	}
	
	public static Map<String, MigrationBuilder> migrationBuilders()
	{
		Map<String, MigrationBuilder> result = new HashMap<String, MigrationBuilder>();

		// Database
		result.put("SqlScript", new SqlScriptDomMigrationBuilder());

		// AnsiSql
		result.put("AnsiSqlCreateDatabase", new AnsiSqlCreateDatabaseDomMigrationBuilder());
		result.put("AnsiSqlDropDatabase", new AnsiSqlDropDatabaseDomMigrationBuilder());
		
		// MySql
		result.put("MySqlCreateDatabase", new MySqlCreateDatabaseDomMigrationBuilder());
		
		// SqlServer
		result.put("SqlServerCreateDatabase", new SqlServerCreateDatabaseDomMigrationBuilder());
		result.put("SqlServerCreateSchema", new SqlServerCreateSchemaDomMigrationBuilder());
		result.put("SqlServerDropSchema", new SqlServerDropSchemaDomMigrationBuilder());

		return result;
	}
	
	public static Map<String, InstanceBuilder> instanceBuilders()
	{
		Map<String, InstanceBuilder> result = new HashMap<String, InstanceBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomInstanceBuilder());
		result.put("SqlServerDatabase", new SqlServerDatabaseDomInstanceBuilder());
		result.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomInstanceBuilder());
		
		return result;
	}
	
	/**
	 * Returns a {@link DomResourceLoader} for the supplied resource XML, configured with the standard builders.
	 * 
	 * @param       resourceXml                 the &lt;resource&gt; XML to be loaded by the DomResourceLoader.
	 * @return                                  a DomResourceLoader configured with the standard builders.
	 * @since                                   4.0
	 */
	public static DomResourceLoader resourceLoader(
		String resourceXml)
	{
		return new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			resourceXml);
	}
	
	/**
	 * Returns a {@link DomInstanceLoader} for the supplied instance XML, configured with the standard builders.
	 * 
	 * @param       instanceXml                 the &lt;instance&gt; XML to be loaded by the DomInstanceLoader.
	 * @return                                  a DomInstanceLoader configured with the standard builders.
	 * @since                                   4.0
	 */
	public static DomInstanceLoader instanceLoader(
		String instanceXml)
	{
		return new DomInstanceLoader(
			DomPlugins.instanceBuilders(),
			instanceXml);
	}
}