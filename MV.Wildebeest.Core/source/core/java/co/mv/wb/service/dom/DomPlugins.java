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

package co.mv.wb.service.dom;

import co.mv.wb.Logger;
import co.mv.wb.service.AssertionBuilder;
import co.mv.wb.service.InstanceBuilder;
import co.mv.wb.service.ResourcePluginBuilder;
import co.mv.wb.service.MigrationBuilder;
import co.mv.wb.service.dom.ansisql.AnsiSqlCreateDatabaseDomMigrationBuilder;
import co.mv.wb.service.dom.ansisql.AnsiSqlDropDatabaseDomMigrationBuilder;
import co.mv.wb.service.dom.ansisql.AnsiSqlTableDoesNotExistDomAssertionBuilder;
import co.mv.wb.service.dom.ansisql.AnsiSqlTableExistsDomAssertionBuilder;
import co.mv.wb.service.dom.composite.ExternalResourceDomMigrationBuilder;
import co.mv.wb.service.dom.database.DatabaseDoesNotExistDomAssertionBuilder;
import co.mv.wb.service.dom.database.DatabaseExistsDomAssertionBuilder;
import co.mv.wb.service.dom.database.RowDoesNotExistDomAssertionBuilder;
import co.mv.wb.service.dom.database.RowExistsDomAssertionBuilder;
import co.mv.wb.service.dom.mysql.MySqlCreateDatabaseDomMigrationBuilder;
import co.mv.wb.service.dom.database.SqlScriptDomMigrationBuilder;
import co.mv.wb.service.dom.mysql.MySqlDatabaseDomInstanceBuilder;
import co.mv.wb.service.dom.mysql.MySqlDatabaseDomResourcePluginBuilder;
import co.mv.wb.service.dom.mysql.MySqlTableDoesNotExistDomAssertionBuilder;
import co.mv.wb.service.dom.mysql.MySqlTableExistsDomAssertionBuilder;
import co.mv.wb.service.dom.postgresql.PostgreSqlDatabaseDomInstanceBuilder;
import co.mv.wb.service.dom.postgresql.PostgreSqlDatabaseDomResourcePluginBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerCreateDatabaseDomMigrationBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerCreateSchemaDomMigrationBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerDatabaseDomInstanceBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerDatabaseDomResourcePluginBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerDropSchemaDomMigrationBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerSchemaDoesNotExistDomAssertionBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerSchemaExistsDomAssertionBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerTableDoesNotExistDomAssertionBuilder;
import co.mv.wb.service.dom.sqlserver.SqlServerTableExistsDomAssertionBuilder;
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
	/**
	 * Builds and returns the collection of factory-shipped {@link ResourceBuilder}s.
	 * 
	 * @return                                  a Map that maps the XML element name to the builder instance.
	 * @since                                   1.0
	 */
	public static Map<String, ResourcePluginBuilder> resourceBuilders()
	{
		Map<String, ResourcePluginBuilder> result = new HashMap<String, ResourcePluginBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomResourcePluginBuilder());
		result.put("SqlServerDatabase", new SqlServerDatabaseDomResourcePluginBuilder());
		result.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomResourcePluginBuilder());
		
		return result;
	}

	/**
	 * Builds and returns the collection of factory-shipped {@link AssertionBuilder}s.
	 * 
	 * @return                                  a Map that maps the XML element name to the builder instance.
	 * @since                                   1.0
	 */
	public static Map<String, AssertionBuilder> assertionBuilders()
	{
		Map<String, AssertionBuilder> result = new HashMap<String, AssertionBuilder>();
		
		// Database
		result.put("DatabaseExists", new DatabaseExistsDomAssertionBuilder());
		result.put("DatabaseDoesNotExist", new DatabaseDoesNotExistDomAssertionBuilder());
		result.put("RowExists", new RowExistsDomAssertionBuilder());
		result.put("RowDoesNotExist", new RowDoesNotExistDomAssertionBuilder());
		
		// AnsiSql
		result.put("AnsiSqlTableExists", new AnsiSqlTableExistsDomAssertionBuilder());
		result.put("AnsiSqlTableDoesNotExist", new AnsiSqlTableDoesNotExistDomAssertionBuilder());
		
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
	
	/**
	 * Builds and returns the collection of factory-shipped {@link MigrationBuilder}s.
	 * 
	 * @param       logger                      the {@link Logger} instance to provide to plugins that require one.
	 * @return                                  a Map that maps the XML element name to the builder instance.
	 * @since                                   1.0
	 */
	public static Map<String, MigrationBuilder> migrationBuilders(
		Logger logger)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }

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

		// Composite
		result.put("External", new ExternalResourceDomMigrationBuilder(logger));

		return result;
	}
	
	/**
	 * Builds and returns the collection of factory-shipped {@link InstanceBuilder}s.
	 * 
	 * @return                                  a Map that maps the XML element name to the builder instance.
	 * @since                                   1.0
	 */
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
	 * @param       logger                      the {@link Logger} to provide to plugins that require one.
	 * @param       resourceXml                 the &lt;resource&gt; XML to be loaded by the DomResourceLoader.
	 * @return                                  a DomResourceLoader configured with the standard builders.
	 * @since                                   4.0
	 */
	public static DomResourceLoader resourceLoader(
		Logger logger,
		String resourceXml)
	{
		return new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(logger),
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
