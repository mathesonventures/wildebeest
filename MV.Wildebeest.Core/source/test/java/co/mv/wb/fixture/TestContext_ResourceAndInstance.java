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

package co.mv.wb.fixture;

import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.plugin.fake.FakeInstance;

import java.util.UUID;

/**
 * A test context that carries a resource and an instance.
 *
 * @since 4.0
 */
public class TestContext_ResourceAndInstance
{
	public final Resource resource;
	public final FakeInstance instance;

	TestContext_ResourceAndInstance(
		Resource resource,
		FakeInstance instance)
	{
		this.resource = resource;
		this.instance = instance;
	}

	public State getState(int stateIndex)
	{
		return this.resource.getStates().get(stateIndex);
	}

	public UUID getStateId(int stateIndex)
	{
		return this.getState(stateIndex).getStateId();
	}
}
