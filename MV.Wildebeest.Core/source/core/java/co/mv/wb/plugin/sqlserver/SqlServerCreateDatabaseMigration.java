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
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.plugin.base.BaseMigration;
import co.mv.wb.plugin.database.DatabaseHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that creates a new SQL-Server database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
public class SqlServerCreateDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new SqlServerCreateDatabseMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source state for this migration.
	 * @param       toStateId                   the target state for this migration.
	 * @since                                   2.0
	 */
	public SqlServerCreateDatabaseMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}
	
	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			FactoryResourceTypes.SqlServerDatabase);
	}

	@Override public void perform(
		Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }

		try
		{
			DatabaseHelper.execute(db.getAdminDataSource(), new StringBuilder()
				.append("CREATE DATABASE [").append(db.getDatabaseName()).append("];").toString());
		}
		catch(SQLServerException e)
		{
			throw new MigrationFailedException(this.getMigrationId(), e.getMessage());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
