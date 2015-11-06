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

import co.zd.wb.Instance;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationFaultException;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.plugin.base.BaseMigration;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.plugin.database.DatabaseResource;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Migration} that drops a database using ANSI-standard SQL statements.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class AnsiSqlDropDatabaseMigration extends BaseMigration
{
    public AnsiSqlDropDatabaseMigration(
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
			throw new MigrationFailedException(
				this.getMigrationId(),
				String.format("database \"%s\" does not exist",	db.getDatabaseName()));
		}
		
		try
		{
			DatabaseHelper.execute(
				db.getAdminDataSource(),
				String.format("DROP DATABASE %s;", db.getDatabaseName()));
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
    }
}
