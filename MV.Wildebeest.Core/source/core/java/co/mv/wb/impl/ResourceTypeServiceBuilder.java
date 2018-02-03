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
import co.mv.wb.Wildebeest;

import java.util.ArrayList;
import java.util.List;

/**
 * A fluent-style helper for building a ResourceTypeService instance.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class ResourceTypeServiceBuilder
{
	private List<ResourceType> _resourceTypes;

	private ResourceTypeServiceBuilder(List<ResourceType> resourceTypes)
	{
		if (resourceTypes == null) { throw new IllegalArgumentException("resourceTypes cannot be null"); }

		_resourceTypes = resourceTypes;
	}

	public static ResourceTypeServiceBuilder create()
	{
		return new ResourceTypeServiceBuilder(new ArrayList<>());
	}

	public ResourceTypeServiceBuilder withFactoryResourceTypes()
	{
		List<ResourceType> resourceTypes = new ArrayList<>();
		resourceTypes.addAll(_resourceTypes);

		resourceTypes.add(Wildebeest.MySqlDatabase);
		resourceTypes.add(Wildebeest.PostgreSqlDatabase);
		resourceTypes.add(Wildebeest.SqlServerDatabase);

		return new ResourceTypeServiceBuilder(resourceTypes);
	}

	public ResourceTypeServiceBuilder with(ResourceType resourceType)
	{
		if (resourceType == null) { throw new IllegalArgumentException("resourceType cannot be null"); }

		List<ResourceType> resourceTypes = new ArrayList<>();
		resourceTypes.addAll(_resourceTypes);

		resourceTypes.add(resourceType);

		return new ResourceTypeServiceBuilder(resourceTypes);
	}

	public ResourceTypeService build()
	{
		return new ResourceTypeServiceImpl(_resourceTypes);
	}
}
