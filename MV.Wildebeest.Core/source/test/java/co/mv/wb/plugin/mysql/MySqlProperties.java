// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.zd.wb.plugin.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlProperties
{
	private static Logger LOG = LoggerFactory.getLogger(MySqlProperties.class);
	
	private MySqlProperties()
	{
	}
	
	public static MySqlProperties get()
	{
		MySqlProperties result = new MySqlProperties();
		
		// HostName
		String hostName = System.getProperty("mySql.hostName");
		if (hostName == null)
		{
			result.setHostName("127.0.0.1");
		}
		else
		{
			LOG.debug("System mySql.hostName: " + hostName);
			result.setHostName(hostName);
		}
		
		// Port
		String portRaw = System.getProperty("mySql.port");
		if (portRaw == null)
		{
			result.setPort(3306);
		}
		else
		{
			LOG.debug("System mySql.port: " + portRaw);
			result.setPort(Integer.parseInt(portRaw));
		}

		// Username
		String username = System.getProperty("mySql.username");
		if (username == null)
		{
			result.setUsername("root");
		}
		else
		{
			LOG.debug("System mySql.username: " + username);
			result.setUsername(username);
		}

		// Password
		String password = System.getProperty("mySql.password");
		if (password == null)
		{
			result.setPassword("password");
		}
		else
		{
			LOG.debug("System mySql.password: " + password);
			result.setPassword(password);
		}
		
		LOG.debug(String.format(
			"MySqlProperties { hostName: %s; port: %d; username: %s; password: %s; }",
			result.getHostName(),
			result.getPort(),
			result.getUsername(),
			result.getPassword()));

		return result;
	}
	
	// <editor-fold desc="HostName" defaultstate="collapsed">

	private String _hostName = null;
	private boolean _hostName_set = false;

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

	// <editor-fold desc="Port" defaultstate="collapsed">

	private int _port = 0;
	private boolean _port_set = false;

	public int getPort() {
		if(!_port_set) {
			throw new IllegalStateException("port not set.  Use the HasPort() method to check its state before accessing it.");
		}
		return _port;
	}

	private void setPort(
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
	
	// <editor-fold desc="Username" defaultstate="collapsed">

	private String _username = null;
	private boolean _username_set = false;

	public String getUsername() {
		if(!_username_set) {
			throw new IllegalStateException("username not set.  Use the HasUsername() method to check its state before accessing it.");
		}
		return _username;
	}

	private void setUsername(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("username cannot be null");
		}
		boolean changing = !_username_set || _username != value;
		if(changing) {
			_username_set = true;
			_username = value;
		}
	}

	private void clearUsername() {
		if(_username_set) {
			_username_set = true;
			_username = null;
		}
	}

	private boolean hasUsername() {
		return _username_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Password" defaultstate="collapsed">

	private String _password = null;
	private boolean _password_set = false;

	public String getPassword() {
		if(!_password_set) {
			throw new IllegalStateException("password not set.  Use the HasPassword() method to check its state before accessing it.");
		}
		return _password;
	}

	private void setPassword(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("password cannot be null");
		}
		boolean changing = !_password_set || _password != value;
		if(changing) {
			_password_set = true;
			_password = value;
		}
	}

	private void clearPassword() {
		if(_password_set) {
			_password_set = true;
			_password = null;
		}
	}

	private boolean hasPassword() {
		return _password_set;
	}

	// </editor-fold>
	
	public MySqlDatabaseInstance toInstance(
		String databaseName)
	{
		return new MySqlDatabaseInstance(
			this.getHostName(),
			this.getPort(),
			this.getUsername(),
			this.getPassword(),
			databaseName,
			null);
	}
}
