package co.zd.wb.plugin.postgresql;

import co.zd.wb.plugin.ansisql.AnsiSqlDatabaseInstance;
import co.zd.wb.plugin.database.BaseDatabaseInstance;
import co.zd.wb.plugin.database.DatabaseHelper;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class PostgreSqlDatabaseInstance extends BaseDatabaseInstance implements AnsiSqlDatabaseInstance
{
	public PostgreSqlDatabaseInstance(
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

	public String getHostName() {
		if(!_hostName_set) {
			throw new IllegalStateException("hostName not set.");
		}
		if(_hostName == null) {
			throw new IllegalStateException("hostName should not be null");
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
			throw new IllegalStateException("port not set.");
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

	// <editor-fold desc="AdminUsername" defaultstate="collapsed">

	private String _adminUsername = null;
	private boolean _adminUsername_set = false;

	public String getAdminUsername() {
		if(!_adminUsername_set) {
			throw new IllegalStateException("adminUsername not set.");
		}
		if(_adminUsername == null) {
			throw new IllegalStateException("adminUsername should not be null");
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

	public String getAdminPassword() {
		if(!_adminPassword_set) {
			throw new IllegalStateException("adminPassword not set.");
		}
		if(_adminPassword == null) {
			throw new IllegalStateException("adminPassword should not be null");
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
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override public boolean databaseExists()
	{
		return DatabaseHelper.rowExists(
			this.getAdminDataSource(),
			String.format("SELECT * FROM pg_database WHERE datname = '%s';", this.getDatabaseName().toLowerCase()));
	}
}