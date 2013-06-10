package co.mv.stm.service.dom;

import org.w3c.dom.Element;

public abstract class BaseDomBuilder implements DomBuilder
{
	// <editor-fold desc="Element" defaultstate="collapsed">

	private Element m_element = null;
	private boolean m_element_set = false;

	public Element getElement() {
		if(!m_element_set) {
			throw new IllegalStateException("element not set.  Use the HasElement() method to check its state before accessing it.");
		}
		return m_element;
	}

	public void setElement(
		Element value) {
		if(value == null) {
			throw new IllegalArgumentException("element cannot be null");
		}
		boolean changing = !m_element_set || m_element != value;
		if(changing) {
			m_element_set = true;
			m_element = value;
		}
	}

	protected void clearElement() {
		if(m_element_set) {
			m_element_set = true;
			m_element = null;
		}
	}

	private boolean hasElement() {
		return m_element_set;
	}

	// </editor-fold>
}