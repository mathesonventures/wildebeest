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

package co.mv.wb.plugin.mysql;

import co.mv.wb.plugin.base.BaseMigration;
import co.mv.wb.plugin.database.DatabaseHelper;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Instance;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationFaultException;
import co.mv.wb.Resource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that drops a {@link MySqlDatabaseResource}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MySqlDropDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new MySqlDropDatabaseMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source-state for the migration, or null if this migration
	 *                                          transitions from the non-existent state.
	 * @param       toStateId                   the target-state for the migration, or null if this migration
	 *                                          transitions to the non-existent state.
	 * @since                                   1.0
	 */
	public MySqlDropDatabaseMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, MySqlDatabaseInstance.class) != null;
	}

	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }

		if (!db.databaseExists())
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				String.format("database \"%s\" does not exist", db.getDatabaseName()));
		}
		
		try
		{
			DatabaseHelper.execute(db.getAdminDataSource(), new StringBuilder()
				.append("DROP DATABASE `").append(db.getDatabaseName()).append("`;").toString());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
