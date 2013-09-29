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

package co.zd.wb.model.sqlserver;

import co.zd.wb.model.base.BaseAssertion;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.ModelExtensions;
import co.zd.wb.model.Instance;
import co.zd.wb.model.base.ImmutableAssertionResponse;
import java.util.UUID;

public class SqlServerTableExistsAssertion extends BaseAssertion
{
	public SqlServerTableExistsAssertion(
		UUID assertionId,
		String name,
		int seqNum,
		String tableName)
	{
		super(assertionId, name, seqNum);
		
		this.setTableName(tableName);
	}

	// <editor-fold desc="TableName" defaultstate="collapsed">

	private String m_tableName = null;
	private boolean m_tableName_set = false;

	public String getTableName() {
		if(!m_tableName_set) {
			throw new IllegalStateException("tableName not set.  Use the HasTableName() method to check its state before accessing it.");
		}
		return m_tableName;
	}

	private void setTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tableName cannot be null");
		}
		boolean changing = !m_tableName_set || m_tableName != value;
		if(changing) {
			m_tableName_set = true;
			m_tableName = value;
		}
	}

	private void clearTableName() {
		if(m_tableName_set) {
			m_tableName_set = true;
			m_tableName = null;
		}
	}

	private boolean hasTableName() {
		return m_tableName_set;
	}

	// </editor-fold>
	
	@Override public AssertionResponse apply(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }
		
		AssertionResponse result = null;

		if (!SqlServerDatabaseHelper.databaseExists(db))
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getDatabaseName()));
		}
		
		else if (SqlServerDatabaseHelper.tableExists(db, this.getTableName()))
		{
			result = new ImmutableAssertionResponse(true, "Table " + this.getTableName() + " exists");
		}
		
		else
		{
			result = new ImmutableAssertionResponse(false, "Table " + this.getTableName() + " does not exist");
		}
		
		return result;
	}
}
