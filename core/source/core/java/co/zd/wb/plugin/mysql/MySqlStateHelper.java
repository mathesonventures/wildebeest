package co.zd.wb.plugin.mysql;

import co.zd.wb.FaultException;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.plugin.database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

public class MySqlStateHelper
{
	public static void setStateId(
		UUID resourceId,
		DataSource appDataSource,
		String stateTableName,
		UUID stateId) throws SQLException
	{
		if (resourceId == null) { throw new IllegalArgumentException("resourceId cannot be null"); }
		if (appDataSource == null) { throw new IllegalArgumentException("appDataSource"); }
		if (stateTableName == null) { throw new IllegalArgumentException("stateTableName cannot be null"); }
		if ("".equals(stateTableName)) { throw new IllegalArgumentException("stateTableName cannot be empty"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }
		
		MySqlStateHelper.createStateTableIfNotExists(
			appDataSource,
			stateTableName);
		
		DatabaseHelper.execute(
			appDataSource,
			String.format(
				"DELETE FROM %s WHERE ResourceId = '%s';",
				stateTableName,
				resourceId));

		DatabaseHelper.execute(
			appDataSource,
			String.format(
				"INSERT INTO %s(ResourceId, StateId) VALUES('%s', '%s');",
				stateTableName,
				resourceId,
				stateId));
	}
	
	public static UUID getStateId(
		UUID resourceId,
		DataSource appDataSource,
		String stateTableName) throws IndeterminateStateException
	{
		if (resourceId == null) { throw new IllegalArgumentException("resourceId cannot be null"); }
		if (appDataSource == null) { throw new IllegalArgumentException("appDataSource"); }
		if (stateTableName == null) { throw new IllegalArgumentException("stateTableName cannot be null"); }
		if ("".equals(stateTableName)) { throw new IllegalArgumentException("stateTableName cannot be empty"); }
		
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

		return stateId;
	}

	private static void createStateTableIfNotExists(
		DataSource appDataSource,
		String stateTableName) throws SQLException
	{
		if (appDataSource == null) { throw new IllegalArgumentException("appDataSource"); }
		if (stateTableName == null) { throw new IllegalArgumentException("stateTableName cannot be null"); }
		if ("".equals(stateTableName)) { throw new IllegalArgumentException("stateTableName cannot be empty"); }
		
		DatabaseHelper.execute(appDataSource, new StringBuilder()
			.append("CREATE TABLE IF NOT EXISTS `").append(stateTableName).append("`(")
				.append("`ResourceId` char(36) NOT NULL, ")
				.append("`StateId` char(36) NOT NULL, ")
				.append("PRIMARY KEY (`ResourceId`)")
				.append(");").toString());
	}
}
