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

package co.zd.wb.plugin.sqlserver;

import co.zd.wb.plugin.base.BaseAssertion;
import co.zd.wb.AssertionResponse;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.plugin.base.ImmutableAssertionResponse;
import java.util.UUID;

public class SqlServerSchemaDoesNotExistAssertion extends BaseAssertion
{
	public SqlServerSchemaDoesNotExistAssertion(
		UUID assertionId,
		int seqNum,
		String schemaName)
	{
		super(assertionId, seqNum);
		
		this.setSchemaName(schemaName);
	}

	@Override public String getDescription()
	{
		return String.format("Schema '%s' does not exist", this.getSchemaName());
	}
	
	// <editor-fold desc="SchemaName" defaultstate="collapsed">

	private String _schemaName = null;
	private boolean _schemaName_set = false;

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
		
		else if (SqlServerDatabaseHelper.schemaExists(
			db,
			this.getSchemaName()))
		{
			result = new ImmutableAssertionResponse(false, "Schema " + this.getSchemaName() + " exists");
		}
		
		else
		{
			result = new ImmutableAssertionResponse(true, "Schema " + this.getSchemaName() + " does not exist");
		}
		
		return result;
	}
}
