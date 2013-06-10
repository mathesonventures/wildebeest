package co.mv.stm.impl;

import co.mv.stm.Assertion;
import co.mv.stm.AssertionResponse;
import co.mv.stm.ModelExtensions;
import co.mv.stm.ResourceInstance;
import java.util.UUID;

public class FakeAssertion extends BaseAssertion implements Assertion
{
	public FakeAssertion(
		UUID assertionId,
		String name,
		int seqNum,
		String tag)
	{
		super(assertionId, name, seqNum);
		
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

	@Override public AssertionResponse apply(ResourceInstance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannt be null"); }
		FakeResourceInstance fake = ModelExtensions.As(instance, FakeResourceInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be a FakeResourceInstance"); }
		
		boolean result = this.getTag().equals(fake.getTag());
		
		AssertionResponse response = null;
		
		if (result)
		{
			response = new ImmutableAssertionResponse(
				result,
				String.format("Tag is \"%s\"", this.getTag()));
		}
		else
		{
			response = new ImmutableAssertionResponse(
				result,
				String.format("Tag expected to be \"%s\" but was \"%s\"", this.getTag(), fake.getTag()));
		}
		
		return response;
	}
}
