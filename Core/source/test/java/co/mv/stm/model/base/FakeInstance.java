package co.mv.stm.model.base;

import co.mv.stm.model.Instance;
import java.util.UUID;

public class FakeInstance implements Instance
{
	public FakeInstance()
	{
	}
	
	public FakeInstance(UUID stateId)
	{
		this.setStateId(stateId);
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

	public void setStateId(
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

	public void clearStateId() {
		if(m_stateId_set) {
			m_stateId_set = true;
			m_stateId = null;
		}
	}

	public boolean hasStateId() {
		return m_stateId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Tag" defaultstate="collapsed">

	private String m_tag = null;
	private boolean m_tag_set = false;

	public String getTag() {
		if(!m_tag_set) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return m_tag;
	}

	public void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !m_tag_set || m_tag != value;
		if(changing) {
			m_tag_set = true;
			m_tag = value;
		}
	}

	public void clearTag() {
		if(m_tag_set) {
			m_tag_set = true;
			m_tag = null;
		}
	}

	public boolean hasTag() {
		return m_tag_set;
	}

	// </editor-fold>
}