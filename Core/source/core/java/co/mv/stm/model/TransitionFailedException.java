package co.mv.stm.model;

import java.util.UUID;

public class TransitionFailedException extends Exception
{
	public TransitionFailedException(
		UUID transitionId,
		String message)
	{
		super(message);

		this.setTransitionId(transitionId);
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

	public boolean hasTransitionId() {
		return m_transitionId_set;
	}

	// </editor-fold>
}
