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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlServerProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(SqlServerProperties.class);

	private String hostName;
	private String instanceName;
	private int port;
	private String databaseName;
	private String username;
	private String password;

	private SqlServerProperties()
	{
		this.hostName = null;
		this.instanceName = null;
		this.port = 0;
		this.databaseName = null;
		this.username = null;
		this.password = null;
	}

	public static SqlServerProperties get()
	{
		SqlServerProperties result = new SqlServerProperties();

		// HostName
		String hostName = System.getProperty("sqlServer.hostName");
		if (hostName == null)
		{
			result.hostName = "127.0.0.1";
		}
		else
		{
			LOG.debug("System sqlServer.hostName: " + hostName);
			result.hostName = hostName;
		}

		// InstanceName
		String instanceName = System.getProperty("sqlServer.instanceName");
		if (instanceName == null)
		{
			LOG.debug("System sqlServer.instanceName not set");
		}
		else
		{
			LOG.debug("System sqlServer.instanceName: " + instanceName);
			result.instanceName = instanceName;
		}

		// Port
		String portRaw = System.getProperty("sqlServer.port");
		if (portRaw == null)
		{
			result.port = 1433;
		}
		else
		{
			LOG.debug("System sqlServer.port: " + portRaw);
			result.port = Integer.parseInt(portRaw);
		}

		// Username
		String username = System.getProperty("sqlServer.username");
		if (username == null)
		{
			result.username = "wb";
		}
		else
		{
			LOG.debug("System sqlServer.username: " + username);
			result.username = username;
		}

		// Password
		String password = System.getProperty("sqlServer.password");
		if (password == null)
		{
			result.password = "password";
		}
		else
		{
			LOG.debug("System sqlServer.password: " + password);
			result.password = password;
		}

		LOG.debug(String.format(
			"SqlServerProperties { hostName: %s; instanceName: %s; port: %d; username: %s; password: %s; }",
			result.getHostName(),
			result.hasInstanceName() ? result.getInstanceName() : "",
			result.getPort(),
			result.getUsername(),
			result.getPassword()));

		return result;
	}

	public String getHostName()
	{
		return this.hostName;
	}

	public String getInstanceName()
	{
		return this.instanceName;
	}

	public boolean hasInstanceName()
	{
		return this.instanceName != null;
	}

	public int getPort()
	{
		return this.port;
	}

	public String getDatabaseName()
	{
		return this.databaseName;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPassword()
	{
		return this.password;
	}

	public SqlServerDatabaseInstance toInstance(
		String databaseName)
	{
		return new SqlServerDatabaseInstance(
			this.getHostName(),
			this.getInstanceName(),
			this.getPort(),
			this.getUsername(),
			this.getPassword(),
			databaseName,
			null);
	}
}
