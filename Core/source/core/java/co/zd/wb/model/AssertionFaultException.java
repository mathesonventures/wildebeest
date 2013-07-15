package co.zd.wb.model;

import java.util.UUID;

public class AssertionFaultException extends RuntimeException
{
	public AssertionFaultException(
		UUID assertionId,
		Exception cause)
	{
		super(cause);
	}
	
	// <editor-fold desc="AssertionId" defaultstate="collapsed">

	private UUID m_assertionId = null;
	private boolean m_assertionId_set = false;

	public UUID getAssertionId() {
		if(!m_assertionId_set) {
			throw new IllegalStateException("assertionId not set.  Use the HasAssertionId() method to check its state before accessing it.");
		}
		return m_assertionId;
	}

	private void setAssertionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionId cannot be null");
		}
		boolean changing = !m_assertionId_set || m_assertionId != value;
		if(changing) {
			m_assertionId_set = true;
			m_assertionId = value;
		}
	}

	private void clearAssertionId() {
		if(m_assertionId_set) {
			m_assertionId_set = true;
			m_assertionId = null;
		}
	}

	private boolean hasAssertionId() {
		return m_assertionId_set;
	}

	// </editor-fold>
}