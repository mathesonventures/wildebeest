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

import co.mv.wb.framework.ArgumentNullException;

import java.util.List;

/**
 * An injectable service that let's Wildebeest discover the types of resources that are available for it to work with.
 *
 * @since 4.0
 */
public interface ResourceTypeService
{
	List<ResourceType> getResourceTypes();

	default ResourceType forUri(String uri)
	{
		if (uri == null) throw new ArgumentNullException("uri");

		ResourceType result = null;

		for (ResourceType check : this.getResourceTypes())
		{
			if (uri.equals(check.getUri()))
			{
				result = check;
			}
		}

		if (result == null)
		{
			throw new IllegalArgumentException(String.format("url %s not recognized as a valid ResourceType", uri));
		}

		return result;
	}
}
