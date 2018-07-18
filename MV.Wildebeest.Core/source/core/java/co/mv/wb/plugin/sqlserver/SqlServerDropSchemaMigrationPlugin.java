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
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationFaultException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.MigrationPluginType;
import co.mv.wb.ModelExtensions;
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;

/**
 * {@link MigrationPlugin} for {@link SqlServerDropSchemaMigration}.
 *
 * @since 4.0
 */
@MigrationPluginType(uri = "co.mv.wb.sqlserver:SqlServerDropSchema")
public class SqlServerDropSchemaMigrationPlugin implements MigrationPlugin
{
	@Override public void perform(
		EventSink eventSink,
		Migration migration,
		Instance instance) throws
		MigrationFailedException
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (migration == null) throw new ArgumentNullException("migration");
		if (instance == null) throw new ArgumentNullException("instance");

		SqlServerDropSchemaMigration migrationT = ModelExtensions.as(migration, SqlServerDropSchemaMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SqlServerCreateSchemaMigration");
		}

		SqlServerDatabaseInstance instanceT = ModelExtensions.as(instance, SqlServerDatabaseInstance.class);
		if (instanceT == null)
		{
			throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance");
		}

		try
		{
			DatabaseHelper.execute(instanceT.getAppDataSource(), new StringBuilder()
				.append("DROP SCHEMA [").append(migrationT.getSchemaName()).append("];").toString());
		}
		catch (SQLServerException e)
		{
			throw new MigrationFailedException(migration.getMigrationId(), e.getMessage());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
