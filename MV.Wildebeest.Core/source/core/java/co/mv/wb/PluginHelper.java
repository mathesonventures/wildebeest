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

package co.mv.wb;

import co.mv.wb.service.ResourceLoaderFault;

import java.util.Map;

public class PluginHelper
{
	/**
	 * Looks up the ResourcePlugin for the supplied ResourceType.
	 *
	 * @param       resourcePlugins             the set of available ResourcePlugins.
	 * @param       resourceType                the ResourceType for which a plugin should be retrieved.
	 * @return                                  the ResourcePlugin that corresponds to the supplied ResourceType.
	 * @since                                   4.0
	 */
	public static ResourcePlugin getResourcePlugin(
		Map<ResourceType, ResourcePlugin> resourcePlugins,
		ResourceType resourceType)
	{
		if (resourcePlugins == null) { throw new IllegalArgumentException("resourcePlugins cannot be null"); }
		if (resourceType == null) { throw new IllegalArgumentException("resourceType cannot be null"); }

		ResourcePlugin resourcePlugin = resourcePlugins.get(resourceType);

		if (resourcePlugin == null)
		{
			throw new ResourceLoaderFault(String.format(
				"resource plugin for resource type %s not found",
				resourceType.getUri()));
		}

		return resourcePlugin;
	}
}
