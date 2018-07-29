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
import org.joda.time.DateTime;

/**
 * Wire format object for serializing events to JSON via Jackson.
 *
 * @since 4.0
 */
@JsonPropertyOrder(
	{
		"eventUri",
		"raisedInstant",
		"eventBody"
	})
public class EventLog
{
	private final String eventUri;
	private final DateTime raisedInstant;
	private final Object eventBody;

	public static EventLog from(
		Event event,
		Object eventBody)
	{
		if (event == null) throw new ArgumentNullException("event");
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		return new EventLog(
			event.getEventUri(),
			event.getRaisedInstant(),
			eventBody);
	}

	/**
	 * Creates a new EventLog with the supplied properties.
	 *
	 * @param eventUri      the identifying URI for the event.
	 * @param raisedInstant the instant that the event was raised.
	 * @param eventBody     the body of the event.
	 */
	private EventLog(
		String eventUri,
		DateTime raisedInstant,
		Object eventBody)
	{
		if (eventUri == null) throw new ArgumentNullException("eventUri");
		if (raisedInstant == null) throw new ArgumentNullException("raisedInstant");
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		this.eventUri = eventUri;
		this.raisedInstant = raisedInstant;
		this.eventBody = eventBody;
	}

	/**
	 * Gets the identifying URI of the event.
	 *
	 * @return the identifying URI of the event.
	 * @since 4.0
	 */
	public String getEventUri()
	{
		return this.eventUri;
	}

	/**
	 * Gets the raised instant of the event.
	 *
	 * @return the raised instant of the event.
	 * @since 4.0
	 */
	public DateTime getRaisedInstant()
	{
		return this.raisedInstant;
	}

	/**
	 * Gets the body of the event
	 *
	 * @return the body of the event.
	 * @since 4.0
	 */
	public Object getEventBody()
	{
		return this.eventBody;
	}
}
