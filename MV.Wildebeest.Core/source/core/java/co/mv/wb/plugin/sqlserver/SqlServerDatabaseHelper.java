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

package co.mv.wb.plugin.sqlserver;

import co.mv.wb.framework.DatabaseHelper;

/**
 * Functional helper methods for working with SQL Server databases.
 * 
 * @since                                       2.0
 */
public class SqlServerDatabaseHelper
{
	/**
	 * Returns an indication of whether or not the SQL Server database represented by the supplied instance contains
	 * a given schema.
	 * 
	 * @param       instance                    the SqlServerDatabaseIntance to check.
	 * @param       schemaName                  the name of the schema to check for.
	 * @return                                  an indication of whether or not the schema exists.
	 * @since                                   2.0
	 */
	public static boolean schemaExists(
		SqlServerDatabaseInstance instance,
		String schemaName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName.trim())) { throw new IllegalArgumentException("schemaName cannot be empty"); }

		return DatabaseHelper.rowExists(
			instance.getAppDataSource(),
			String.format(
				"SELECT * FROM sys.schemas WHERE name = '%s'",
				schemaName));
	}
	
	/**
	 * Returns an indication of whether or not a SQL Server database schema contains a given table.
	 * 
	 * @param       instance                    the SqlServerDatabseInstance to check.
	 * @param       schemaName                  the name of the schema that should contain the table.
	 * @param       tableName                   the name of the table to check for.
	 * @return                                  an indication of whether or not the table exists.
	 * since                                    2.0
	 */
	public static boolean tableExists(
		SqlServerDatabaseInstance instance,
		String schemaName,
		String tableName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName.trim())) { throw new IllegalArgumentException("schemaName cannot be empty"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName.trim())) { throw new IllegalArgumentException("tableName cannot be empty"); }
		
		return DatabaseHelper.rowExists(
			instance.getAppDataSource(),
			String.format(
				"SELECT * FROM sys.objects WHERE object_id = OBJECT_ID('%s') AND TYPE IN (N'U')",
				"[" + schemaName + "].[" + tableName + "]"));
	}
}
