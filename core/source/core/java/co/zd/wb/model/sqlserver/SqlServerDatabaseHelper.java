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

package co.zd.wb.model.sqlserver;

import co.zd.wb.model.database.DatabaseHelper;
import co.zd.wb.model.FaultException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author brendonm
 */
public class SqlServerDatabaseHelper
{
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
	
	public static boolean tableExists(
		SqlServerDatabaseInstance instance,
		String tableName)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName.trim())) { throw new IllegalArgumentException("instance cannot be empty"); }
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = instance.getAppDataSource().getConnection();
			
			ps = conn.prepareStatement(
				"SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(?) AND TYPE IN (N'U')");
			ps.setString(1, "[dbo].[" + tableName + "]");
			
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
}
