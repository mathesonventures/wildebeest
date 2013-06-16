package co.mv.stm.model.database;

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