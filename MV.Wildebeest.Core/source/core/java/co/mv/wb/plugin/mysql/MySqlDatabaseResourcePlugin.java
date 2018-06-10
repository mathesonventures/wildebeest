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

package co.mv.wb.plugin.mysql;

import co.mv.wb.FaultException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.State;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.generaldatabase.Extensions;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A {@link Resource} that is a MySQL database.
 * 
 * @since                                       1.0
 */
public class MySqlDatabaseResourcePlugin implements ResourcePlugin
{
	/**
	 * Creates a new MySqlDatabaseResourcePlugin
	 */
	public MySqlDatabaseResourcePlugin()
	{
	}

	@Override
	public State currentState(
		Resource resource,
		Instance instance) throws IndeterminateStateException
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }

		UUID declaredStateId = null;
		
		if (db.databaseExists())
		{
			declaredStateId = MySqlStateHelper.getStateId(
				resource.getResourceId(),
				db.getAppDataSource(),
				Extensions.getStateTableName(db));
		}
		
		// If we found a declared state, check that the state is actually defined
		State result = null;
		if (declaredStateId != null)
		{
			result = Wildebeest.stateForId(resource, declaredStateId);

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
	
	@Override
	public void setStateId(
		PrintStream output,
		Resource resource,
		Instance instance,
		String stateId)
	{
		if (output == null) { throw new IllegalArgumentException("output"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }
		
		// Set the state tracking row
		try
		{
			MySqlStateHelper.setStateId(
				resource.getResourceId(),
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
