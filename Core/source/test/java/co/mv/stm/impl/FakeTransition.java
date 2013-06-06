package co.mv.stm.impl;

import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionType;
import java.util.UUID;

public class FakeTransition extends BaseTransition
{
	public FakeTransition(
		UUID transitionId,
		UUID fromStateId,
		UUID toStateId,
		String tag)
	{
		super(transitionId, TransitionType.DatabaseSqlScript, fromStateId, toStateId);
		
		this.setTag(tag);
	}
	
	public FakeTransition(
		UUID transitionId,
		UUID toStateId,
		String tag)
	{
		super(transitionId, TransitionType.DatabaseSqlScript, toStateId);
		
		this.setTag(tag);
	}
	
	// <editor-fold desc="Tag" defaultstate="collapsed">

	private String m_tag = null;
	private boolean m_tag_set = false;

	public String getTag() {
		if(!m_tag_set) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return m_tag;
	}

	private void setTag(
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

	private void clearTag() {
		if(m_tag_set) {
			m_tag_set = true;
			m_tag = null;
		}
	}

	private boolean hasTag() {
		return m_tag_set;
	}

	// </editor-fold>
	
	@Override public void perform(ResourceInstance instance) throws TransitionFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeResourceInstance fake = ModelExtensions.As(instance, FakeResourceInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must of type FakeResource"); }
	
		fake.setTag(this.getTag());
	}
}
