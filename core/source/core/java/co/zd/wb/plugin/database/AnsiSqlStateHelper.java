package co.zd.wb.plugin.database;

import co.zd.wb.FaultException;
import co.zd.wb.IndeterminateStateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A helper for working with the Wildebeeste state table in database systems that support ANSI-SQL.
 * 
 * @author brendonm
 */
public class AnsiSqlStateHelper
{
	public static void createStateTable(
		DatabaseInstance instance,
		String tableName) throws SQLException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName)) { throw new IllegalArgumentException("tableName cannot be empty"); }
		
		DatabaseHelper.execute(instance.getAppDataSource(), new StringBuilder()
			.append("CREATE TABLE `").append(Extensions.getStateTableName(instance)).append("`(")
				.append("`InstanceId` char(36) NOT NULL, ")
				.append("`StateId` char(36) NOT NULL, ")
				.append("PRIMARY KEY (`InstanceId`)")
				.append(");").toString());
	}
	
	public static void createStateTableIfNotExists(
		DatabaseInstance instance,
		String tableName) throws SQLException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		if (tableName == null) { throw new IllegalArgumentException("tableName cannot be null"); }
		if ("".equals(tableName)) { throw new IllegalArgumentException("tableName cannot be empty"); }
		
		DatabaseHelper.execute(instance.getAppDataSource(), new StringBuilder()
			.append("CREATE TABLE IF NOT EXISTS `").append(Extensions.getStateTableName(instance)).append("`(")
				.append("`InstanceId` char(36) NOT NULL, ")
				.append("`StateId` char(36) NOT NULL, ")
				.append("PRIMARY KEY (`InstanceId`)")
				.append(");").toString());
	}
	
	public static void setStateId(
		DatabaseInstance instance,
		UUID stateId) throws SQLException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }
		
		DatabaseHelper.execute(
			instance.getAppDataSource(),
			String.format(
				"DELETE FROM %s WHERE InstanceId = '%s';",
				Extensions.getStateTableName(instance),
				instance.getInstanceId()));

		DatabaseHelper.execute(
			instance.getAppDataSource(),
			String.format(
				"INSERT INTO %s(InstanceId, StateId) VALUES('%s', '%s');",
				Extensions.getStateTableName(instance),
				instance.getInstanceId().toString(),
				stateId));
	}
	
	public static UUID getStateId(
		DatabaseInstance db) throws IndeterminateStateException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		
		String stateTableName = Extensions.getStateTableName(db);
		
		UUID stateId = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = db.getAppDataSource().getConnection();
			ps = conn.prepareStatement("SELECT StateId FROM " + stateTableName + ";");
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
}
