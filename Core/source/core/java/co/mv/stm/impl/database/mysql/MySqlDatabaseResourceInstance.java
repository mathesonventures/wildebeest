package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.DatabaseResourceInstance;
import co.mv.stm.ResourceInstance;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;

public class MySqlDatabaseResourceInstance implements ResourceInstance, DatabaseResourceInstance
{
	public MySqlDatabaseResourceInstance(
		String hostName,
		int port,
		String adminUsername,
		String adminPassword,
		String schemaName)
	{
		this.setHostName(hostName);
		this.setPort(port);
		this.setAdminUsername(adminUsername);
		this.setAdminPassword(adminPassword);
		this.setSchemaName(schemaName);
	}
	
	// <editor-fold desc="HostName" defaultstate="collapsed">

	private String m_hostName = null;
	private boolean m_hostName_set = false;

	public String getHostName() {
		if(!m_hostName_set) {
			throw new IllegalStateException("hostName not set.  Use the HasHostName() method to check its state before accessing it.");
		}
		return m_hostName;
	}

	private void setHostName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("hostName cannot be null");
		}
		boolean changing = !m_hostName_set || m_hostName != value;
		if(changing) {
			m_hostName_set = true;
			m_hostName = value;
		}
	}

	private void clearHostName() {
		if(m_hostName_set) {
			m_hostName_set = true;
			m_hostName = null;
		}
	}

	private boolean hasHostName() {
		return m_hostName_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Port" defaultstate="collapsed">

	private int m_port = 0;
	private boolean m_port_set = false;

	public int getPort() {
		if(!m_port_set) {
			throw new IllegalStateException("port not set.  Use the HasPort() method to check its state before accessing it.");
		}
		return m_port;
	}

	private void setPort(
		int value) {
		boolean changing = !m_port_set || m_port != value;
		if(changing) {
			m_port_set = true;
			m_port = value;
		}
	}

	private void clearPort() {
		if(m_port_set) {
			m_port_set = true;
			m_port = 0;
		}
	}

	private boolean hasPort() {
		return m_port_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AdminUsername" defaultstate="collapsed">

	private String m_adminUsername = null;
	private boolean m_adminUsername_set = false;

	public String getAdminUsername() {
		if(!m_adminUsername_set) {
			throw new IllegalStateException("adminUsername not set.  Use the HasAdminUsername() method to check its state before accessing it.");
		}
		return m_adminUsername;
	}

	private void setAdminUsername(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("adminUsername cannot be null");
		}
		boolean changing = !m_adminUsername_set || m_adminUsername != value;
		if(changing) {
			m_adminUsername_set = true;
			m_adminUsername = value;
		}
	}

	private void clearAdminUsername() {
		if(m_adminUsername_set) {
			m_adminUsername_set = true;
			m_adminUsername = null;
		}
	}

	private boolean hasAdminUsername() {
		return m_adminUsername_set;
	}

	// </editor-fold>

	// <editor-fold desc="AdminPassword" defaultstate="collapsed">

	private String m_adminPassword = null;
	private boolean m_adminPassword_set = false;

	public String getAdminPassword() {
		if(!m_adminPassword_set) {
			throw new IllegalStateException("adminPassword not set.  Use the HasAdminPassword() method to check its state before accessing it.");
		}
		return m_adminPassword;
	}

	private void setAdminPassword(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("adminPassword cannot be null");
		}
		boolean changing = !m_adminPassword_set || m_adminPassword != value;
		if(changing) {
			m_adminPassword_set = true;
			m_adminPassword = value;
		}
	}

	private void clearAdminPassword() {
		if(m_adminPassword_set) {
			m_adminPassword_set = true;
			m_adminPassword = null;
		}
	}

	private boolean hasAdminPassword() {
		return m_adminPassword_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateTableName" defaultstate="collapsed">

	private String m_stateTableName = null;
	private boolean m_stateTableName_set = false;

	public String getStateTableName() {
		if(!m_stateTableName_set) {
			throw new IllegalStateException("stateTableName not set.  Use the HasStateTableName() method to check its state before accessing it.");
		}
		return m_stateTableName;
	}

	public void setStateTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("stateTableName cannot be null");
		}
		boolean changing = !m_stateTableName_set || m_stateTableName != value;
		if(changing) {
			m_stateTableName_set = true;
			m_stateTableName = value;
		}
	}

	private void clearStateTableName() {
		if(m_stateTableName_set) {
			m_stateTableName_set = true;
			m_stateTableName = null;
		}
	}

	public boolean hasStateTableName() {
		return m_stateTableName_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="SchemaName" defaultstate="collapsed">

	private String m_schemaName = null;
	private boolean m_schemaName_set = false;

	public String getSchemaName() {
		if(!m_schemaName_set) {
			throw new IllegalStateException("schemaName not set.  Use the HasSchemaName() method to check its state before accessing it.");
		}
		return m_schemaName;
	}

	private void setSchemaName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("schemaName cannot be null");
		}
		boolean changing = !m_schemaName_set || m_schemaName != value;
		if(changing) {
			m_schemaName_set = true;
			m_schemaName = value;
		}
	}

	private void clearSchemaName() {
		if(m_schemaName_set) {
			m_schemaName_set = true;
			m_schemaName = null;
		}
	}

	private boolean hasSchemaName() {
		return m_schemaName_set;
	}

	// </editor-fold>
	
	/**
	 * Returns a DataSource pointing at the information schema in the target MySql database.
	 * 
	 * @return 
	 */
	public DataSource getInfoDataSource()
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
	 * Returns a DataSource pointing at the application schema that this Resource represents, in the target  MySql
	 * database.
	 * 
	 * @return 
	 */
	public DataSource getAppDataSource()
	{
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(this.getHostName());
		ds.setPort(this.getPort());
		ds.setUser(this.getAdminUsername());
		ds.setPassword(this.getAdminPassword());
		ds.setDatabaseName(this.getSchemaName());
		
		return ds;
	}
}