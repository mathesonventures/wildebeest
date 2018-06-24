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

public enum EntityType
{
	State("urn:co.mv.wildebeest:state", "State"),
	Migration("urn:co.mv.wildebeest:migration", "Migration"),
	AssertionGroup("urn:co.mv.wildebeest:assertionGroup", "Assertion Group");

	private final String urn;
	private final String name;

	EntityType(
		String urn,
		String name)
	{
		if (urn == null) throw new ArgumentNullException("urn");
		if (name == null) throw new ArgumentNullException("name");

		this.urn = urn;
		this.name = name;
	}

	public String getUrn()
	{
		return this.urn;
	}

	public String getName()
	{
		return this.name;
	}
}
