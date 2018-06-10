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

package co.mv.wb.plugin.generaldatabase;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.AssertionType;
import co.mv.wb.Instance;
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a given table exists in an ANSI-SQL compliant database.
 * 
 * @since                                       4.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:AnsiSqlTableExists",
	description =
		"Asserts that a table exists using an ANSI-SQL compliant query.",
	example =
		"<assertion\n" +
		"    type=\"AnsiSqlTableExists\"\n" +
		"    id=\"bf9fe573-2586-4586-bf7d-a020a33a5f09\"\n" +
		"    name=\"ProductType table does not exist\">\n" +
		"    <schemaName>prdcat</schemaName>\n" +
		"    <tableName>ProductType</tableName>\n" +
		"</assertion>"
)
public class AnsiSqlTableExistsAssertion extends BaseAssertion
{
	/**
	 * Creates a new AnsiSqlTableExistsAssertion.
	 * 
	 * @param       assertionId                 the ID for the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's parent container.
	 * @param       schemaName                  the name of the schema to check.
	 * @param       tableName                   the name of the table to check.
	 * @since                                   4.0
	 */
	public AnsiSqlTableExistsAssertion(
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
	public String getTableName() {
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
			Wildebeest.PostgreSqlDatabase);
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		AnsiSqlDatabaseInstance db = ModelExtensions.As(instance, AnsiSqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }
		
		AssertionResponse result;

		if (!db.databaseExists())
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getDatabaseName()));
		}
		
		else if (AnsiSqlDatabaseHelper.tableExists(
			db,
			this.getSchemaName(),
			this.getTableName()))
		{
			result = new ImmutableAssertionResponse(true, "Table " + this.getTableName() +
				" exists in schema " + this.getSchemaName());
		}
		
		else
		{
			result = new ImmutableAssertionResponse(false, "Table " + this.getTableName() +
				" does not exist in schema " + this.getSchemaName());
		}
		
		return result;
	}
}
