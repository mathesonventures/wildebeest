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
import co.mv.wb.framework.ArgumentNullException;

import java.util.ArrayList;
import java.util.List;

/**
 * A fluent-style helper for building a ResourceTypeService instance.
 *
 * @since 4.0
 */
public class ResourceTypeServiceBuilder
{
	private final List<ResourceType> resourceTypes;

	private ResourceTypeServiceBuilder(List<ResourceType> resourceTypes)
	{
		if (resourceTypes == null) throw new ArgumentNullException("resourceTypes");

		this.resourceTypes = resourceTypes;
	}

	/**
	 * Fluently creates a new empty ResourceTypeServiceBuilder instance.
	 *
	 * @return a new builder instance.
	 * @since 4.0
	 */
	public static ResourceTypeServiceBuilder create()
	{
		return new ResourceTypeServiceBuilder(new ArrayList<>());
	}

	/**
	 * Fluently adds the factory-preset set of {@link ResourceType}'s into the builder.  A new builder is returned and
	 * the original is left unmutated.
	 *
	 * @return a new builder instance with the state of the source builder, plus the
	 * factory-preset set of ResourceTypes added.
	 * @since 4.0
	 */
	public ResourceTypeServiceBuilder withFactoryResourceTypes()
	{
		List<ResourceType> updatedResourceTypes = new ArrayList<>(this.resourceTypes);

		updatedResourceTypes.add(Wildebeest.MySqlDatabase);
		updatedResourceTypes.add(Wildebeest.PostgreSqlDatabase);
		updatedResourceTypes.add(Wildebeest.SqlServerDatabase);

		return new ResourceTypeServiceBuilder(updatedResourceTypes);
	}

	/**
	 * Fluently adds the specified {@link ResourceType} into the builder.  A new builder instance is returned and the
	 * original is left unmutated.
	 *
	 * @param resourceType the ResourceType to accumulated to the builder.
	 * @return a new builder instance with the state of the source builder, plus the
	 * supplied new ResourceType added.
	 * @since 4.0
	 */
	public ResourceTypeServiceBuilder with(ResourceType resourceType)
	{
		if (resourceType == null) throw new ArgumentNullException("resourceType");

		List<ResourceType> updatedResourceTypes = new ArrayList<>(this.resourceTypes);

		updatedResourceTypes.add(resourceType);

		return new ResourceTypeServiceBuilder(updatedResourceTypes);
	}

	/**
	 * Returns a {@link ResourceTypeService} configured with the set of {@link ResourceType}'s that were collected into
	 * this builder.
	 *
	 * @return a new ResourceTypeService configured with the set of ResourceType's that
	 * were collected into this builder.
	 * @since 4.0
	 */
	public ResourceTypeService build()
	{
		return new ResourceTypeServiceImpl(resourceTypes);
	}
}
