package co.mv.stm.model;

import java.util.List;
import java.util.UUID;

/**
 * Indicates that assertion failed for a resource, and provides the state for which assertion failed and the assertion
 * results.
 * 
 * @author brendonm
 */
public class AssertionFailedException extends Exception
{
	public AssertionFailedException(
		UUID stateId,
		List<AssertionResult> assertionResults)
	{
		this.setStateId(stateId);
		this.setAssertionResults(assertionResults);
	}
	
	// <editor-fold desc="StateId" defaultstate="collapsed">

	private UUID m_stateId = null;
	private boolean m_stateId_set = false;

	public AssertionFailedException()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public UUID getStateId() {
		if(!m_stateId_set) {
			throw new IllegalStateException("stateId not set.  Use the HasStateId() method to check its state before accessing it.");
		}
		return m_stateId;
	}

	private void setStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !m_stateId_set || m_stateId != value;
		if(changing) {
			m_stateId_set = true;
			m_stateId = value;
		}
	}

	private void clearStateId() {
		if(m_stateId_set) {
			m_stateId_set = true;
			m_stateId = null;
		}
	}

	private boolean hasStateId() {
		return m_stateId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="AssertionResults" defaultstate="collapsed">

	private List<AssertionResult> m_assertionResults = null;
	private boolean m_assertionResults_set = false;

	public List<AssertionResult> getAssertionResults() {
		if(!m_assertionResults_set) {
			throw new IllegalStateException("assertionResults not set.  Use the HasAssertionResults() method to check its state before accessing it.");
		}
		return m_assertionResults;
	}

	private void setAssertionResults(List<AssertionResult> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionResults cannot be null");
		}
		boolean changing = !m_assertionResults_set || m_assertionResults != value;
		if(changing) {
			m_assertionResults_set = true;
			m_assertionResults = value;
		}
	}

	private void clearAssertionResults() {
		if(m_assertionResults_set) {
			m_assertionResults_set = true;
			m_assertionResults = null;
		}
	}

	private boolean hasAssertionResults() {
		return m_assertionResults_set;
	}

	// </editor-fold>
}