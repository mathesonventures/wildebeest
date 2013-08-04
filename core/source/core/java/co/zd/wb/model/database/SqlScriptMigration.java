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

package co.zd.wb.model.database;

import co.zd.wb.model.base.BaseMigration;
import co.zd.wb.model.ModelExtensions;
import co.zd.wb.model.Instance;
import co.zd.wb.model.Migration;
import co.zd.wb.model.MigrationFailedException;
import co.zd.wb.model.MigrationFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class SqlScriptMigration extends BaseMigration implements Migration
{
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

	private String m_sql = null;
	private boolean m_sql_set = false;

	public String getSql() {
		if(!m_sql_set) {
			throw new IllegalStateException("sql not set.  Use the HasSql() method to check its state before accessing it.");
		}
		return m_sql;
	}

	private void setSql(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("sql cannot be null");
		}
		boolean changing = !m_sql_set || m_sql != value;
		if(changing) {
			m_sql_set = true;
			m_sql = value;
		}
	}

	private void clearSql() {
		if(m_sql_set) {
			m_sql_set = true;
			m_sql = null;
		}
	}

	private boolean hasSql() {
		return m_sql_set;
	}

	// </editor-fold>
	
	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }

		try
		{
			// Strip out any comments, and split the block of SQL into individual statements
			String sql = this.getSql().replaceAll("/\\*.*?\\*/", "");
			String[] statements = sql.split(";");

			// Execute each statement
			for(String statement : statements)
			{
				if (!"".equals(statement.trim()))
				{
					DatabaseHelper.execute(db.getAppDataSource(), statement);
				}
			}

			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("UPDATE ").append(Extensions.getStateTableName(db))
					.append(" SET StateId = '").append(this.getToStateId().toString())
				.append("';").toString());
		}
		catch(SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
