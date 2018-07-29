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

import co.mv.wb.framework.ArgumentNullException;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.UUID;

/**
 * Wire format object for serializing assertion-with-message log lines to JSON via Jackson.
 *
 * @since 4.0
 */
@JsonPropertyOrder(
	{
		"stateId",
		"assertionId",
		"message"
	})
public class AssertionWithMessageEventLog
{
	private final UUID stateId;
	private final UUID assertionId;
	private final String message;

	/**
	 * Creates a new AssertionWithMessageEventLog to match the supplied {@link AssertionWithMessageEventBody}.
	 *
	 * @param eventBody the AssertionEventBody to copy into a new AssertionEventLog.
	 * @return an AssertionEventLog matching the supplied AssertionEventBody.
	 * @since 4.0
	 */
	public static AssertionWithMessageEventLog from(AssertionWithMessageEventBody eventBody)
	{
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		return new AssertionWithMessageEventLog(
			eventBody.getStateId(),
			eventBody.getStateId(),
			eventBody.getMessage());
	}

	/**
	 * Creates a new AssertionWithMessageEventLog with the supplied properties.
	 *
	 * @param stateId     the ID of the state that the assertion was applied to.
	 * @param assertionId the ID of the specific assertion that was applied.
	 * @param message     the message.
	 * @since 4.0
	 */
	private AssertionWithMessageEventLog(
		UUID stateId,
		UUID assertionId,
		String message)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertionId == null) throw new ArgumentNullException("assertionId");
		if (message == null) throw new ArgumentNullException("message");

		this.stateId = stateId;
		this.assertionId = assertionId;
		this.message = message;
	}

	/**
	 * Gets the ID of the state that the assertion was applied to.
	 *
	 * @return the ID of the state that the assertion was applied to.
	 * @since 4.0
	 */
	public UUID getStateId()
	{
		return this.stateId;
	}

	/**
	 * Gets the ID of the specific assertion that was applied.
	 *
	 * @return the ID of the specific assertion that was applied.
	 * @since 4.0
	 */
	public UUID getAssertionId()
	{
		return this.assertionId;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message.
	 * @since 4.0
	 */
	public String getMessage()
	{
		return this.message;
	}
}
