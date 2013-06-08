package co.mv.stm;

public enum TransitionType
{
	DatabaseSqlScript("DBSQL", ResourceType.Database, "SQL Script");
	
	TransitionType(
		String transitionTypeRcd,
		ResourceType resourceType,
		String name)
	{
		this.setTransitionTypeRcd(transitionTypeRcd);
		this.setResourceType(resourceType);
		this.setName(name);
	}

	// <editor-fold desc="TransitionTypeRcd" defaultstate="collapsed">

	private String m_transitionTypeRcd = null;
	private boolean m_transitionTypeRcd_set = false;

	private String getTransitionTypeRcd() {
		if(!m_transitionTypeRcd_set) {
			throw new IllegalStateException("transitionTypeRcd not set.  Use the HasTransitionTypeRcd() method to check its state before accessing it.");
		}
		return m_transitionTypeRcd;
	}

	private void setTransitionTypeRcd(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("transitionTypeRcd cannot be null");
		}
		boolean changing = !m_transitionTypeRcd_set || m_transitionTypeRcd != value;
		if(changing) {
			m_transitionTypeRcd_set = true;
			m_transitionTypeRcd = value;
		}
	}

	private void clearTransitionTypeRcd() {
		if(m_transitionTypeRcd_set) {
			m_transitionTypeRcd_set = true;
			m_transitionTypeRcd = null;
		}
	}

	private boolean hasTransitionTypeRcd() {
		return m_transitionTypeRcd_set;
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

	private String getName() {
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