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

package co.mv.wb.event;

import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;

import java.util.Optional;
import java.util.UUID;

/**
 * An {@link EventBody} representing a event on a {@link State}.
 *
 * @since 4.0
 */
public class StateEventBody implements EventBody
{
	private final UUID stateId;
	private final String name;

	/**
	 * Creates a new StateEventBody with the supplied properties.
	 *
	 * @param stateId the ID of the {@link State} that the event relates to.
	 * @param name    the optional name of the State.
	 * @since 4.0
	 */
	public StateEventBody(
		UUID stateId,
		String name)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");

		this.stateId = stateId;
		this.name = name;
	}

	/**
	 * Gets the ID of the {@link State} that the event relates to.
	 *
	 * @return the ID of the State that the event relates to
	 * @since 4.0
	 */
	public UUID getStateId()
	{
		return this.stateId;
	}

	/**
	 * Gets the optional name of the State.
	 *
	 * @return the optional name of the State.
	 * @since 4.0
	 */
	public Optional<String> getName()
	{
		return Optional.ofNullable(this.name);
	}
}
