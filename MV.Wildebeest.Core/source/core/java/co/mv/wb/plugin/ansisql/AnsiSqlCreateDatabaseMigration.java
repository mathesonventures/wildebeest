// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.mv.wb.plugin.ansisql;

import co.mv.wb.Instance;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationFaultException;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.plugin.base.BaseMigration;
import co.mv.wb.plugin.database.DatabaseHelper;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Migration} that creates a database using ANSI-standard SQL statements.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class AnsiSqlCreateDatabaseMigration extends BaseMigration
{
    public AnsiSqlCreateDatabaseMigration(
        UUID migrationId,
        UUID fromStateId,
        UUID toStateId)
    {
        super(migrationId, fromStateId, toStateId);
    }

    @Override public boolean canPerformOn(Resource resource)
    {
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, AnsiSqlDatabaseResource.class) != null;
    }

    @Override public void perform(Instance instance) throws MigrationFailedException
    {
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		AnsiSqlDatabaseInstance db = ModelExtensions.As(instance, AnsiSqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a AnsiSqlDatabaseInstance"); }

		if (!db.databaseExists())
		{
			try
			{
				DatabaseHelper.execute(
					db.getAdminDataSource(),
					String.format("CREATE DATABASE %s;", db.getDatabaseName()));
			}
			catch (SQLException e)
			{
				throw new MigrationFaultException(e);
			}
		}
    }
}
