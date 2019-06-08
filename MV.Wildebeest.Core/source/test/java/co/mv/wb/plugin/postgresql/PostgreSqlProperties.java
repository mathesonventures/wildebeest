package co.mv.wb.plugin.postgresql;

import co.mv.wb.Config;
import co.mv.wb.framework.ArgumentNullException;

public class PostgreSqlProperties
{
	private static final String KEY_HOSTNAME = "WB_POSTGRESQL_HOSTNAME";
	private static final String KEY_PORT = "WB_POSTGRESQL_PORT";
	private static final String KEY_ADMINUSERNAME = "WB_POSTGRESQL_ADMINUSERNAME";
	private static final String KEY_ADMINPASSWORD = "WB_POSTGRESQL_ADMINPASSWORD";

	private static final String DEFAULT_HOSTNAME = "127.0.0.1";
	private static final int DEFAULT_PORT = 15432;
	private static final String DEFAULT_ADMINUSERNAME = "postgres";
	private static final String DEFAULT_ADMINPASSWORD = "Password123!";

	private final String hostName;
	private final int port;
	private final String adminUsername;
	private final String adminPassword;

	public PostgreSqlProperties(
		final String hostName,
		final int port,
		final String adminUsername,
		final String adminPassword)
	{
		if (hostName == null) throw new ArgumentNullException("hostName");
		if (adminUsername == null) throw new ArgumentNullException("adminUsername");
		if (adminPassword == null) throw new ArgumentNullException("adminPassword");

		this.hostName = hostName;
		this.port = port;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
	}

	public static PostgreSqlProperties get()
	{
		return new PostgreSqlProperties(
			Config.getSettingString(KEY_HOSTNAME, DEFAULT_HOSTNAME),
			Config.getSettingInt(KEY_PORT, DEFAULT_PORT),
			Config.getSettingString(KEY_ADMINUSERNAME, DEFAULT_ADMINUSERNAME),
			Config.getSettingString(KEY_ADMINPASSWORD, DEFAULT_ADMINPASSWORD));
	}

	public String getHostName()
	{
		return this.hostName;
	}

	public int getPort()
	{
		return this.port;
	}

	public String getAdminUsername()
	{
		return this.adminUsername;
	}

	public String getAdminPassword()
	{
		return this.adminPassword;
	}

	public PostgreSqlDatabaseInstance toInstance()
	{
		return new PostgreSqlDatabaseInstance(
			this.hostName,
			this.port,
			this.adminUsername,
			this.adminPassword,
			"WildebeestTest",
			null,
			null);
	}

	public PostgreSqlDatabaseInstance toInstance(
		String databaseName,
		String metaSchemaName,
		String stateTableName)
	{
		return new PostgreSqlDatabaseInstance(
			this.hostName,
			this.port,
			this.adminUsername,
			this.adminPassword,
			databaseName,
			metaSchemaName,
			stateTableName);
	}
}
