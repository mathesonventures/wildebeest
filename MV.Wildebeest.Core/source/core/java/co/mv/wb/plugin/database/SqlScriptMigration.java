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

package co.mv.wb.plugin.database;

import co.mv.wb.plugin.base.BaseMigration;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Instance;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationFaultException;
import co.mv.wb.Resource;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Migration} that performs a SQL script to transition between states.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class SqlScriptMigration extends BaseMigration implements Migration
{
	/**
	 * Creates a new SqlScriptMigration.
	 * 
	 * @param       migrationId                 the ID of the migration
	 * @param       fromStateId                 the ID of the source state that this migration applies to, or null if
	 *                                          this migration transitions from the non-existent state.
	 * @param       toStateId                   the ID of the target state that the migration applies to, or null if
	 *                                          this migration transitions to the non-existent state.
	 * @param       sql                         the SQL script that performs the migration from the fron-state to the
	 *                                          to-state.
	 */
	public SqlScriptMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		String sql)
	{
		super(migrationId, fromStateId, toStateId);
		this.setSql(sql);
	}
	
	// <editor-fold desc="Sql" defaultstate="collapsed">

	private String _sql = null;
	private boolean _sql_set = false;

	private String getSql() {
		if(!_sql_set) {
			throw new IllegalStateException("sql not set.  Use the HasSql() method to check its state before accessing it.");
		}
		return _sql;
	}

	private void setSql(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("sql cannot be null");
		}
		boolean changing = !_sql_set || !_sql.equals(value);
		if(changing) {
			_sql_set = true;
			_sql = value;
		}
	}

	private void clearSql() {
		if(_sql_set) {
			_sql_set = true;
			_sql = null;
		}
	}

	private boolean hasSql() {
		return _sql_set;
	}

	// </editor-fold>
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, DatabaseResource.class) != null;
	}

	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }

		try
		{
			// Strip out any comments, and split the block of SQL into individual statements
			DatabaseHelper.execute(db.getAppDataSource(), this.getSql());
		}
		catch(SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
