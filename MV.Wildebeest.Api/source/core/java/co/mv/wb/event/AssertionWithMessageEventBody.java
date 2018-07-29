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
 * Defines an event for Assertion which also carries a message.
 *
 * @since 4.0
 */
public class AssertionWithMessageEventBody extends AssertionEventBody
{
	private final String message;

	/**
	 * Constructs a new AssertionWithMessageEventBody with the supplied details.
	 *
	 * @param stateId     the ID of the {@link State} the event relates to
	 * @param assertionId the ID of the {@link Assertion} that the event relates to
	 * @param message     the message to include with the event
	 * @since 4.0
	 */
	public AssertionWithMessageEventBody(
		UUID stateId,
		UUID assertionId,
		String message)
	{
		super(
			stateId,
			assertionId);

		if (message == null) throw new ArgumentNullException("message");

		this.message = message;
	}

	/**
	 * Gets the message included with the event.
	 *
	 * @return the message included with the event
	 * @since 4.0
	 */
	public String getMessage()
	{
		return this.message;
	}
}
