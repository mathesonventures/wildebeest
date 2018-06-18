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

import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MySqlUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(MySqlUtil.class);

	public static String createDatabase(
		MySqlProperties properties,
		String databaseName,
		String setupScript)
	{
		if (properties == null) throw new ArgumentNullException("properties");
		if (databaseName == null) throw new ArgumentNullException("databaseName");
		if (setupScript == null) throw new ArgumentNullException("setupScript");

		return MySqlUtil.createDatabase(
			properties.getHostName(),
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName,
			setupScript);
	}

	public static DataSource getDataSource(
		MySqlProperties properties,
		String databaseName)
	{
		if (properties == null) throw new ArgumentNullException("properties");
		if (databaseName == null) throw new ArgumentNullException("databaseName");

		return MySqlUtil.getDataSource(
			properties.getHostName(),
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName);
	}

	public static DataSource getDataSource(
		String hostName,
		int port,
		String username,
		String passwordClear,
		String databaseName)
	{
		if (hostName == null) throw new ArgumentNullException("hostName");
		if (username == null) throw new ArgumentNullException("username");
		if (passwordClear == null) throw new ArgumentNullException("passwordClear");
		if (databaseName == null) throw new ArgumentNullException("databaseName");

		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(hostName);
		ds.setPort(port);
		ds.setUser(username);
		ds.setPassword(passwordClear);
		ds.setDatabaseName(databaseName);

		return ds;
	}

	public static String createDatabase(
		String hostName,
		int port,
		String username,
		String passwordClear,
		String databaseName,
		String setupScript)
	{
		if (hostName == null) throw new ArgumentNullException("hostName");
		if (username == null) throw new ArgumentNullException("username");
		if (passwordClear == null) throw new ArgumentNullException("passwordClear");
		if (databaseName == null) throw new ArgumentNullException("databaseName");
		if (setupScript == null) throw new ArgumentNullException("setupScript");

		LOG.info(String.format(
			"Setting up test database { name: %s: hostName: %s; username: %s; password: %s; }",
			databaseName,
			hostName,
			username,
			passwordClear));

		//
		// Create Database
		//

		DataSource rootDs = MySqlUtil.getDataSource(
			hostName,
			port,
			username,
			passwordClear,
			"mysql");

		try
		{
			DatabaseHelper.execute(rootDs, String.format("CREATE DATABASE `%s`;", databaseName));
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}

		//
		// Execute Setup Script
		//

		DataSource testDs = MySqlUtil.getDataSource(
			hostName,
			port,
			username,
			passwordClear,
			databaseName);

		try
		{
			DatabaseHelper.execute(testDs, setupScript);
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}

		return databaseName;
	}

	public static void dropDatabase(
		MySqlProperties properties,
		String databaseName)
	{
		if (properties == null) throw new ArgumentNullException("properties");
		if (databaseName == null) throw new ArgumentNullException("databaseName");

		MySqlUtil.dropDatabase(
			properties.getHostName(),
			properties.getPort(),
			properties.getUsername(),
			properties.getPassword(),
			databaseName);
	}

	public static void dropDatabase(
		String hostName,
		int port,
		String username,
		String passwordClear,
		String databaseName)
	{
		if (hostName == null) throw new ArgumentNullException("hostName");
		if (username == null) throw new ArgumentNullException("username");
		if (passwordClear == null) throw new ArgumentNullException("passwordClear");
		if (databaseName == null) throw new ArgumentNullException("databaseName");

		MysqlDataSource rootDs = new MysqlDataSource();
		rootDs.setServerName(hostName);
		rootDs.setPort(port);
		rootDs.setUser(username);
		rootDs.setPassword(passwordClear);
		rootDs.setDatabaseName("mysql");

		try
		{
			DatabaseHelper.execute(rootDs, String.format("DROP DATABASE `%s`;", databaseName));
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void dropDatabase(
		MySqlDatabaseInstance instance,
		String databaseName) throws SQLException
	{
		if (instance == null) throw new ArgumentNullException("instance");
		if ("".equals(databaseName)) throw new IllegalArgumentException("databaseName cannot be empty");

		DatabaseHelper.execute(instance.getAdminDataSource(), "DROP DATABASE `" + databaseName + "`;");
	}
}
