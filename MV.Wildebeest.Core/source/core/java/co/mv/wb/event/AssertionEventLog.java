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
 * Wire format object for serializing assertion log lines to JSON via Jackson.
 *
 * @since 4.0
 */
@JsonPropertyOrder(
	{
		"stateId",
		"assertionId"
	})
public class AssertionEventLog
{
	private final UUID stateId;
	private final UUID assertionId;

	/**
	 * Creates a new AssertionEventLog to match the supplied {@link AssertionEventBody}.
	 *
	 * @param eventBody the AssertionEventBody to copy into a new AssertionEventLog.
	 * @return an AssertionEventLog matching the supplied AssertionEventBody.
	 * @since 4.0
	 */
	public static AssertionEventLog from(AssertionEventBody eventBody)
	{
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		return new AssertionEventLog(
			eventBody.getStateId(),
			eventBody.getStateId());
	}

	/**
	 * Creates a new AssertionEventLog with the supplied properties.
	 *
	 * @param stateId     the ID of the state that the assertion was applied to.
	 * @param assertionId the ID of the specific assertion that was applied.
	 * @since 4.0
	 */
	private AssertionEventLog(
		UUID stateId,
		UUID assertionId)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertionId == null) throw new ArgumentNullException("assertionId");

		this.stateId = stateId;
		this.assertionId = assertionId;
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
}
