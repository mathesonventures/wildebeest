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
import org.joda.time.DateTime;

/**
 * Defines an event that is fired from Wildebeest to notify activity to external consumers.
 *
 * @since 4.0
 */
public final class Event
{
	private final String eventUri;
	private final DateTime raisedInstant;
	private final EventBody eventBody;

	/**
	 * Constructs a new Event with the supplied details.
	 *
	 * @param eventUri      the identifying URI of the event which can be used by consumers to route or process events
	 *                      correctly
	 * @param raisedInstant the instant when the event was raised
	 * @param eventBody     the body of hte event conveying type-specific details
	 * @since 4.0
	 */
	public Event(
		String eventUri,
		DateTime raisedInstant,
		EventBody eventBody)
	{
		if (eventUri == null) throw new ArgumentNullException("name");
		if (raisedInstant == null) throw new ArgumentNullException("raisedInstant");
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		this.eventUri = eventUri;
		this.raisedInstant = raisedInstant;
		this.eventBody = eventBody;
	}

	/**
	 * Gets the identifying URI of the which can be used by consumers to route or process events.
	 *
	 * @return the identifying URI of the event
	 * @since 4.0
	 */
	public String getEventUri()
	{
		return this.eventUri;
	}

	/**
	 * The instant when the event was first raised.
	 *
	 * @return the instant when the event was first raised.
	 * @since 4.0
	 */
	public DateTime getRaisedInstant()
	{
		return this.raisedInstant;
	}

	/**
	 * The body for this event which conveys specific information related to the event.
	 *
	 * @return the body for this event
	 * @since 4.0
	 */
	public EventBody getEventBody()
	{
		return this.eventBody;
	}
}
