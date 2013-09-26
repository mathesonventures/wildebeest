// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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
import co.zd.wb.service.dom.database.RowDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.database.RowExistsDomAssertionBuilder;
import co.zd.wb.service.dom.mysql.MySqlCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.mysql.MySqlDatabaseDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.database.SqlScriptDomMigrationBuilder;
import co.zd.wb.service.dom.mysql.MySqlDatabaseDomInstanceBuilder;
import co.zd.wb.service.dom.mysql.MySqlDatabaseDomResourceBuilder;
import co.zd.wb.service.dom.mysql.MySqlDatabaseExistsDomAssertionBuilder;
import co.zd.wb.service.dom.mysql.MySqlTableDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.mysql.MySqlTableExistsDomAssertionBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDatabaseDoesNotExistDomAssertionBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDatabaseDomInstanceBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDatabaseDomResourceBuilder;
import co.zd.wb.service.dom.sqlserver.SqlServerDatabaseExistsDomAssertionBuilder;
import java.util.HashMap;
import java.util.Map;

public class DomPlugins
{
	public static Map<String, ResourceBuilder> resourceBuilders()
	{
		Map<String, ResourceBuilder> result = new HashMap<String, ResourceBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomResourceBuilder());
		result.put("SqlServerDatabase", new SqlServerDatabaseDomResourceBuilder());
		
		return result;
	}

	public static Map<String, AssertionBuilder> assertionBuilders()
	{
		Map<String, AssertionBuilder> result = new HashMap<String, AssertionBuilder>();
		
		// Database
		result.put("RowExists", new RowExistsDomAssertionBuilder());
		result.put("RowDoesNotExist", new RowDoesNotExistDomAssertionBuilder());
		
		// MySql
		result.put("MySqlDatabaseDoesNotExist", new MySqlDatabaseDoesNotExistDomAssertionBuilder());
		result.put("MySqlDatabaseExists", new MySqlDatabaseExistsDomAssertionBuilder());
		result.put("MySqlTableDoesNotExist", new MySqlTableDoesNotExistDomAssertionBuilder());
		result.put("MySqlTableExists", new MySqlTableExistsDomAssertionBuilder());
		
		// SqlServer
		result.put("SqlServerDatabaseDoesNotExist", new SqlServerDatabaseDoesNotExistDomAssertionBuilder());
		result.put("SqlServerDatabaseExists", new SqlServerDatabaseExistsDomAssertionBuilder());

		return result;
	}
	
	public static Map<String, MigrationBuilder> migrationBuilders()
	{
		Map<String, MigrationBuilder> result = new HashMap<String, MigrationBuilder>();
		
		// Database
		result.put("SqlScript", new SqlScriptDomMigrationBuilder());
		
		// MySql
		result.put("MySqlCreateDatabase", new MySqlCreateDatabaseDomMigrationBuilder());
		
		// SqlServer
		result.put("SqlServerCreateDatabase", new SqlServerCreateDatabaseDomMigrationBuilder());

		return result;
	}
	
	public static Map<String, InstanceBuilder> instanceBuilders()
	{
		Map<String, InstanceBuilder> result = new HashMap<String, InstanceBuilder>();
		
		result.put("MySqlDatabase", new MySqlDatabaseDomInstanceBuilder());
		result.put("SqlServerDatabase", new SqlServerDatabaseDomInstanceBuilder());
		
		return result;
	}
}
