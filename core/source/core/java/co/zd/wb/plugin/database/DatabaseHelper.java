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

package co.zd.wb.plugin.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DatabaseHelper
{
	public static void execute(
		DataSource dataSource,
		String sql) throws SQLException
	{
		if (dataSource == null) { throw new IllegalArgumentException("dataSource"); }
		if (sql == null) { throw new IllegalArgumentException("sql"); }
		if ("".equals(sql)) { throw new IllegalArgumentException("sql cannot be empty"); }
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try
		{
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.execute();
		}
		finally
		{
			DatabaseHelper.release(ps);
			DatabaseHelper.release(conn);
		}
	}
	
	public static Object querySingle(
		DataSource dataSource,
		String sql) throws SQLException
	{
		if (dataSource == null) { throw new IllegalArgumentException("dataSource"); }
		if (sql == null) { throw new IllegalArgumentException("sql"); }
		if ("".equals(sql)) { throw new IllegalArgumentException("sql cannot be empty"); }
		
		Object result = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next())
			{
				result = rs.getObject(1);
			}
		}
		finally
		{
			DatabaseHelper.release(rs);
			DatabaseHelper.release(ps);
			DatabaseHelper.release(conn);
		}
		
		return result;
	}
	
	public static void release(Connection conn) throws SQLException
	{
		if (conn != null)
		{
			conn.close();
		}
	}
	
	public static void release(PreparedStatement ps) throws SQLException
	{
		if (ps != null)
		{
			ps.close();
		}
	}
	
	public static void release(ResultSet rs) throws SQLException
	{
		if (rs != null)
		{
			rs.close();
		}
	}
}