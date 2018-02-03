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

package co.mv.wb.plugin.base.dom;

import co.mv.wb.AssertionBuilder;
import co.mv.wb.InstanceBuilder;
import co.mv.wb.MigrationBuilder;
import co.mv.wb.ResourceTypeService;
import co.mv.wb.WildebeestFactory;
import co.mv.wb.plugin.composite.dom.ExternalResourceDomMigrationBuilder;
import co.mv.wb.plugin.generaldatabase.dom.AnsiSqlCreateDatabaseDomMigrationBuilder;
import co.mv.wb.plugin.generaldatabase.dom.AnsiSqlDropDatabaseDomMigrationBuilder;
import co.mv.wb.plugin.generaldatabase.dom.AnsiSqlTableDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.AnsiSqlTableExistsDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.DatabaseDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.DatabaseExistsDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.RowDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.RowExistsDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.SqlScriptDomMigrationBuilder;
import co.mv.wb.plugin.mysql.dom.MySqlCreateDatabaseDomMigrationBuilder;
import co.mv.wb.plugin.mysql.dom.MySqlDatabaseDomInstanceBuilder;
import co.mv.wb.plugin.mysql.dom.MySqlTableDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.mysql.dom.MySqlTableExistsDomAssertionBuilder;
import co.mv.wb.plugin.postgresql.dom.PostgreSqlDatabaseDomInstanceBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerCreateDatabaseDomMigrationBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerCreateSchemaDomMigrationBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerDatabaseDomInstanceBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerDropSchemaDomMigrationBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerSchemaDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerSchemaExistsDomAssertionBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerTableDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.sqlserver.dom.SqlServerTableExistsDomAssertionBuilder;

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
	 * Builds and returns the collection of factory-shipped {@link AssertionBuilder}s.
	 * 
	 * @return                                  a Map that maps the XML element name to the builder instance.
	 * @since                                   1.0
	 */
	public static Map<String, AssertionBuilder> assertionBuilders()
	{
		Map<String, AssertionBuilder> result = new HashMap<>();
		
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
	 * @return                                  a Map that maps the XML element name to the builder instance.
	 * @since                                   1.0
	 */
	public static Map<String, MigrationBuilder> migrationBuilders()
	{
		Map<String, MigrationBuilder> result = new HashMap<>();

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
		result.put("External", new ExternalResourceDomMigrationBuilder());

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
		Map<String, InstanceBuilder> result = new HashMap<>();
		
		result.put(WildebeestFactory.MySqlDatabase.getUri(), new MySqlDatabaseDomInstanceBuilder());
		result.put(WildebeestFactory.PostgreSqlDatabase.getUri(), new PostgreSqlDatabaseDomInstanceBuilder());
		result.put(WildebeestFactory.SqlServerDatabase.getUri(), new SqlServerDatabaseDomInstanceBuilder());

		return result;
	}
	
	/**
	 * Returns a {@link DomResourceLoader} for the supplied resource XML, configured with the standard builders.
	 *
	 * @param       resourceTypeService         the {@link ResourceTypeService} to use to lookup resource types.
	 * @param       resourceXml                 the &lt;resource&gt; XML to be loaded by the DomResourceLoader.
	 * @return                                  a DomResourceLoader configured with the standard builders.
	 * @since                                   4.0
	 */
	public static DomResourceLoader resourceLoader(
		ResourceTypeService resourceTypeService,
		String resourceXml)
	{
		return new DomResourceLoader(
			resourceTypeService,
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
