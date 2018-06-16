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
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.State;
import co.mv.wb.Wildebeest;

import java.io.PrintStream;

/**
 * {@link ResourcePlugin} for the Fake plugin implementation.
 *
 * @since                                       1.0
 */
public class FakeResourcePlugin implements ResourcePlugin
{
	private String stateId;

	// <editor-fold desc="StateId" defaultstate="collapsed">

	public String getStateId()
	{
		return this.stateId;
	}

	@Override public State currentState(
		Resource resource,
		Instance instance)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be of type FakeInstance"); }

		return fake.hasStateId()
			? Wildebeest.stateForId(resource, fake.getStateId().toString())
			: null;
	}

	@Override
	public void setStateId(
		PrintStream output,
		Resource resource,
		Instance instance,
		String stateId)
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (stateId == null) { throw new IllegalArgumentException("stateId must be of type FakeInstance"); }
		
		this.stateId = stateId;
	}
}
