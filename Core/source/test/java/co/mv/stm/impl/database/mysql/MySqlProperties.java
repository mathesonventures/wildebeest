package co.mv.stm.impl.database.mysql;

import org.apache.log4j.Logger;

public class MySqlProperties
{
	private static Logger LOG = Logger.getLogger(MySqlProperties.class);
	
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
	
	// <editor-fold desc="Username" defaultstate="collapsed">

	private String m_username = null;
	private boolean m_username_set = false;

	public String getUsername() {
		if(!m_username_set) {
			throw new IllegalStateException("username not set.  Use the HasUsername() method to check its state before accessing it.");
		}
		return m_username;
	}

	private void setUsername(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("username cannot be null");
		}
		boolean changing = !m_username_set || m_username != value;
		if(changing) {
			m_username_set = true;
			m_username = value;
		}
	}

	private void clearUsername() {
		if(m_username_set) {
			m_username_set = true;
			m_username = null;
		}
	}

	private boolean hasUsername() {
		return m_username_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Password" defaultstate="collapsed">

	private String m_password = null;
	private boolean m_password_set = false;

	public String getPassword() {
		if(!m_password_set) {
			throw new IllegalStateException("password not set.  Use the HasPassword() method to check its state before accessing it.");
		}
		return m_password;
	}

	private void setPassword(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("password cannot be null");
		}
		boolean changing = !m_password_set || m_password != value;
		if(changing) {
			m_password_set = true;
			m_password = value;
		}
	}

	private void clearPassword() {
		if(m_password_set) {
			m_password_set = true;
			m_password = null;
		}
	}

	private boolean hasPassword() {
		return m_password_set;
	}

	// </editor-fold>
}
