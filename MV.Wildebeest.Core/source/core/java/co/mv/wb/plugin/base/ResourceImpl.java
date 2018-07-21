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

package co.mv.wb.plugin.base;

import co.mv.wb.Migration;
import co.mv.wb.Resource;
import co.mv.wb.ResourceType;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Resource}
 *
 * @since 1.0
 */
public final class ResourceImpl implements Resource
{
	private final UUID resourceId;
	private final ResourceType type;
	private final String name;
	private final List<State> states;
	private final List<Migration> migrations;
	private final String defaultTarget;

	/**
	 * Creates a new concrete Resource instance.
	 *
	 * @param resourceId    the ID of the new Resource
	 * @param type          the type of the new Resource
	 * @param name          the name of the new Resource
	 * @param defaultTarget the optional default target for this resource
	 * @since 1.0
	 */
	public ResourceImpl(
		UUID resourceId,
		ResourceType type,
		String name,
		String defaultTarget)
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (type == null) throw new ArgumentNullException("type");
		if (name == null) throw new ArgumentNullException("name");

		this.resourceId = resourceId;
		this.type = type;
		this.name = name;
		this.states = new ArrayList<>();
		this.migrations = new ArrayList<>();
		this.defaultTarget = defaultTarget;
	}

	@Override public UUID getResourceId()
	{
		return this.resourceId;
	}

	@Override
	public ResourceType getType()
	{
		return this.type;
	}

	@Override public String getName()
	{
		return this.name;
	}

	@Override public List<State> getStates()
	{
		return this.states;
	}

	@Override public List<Migration> getMigrations()
	{
		return this.migrations;
	}

	@Override
	public Optional<String> getDefaultTarget()
	{
		return Optional.ofNullable(this.defaultTarget);
	}
}
