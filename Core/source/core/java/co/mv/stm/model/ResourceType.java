package co.mv.stm.model;

public enum ResourceType
{
	Database("DB", "Database"),
	FileSystem("FS", "File System");
	
	ResourceType(
		String resourceTypeRcd,
		String name)
	{
		this.setResourceTypeRcd(resourceTypeRcd);
		this.setName(name);
	}
	
	// <editor-fold desc="ResourceTypeRcd" defaultstate="collapsed">

	private String m_resourceTypeRcd = null;
	private boolean m_resourceTypeRcd_set = false;

	public String getResourceTypeRcd() {
		if(!m_resourceTypeRcd_set) {
			throw new IllegalStateException("resourceTypeRcd not set.  Use the HasResourceTypeRcd() method to check its state before accessing it.");
		}
		return m_resourceTypeRcd;
	}

	private void setResourceTypeRcd(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceTypeRcd cannot be null");
		}
		boolean changing = !m_resourceTypeRcd_set || m_resourceTypeRcd != value;
		if(changing) {
			m_resourceTypeRcd_set = true;
			m_resourceTypeRcd = value;
		}
	}

	private void clearResourceTypeRcd() {
		if(m_resourceTypeRcd_set) {
			m_resourceTypeRcd_set = true;
			m_resourceTypeRcd = null;
		}
	}

	private boolean hasResourceTypeRcd() {
		return m_resourceTypeRcd_set;
	}

	// </editor-fold>

	// <editor-fold desc="Name" defaultstate="collapsed">

	private String m_name = null;
	private boolean m_name_set = false;

	public String getName() {
		if(!m_name_set) {
			throw new IllegalStateException("name not set.  Use the HasName() method to check its state before accessing it.");
		}
		return m_name;
	}

	private void setName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		boolean changing = !m_name_set || m_name != value;
		if(changing) {
			m_name_set = true;
			m_name = value;
		}
	}

	private void clearName() {
		if(m_name_set) {
			m_name_set = true;
			m_name = null;
		}
	}

	private boolean hasName() {
		return m_name_set;
	}

	// </editor-fold>
}