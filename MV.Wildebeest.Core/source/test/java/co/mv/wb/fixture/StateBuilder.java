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

package co.mv.wb.fixture;

import co.mv.wb.framework.ArgumentNullException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Creates &lt;state&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 *
 * @since 4.0
 */
public class StateBuilder
{
	private final FixtureBuilder builder;
	private final ResourceBuilder resourceBuilder;
	private final UUID stateId;
	private final String name;
	private final String description;
	private final List<AssertionBuilder> assertions;

	public StateBuilder(
		FixtureBuilder builder,
		ResourceBuilder resourceBuilder,
		UUID stateId,
		String name,
		String description)
	{
		if (builder == null) throw new ArgumentNullException("builder");
		if (resourceBuilder == null) throw new ArgumentNullException("resourceBuilder");
		if (stateId == null) throw new ArgumentNullException("stateId");

		this.builder = builder;
		this.resourceBuilder = resourceBuilder;
		this.stateId = stateId;
		this.name = name;
		this.description = description;
		this.assertions = new ArrayList<>();
	}

	public UUID getStateId()
	{
		return this.stateId;
	}

	public String getName()
	{
		return this.name;
	}

	public boolean hasName()
	{
		return this.name != null;
	}

	public String getDescription()
	{
		return this.description;
	}

	public boolean hasDescription()
	{
		return this.description != null;
	}

	public List<AssertionBuilder> getAssertions()
	{
		return this.assertions;
	}

	public AssertionBuilder assertion(
		String type,
		UUID assertionId)
	{
		AssertionBuilder assertionBuilder = new AssertionBuilder(this.builder, this, type, assertionId);
		this.assertions.add(assertionBuilder);
		return assertionBuilder;
	}

	public ResourceBuilder resource()
	{
		return this.resourceBuilder;
	}

	public StateBuilder state(
		UUID stateId,
		String name)
	{
		return this.resourceBuilder.state(stateId, name);
	}

	public MigrationBuilder migration(
		String type,
		UUID migrationId,
		String fromState,
		String toState)
	{
		return this.resource().migration(
			type,
			migrationId,
			fromState,
			toState);
	}

	public String render()
	{
		return this.builder.build();
	}
}
