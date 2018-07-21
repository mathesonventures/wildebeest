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

package co.mv.wb.plugin.postgresql;

import co.mv.wb.FaultException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.State;
import co.mv.wb.Wildebeest;
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDatabaseInstance;
import co.mv.wb.plugin.generaldatabase.Extensions;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Defines a PostgreSQL database resource.
 *
 * @since 4.0
 */
public class PostgreSqlDatabaseResourcePlugin implements ResourcePlugin
{
	public PostgreSqlDatabaseResourcePlugin()
	{
	}

	@Override
	public State currentState(
		Resource resource,
		Instance instance) throws
		IndeterminateStateException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (instance == null) throw new ArgumentNullException("instance");

		PostgreSqlDatabaseInstance db = ModelExtensions.as(instance, PostgreSqlDatabaseInstance.class);
		if (db == null)
		{
			throw new IllegalArgumentException("instance must be a PostgreSqlDatabaseInstance");
		}

		String metaSchemaName = Extensions.getMetaSchemaName(db);
		String stateTableName = Extensions.getStateTableName(db);

		UUID declaredStateId = null;

		if (db.databaseExists() && PostgreSqlStateHelper.hasStateId(
			resource.getResourceId(), db.getAppDataSource(), metaSchemaName, stateTableName))
		{
			declaredStateId = PostgreSqlStateHelper.getStateId(
				resource.getResourceId(),
				db.getAppDataSource(),
				metaSchemaName,
				stateTableName);
		}

		// If we found a declared state, check that the state is actually defined
		State result = null;

		if (declaredStateId != null)
		{
			try
			{
				result = Wildebeest.findState(resource, declaredStateId.toString());
			}

			// If the declared state ID is not known, throw
			catch (InvalidReferenceException e)
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
		EventSink eventSink,
		Resource resource,
		Instance instance,
		UUID stateId)
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (resource == null) throw new ArgumentNullException("resource");
		if (instance == null) throw new ArgumentNullException("instance");

		AnsiSqlDatabaseInstance db = ModelExtensions.as(instance, AnsiSqlDatabaseInstance.class);
		if (db == null)
		{
			throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance");
		}

		if (stateId == null) throw new ArgumentNullException("stateId");

		// Set the state tracking row
		try
		{
			PostgreSqlStateHelper.setStateId(
				resource.getResourceId(),
				db.getAppDataSource(),
				Extensions.getMetaSchemaName(db),
				Extensions.getStateTableName(db),
				stateId);
		}
		catch (SQLException e)
		{
			throw new FaultException(e);
		}
	}
}
