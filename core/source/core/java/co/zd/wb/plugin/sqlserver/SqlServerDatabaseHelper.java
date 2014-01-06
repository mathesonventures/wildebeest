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

import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.FaultException;
import co.zd.wb.plugin.database.Extensions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Functional helper methods for working with SQL Server databases.
 * 
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
public class SqlServerDatabaseHelper
{
	/**
	 * Returns an indication of whether or not the SQL Server database represented by the supplied instance exists.
	 * 
	 * @param       instance                    the SqlServerDatabseInstance to check.
	 * @return                                  an indication of whether or not the SQL Server database exists
	 * @since                                   2.0
	 */
	public static boolean databaseExists(SqlServerDatabaseInstance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = instance.getMasterDataSource().getConnection();
			
			ps = conn.prepareStatement(
				"SELECT * FROM master.dbo.sysdatabases WHERE ('[' + name + ']' = ?) OR name = ?;");
			ps.setString(1, instance.getDatabaseName());
			ps.setString(2, instance.getDatabaseName());
			
			rs = ps.executeQuery();
		
			result = rs.next();
		}
		catch(SQLException e)
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
			catch(SQLException e)
			{
				throw new FaultException(e);
			}
		}
		
		return result;
	}

	/**
	 * Returns an indication of whether or not the SQL Server database represented by the supplied instance contains
	 * a given schema.
	 * 
	 * @param       instance                    the SqlServerDatabaseIntance to check.
	 * @param       schemaName                  the name of the schema to check for.
	 * @return                                  an indication of whether or not the schema exists.
	 * @since                                   2.0
	 */
	public static boolean schemaExists(
		SqlServerDatabaseInstance instance,
		String schemaName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName.trim())) { throw new IllegalArgumentException("schemaName cannot be empty"); }

		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = instance.getAppDataSource().getConnection();
			
			ps = conn.prepareStatement(
				"SELECT * FROM sys.schemas WHERE name = ?");
			ps.setString(1, schemaName);
	
			rs = ps.executeQuery();
		
			result = rs.next();
		}
		catch(SQLException e)
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
			catch(SQLException e)
			{
				throw new FaultException(e);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns an indication of whether or not a SQL Server database schema contains a given table.
	 * 
	 * @param       instance                    the SqlServerDatabseInstance to check.
	 * @param       schemaName                  the name of the schema that should contain the table.
	 * @param       tableName                   the name of the table to check for.
	 * @return                                  an indication of whether or not the table exists.
	 * since                                    2.0
	 */
	public static boolean tableExists(
		SqlServerDatabaseInstance instance,
		String schemaName,
		String tableName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName.trim())) { throw new IllegalArgumentException("schemaName cannot be empty"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName.trim())) { throw new IllegalArgumentException("tableName cannot be empty"); }
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = instance.getAppDataSource().getConnection();
			
			ps = conn.prepareStatement(
				"SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(?) AND TYPE IN (N'U')");
			ps.setString(1, "[" + schemaName + "].[" + tableName + "]");
			
			rs = ps.executeQuery();
		
			result = rs.next();
		}
		catch(SQLException e)
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
			catch(SQLException e)
			{
				throw new FaultException(e);
			}
		}
		
		return result;
	}
	
	public static void createTableIfNotExists(
		SqlServerDatabaseInstance instance,
		String tableName) throws SQLException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName)) { throw new IllegalArgumentException("tableName cannot be empty"); }
		
		DatabaseHelper.execute(instance.getAppDataSource(), new StringBuilder()
			.append("CREATE TABLE IF NOT EXISTS `").append(Extensions.getStateTableName(instance))
				.append("`(`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`));").toString());
	}
	
	public static void setStateId(
		SqlServerDatabaseInstance instance,
		UUID stateId) throws SQLException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }
		
		DatabaseHelper.execute(
			instance.getAppDataSource(),
			String.format("DELETE FROM %s;", Extensions.getStateTableName(instance)));

		DatabaseHelper.execute(
			instance.getAppDataSource(),
			String.format(
				"INSERT INTO %s(StateId) VALUES('%s');",
				Extensions.getStateTableName(instance),
				stateId));
	}
}
