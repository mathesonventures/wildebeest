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
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.framework.SqlParameters;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Centralizes state tracking operations for MySqlDatabaseInstances.
 *
 * @since 1.0
 */
public class MySqlStateHelper
{
	/**
	 * Sets the tracked state for an instance.  Note that a MySqlDatabaseInstance may be migrated by multiple separate
	 * resource definitions (to support composite resources).  The state set is for a specific resource definition.
	 *
	 * @param resourceId     the ID of the resource for which we are tracking state.
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @param stateId        the ID of the state.
	 * @throws SQLException if an error occurs when interacting with the database.
	 * @since 1.0
	 */
	public static void setStateId(
		UUID resourceId,
		DataSource appDataSource,
		String stateTableName,
		UUID stateId) throws SQLException
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");
		if (stateId == null) throw new ArgumentNullException("stateId");

		MySqlStateHelper.createStateTableIfNotExists(
			appDataSource,
			stateTableName);

		DatabaseHelper.execute(
			  appDataSource,
			  String.format(
					"DELETE FROM %s WHERE ResourceId = ?;",
					stateTableName),
			  Arrays.asList(
					new SqlParameters("resourceId", resourceId)
			  ));

		DatabaseHelper.execute(
			appDataSource,
			String.format(
				"INSERT INTO %s(ResourceId, StateId, LastMigrationInstant) VALUES(?, ?, ?);",  // TODO: What is the correct syntax for indexed parameters?  Also note we'll still need to String.format to get the table name in place - not ideal but we can improve that later.
				stateTableName),
			Arrays.asList(
				new SqlParameters("resourceId", resourceId),
				new SqlParameters("stateId", stateId),
				new SqlParameters("lastUpdatedInstant", DatabaseHelper.getInstant())
			));
	}

	/**
	 * Gets the tracked state for an instance.  Note that a MySqlDatabaseInstance may be migrated by multiple separate
	 * resource definitions (to support composite resources).  The state retrieved is for a specific resource
	 * definition.
	 *
	 * @param resourceId     the ID of the resource for which the state should be queried.
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @return the ID or name of the state that the instance is in for the specified resource.
	 * @throws IndeterminateStateException if the current state of the instance cannot be determined for the
	 *                                     specified resource.
	 * @since 1.0
	 */
	public static UUID getStateId(
		UUID resourceId,
		DataSource appDataSource,
		String stateTableName) throws IndeterminateStateException
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");

		UUID stateId = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = appDataSource.getConnection();
			ps = conn.prepareStatement(
				"SELECT StateId FROM " + stateTableName + " WHERE ResourceId = ?;");
			ps.setString(1, resourceId.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				stateId = UUID.fromString(rs.getString(1));

				if (rs.next())
				{
					throw new IndeterminateStateException(String.format(
						"Multiple rows found in the state tracking table \"%s\"",
						stateTableName));
				}
			}
			else
			{
				throw new IndeterminateStateException(String.format(
					"The state tracking table \"%s\" was not found in the target schema",
					stateTableName));
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

		return stateId;
	}

	/**
	 * Creates the state tracking table in a MySqlDatabaseInstance.
	 *
	 * @param appDataSource  the DataSource for interacting with the database.
	 * @param stateTableName the name of the state tracking table in use for this instance.
	 * @throws SQLException if an error occurs when interacting with the database.
	 * @since 1.0
	 */
	private static void createStateTableIfNotExists(
		DataSource appDataSource,
		String stateTableName) throws SQLException
	{
		if (appDataSource == null) throw new ArgumentNullException("appDataSource");
		if (stateTableName == null) throw new ArgumentNullException("stateTableName");
		if ("".equals(stateTableName)) throw new IllegalArgumentException("stateTableName cannot be empty");

		DatabaseHelper.execute(appDataSource, new StringBuilder()
			.append("CREATE TABLE IF NOT EXISTS `").append(stateTableName).append("`(")
			.append("`ResourceId` char(36) NOT NULL, ")
			.append("`StateId` char(36) NOT NULL, ")
			.append("`LastMigrationInstant` datetime NOT NULL, ")
			.append("PRIMARY KEY (`ResourceId`)")
			.append(");").toString(),
			false);
	}
}
