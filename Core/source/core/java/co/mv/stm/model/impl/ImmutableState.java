package co.mv.stm.model.impl;

import co.mv.stm.model.State;
import java.util.UUID;

public class ImmutableState implements State
{
	public ImmutableState(
		UUID stateId)
	{
		this.setStateId(stateId);
	}
	
	public ImmutableState(
		UUID stateId,
		String label)
	{
		this.setStateId(stateId);
		this.setLabel(label);
	}

	// <editor-fold desc="StateId" defaultstate="collapsed">

	private UUID m_stateId = null;
	private boolean m_stateId_set = false;

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
	
	// <editor-fold desc="Label" defaultstate="collapsed">

	private String m_label = null;
	private boolean m_label_set = false;

	public String getLabel() {
		if(!m_label_set) {
			throw new IllegalStateException("label not set.  Use the HasLabel() method to check its state before accessing it.");
		}
		return m_label;
	}

	private void setLabel(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("label cannot be null");
		}
		boolean changing = !m_label_set || m_label != value;
		if(changing) {
			m_label_set = true;
			m_label = value;
		}
	}

	private void clearLabel() {
		if(m_label_set) {
			m_label_set = true;
			m_label = null;
		}
	}

	public boolean hasLabel() {
		return m_label_set;
	}

	// </editor-fold>
}
