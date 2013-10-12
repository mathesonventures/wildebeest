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

import co.zd.wb.Instance;
import co.zd.wb.plugin.database.DatabaseConstants;
import co.zd.wb.plugin.database.DatabaseInstance;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;

/**
 * An {@link Instance} of a {@link SqlServerDatabaseResource}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.1
 */
public class SqlServerDatabaseInstance implements Instance, DatabaseInstance
{
	/**
	 * Creates a new SqlServerDatabaseInstance.
	 * 
	 * @param       hostName                    the host name of the server for this instance.
	 * @param       instanceName                the SQL Server instance name for this instance.  Null may be supplied
	 *                                          where no instance name needs to be specified.
	 * @param       portNumber                  the port number of the server for this instance.
	 * @param       adminUsername               the username of the user that will be used to administer the database
	 *                                          represented by this instance.
	 * @param       adminPassword               the password of the user that will be used to administer the database
	 *                                          represented by this instance.
	 * @param       databaseName                the name of the database for this instance.
	 * @param       stateTableName              the optional name for the state tracking table.  If null is supplied for
	 *                                          this parameter, the default name will be used.
	 * @since                                   1.1
	 */
	public SqlServerDatabaseInstance(
		String hostName,
		String instanceName,
		int portNumber,
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
		this.setPortNumber(portNumber);
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

	/**
	 * Returns the host name of the server for this instance.
	 * 
	 * @since                                   1.1
	 */
	public String getHostName() {
		if(!_hostName_set) {
			throw new IllegalStateException("hostName not set.  Use the HasHostName() method to check its state before accessing it.");
		}
		return _hostName;
	}

	private void setHostName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("hostName cannot be null");
		}
		boolean changing = !_hostName_set || _hostName != value;
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

	private void setInstanceName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceName cannot be null");
		}
		boolean changing = !_instanceName_set || _instanceName != value;
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

	// <editor-fold desc="PortNumber" defaultstate="collapsed">

	private int _portNumber = 0;
	private boolean _portNumber_set = false;

	/**
	 * Returns the port number of the server for this instance.
	 * 
	 * @since                                   1.1
	 */
	public int getPortNumber() {
		if(!_portNumber_set) {
			throw new IllegalStateException("portNumber not set.  Use the HasPortNumber() method to check its state before accessing it.");
		}
		return _portNumber;
	}

	private void setPortNumber(
		int value) {
		boolean changing = !_portNumber_set || _portNumber != value;
		if(changing) {
			_portNumber_set = true;
			_portNumber = value;
		}
	}

	private void clearPortNumber() {
		if(_portNumber_set) {
			_portNumber_set = true;
			_portNumber = 0;
		}
	}

	private boolean hasPortNumber() {
		return _portNumber_set;
	}

	// </editor-fold>

	// <editor-fold desc="AdminUsername" defaultstate="collapsed">

	private String _adminUsername = null;
	private boolean _adminUsername_set = false;

	/**
	 * Returns the username of the user that will be used to administer the database represented by this instance.
	 * 
	 * @since                                   1.1
	 */
	public String getAdminUsername() {
		if(!_adminUsername_set) {
			throw new IllegalStateException("adminUsername not set.  Use the HasAdminUsername() method to check its state before accessing it.");
		}
		return _adminUsername;
	}

	private void setAdminUsername(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("adminUsername cannot be null");
		}
		boolean changing = !_adminUsername_set || _adminUsername != value;
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

	/**
	 * Returns the password of the user that will be used to administer the database represented by this instance.
	 * 
	 * @since                                   1.1
	 */
	public String getAdminPassword() {
		if(!_adminPassword_set) {
			throw new IllegalStateException("adminPassword not set.  Use the HasAdminPassword() method to check its state before accessing it.");
		}
		return _adminPassword;
	}

	private void setAdminPassword(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("adminPassword cannot be null");
		}
		boolean changing = !_adminPassword_set || _adminPassword != value;
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

	/**
	 * Returns the name of the database for this instance.
	 * 
	 * @since                                   1.1
	 */
	public String getDatabaseName() {
		if(!_databaseName_set) {
			throw new IllegalStateException("databaseName not set.  Use the HasDatabaseName() method to check its state before accessing it.");
		}
		return _databaseName;
	}

	private void setDatabaseName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("databaseName cannot be null");
		}
		boolean changing = !_databaseName_set || _databaseName != value;
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

	private String m_stateTableName = null;
	private boolean m_stateTableName_set = false;

	@Override public String getStateTableName() {
		if(!m_stateTableName_set) {
			throw new IllegalStateException("stateTableName not set.  Use the HasStateTableName() method to check its state before accessing it.");
		}
		return m_stateTableName;
	}

	private void setStateTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("stateTableName cannot be null");
		}
		boolean changing = !m_stateTableName_set || m_stateTableName != value;
		if(changing) {
			m_stateTableName_set = true;
			m_stateTableName = value;
		}
	}

	private void clearStateTableName() {
		if(m_stateTableName_set) {
			m_stateTableName_set = true;
			m_stateTableName = null;
		}
	}

	@Override public boolean hasStateTableName() {
		return m_stateTableName_set;
	}

	// </editor-fold>

	/**
	 * Returns a DataSource for the master database in the target SQL Server instance.
	 * 
	 * @since                                   1.1
	 */
	public DataSource getMasterDataSource()
	{
		SQLServerDataSource result = new SQLServerDataSource();
		result.setServerName(this.getHostName());
		if (this.hasInstanceName())
		{
			result.setInstanceName(this.getInstanceName());
		}
		result.setPortNumber(this.getPortNumber());
		result.setUser(this.getAdminUsername());
		result.setPassword(this.getAdminPassword());
		result.setDatabaseName("master");
		
		return result;
	}

	/**
	 * Returns a DataSource for the application database defined by this instance.
	 * 
	 * @since                                   1.1
	 */
	@Override public DataSource getAppDataSource()
	{
		SQLServerDataSource result = new SQLServerDataSource();
		result.setServerName(this.getHostName());
		if (this.hasInstanceName())
		{
			result.setInstanceName(this.getInstanceName());
		}
		result.setPortNumber(this.getPortNumber());
		result.setUser(this.getAdminUsername());
		result.setPassword(this.getAdminPassword());
		result.setDatabaseName(this.getDatabaseName());
		
		return result;
	}
}
