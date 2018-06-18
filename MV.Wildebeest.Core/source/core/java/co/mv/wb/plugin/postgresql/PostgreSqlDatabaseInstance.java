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

import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.plugin.generaldatabase.AnsiSqlDatabaseInstance;
import co.mv.wb.plugin.generaldatabase.BaseDatabaseInstance;
import co.mv.wb.plugin.generaldatabase.JdbcDatabaseInstance;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

/**
 * Represents an instance of a PostgreSQL database, which is an ANSI-compliant database system.
 * 
 * @since                                       4.0
 */
public class PostgreSqlDatabaseInstance
	extends BaseDatabaseInstance
	implements AnsiSqlDatabaseInstance, JdbcDatabaseInstance
{
	private final String hostName;
	private final int port;
	private final String adminUsername;
	private final String adminPassword;
	private final String metaSchemaName;

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

		if (hostName == null) throw new ArgumentNullException("hostName");
		if (adminUsername == null) throw new ArgumentNullException("adminUsername");
		if (adminPassword == null) throw new ArgumentNullException("adminPassword");

		this.hostName = hostName;
		this.port = port;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.metaSchemaName = metaSchemaName;
	}
	
	@Override public final String getHostName()
	{
		return hostName;
	}

	@Override public final int getPort()
	{
		return port;
	}

	@Override public final String getAdminUsername()
	{
		return adminUsername;
	}

	@Override public final String getAdminPassword()
	{
		return this.adminPassword;
	}

	@Override public String getMetaSchemaName()
	{
		if (this.metaSchemaName == null)
		{
			throw new IllegalStateException("metaSchemaName not set.");
		}

		return metaSchemaName;
	}

	@Override public boolean hasMetaSchemaName()
	{
		return this.metaSchemaName != null;
	}

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
