// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

import co.mv.wb.Instance;
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

	private UUID _stateId = null;
	private boolean _stateId_set = false;

	public UUID getStateId() {
		if(!_stateId_set) {
			throw new IllegalStateException("stateId not set.  Use the HasStateId() method to check its state before accessing it.");
		}
		return _stateId;
	}

	public final void setStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !_stateId_set || _stateId != value;
		if(changing) {
			_stateId_set = true;
			_stateId = value;
		}
	}

	public void clearStateId() {
		if(_stateId_set) {
			_stateId_set = true;
			_stateId = null;
		}
	}

	public boolean hasStateId() {
		return _stateId_set;
	}

	// </editor-fold>
	
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
		boolean changing = !_tag_set || !_tag.equals(value);
		if(changing) {
			_tag_set = true;
			_tag = value;
		}
	}

	public void clearTag() {
		if(_tag_set) {
			_tag_set = true;
			_tag = null;
		}
	}

	public boolean hasTag() {
		return _tag_set;
	}

	// </editor-fold>
}
