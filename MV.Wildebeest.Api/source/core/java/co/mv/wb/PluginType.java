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

public enum PluginType
{
	Resource("co.mv.wb:Resource", "Resource"),
	Assertion("co.mv.wb:Assertion", "Assertion"),
	Migration("co.mv.wb:Migration", "Migration");

	private final String uri;
	private final String name;

	PluginType(
		String uri,
		String name)
	{
		if (uri == null) throw new ArgumentNullException("uri");
		if (name == null) throw new ArgumentNullException("name");

		this.uri = uri;
		this.name = name;
	}

	public String getUri()
	{
		return this.uri;
	}

	public String getName()
	{
		return this.name;
	}
}
