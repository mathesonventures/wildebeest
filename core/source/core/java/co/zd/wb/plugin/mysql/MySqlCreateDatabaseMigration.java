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

package co.zd.wb.plugin.mysql;

import co.zd.wb.plugin.base.BaseMigration;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationFaultException;
import co.zd.wb.Resource;
import co.zd.wb.plugin.database.DatabaseInstance;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Migration} that creates a {@link MySqlDatabaseResource}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MySqlCreateDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new MySqlCreateDatabaseMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source-state for the migration, or null if this migration
	 *                                          transitions from the non-existent state.
	 * @param       toStateId                   the target-state for the migration, or null if this migration
	 *                                          transitions to the non-existent state.
	 * @since                                   1.0
	 */
	public MySqlCreateDatabaseMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, DatabaseInstance.class) != null;
	}

	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }

		if (MySqlDatabaseHelper.schemaExists(db))
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				String.format("database \"%s\" already exists",	db.getSchemaName()));
		}
		
		try
		{
			DatabaseHelper.execute(db.getInfoDataSource(), new StringBuilder()
				.append("CREATE DATABASE `").append(db.getSchemaName()).append("`;").toString());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
