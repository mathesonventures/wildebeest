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
 * Represents a reference to an entity (e.g. a state, migration, assertion, etc) defined in Wildebeest.
 *
 * @since 4.0
 */
public class Reference
{
	private final EntityType type;
	private final String ref;

	public Reference(
		EntityType type,
		String ref)
	{
		if (type == null) throw new ArgumentNullException("type");
		if (ref == null) throw new ArgumentNullException("ref");

		this.type = type;
		this.ref = ref;
	}

	public EntityType getType()
	{
		return this.type;
	}

	public String getRef()
	{
		return this.ref;
	}
}
