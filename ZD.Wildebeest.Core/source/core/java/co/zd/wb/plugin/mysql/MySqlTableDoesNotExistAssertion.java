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

package co.zd.wb.plugin.mysql;

import co.zd.wb.plugin.base.BaseAssertion;
import co.zd.wb.AssertionResponse;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.Resource;
import co.zd.wb.plugin.base.ImmutableAssertionResponse;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a given table does not exist in a MySQL database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MySqlTableDoesNotExistAssertion extends BaseAssertion
{
	/**
	 * Creates a new MySqlTableDoesNotExistAssertion.
	 * 
	 * @param       assertionId                 the ID of the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's parent container.
	 * @param       tableName                   the name of the table that this assertion will check for.
	 * @since                                   1.0
	 */
	public MySqlTableDoesNotExistAssertion(
		UUID assertionId,
		int seqNum,
		String tableName)
	{
		super(assertionId, seqNum);
		
		this.setTableName(tableName);
	}
	
	@Override public String getDescription()
	{
		return String.format("Table '%s' does not exist", this.getTableName());
	}
	
	// <editor-fold desc="TableName" defaultstate="collapsed">

	private String _tableName = null;
	private boolean _tableName_set = false;

	/**
	 * Gets the name of the table that this assertion will check for.
	 * 
	 * @return                                  the name of the table that this assertion will check for.
	 * @since                                   1.0
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
		boolean changing = !_tableName_set || _tableName != value;
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
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, MySqlDatabaseResource.class) != null;
	}
	
	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }
		
		AssertionResponse result = null;

		if (!db.databaseExists())
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getDatabaseName()));
		}
		
		else if (MySqlDatabaseHelper.tableExists(db, this.getTableName()))
		{
			result = new ImmutableAssertionResponse(false, "Table " + this.getTableName() + " exists");
		}
		
		else
		{
			result = new ImmutableAssertionResponse(true, "Table " + this.getTableName() + " does not exist");
		}
		
		return result;
	}
}