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

import co.mv.wb.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlType;

public class MySqlProperties
{
	private static final Logger LOG = LoggerFactory.getLogger(MySqlProperties.class);

	private static final String KEY_HOSTNAME = "mysql.hostName";
	private static final String KEY_PORT = "mySql.port";
	private static final String KEY_USERNAME = "mySql.username";
	private static final String KEY_PASSWORD = "mySql.password";

	private static final String DEFAULT_HOSTNAME = "127.0.0.1";
	private static final int DEFAULT_PORT = 3306;
	private static final String DEFAULT_USERNAME = "root";
	private static final String DEFAULT_PASSWORD = "Password123!";

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

		result.hostName = Config.getSettingString(KEY_HOSTNAME, DEFAULT_HOSTNAME);
		result.port = Config.getSettingInt(KEY_PORT, DEFAULT_PORT);
		result.username = Config.getSettingString(KEY_USERNAME, DEFAULT_USERNAME);
		result.password = Config.getSettingString(KEY_PASSWORD, DEFAULT_PASSWORD);

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
