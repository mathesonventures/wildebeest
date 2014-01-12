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
import java.util.UUID;
import javax.sql.DataSource;

/**
 * Provides a set of convenience methods for working with JDBC-accessed databases.
 * 
 * @author                                      Brendon Matheson
 */
public class DatabaseHelper
{
	/**
	 * Executes a SQL statement against the database represented by the supplied DataSource.
	 * 
	 * @param       dataSource                  the DataSource that represents the database to work with
	 * @param       sql                         the SQL statement to execute against the target database.
	 * @throws      SQLException                may be thrown due to a mal-formed SQL statement, connectivity problem,
	 *                                          or some other issue.
	 */
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
	
	/**
	 * Executes a SQL query against the database represented by the supplied DataSource and returns the value from the
	 * first column of the single resultant row as an Object.
	 * 
	 * @param       dataSource                  the DataSource that represents the database to work with
	 * @param       sql                         the SQL query to execute against the target database
	 * @return                                  the value from the first column of the single resultant row
	 * @throws      SQLException                may be thrown due to a mal-formed SQL statement, connectivity problem,
	 *                                          or some other issue.
	 */
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
			
			// TODO: Fail if there is more than one row in the resultset.
		}
		finally
		{
			DatabaseHelper.release(rs);
			DatabaseHelper.release(ps);
			DatabaseHelper.release(conn);
		}
		
		return result;
	}

	/**
	 * If the supplied Connection reference is non-null, attempts to close that Connection.
	 * 
	 * @param       conn                        the Connection to be closed.  Ignored if null is supplied.
	 * @throws      SQLException                may be thrown due to a state exception, connectivity problem, or some
	 *                                          other issue.
	 */
	public static void release(Connection conn) throws SQLException
	{
		if (conn != null)
		{
			conn.close();
		}
	}

	/**
	 * If the supplied PreparedStatement reference is non-null, attempts to close that PreparedStatement.
	 * 
	 * @param       ps                          the PreparedStatement to be closed.  Ignored if null is supplied.
	 * @throws      SQLException                may be thrown due to a state exception, connectivity problem or some
	 *                                          other issue.
	 */
	public static void release(PreparedStatement ps) throws SQLException
	{
		if (ps != null)
		{
			ps.close();
		}
	}
	
	/**
	 * If the supplied ResultSet is non-null, attempts to close that ResultSet.
	 * 
	 * @param       rs                          the ResultSet to be closed.  Ignored if null is supplied.
	 * @throws      SQLException                may be thrown due to a state exception, connectiivty problem or some
	 *                                          other issue.
	 */
	public static void release(ResultSet rs) throws SQLException
	{
		if (rs != null)
		{
			rs.close();
		}
	}
}