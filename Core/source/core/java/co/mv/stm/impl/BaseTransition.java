package co.mv.stm.impl;

import co.mv.stm.Transition;
import java.util.UUID;

public abstract class BaseTransition implements Transition
{
	public BaseTransition(
		UUID transitionId,
		UUID toStateId)
	{
		this.setTransitionId(transitionId);
		this.setToStateId(toStateId);
	}
	
	public BaseTransition(
		UUID transitionId,
		UUID fromStateId,
		UUID toStateId)
	{
		this.setTransitionId(transitionId);
		this.setFromStateId(fromStateId);
		this.setToStateId(toStateId);
	}
	
	// <editor-fold desc="TransitionId" defaultstate="collapsed">

	private UUID m_transitionId = null;
	private boolean m_transitionId_set = false;

	public UUID getTransitionId() {
		if(!m_transitionId_set) {
			throw new IllegalStateException("transitionId not set.  Use the HasTransitionId() method to check its state before accessing it.");
		}
		return m_transitionId;
	}

	private void setTransitionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("transitionId cannot be null");
		}
		boolean changing = !m_transitionId_set || m_transitionId != value;
		if(changing) {
			m_transitionId_set = true;
			m_transitionId = value;
		}
	}

	private void clearTransitionId() {
		if(m_transitionId_set) {
			m_transitionId_set = true;
			m_transitionId = null;
		}
	}

	private boolean hasTransitionId() {
		return m_transitionId_set;
	}

	// </editor-fold>

	// <editor-fold desc="FromStateId" defaultstate="collapsed">

	private UUID m_fromStateId = null;
	private boolean m_fromStateId_set = false;

	public UUID getFromStateId() {
		if(!m_fromStateId_set) {
			throw new IllegalStateException("fromStateId not set.  Use the HasFromStateId() method to check its state before accessing it.");
		}
		return m_fromStateId;
	}

	private void setFromStateId(
		UUID value) {
		boolean changing = !m_fromStateId_set || m_fromStateId != value;
		if(changing) {
			m_fromStateId_set = true;
			m_fromStateId = value;
		}
	}

	private void clearFromStateId() {
		if(m_fromStateId_set) {
			m_fromStateId_set = true;
			m_fromStateId = null;
		}
	}

	public boolean hasFromStateId() {
		return m_fromStateId_set;
	}

	// </editor-fold>

	// <editor-fold desc="ToStateId" defaultstate="collapsed">

	private UUID m_toStateId = null;
	private boolean m_toStateId_set = false;

	public UUID getToStateId() {
		if(!m_toStateId_set) {
			throw new IllegalStateException("toStateId not set.  Use the HasToStateId() method to check its state before accessing it.");
		}
		return m_toStateId;
	}

	private void setToStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("toStateId cannot be null");
		}
		boolean changing = !m_toStateId_set || m_toStateId != value;
		if(changing) {
			m_toStateId_set = true;
			m_toStateId = value;
		}
	}

	private void clearToStateId() {
		if(m_toStateId_set) {
			m_toStateId_set = true;
			m_toStateId = null;
		}
	}

	private boolean hasToStateId() {
		return m_toStateId_set;
	}

	// </editor-fold>
}
