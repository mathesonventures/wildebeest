// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.mv.wb.fake;

import co.mv.wb.AssertionResponse;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;
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

	private String _tag = null;
	private boolean _tag_set = false;

	public String getTag() {
		if(!_tag_set) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return _tag;
	}

	public void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !_tag_set || _tag != value;
		if(changing) {
			_tag_set = true;
			_tag = value;
		}
	}

	private void clearTag() {
		if(_tag_set) {
			_tag_set = true;
			_tag = null;
		}
	}

	private boolean hasTag() {
		return _tag_set;
	}

	// </editor-fold>
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, FakeResourcePlugin.class) != null;
	}

	@Override public AssertionResponse perform(Instance instance)
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
