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

package co.mv.wb.plugin.fake;

import co.mv.wb.Instance;

/**
 * {@link Instance} for the Fake plugin implementation.
 *
 * @since                                       1.0
 */
public class FakeInstance implements Instance
{
	private String stateId = null;
	private boolean stateIdSet = false;
	private String tag = null;
	private boolean tagSet = false;


	public FakeInstance()
    {
    }

	public FakeInstance(String stateId)
	{
		this.setStateId(stateId);
	}


	public String getStateId() {
		if(!stateIdSet) {
			throw new IllegalStateException("stateId not set.  Use the HasStateId() method to check its state before accessing it.");
		}
		return stateId;
	}

	public final void setStateId(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !stateIdSet || stateId != value;
		if(changing) {
			stateIdSet = true;
			stateId = value;
		}
	}

	public void clearStateId() {
		if(stateIdSet) {
			stateIdSet = true;
			stateId = null;
		}
	}

	public boolean hasStateId() {
		return stateIdSet;
	}

	public String getTag() {
		if(!tagSet) {
			throw new IllegalStateException("tag not set.  Use the HasTag() method to check its state before accessing it.");
		}
		return tag;
	}

	public void setTag(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tag cannot be null");
		}
		boolean changing = !tagSet || !tag.equals(value);
		if(changing) {
			tagSet = true;
			tag = value;
		}
	}

	public void clearTag() {
		if(tagSet) {
			tagSet = true;
			tag = null;
		}
	}

	public boolean hasTag() {
		return tagSet;
	}

}
