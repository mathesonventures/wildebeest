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

import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.ResourceType;
import co.mv.wb.State;

import java.util.Map;
import java.util.UUID;

public class TestContext_SimpleFakeResource
{
	public final Map<ResourceType, ResourcePlugin> resourcePlugins;

	public final Resource resource;
	public final UUID fooStateId;
	public final State fooState;
	public final UUID barStateId;
	public final State barState;
	public final Instance instance;

	TestContext_SimpleFakeResource(
		Map<ResourceType, ResourcePlugin> resourcePlugins,
		Resource resource,
		UUID fooStateId,
		State fooState,
		UUID barStateId,
		State barState,
		Instance instance)
	{
		this.resourcePlugins = resourcePlugins;

		this.resource = resource;
		this.fooStateId = fooStateId;
		this.fooState = fooState;
		this.barStateId = barStateId;
		this.barState = barState;
		this.instance = instance;
	}
}
