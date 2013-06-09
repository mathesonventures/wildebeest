package co.mv.stm.service.impl.dom;

import java.util.UUID;
import org.w3c.dom.Element;

public abstract class BaseDomResourceBuilder implements DomResourceBuilder
{
	// <editor-fold desc="ResourceElement" defaultstate="collapsed">

	private Element m_resourceElement = null;
	private boolean m_resourceElement_set = false;

	public Element getResourceElement() {
		if(!m_resourceElement_set) {
			throw new IllegalStateException("resourceElement not set.  Use the HasResourceElement() method to check its state before accessing it.");
		}
		return m_resourceElement;
	}

	@Override public void setResourceElement(
		Element value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceElement cannot be null");
		}
		boolean changing = !m_resourceElement_set || m_resourceElement != value;
		if(changing) {
			m_resourceElement_set = true;
			m_resourceElement = value;
		}
	}

	private void clearResourceElement() {
		if(m_resourceElement_set) {
			m_resourceElement_set = true;
			m_resourceElement = null;
		}
	}

	private boolean hasResourceElement() {
		return m_resourceElement_set;
	}

	// </editor-fold>

	@Override public void reset()
	{
		this.clearResourceElement();
	}
}