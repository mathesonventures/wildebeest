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
 * Wire format object for serializing state log lines to JSON via Jackson.
 *
 * @since 4.0
 */
@JsonPropertyOrder(
	{
		"stateId",
		"name"
	})
public class StateEventLog
{
	private final UUID stateId;
	private final String name;

	/**
	 * Creates a new StateEventLog to match the supplied StateEventBody.
	 *
	 * @param eventBody the StateEventBody to copy into a new StateEventLog.
	 * @return a new StateEventLog matching the supplied StateEventBody.
	 * @since 4.0
	 */
	public static StateEventLog from(StateEventBody eventBody)
	{
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		return new StateEventLog(
			eventBody.getStateId(),
			eventBody.getName().orElse(null));
	}

	/**
	 * Creates a new StateEventLog with the supplied properties.
	 *
	 * @param stateId the ID of the State.
	 * @param name    the optional name of the State.
	 */
	private StateEventLog(
		UUID stateId,
		String name)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");

		this.stateId = stateId;
		this.name = name;
	}

	/**
	 * Gets the ID of the State.
	 *
	 * @return the ID of the State.
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
	public String getName()
	{
		return this.name;
	}
}
