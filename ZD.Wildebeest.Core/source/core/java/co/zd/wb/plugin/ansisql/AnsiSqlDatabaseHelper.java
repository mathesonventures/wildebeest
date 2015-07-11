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

package co.zd.wb.plugin.ansisql;

import co.zd.wb.plugin.database.DatabaseHelper;

/**
 * Helper functions for common operations on an ANSI-SQL compliant database system.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class AnsiSqlDatabaseHelper
{
	public static boolean tableExists(
		AnsiSqlDatabaseInstance db,
		String schemaName,
		String tableName)
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName)) { throw new IllegalArgumentException("schemaName cannot be empty"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName)) { throw new IllegalArgumentException("tableName cannot be empty"); }

		return DatabaseHelper.rowExists(
			db.getAppDataSource(),
			String.format(
				"SELECT * FROM information_schema.tables WHERE table_schema = '%s' AND table_name = '%s';",
				schemaName.toLowerCase(),
				tableName.toLowerCase()));
	}
}
