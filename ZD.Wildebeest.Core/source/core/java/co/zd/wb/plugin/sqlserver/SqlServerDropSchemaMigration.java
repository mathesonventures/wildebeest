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

package co.zd.wb.plugin.sqlserver;

import co.zd.wb.Instance;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationFaultException;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.plugin.base.BaseMigration;
import co.zd.wb.plugin.database.DatabaseHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Migration} that drops a schema from a {@link SqlServerDatabaseResource}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
public class SqlServerDropSchemaMigration extends BaseMigration
{
	/**
	 * Creates a new SqlServerDropSchemaMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source state for this migration.
	 * @param       toStateId                   the target state for this migration.
	 * @param       schemaName                  the name of the schema to be dropped.
	 * @since                                   2.0
	 */
	public SqlServerDropSchemaMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		String schemaName)
	{
		super(migrationId, fromStateId, toStateId);

		this.setSchemaName(schemaName);
	}
	
	// <editor-fold desc="SchemaName" defaultstate="collapsed">

	private String _schemaName = null;
	private boolean _schemaName_set = false;

	/**
	 * Returns the name of the schema to be dropped.
	 * 
	 * @since                                   2.0
	 */
	public String getSchemaName() {
		if(!_schemaName_set) {
			throw new IllegalStateException("schemaName not set.  Use the HasSchemaName() method to check its state before accessing it.");
		}
		return _schemaName;
	}

	private void setSchemaName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("schemaName cannot be null");
		}
		boolean changing = !_schemaName_set || _schemaName != value;
		if(changing) {
			_schemaName_set = true;
			_schemaName = value;
		}
	}

	private void clearSchemaName() {
		if(_schemaName_set) {
			_schemaName_set = true;
			_schemaName = null;
		}
	}

	private boolean hasSchemaName() {
		return _schemaName_set;
	}

	// </editor-fold>
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, SqlServerDatabaseInstance.class) != null;
	}

	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }

		try
		{
			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("DROP SCHEMA [").append(this.getSchemaName()).append("];").toString());
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
