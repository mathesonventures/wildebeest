// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

import co.zd.wb.plugin.database.Extensions;
import co.zd.wb.plugin.base.BaseMigration;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class MySqlCreateDatabaseMigration extends BaseMigration
{
	public MySqlCreateDatabaseMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}

	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }

		if (MySqlDatabaseHelper.schemaExists(db, db.getSchemaName()))
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				String.format("database \"%s\" already exists",	db.getSchemaName()));
		}
		
		try
		{
			DatabaseHelper.execute(db.getInfoDataSource(), new StringBuilder()
				.append("CREATE DATABASE `").append(db.getSchemaName()).append("`;").toString());
			
			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("CREATE TABLE `").append(Extensions.getStateTableName(db))
					.append("`(`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`));").toString());
			
			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("INSERT INTO `").append(Extensions.getStateTableName(db))
					.append("`(StateId) VALUES('").append(this.getToStateId().toString())
				.append("');").toString());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
