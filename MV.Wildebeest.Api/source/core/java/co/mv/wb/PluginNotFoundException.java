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

public class PluginNotFoundException extends Exception
{
	private final PluginType pluginType;
	private final String uri;
	private final List<String> knownUris;

	public PluginNotFoundException(
		PluginType pluginType,
		String uri,
		List<String> knownUris)
	{
		if (pluginType == null) throw new ArgumentNullException("pluginType");
		if (uri == null) throw new ArgumentNullException("uri");
		if (knownUris == null) throw new ArgumentNullException("knownUris");

		this.pluginType = pluginType;
		this.uri = uri;
		this.knownUris = knownUris;
	}

	public PluginType getPluginType()
	{
		return this.pluginType;
	}

	public String getUri()
	{
		return this.uri;
	}

	public List<String> getKnownUris()
	{
		return this.knownUris;
	}
}
