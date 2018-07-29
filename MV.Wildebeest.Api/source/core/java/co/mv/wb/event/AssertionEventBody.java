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

import co.mv.wb.Assertion;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * Defines an {@link EventBody} representing an Assertion.
 *
 * @since 4.0
 */
public class AssertionEventBody implements EventBody
{
	private final UUID stateId;
	private final UUID assertionId;

	/**
	 * Constructs a new AssertionEventBody with the supplied details.
	 *
	 * @since 4.0
	 */
	public AssertionEventBody(
		UUID stateId,
		UUID assertionId)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertionId == null) throw new ArgumentNullException("assertionId");

		this.stateId = stateId;
		this.assertionId = assertionId;
	}

	/**
	 * Gets the ID of the {@link State} that the event was relates to.
	 *
	 * @return the ID of the State that the event relates to.
	 * @since 4.0
	 */
	public UUID getStateId()
	{
		return this.stateId;
	}

	/**
	 * Gets the ID of the {@link Assertion} that the event relates to.
	 *
	 * @return the ID of the Assertion that hte event relates to.
	 * @since 4.0
	 */
	public UUID getAssertionId()
	{
		return this.assertionId;
	}
}
