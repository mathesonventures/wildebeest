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

package co.mv.wb.plugin.generaldatabase;

import co.mv.wb.Instance;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationFaultException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginHandler;
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;

import java.sql.SQLException;

/**
 * {@link MigrationPlugin} for {@link AnsiSqlDropDatabaseMigration}.
 *
 * @since 4.0
 */
@PluginHandler(uri = "co.mv.wb.generaldatabase:AnsiSqlDropDatabaseMigration")
public class AnsiSqlDropDatabaseMigrationPlugin implements MigrationPlugin
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

		AnsiSqlDropDatabaseMigration migrationT = ModelExtensions.as(migration, AnsiSqlDropDatabaseMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SqlServerCreateSchemaMigration");
		}

		AnsiSqlDatabaseInstance instanceT = ModelExtensions.as(instance, AnsiSqlDatabaseInstance.class);
		if (instanceT == null)
		{
			throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance");
		}

		if (!instanceT.databaseExists())
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format("database \"%s\" does not exist", instanceT.getDatabaseName()));
		}

		try
		{
			DatabaseHelper.execute(
				instanceT.getAdminDataSource(),
				String.format("DROP DATABASE %s;", instanceT.getDatabaseName()),
				false);
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
