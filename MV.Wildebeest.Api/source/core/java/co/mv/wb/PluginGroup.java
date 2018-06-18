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
 * Defines a meta classification for plugins to help with discoverability and documentation generation.  PluginGroup
 * plays no role in Wildebeest workflows.
 *
 * @since 4.0
 */
public class PluginGroup
{
	private final String uri;
	private final String title;
	private final String description;

	/**
	 * Constructs a new PluginGroup with the supplied details.
	 *
	 * @param uri         the URI identifying the plugin group.
	 * @param title       the human-readable title of the plugin group, for use in tooling.
	 * @param description a description of the plugin group, for use in tooling.
	 * @since 4.0
	 */
	public PluginGroup(
		String uri,
		String title,
		String description)
	{
		if (uri == null) throw new ArgumentNullException("uri");
		if (title == null) throw new ArgumentNullException("title");
		if (description == null) throw new ArgumentNullException("description");

		this.uri = uri;
		this.title = title;
		this.description = description;
	}

	/**
	 * Gets the URI identifying the plugin group.
	 *
	 * @return the URI identifying the plugin group.
	 * @since 4.0
	 */
	public String getUri()
	{
		return uri;
	}

	/**
	 * Gets the name of the plugin group, extracted from the URI.
	 *
	 * @return the name of the plugin group, extracted from the URI.
	 * @since 4.0
	 */
	public String getName()
	{
		int index = this.getUri().lastIndexOf(":");
		return this.getUri().substring(index + 1);
	}

	/**
	 * Gets the human-readable title of the plugin group.
	 *
	 * @return the human-readable title of the plugin group.
	 * @since 4.0
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Gets the description of the plugin group.
	 *
	 * @return the description of the plugin group.
	 * @since 4.0
	 */
	public String getDescription()
	{
		return description;
	}
}
