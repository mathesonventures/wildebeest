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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(MySqlProperties.class);

	private String hostName;
	private int port;
	private String username;
	private String password;

	private MySqlProperties()
	{
		this.hostName = null;
		this.port = 0;
		this.username = null;
		this.password = null;
	}
	
	public static MySqlProperties get()
	{
		MySqlProperties result = new MySqlProperties();
		
		// HostName
		String hostName = System.getProperty("mySql.hostName");
		if (hostName == null)
		{
			result.hostName = "127.0.0.1";
		}
		else
		{
			LOG.debug("System mySql.hostName: " + hostName);
			result.hostName = hostName;
		}
		
		// Port
		String portRaw = System.getProperty("mySql.port");
		if (portRaw == null)
		{
			result.port = 3306;
		}
		else
		{
			LOG.debug("System mySql.port: " + portRaw);
			result.port = Integer.parseInt(portRaw);
		}

		// Username
		String username = System.getProperty("mySql.username");
		if (username == null)
		{
			result.username = "root";
		}
		else
		{
			LOG.debug("System mySql.username: " + username);
			result.username = username;
		}

		// Password
		String password = System.getProperty("mySql.password");
		if (password == null)
		{
			result.password = "password";
		}
		else
		{
			LOG.debug("System mySql.password: " + password);
			result.password = password;
		}
		
		LOG.debug(String.format(
			"MySqlProperties { hostName: %s; port: %d; username: %s; password: %s; }",
			result.getHostName(),
			result.getPort(),
			result.getUsername(),
			result.getPassword()));

		return result;
	}
	
	public String getHostName()
	{
		return hostName;
	}

	public int getPort()
	{
		return this.port;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPassword()
	{
		return this.password;
	}

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
