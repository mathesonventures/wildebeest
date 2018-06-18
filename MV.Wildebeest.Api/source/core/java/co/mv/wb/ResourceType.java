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

/**
 * Identifies a type of {@link Resource}.  The resource's type is identified in the XML file, and is used to verify
 * which {@link Assertion}'s and {@link Migration}'s can be applied to the Resource.
 *
 * @since 4.0
 */
public class ResourceType
{

	private final String uri;
	private final String name;

	/**
	 * Constructs a new ResourceType with the specified details.
	 *
	 * @param uri  the URI identifying the resource type.
	 * @param name the human-readable name of the resource type.
	 * @since 4.0
	 */
	public ResourceType(
		String uri,
		String name)
	{
		if (uri == null) throw new ArgumentNullException("uri");
		if (name == null) throw new ArgumentNullException("name");

		this.uri = uri;
		this.name = name;
	}

	/**
	 * Gets the URI identifying the resource type.
	 *
	 * @return the URI identifying the resource type.
	 * @since 4.0
	 */
	public String getUri()
	{
		return this.uri;
	}

	/**
	 * Gets the human-readable name of the resource type.
	 *
	 * @return the human-readable name of the resource type.
	 * @since 4.0
	 */
	public String getName()
	{
		return this.name;
	}
}
