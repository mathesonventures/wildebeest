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
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Centralizes state tracking operations for PostgreSqlDatabaseInstances.
 *
 * @since 1.0
 */
public class PostgreSqlStateHelper
{
	/**
	 * Sets the tracked state for an instance.  Note that a PostgreSqlDatabaseInstance may be migrated by multiple
	 * separate resource definitions (to support composite resources).  The state set is for a specific resource
	 * definition.
	 *
	 * @param resourceId     the ID of the resource for which we are tracking state.
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param metaSchemaName the name of the schema where state tracking meta data should be stored.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @param stateId        the ID or name of the state.
	 * @throws SQLException if an error occurs when interacting with the database.
	 * @since 4.0
	 */
	public static void setStateId(
		UUID resourceId,
		DataSource appDataSource,
		String metaSchemaName,
		String stateTableName,
		UUID stateId) throws SQLException
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (metaSchemaName == null) throw new ArgumentNullException("metaSchemaName");
		if ("".equals(metaSchemaName)) throw new IllegalArgumentException("metaSchemaName cannot be empty");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");
		if (stateId == null) throw new ArgumentNullException("stateId");

		PostgreSqlStateHelper.createStateTableIfNotExists(
			appDataSource,
			metaSchemaName,
			stateTableName);

		DatabaseHelper.execute(
			appDataSource,
			String.format(
				"DELETE FROM %s.%s WHERE ResourceId = '%s';",
				metaSchemaName,
				stateTableName,
				resourceId));

		DatabaseHelper.execute(
			appDataSource,
			String.format(
				"INSERT INTO %s.%s(ResourceId, StateId, LastMigrationInstant) VALUES('%s', '%s', '%s');",
				metaSchemaName,
				stateTableName,
				resourceId,
				stateId,
				DatabaseHelper.getInstant()));
	}

	/**
	 * Gets the tracked state for an instance.  Note that a PostgreSqlDatabaseInstance may be migrated by multiple
	 * separate resource definitions (to support composite resources).  The state retrieved is for a specific resource
	 * definition.
	 *
	 * @param resourceId     the ID of the resource for which the state should be queried.
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param metaSchemaName the name of the meta-data tracking schema to use.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @return the ID of the state that the instance is in for the specified resource.
	 * @throws IndeterminateStateException if the current state of the instance cannot be determined for the
	 *                                     specified resource.
	 * @since 1.0
	 */
	public static UUID getStateId(
		UUID resourceId,
		DataSource appDataSource,
		String metaSchemaName,
		String stateTableName) throws IndeterminateStateException
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (metaSchemaName == null) throw new ArgumentNullException("metaSchemaName");
		if ("".equals(metaSchemaName)) throw new IllegalArgumentException("metaSchemaName cannot be empty");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");

		PostgreSqlStateHelper.createStateTableIfNotExists(
			appDataSource,
			metaSchemaName,
			stateTableName);

		UUID stateId = PostgreSqlStateHelper.stateIdScalarOptional(
			resourceId,
			appDataSource,
			metaSchemaName, stateTableName);

		if (stateId == null)
		{
			throw new IndeterminateStateException(String.format(
				"The state tracking table \"%s\" was not found in the target schema",
				stateTableName));
		}

		return stateId;
	}

	/**
	 * Indicates whether or not a state is currently tracked for the specified resource.
	 *
	 * @param resourceId     the ID of the resource for which the state should be queried.
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param metaSchemaName the name of the meta-data tracking schema to use.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @return a boolean value indicating whether or not a state is currently tracked
	 * for the specified resource.
	 * @throws IndeterminateStateException if the state of the resource cannot be determined
	 * @since 4.0
	 */
	public static boolean hasStateId(
		UUID resourceId,
		DataSource appDataSource,
		String metaSchemaName,
		String stateTableName) throws IndeterminateStateException
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (metaSchemaName == null) throw new ArgumentNullException("metaSchemaName");
		if ("".equals(metaSchemaName)) throw new IllegalArgumentException("metaSchemaName cannot be empty");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");

		PostgreSqlStateHelper.createStateTableIfNotExists(
			appDataSource,
			metaSchemaName,
			stateTableName);

		UUID stateId = PostgreSqlStateHelper.stateIdScalarOptional(
			resourceId,
			appDataSource,
			metaSchemaName, stateTableName);

		return stateId != null;
	}

	/**
	 * Creates the state tracking table in a PostgreSqlDatabaseInstance.
	 *
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param metaSchemaName the name of the meta-data tracking schema to use.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @throws SQLException if an error occurs when interacting with the database.
	 * @since 1.0
	 */
	private static void createStateTableIfNotExists(
		DataSource appDataSource,
		String metaSchemaName,
		String stateTableName)
	{
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (metaSchemaName == null) throw new ArgumentNullException("metaSchemaName");
		if ("".equals(metaSchemaName)) throw new IllegalArgumentException("metaSchemaName cannot be empty");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");

		try
		{
			DatabaseHelper.execute(
				appDataSource,
				String.format("CREATE SCHEMA IF NOT EXISTS %s;", metaSchemaName));

			DatabaseHelper.execute(appDataSource, new StringBuilder()
				.append("CREATE TABLE IF NOT EXISTS ")
				.append(metaSchemaName).append(".")
				.append(stateTableName).append("(")
				.append("ResourceId UUID NOT NULL, ")
				.append("StateId UUID NOT NULL, ")
				.append("LastMigrationInstant timestamp NOT NULL, ")
				.append("CONSTRAINT PK_").append(stateTableName).append(" PRIMARY KEY (ResourceId)")
				.append(");").toString());
		}
		catch (SQLException e)
		{
			throw new FaultException(e);
		}
	}

	/**
	 * Data access method for retrieving the state ID for the specified resource, if it is tracked.  If not then this
	 * method will return null.
	 *
	 * @param resourceId     the ID of the resource for which the state should be queried.
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param metaSchemaName the name of the meta-data tracking schema to use.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @return the ID of the tracked state for this resource if tracked, or null
	 * otherwise.
	 * @throws IndeterminateStateException if multiple states are tracked for the
	 *                                     specified resource.
	 * @since 4.0
	 */
	private static UUID stateIdScalarOptional(
		UUID resourceId,
		DataSource appDataSource,
		String metaSchemaName,
		String stateTableName) throws IndeterminateStateException
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (metaSchemaName == null) throw new ArgumentNullException("metaSchemaName");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");

		UUID result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = appDataSource.getConnection();
			ps = conn.prepareStatement(String.format(
				"SELECT StateId FROM %s.%s WHERE ResourceId = ?::uuid;",
				metaSchemaName,
				stateTableName));
			ps.setObject(1, resourceId.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				result = (UUID)rs.getObject(1);

				if (rs.next())
				{
					throw new IndeterminateStateException(String.format(
						"Multiple rows found in the state tracking table \"%s\"",
						stateTableName));
				}
			}
		}
		catch (SQLException e)
		{
			throw new FaultException(e);
		}
		finally
		{
			try
			{
				DatabaseHelper.release(rs);
				DatabaseHelper.release(ps);
				DatabaseHelper.release(conn);
			}
			catch (SQLException e)
			{
				throw new FaultException(e);
			}
		}

		return result;
	}
}
