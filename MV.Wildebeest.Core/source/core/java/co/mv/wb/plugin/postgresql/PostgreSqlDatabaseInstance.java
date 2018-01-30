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

package co.mv.wb.plugin.postgresql;

import co.mv.wb.plugin.ansisql.AnsiSqlDatabaseInstance;
import co.mv.wb.plugin.database.BaseDatabaseInstance;
import co.mv.wb.plugin.database.DatabaseHelper;
import co.mv.wb.plugin.database.JdbcDatabaseInstance;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

/**
 * Represents an instance of a PostgreSQL database, which is an ANSI-compliant database system.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlDatabaseInstance extends BaseDatabaseInstance implements AnsiSqlDatabaseInstance,
	JdbcDatabaseInstance
{
	public PostgreSqlDatabaseInstance(
		String hostName,
		int port,
		String adminUsername,
		String adminPassword,
		String databaseName,
		String metaSchemaName,
		String stateTableName)
	{
		super(databaseName, stateTableName);
		
		this.setHostName(hostName);
		this.setPort(port);
		this.setAdminUsername(adminUsername);
		this.setAdminPassword(adminPassword);
		if (metaSchemaName != null)
		{
			this.setMetaSchemaName(metaSchemaName);
		}
	}
	
	// <editor-fold desc="HostName" defaultstate="collapsed">

	private String _hostName = null;
	private boolean _hostName_set = false;

	@Override public final String getHostName() {
		if(!_hostName_set) {
			throw new IllegalStateException("hostName not set.");
		}
		if(_hostName == null) {
			throw new IllegalStateException("hostName should not be null");
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
			throw new IllegalStateException("adminUsername not set.");
		}
		if(_adminUsername == null) {
			throw new IllegalStateException("adminUsername should not be null");
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
			throw new IllegalStateException("adminPassword not set.");
		}
		if(_adminPassword == null) {
			throw new IllegalStateException("adminPassword should not be null");
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

	// <editor-fold desc="MetaSchemaName" defaultstate="collapsed">

	private String _metaSchemaName = null;
	private boolean _metaSchemaName_set = false;

	@Override public String getMetaSchemaName() {
		if(!_metaSchemaName_set) {
			throw new IllegalStateException("metaSchemaName not set.");
		}
		if(_metaSchemaName == null) {
			throw new IllegalStateException("metaSchemaName should not be null");
		}
		return _metaSchemaName;
	}

	@Override public final void setMetaSchemaName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("metaSchemaName cannot be null");
		}
		boolean changing = !_metaSchemaName_set || !_metaSchemaName.equals(value);
		if(changing) {
			_metaSchemaName_set = true;
			_metaSchemaName = value;
		}
	}

	@Override public void clearMetaSchemaName() {
		if(_metaSchemaName_set) {
			_metaSchemaName_set = true;
			_metaSchemaName = null;
		}
	}

	@Override public boolean hasMetaSchemaName() {
		return _metaSchemaName_set;
	}

	// </editor-fold>

	@Override public DataSource getAdminDataSource()
	{
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(this.getHostName());
		ds.setPortNumber(this.getPort());
		ds.setUser(this.getAdminUsername());
		ds.setPassword(this.getAdminPassword());
		ds.setDatabaseName("postgres");
		
		return ds;
	}

	@Override public DataSource getAppDataSource()
	{
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(this.getHostName());
		ds.setPortNumber(this.getPort());
		ds.setUser(this.getAdminUsername());
		ds.setPassword(this.getAdminPassword());
		ds.setDatabaseName(this.getDatabaseName().toLowerCase());
		
		return ds;
	}

	@Override public boolean databaseExists()
	{
		return DatabaseHelper.rowExists(
			this.getAdminDataSource(),
			String.format("SELECT * FROM pg_database WHERE datname = '%s';", this.getDatabaseName().toLowerCase()));
	}
}
