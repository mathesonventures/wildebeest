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
 * @since                                       2.0
 */
public class SqlServerDatabaseInstance implements DatabaseInstance, JdbcDatabaseInstance
{
	/**
	 * Creates a new SqlServerDatabaseInstance.
	 * 
	 * @param       hostName                    the host name of the server for this instance.
	 * @param       instanceName                the SQL Server instance name for this instance.  Null may be supplied
	 *                                          where no instance name needs to be specified.
	 * @param       port                  the port number of the server for this instance.
	 * @param       adminUsername               the username of the user that will be used to administer the database
	 *                                          represented by this instance.
	 * @param       adminPassword               the password of the user that will be used to administer the database
	 *                                          represented by this instance.
	 * @param       databaseName                the name of the database for this instance.
	 * @param       stateTableName              the optional name for the state tracking table.  If null is supplied for
	 *                                          this parameter, the default name will be used.
	 * @since                                   2.0
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
		if (stateTableName == null || stateTableName.trim().equals(""))
		{
			stateTableName = DatabaseConstants.DefaultStateTableName;
		}
		
		this.setHostName(hostName);
		if (instanceName != null)
		{
			this.setInstanceName(instanceName);
		}
		this.setPort(port);
		this.setAdminUsername(adminUsername);
		this.setAdminPassword(adminPassword);
		this.setDatabaseName(databaseName);
		if (stateTableName != null)
		{
			this.setStateTableName(stateTableName);
		}
	}

	// <editor-fold desc="HostName" defaultstate="collapsed">

	private String _hostName = null;
	private boolean _hostName_set = false;

	@Override public final String getHostName() {
		if(!_hostName_set) {
			throw new IllegalStateException("hostName not set.  Use the HasHostName() method to check its state before accessing it.");
		}
		return _hostName;
	}

	public final void setHostName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("hostName cannot be null");
		}
		boolean changing = !_hostName_set || !_hostName.equals(value);
		if(changing) {
			_hostName_set = true;
			_hostName = value;
		}
	}

	private void clearHostName() {
		if(_hostName_set) {
			_hostName_set = true;
			_hostName = null;
		}
	}

	private boolean hasHostName() {
		return _hostName_set;
	}

	// </editor-fold>

	// <editor-fold desc="InstanceName" defaultstate="collapsed">

	private String _instanceName = null;
	private boolean _instanceName_set = false;

	private String getInstanceName() {
		if(!_instanceName_set) {
			throw new IllegalStateException("instanceName not set.  Use the HasInstanceName() method to check its state before accessing it.");
		}
		return _instanceName;
	}

	public final void setInstanceName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceName cannot be null");
		}
		boolean changing = !_instanceName_set || !_instanceName.equals(value);
		if(changing) {
			_instanceName_set = true;
			_instanceName = value;
		}
	}

	private void clearInstanceName() {
		if(_instanceName_set) {
			_instanceName_set = true;
			_instanceName = null;
		}
	}

	private boolean hasInstanceName() {
		return _instanceName_set;
	}

	// </editor-fold>

	// <editor-fold desc="Port" defaultstate="collapsed">

	private int _port = 0;
	private boolean _port_set = false;

	@Override public final int getPort() {
		if(!_port_set) {
			throw new IllegalStateException("port not set.");
		}
		return _port;
	}

	public final void setPort(
		int value) {
		boolean changing = !_port_set || _port != value;
		if(changing) {
			_port_set = true;
			_port = value;
		}
	}

	private void clearPort() {
		if(_port_set) {
			_port_set = true;
			_port = 0;
		}
	}

	private boolean hasPort() {
		return _port_set;
	}

	// </editor-fold>

	// <editor-fold desc="AdminUsername" defaultstate="collapsed">

	private String _adminUsername = null;
	private boolean _adminUsername_set = false;

	@Override public final String getAdminUsername() {
		if(!_adminUsername_set) {
			throw new IllegalStateException("adminUsername not set.  Use the HasAdminUsername() method to check its state before accessing it.");
		}
		return _adminUsername;
	}

	public final void setAdminUsername(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("adminUsername cannot be null");
		}
		boolean changing = !_adminUsername_set || !_adminUsername.equals(value);
		if(changing) {
			_adminUsername_set = true;
			_adminUsername = value;
		}
	}

	private void clearAdminUsername() {
		if(_adminUsername_set) {
			_adminUsername_set = true;
			_adminUsername = null;
		}
	}

	private boolean hasAdminUsername() {
		return _adminUsername_set;
	}

	// </editor-fold>

	// <editor-fold desc="AdminPassword" defaultstate="collapsed">

	private String _adminPassword = null;
	private boolean _adminPassword_set = false;

	@Override public final String getAdminPassword() {
		if(!_adminPassword_set) {
			throw new IllegalStateException("adminPassword not set.  Use the HasAdminPassword() method to check its state before accessing it.");
		}
		return _adminPassword;
	}

	public final void setAdminPassword(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("adminPassword cannot be null");
		}
		boolean changing = !_adminPassword_set || !_adminPassword.equals(value);
		if(changing) {
			_adminPassword_set = true;
			_adminPassword = value;
		}
	}

	private void clearAdminPassword() {
		if(_adminPassword_set) {
			_adminPassword_set = true;
			_adminPassword = null;
		}
	}

	private boolean hasAdminPassword() {
		return _adminPassword_set;
	}

	// </editor-fold>

	// <editor-fold desc="DatabaseName" defaultstate="collapsed">

	private String _databaseName = null;
	private boolean _databaseName_set = false;

	@Override public final String getDatabaseName() {
		if(!_databaseName_set) {
			throw new IllegalStateException("databaseName not set.");
		}
		if(_databaseName == null) {
			throw new IllegalStateException("databaseName should not be null");
		}
		return _databaseName;
	}

	@Override public final void setDatabaseName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("databaseName cannot be null");
		}
		boolean changing = !_databaseName_set || !_databaseName.equals(value);
		if(changing) {
			_databaseName_set = true;
			_databaseName = value;
		}
	}

	private void clearDatabaseName() {
		if(_databaseName_set) {
			_databaseName_set = true;
			_databaseName = null;
		}
	}

	private boolean hasDatabaseName() {
		return _databaseName_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateTableName" defaultstate="collapsed">

	private String _stateTableName = null;
	private boolean _stateTableName_set = false;

	@Override public final String getStateTableName() {
		if(!_stateTableName_set) {
			throw new IllegalStateException("stateTableName not set.  Use the HasStateTableName() method to check its state before accessing it.");
		}
		return _stateTableName;
	}

	@Override public final void setStateTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("stateTableName cannot be null");
		}
		boolean changing = !_stateTableName_set || !_stateTableName.equals(value);
		if(changing) {
			_stateTableName_set = true;
			_stateTableName = value;
		}
	}

	@Override public void clearStateTableName() {
		if(_stateTableName_set) {
			_stateTableName_set = true;
			_stateTableName = null;
		}
	}

	@Override public boolean hasStateTableName() {
		return _stateTableName_set;
	}

	// </editor-fold>

	/**
	 * Returns a DataSource for the master database in the target SQL Server instance.
	 * 
	 * @since                                   2.0
	 */
	@Override public DataSource getAdminDataSource()
	{
		SQLServerDataSource result = new SQLServerDataSource();
		result.setServerName(this.getHostName());
		if (this.hasInstanceName())
		{
			result.setInstanceName(this.getInstanceName());
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
	 * @since                                   2.0
	 */
	@Override public DataSource getAppDataSource()
	{
		SQLServerDataSource result = new SQLServerDataSource();
		result.setServerName(this.getHostName());
		if (this.hasInstanceName())
		{
			result.setInstanceName(this.getInstanceName());
		}
		result.setPortNumber(this.getPort());
		result.setUser(this.getAdminUsername());
		result.setPassword(this.getAdminPassword());
		result.setDatabaseName(this.getDatabaseName());
		
		return result;
	}

	@Override public boolean databaseExists()
	{
		boolean result = false;
		
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
