package co.mv.stm.model;

public enum AssertionType
{
	DatabaseRowExists(
		"DBREX",
		ResourceType.Database,
		"Database Row Exists",
		"Passes if exactly one row is identified by the supplied SELECT query"),
	DatabaseRowDoesNotExist(
		"DBRNX",
		ResourceType.Database,
		"Database Row Does Not Exist",
		"Passes if no rows are returned by the supplied SELECT query");
	
	AssertionType(
		String assertionTypeRcd,
		ResourceType resourceType,
		String name,
		String description)
	{
		this.setAssertionTypeRcd(assertionTypeRcd);
		this.setResourceType(resourceType);
		this.setName(name);
		this.setDescription(description);
	}
	
	// <editor-fold desc="AssertionTypeRcd" defaultstate="collapsed">

	private String m_assertionTypeRcd = null;
	private boolean m_assertionTypeRcd_set = false;

	public String getAssertionTypeRcd() {
		if(!m_assertionTypeRcd_set) {
			throw new IllegalStateException("assertionTypeRcd not set.  Use the HasAssertionTypeRcd() method to check its state before accessing it.");
		}
		return m_assertionTypeRcd;
	}

	private void setAssertionTypeRcd(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionTypeRcd cannot be null");
		}
		boolean changing = !m_assertionTypeRcd_set || m_assertionTypeRcd != value;
		if(changing) {
			m_assertionTypeRcd_set = true;
			m_assertionTypeRcd = value;
		}
	}

	private void clearAssertionTypeRcd() {
		if(m_assertionTypeRcd_set) {
			m_assertionTypeRcd_set = true;
			m_assertionTypeRcd = null;
		}
	}

	private boolean hasAssertionTypeRcd() {
		return m_assertionTypeRcd_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourceType" defaultstate="collapsed">

	private ResourceType m_resourceType = null;
	private boolean m_resourceType_set = false;

	private ResourceType getResourceType() {
		if(!m_resourceType_set) {
			throw new IllegalStateException("resourceType not set.  Use the HasResourceType() method to check its state before accessing it.");
		}
		return m_resourceType;
	}

	private void setResourceType(
		ResourceType value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceType cannot be null");
		}
		boolean changing = !m_resourceType_set || m_resourceType != value;
		if(changing) {
			m_resourceType_set = true;
			m_resourceType = value;
		}
	}

	private void clearResourceType() {
		if(m_resourceType_set) {
			m_resourceType_set = true;
			m_resourceType = null;
		}
	}

	private boolean hasResourceType() {
		return m_resourceType_set;
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

	// <editor-fold desc="Description" defaultstate="collapsed">

	private String m_description = null;
	private boolean m_description_set = false;

	public String getDescription() {
		if(!m_description_set) {
			throw new IllegalStateException("description not set.  Use the HasDescription() method to check its state before accessing it.");
		}
		return m_description;
	}

	private void setDescription(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("description cannot be null");
		}
		boolean changing = !m_description_set || m_description != value;
		if(changing) {
			m_description_set = true;
			m_description = value;
		}
	}

	private void clearDescription() {
		if(m_description_set) {
			m_description_set = true;
			m_description = null;
		}
	}

	private boolean hasDescription() {
		return m_description_set;
	}

	// </editor-fold>
}
