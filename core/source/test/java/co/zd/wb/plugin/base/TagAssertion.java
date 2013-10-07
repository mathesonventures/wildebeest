// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb.plugin.base;

import co.zd.wb.AssertionResponse;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import java.util.UUID;

public class TagAssertion extends BaseAssertion
{
	public TagAssertion(
		UUID assertionId,
		int seqNum,
		String tag)
	{
		super(assertionId, seqNum);
		
		this.setTag(tag);
	}
	
	@Override public String getDescription()
	{
		return "Tag";
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

	@Override public AssertionResponse apply(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be a FakeInstance"); }
		
		AssertionResponse response = null;
		
		if (this.getTag().equals(fake.getTag()))
		{
			response = new ImmutableAssertionResponse(true, "Tag is as expected");
		}
		else
		{
			response = new ImmutableAssertionResponse(false, "Tag not as expected");
		}
		
		return response;
	}
}
