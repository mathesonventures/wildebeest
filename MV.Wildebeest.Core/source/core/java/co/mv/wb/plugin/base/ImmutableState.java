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

import co.mv.wb.Assertion;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link State} that cannot be modified after it's initial construction.
 *
 * @since 1.0
 */
public class ImmutableState implements State
{

	private final UUID stateId;
	private final String name;

	private final List<Assertion> assertions;
	private final String description;

	/**
	 * Creates a new ImmutableState with the supplied ID.
	 *
	 * @param stateId the ID of the new state
	 */
	public ImmutableState(
		UUID stateId)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");

		this.stateId = stateId;
		this.name = null;
		this.assertions = new ArrayList<>();
		this.description = null;
	}

	/**
	 * Creates a new ImmutableState with an ID and a name.
	 *
	 * @param stateId the ID of the new state
	 * @param name    the unique name of the new state
	 */
	public ImmutableState(
		UUID stateId,
		String name)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");

		this.stateId = stateId;
		this.name = name;
		this.assertions = new ArrayList<>();
		this.description = null;
	}

	/**
	 * Creates a new ImmutableState with the supplied ID and set of {@link Assertion}s.
	 *
	 * @param stateId    the ID of the new state
	 * @param assertions the assertions that apply to this state
	 */
	public ImmutableState(
		UUID stateId,
		List<Assertion> assertions)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertions == null) throw new ArgumentNullException("assertions");

		this.stateId = stateId;
		this.name = null;
		this.assertions = assertions;
		this.description = null;
	}

	/**
	 * Creates a new ImmutableState with an ID and a name, and with a set of {@link Assertion}s.
	 *
	 * @param stateId    the ID of the new state
	 * @param name       the unique name of the new state
	 * @param assertions the assertions that apply to this state
	 */
	public ImmutableState(
		UUID stateId,
		String name,
		List<Assertion> assertions)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertions == null) throw new ArgumentNullException("assertions");

		this.stateId = stateId;
		this.name = name;
		this.assertions = assertions;
		this.description = null;
	}

	/**
	 * Creates a new ImmutableState with an ID and a name, and with a set of {@link Assertion}s.
	 *
	 * @param stateId     the ID of the new state
	 * @param name        the unique name of the new state
	 * @param description the description that apply to this state
	 */
	public ImmutableState(
		UUID stateId,
		String name,
		String description)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");

		this.stateId = stateId;
		this.name = name;
		this.assertions = new ArrayList<>();
		this.description = description;
	}

	/**
	 * Creates a new ImmutableState with an ID and a name, and with a set of {@link Assertion}s.
	 *
	 * @param stateId     the ID of the new state
	 * @param name        the unique name of the new state
	 * @param assertions  the assertions that apply to this state
	 * @param description the description that apply to this state
	 */
	public ImmutableState(
		UUID stateId,
		String name,
		List<Assertion> assertions,
		String description)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertions == null) throw new ArgumentNullException("assertions");

		this.stateId = stateId;
		this.name = name;
		this.assertions = assertions;
		this.description = description;
	}

	@Override
	public UUID getStateId()
	{
		return this.stateId;
	}

	@Override
	public Optional<String> getName()
	{
		return Optional.ofNullable(this.name);
	}

	@Override
	public List<Assertion> getAssertions()
	{
		return this.assertions;
	}

	@Override
	public String getDisplayName()
	{
		return this.name != null ? this.name : this.stateId.toString();
	}

	@Override
	public Optional<String> getDescription()
	{
		return Optional.ofNullable(this.description);
	}
}
