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

package co.mv.wb.plugin.sqlserver;

import co.mv.wb.FaultException;
import co.mv.wb.Instance;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.plugin.generaldatabase.DatabaseConstants;
import co.mv.wb.plugin.generaldatabase.DatabaseInstance;
import co.mv.wb.plugin.generaldatabase.JdbcDatabaseInstance;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An {@link Instance} of a SQL Server database.
 *
 * @since 2.0
 */
public class SqlServerDatabaseInstance implements DatabaseInstance,
	JdbcDatabaseInstance
{
	private final String hostName;
	private final String instanceName;
	private final int port;
	private final String adminUsername;
	private final String adminPassword;
	private String databaseName;
	private String stateTableName;

	/**
	 * Creates a new SqlServerDatabaseInstance.
	 *
	 * @param hostName       the host name of the server for this instance.
	 * @param instanceName   the SQL Server instance name for this instance.  Null may be supplied
	 *                       where no instance name needs to be specified.
	 * @param port           the port number of the server for this instance.
	 * @param adminUsername  the username of the user that will be used to administer the database
	 *                       represented by this instance.
	 * @param adminPassword  the password of the user that will be used to administer the database
	 *                       represented by this instance.
	 * @param databaseName   the name of the database for this instance.
	 * @param stateTableName the optional name for the state tracking table.  If null is supplied for
	 *                       this parameter, the default name will be used.
	 * @since 2.0
	 */
	public SqlServerDatabaseInstance(
		String hostName,
		String instanceName,
		int port,
		String adminUsername,
		String adminPassword,
		String databaseName,
		String stateTableName)
	{
		if (hostName == null) throw new ArgumentNullException("hostName");
		if (adminUsername == null) throw new ArgumentNullException("adminUsername");
		if (adminPassword == null) throw new ArgumentNullException("adminPassword");
		if (databaseName == null) throw new ArgumentNullException("databaseName");

		if (stateTableName == null || stateTableName.trim().equals(""))
		{
			stateTableName = DatabaseConstants.DefaultStateTableName;
		}

		this.hostName = hostName;
		this.instanceName = instanceName;
		this.port = port;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.databaseName = databaseName;
		this.stateTableName = stateTableName;
	}

	@Override public final String getHostName()
	{
		return this.hostName;
	}

	@Override public final int getPort()
	{
		return this.port;
	}

	@Override public final String getAdminUsername()
	{
		return this.adminUsername;
	}

	@Override public final String getAdminPassword()
	{
		return this.adminPassword;
	}

	@Override public final String getDatabaseName()
	{
		return this.databaseName;
	}

	@Override public final String getStateTableName()
	{
		return this.stateTableName;
	}

	@Override public boolean hasStateTableName()
	{
		return this.stateTableName != null;
	}

	// </editor-fold>

	/**
	 * Returns a DataSource for the master database in the target SQL Server instance.
	 *
	 * @since 2.0
	 */
	@Override public DataSource getAdminDataSource()
	{
		SQLServerDataSource result = new SQLServerDataSource();
		result.setServerName(this.getHostName());
		if (this.instanceName != null)
		{
			result.setInstanceName(this.instanceName);
		}
		result.setPortNumber(this.getPort());
		result.setUser(this.getAdminUsername());
		result.setPassword(this.getAdminPassword());
		result.setDatabaseName("master");

		return result;
	}

	/**
	 * Returns a DataSource for the application database defined by this instance.
	 *
	 * @since 2.0
	 */
	@Override public DataSource getAppDataSource()
	{
		SQLServerDataSource result = new SQLServerDataSource();
		result.setServerName(this.getHostName());
		if (this.instanceName != null)
		{
			result.setInstanceName(this.instanceName);
		}
		result.setPortNumber(this.getPort());
		result.setUser(this.getAdminUsername());
		result.setPassword(this.getAdminPassword());
		result.setDatabaseName(this.getDatabaseName());

		return result;
	}

	@Override public boolean databaseExists()
	{
		boolean result;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = this.getAdminDataSource().getConnection();

			ps = conn.prepareStatement(
				"SELECT * FROM master.dbo.sysdatabases WHERE ('[' + name + ']' = ?) OR name = ?;");
			ps.setString(1, this.getDatabaseName());
			ps.setString(2, this.getDatabaseName());

			rs = ps.executeQuery();

			result = rs.next();
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
