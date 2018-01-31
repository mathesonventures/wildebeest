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

import co.mv.wb.Instance;
import co.mv.wb.Logger;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationFaultException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.ModelExtensions;
import co.mv.wb.plugin.database.DatabaseHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;

public class SqlServerDropSchemaMigrationPlugin implements MigrationPlugin
{
	@Override public void perform(
		Logger logger,
		Migration migration,
		Instance instance) throws MigrationFailedException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		SqlServerDropSchemaMigration migrationT = ModelExtensions.As(migration, SqlServerDropSchemaMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SqlServerCreateSchemaMigration");
		}

		SqlServerDatabaseInstance instanceT = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (instanceT == null)
		{
			throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance");
		}

		try
		{
			DatabaseHelper.execute(instanceT.getAppDataSource(), new StringBuilder()
				.append("DROP SCHEMA [").append(migrationT.getSchemaName()).append("];").toString());
		}
		catch(SQLServerException e)
		{
			throw new MigrationFailedException(migration.getMigrationId(), e.getMessage());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
