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

package co.mv.wb.plugin.mysql;

import co.mv.wb.FaultException;
import co.mv.wb.Instance;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.plugin.generaldatabase.BaseDatabaseInstance;
import co.mv.wb.plugin.generaldatabase.JdbcDatabaseInstance;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A resource {@link Instance} that describes a MySQL database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MySqlDatabaseInstance extends BaseDatabaseInstance implements JdbcDatabaseInstance
{
	/**
	 * Creates a new MySqlDatabaseInstance.
	 * 
	 * @param       hostName                    the host name or IP address of the server.
	 * @param       port                        the port number of the server.
	 * @param       adminUsername               the username for a user that has permission to administer the database.
	 * @param       adminPassword               the password for the admin user, in clear text.
	 * @param       databaseName                the name of the database for this instance of the resource.
	 * @param       stateTableName              the name to give the state tracking table.  This is optional and null
	 *                                          may be supplied.
	 * @since                                   1.0
	 */
	public MySqlDatabaseInstance(
		String hostName,
		int port,
		String adminUsername,
		String adminPassword,
		String databaseName,
		String stateTableName)
	{
		super(databaseName, stateTableName);

		this.setHostName(hostName);
		this.setPort(port);
		this.setAdminUsername(adminUsername);
		this.setAdminPassword(adminPassword);
	}
	
	// <editor-fold desc="HostName" defaultstate="collapsed">

	private String _hostName = null;
	private boolean _hostName_set = false;

	/**
	 * Returns the host name of the server
	 * 
	 * @return                                  the host name of the server
	 * @since                                   1.0
	 */
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
	
	// <editor-fold desc="Port" defaultstate="collapsed">

	private int _port = 0;
	private boolean _port_set = false;

	/**
	 * Returns the port number of the server
	 * 
	 * @return                                  the port number of the server
	 * @since                                   1.0
	 */
	@Override public final int getPort() {
		if(!_port_set) {
			throw new IllegalStateException("port not set.  Use the HasPort() method to check its state before accessing it.");
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

	/**
	 * Gets the username of the administrative user on the server.
	 * 
	 * @return                                  the username of the administrative user on the server
	 * @since                                   1.0
	 */
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

	/**
	 * Gets the password of the administrative user on the server.
	 * 
	 * @return                                  the password for the administrative user on the server
	 * @since                                   1.0
	 */
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
	
	/**
	 * Returns a DataSource for the information schema in the target MySQL database.
	 * 
	 * @return                                  a DataSource for the information schema in the target MySQL server.
	 * @since                                   1.0
	 */
	@Override public DataSource getAdminDataSource()
	{
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(this.getHostName());
		ds.setPort(this.getPort());
		ds.setUser(this.getAdminUsername());
		ds.setPassword(this.getAdminPassword());
		ds.setDatabaseName("information_schema");
		
		return ds;
	}
	
	/**
	 * Returns a DataSource for the application schema that this instance represents, in the target MySQL server.
	 * 
	 * @return                                  a DataSource for the application schema in the target MySQL server.
	 * @since                                   1.0
	 */
	@Override public DataSource getAppDataSource()
	{
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(this.getHostName());
		ds.setPort(this.getPort());
		ds.setUser(this.getAdminUsername());
		ds.setPassword(this.getAdminPassword());
		ds.setDatabaseName(this.getDatabaseName());
		
		return ds;
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
			ps = conn.prepareStatement("SELECT * FROM SCHEMATA WHERE SCHEMA_NAME = ?;");
			ps.setString(1, this.getDatabaseName());
			
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
