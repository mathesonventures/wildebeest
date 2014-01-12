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

import co.zd.wb.FaultException;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Instance;
import co.zd.wb.Logger;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.State;
import co.zd.wb.plugin.base.BaseResource;
import co.zd.wb.plugin.database.DatabaseResource;
import co.zd.wb.plugin.database.Extensions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Resource} that is a SQL Server database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
public class SqlServerDatabaseResource extends BaseResource implements DatabaseResource
{
	/**
	 * Creates a new SqlServerDatabaseResource.
	 * 
	 * @param       resourceId                  the ID of the new resource.
	 * @param       name                        the name of the new resource.
	 * @since                                   2.0
	 */
	public SqlServerDatabaseResource(
		UUID resourceId,
		String name)
	{
		super(resourceId, name);
	}
	
	@Override public State currentState(Instance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }

		UUID declaredStateId = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (SqlServerDatabaseHelper.databaseExists(db))
		{
			declaredStateId = SqlServerStateHelper.getStateId(
				this.getResourceId(),
				db.getAppDataSource(),
				Extensions.getStateTableName(db));
		}
		
		// If we found a declared state, check that the state is actually defined
		State result = null;
		if (declaredStateId != null)
		{
			result = this.stateForId(declaredStateId);

			// If the declared state ID is not known, throw
			if (result == null)
			{
				throw new IndeterminateStateException(String.format(
					"The resource is declared to be in state %s, but this state is not defined for this resource",
					declaredStateId.toString()));
			}
		}
		
		return result;
	}

	@Override public void setStateId(
		Logger logger,
		Instance instance,
		UUID stateId)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }

		// Set the state tracking row
		try
		{
			SqlServerStateHelper.setStateId(
				this.getResourceId(),
				db.getAppDataSource(),
				Extensions.getStateTableName(db),
				stateId);
		}
		catch (SQLException e)
		{
			throw new FaultException(e);
		}
	}
}