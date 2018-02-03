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

/**
 * Base class for building DatabaseInstance implementations.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public abstract class BaseDatabaseInstance implements DatabaseInstance
{
	protected BaseDatabaseInstance(
		String databaseName,
		String stateTableName)
	{
		if (stateTableName != null && "".equals(stateTableName.trim()))
		{
			throw new IllegalArgumentException("stateTableName cannot be empty");
		}
		
		this.setDatabaseName(databaseName);
		if (stateTableName != null)
		{
			this.setStateTableName(stateTableName);
		}
	}
	
	// <editor-fold desc="DatabaseName" defaultstate="collapsed">

	private String _databaseName = null;
	private boolean _databaseName_set = false;

	@Override public final String getDatabaseName() {
		if(!_databaseName_set) {
			throw new IllegalStateException("databaseName not set.");
		}
		if(_databaseName == null) {
			throw new IllegalStateException("databaseName should not be null");
		}
		return _databaseName;
	}

	@Override public final void setDatabaseName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("databaseName cannot be null");
		}
		boolean changing = !_databaseName_set || !_databaseName.equals(value);
		if(changing) {
			_databaseName_set = true;
			_databaseName = value;
		}
	}

	private void clearDatabaseName() {
		if(_databaseName_set) {
			_databaseName_set = true;
			_databaseName = null;
		}
	}

	private boolean hasDatabaseName() {
		return _databaseName_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateTableName" defaultstate="collapsed">

	private String _stateTableName = null;
	private boolean _stateTableName_set = false;

	@Override public final String getStateTableName() {
		if(!_stateTableName_set) {
			throw new IllegalStateException("stateTableName not set.  Use the HasStateTableName() method to check its state before accessing it.");
		}
		return _stateTableName;
	}

	@Override public final void setStateTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("stateTableName cannot be null");
		}
		boolean changing = !_stateTableName_set || !_stateTableName.equals(value);
		if(changing) {
			_stateTableName_set = true;
			_stateTableName = value;
		}
	}

	@Override public void clearStateTableName() {
		if(_stateTableName_set) {
			_stateTableName_set = true;
			_stateTableName = null;
		}
	}

	@Override public boolean hasStateTableName() {
		return _stateTableName_set;
	}

	// </editor-fold>
}
