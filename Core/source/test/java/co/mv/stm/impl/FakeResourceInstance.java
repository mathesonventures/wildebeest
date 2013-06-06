package co.mv.stm.impl;

import co.mv.stm.model.ResourceInstance;

public class FakeResourceInstance implements ResourceInstance
{
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
