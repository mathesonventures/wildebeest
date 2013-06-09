package co.mv.stm.service.impl.sax;

import co.mv.stm.Assertion;
import co.mv.stm.service.AssertionBuilder;
import org.w3c.dom.Element;

public class BaseXmlAssertionBuilder implements AssertionBuilder
{
	public BaseXmlAssertionBuilder(
		Element assertionXe)
	{
		this.setAssertionXe(assertionXe);
	}

	// <editor-fold desc="AssertionXe" defaultstate="collapsed">

	private Element m_assertionXe = null;
	private boolean m_assertionXe_set = false;

	public Element getAssertionXe() {
		if(!m_assertionXe_set) {
			throw new IllegalStateException("assertionXe not set.  Use the HasAssertionXe() method to check its state before accessing it.");
		}
		return m_assertionXe;
	}

	private void setAssertionXe(
		Element value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionXe cannot be null");
		}
		boolean changing = !m_assertionXe_set || m_assertionXe != value;
		if(changing) {
			m_assertionXe_set = true;
			m_assertionXe = value;
		}
	}

	private void clearAssertionXe() {
		if(m_assertionXe_set) {
			m_assertionXe_set = true;
			m_assertionXe = null;
		}
	}

	private boolean hasAssertionXe() {
		return m_assertionXe_set;
	}

	// </editor-fold>

	@Override public Assertion build()
	{
		throw new UnsupportedOperationException();
	}
}