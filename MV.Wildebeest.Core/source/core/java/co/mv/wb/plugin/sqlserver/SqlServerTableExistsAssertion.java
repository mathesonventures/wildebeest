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

import co.mv.wb.Assertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.MigrationType;
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a given table exists in a SQL Server database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerTableExists",
	description =
		"Verifies that a table exists in a SQL Server database resource.")
public class SqlServerTableExistsAssertion extends BaseAssertion
{
	/**
	 * Creates a new SqlServerTableExistsAssertion.
	 * 
	 * @param       assertionId                 the ID for the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's parent container.
	 * @param       schemaName                  the name of the schema to check.
	 * @param       tableName                   the name of the table to check.
	 * @since                                   2.0
	 */
	public SqlServerTableExistsAssertion(
		UUID assertionId,
		int seqNum,
		String schemaName,
		String tableName)
	{
		super(assertionId, seqNum);
		
		this.setSchemaName(schemaName);
		this.setTableName(tableName);
	}

	@Override public String getDescription()
	{
		return String.format("Table '%s' exists in schema '%s'", this.getTableName(), this.getSchemaName());
	}
	
	// <editor-fold desc="SchemaName" defaultstate="collapsed">

	private String _schemaName = null;
	private boolean _schemaName_set = false;

	/**
	 * Gets the name of the schema to check.
	 * 
	 * @return                                  the name of the schema to check
	 * @since                                   2.0
	 */
	public final String getSchemaName() {
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
		boolean changing = !_schemaName_set || !_schemaName.equals(value);
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

	// <editor-fold desc="TableName" defaultstate="collapsed">

	private String _tableName = null;
	private boolean _tableName_set = false;

	/**
	 * Gets the name of the table to check
	 * 
	 * @return                                  the name of the table to check
	 * @since                                   2.0
	 */
	public final String getTableName() {
		if(!_tableName_set) {
			throw new IllegalStateException("tableName not set.  Use the HasTableName() method to check its state before accessing it.");
		}
		return _tableName;
	}

	private void setTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tableName cannot be null");
		}
		boolean changing = !_tableName_set || !_tableName.equals(value);
		if(changing) {
			_tableName_set = true;
			_tableName = value;
		}
	}

	private void clearTableName() {
		if(_tableName_set) {
			_tableName_set = true;
			_tableName = null;
		}
	}

	private boolean hasTableName() {
		return _tableName_set;
	}

	// </editor-fold>
	
	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.SqlServerDatabase);
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }
		
		AssertionResponse result;

		if (!db.databaseExists())
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getDatabaseName()));
		}
		
		else if (SqlServerDatabaseHelper.tableExists(
			db,
			this.getSchemaName(),
			this.getTableName()))
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
