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

package co.zd.wb.plugin.mysql;

import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.FaultException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author brendonm
 */
public class MySqlDatabaseHelper
{
	public static boolean schemaExists(
		MySqlDatabaseInstance instance,
		String schemaName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName)) { throw new IllegalArgumentException("schemaName cannot be empty"); }
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = instance.getInfoDataSource().getConnection();
			ps = conn.prepareStatement("SELECT * FROM SCHEMATA WHERE SCHEMA_NAME = ?;");
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
	
	public static boolean tableExists(
		MySqlDatabaseInstance instance,
		String schemaName,
		String tableName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		if (schemaName == null) { throw new IllegalArgumentException("schemaName cannot be null"); }
		if ("".equals(schemaName)) { throw new IllegalArgumentException("schemaName cannot be empty"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName)) { throw new IllegalArgumentException("tableName cannot be empty"); }
		
		StringBuilder query = new StringBuilder();
		query
			.append("SELECT TABLE_NAME FROM TABLES ")
			.append("WHERE ")
				.append("TABLE_SCHEMA = ? AND ")
				.append("TABLE_NAME = ?;");

		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = instance.getInfoDataSource().getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, instance.getSchemaName());
			ps.setString(2, tableName);
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
			catch (SQLException e)
			{
				throw new FaultException(e);
			}
		}
		
		return result;
	}
}
