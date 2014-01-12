// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Instance;
import co.zd.wb.Logger;
import co.zd.wb.ModelExtensions;
import co.zd.wb.State;
import java.util.UUID;

public class FakeResource extends BaseResource
{
	public FakeResource(
		UUID resourceId,
		String name)
	{
		super(resourceId, name);
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

	private void setStateId(
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

	private void clearStateId() {
		if(_stateId_set) {
			_stateId_set = true;
			_stateId = null;
		}
	}

	private boolean hasStateId() {
		return _stateId_set;
	}

	// </editor-fold>

	@Override public State currentState(
		Instance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be of type FakeInstance"); }

		return fake.hasStateId() ? this.stateForId(fake.getStateId()) : null;
	}

	@Override public void setStateId(
		Logger logger,
		Instance instance,
		UUID stateId)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (stateId == null) { throw new IllegalArgumentException("stateId must be of type FakeInstance"); }
		
		this.setStateId(stateId);
	}
}