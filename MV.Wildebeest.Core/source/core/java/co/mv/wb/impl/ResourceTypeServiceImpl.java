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

package co.mv.wb.impl;

import co.mv.wb.ResourceType;
import co.mv.wb.ResourceTypeService;

import java.util.List;

/**
 * Default in-memory implementation of {@link ResourceTypeService}
 *
 * @since                                       4.0
 */
public class ResourceTypeServiceImpl implements ResourceTypeService
{
	private List<ResourceType> _resourceTypes;

	public ResourceTypeServiceImpl(
		List<ResourceType> resourceTypes)
	{
		if (resourceTypes == null) { throw new IllegalArgumentException("resourceTypes cannot be null"); }

		_resourceTypes = resourceTypes;
	}

	@Override public List<ResourceType> getResourceTypes()
	{
		return _resourceTypes;
	}
}
