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
import org.codehaus.jackson.map.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Internal helper methods for working with {@link Event}'s.
 *
 * @since 4.0
 */
public final class EventHelper
{
	public static Object toEventLog(EventBody eventBody)
	{
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		Object result;

		if (eventBody.getClass().equals(MigrationEventBody.class))
		{
			result = MigrationEventLog.from((MigrationEventBody)eventBody);
		}
		else if (eventBody.getClass().equals(MigrationWithMessageEventBody.class))
		{
			result = MigrationWithMessageEventLog
				.from((MigrationWithMessageEventBody)eventBody);
		}
		else if (eventBody.getClass().equals(AssertionEventBody.class))
		{
			result = AssertionEventLog.from((AssertionEventBody)eventBody);
		}
		else if (eventBody.getClass().equals((AssertionWithMessageEventBody.class)))
		{
			result = AssertionWithMessageEventLog
				.from((AssertionWithMessageEventBody)eventBody);
		}
		else if (eventBody.getClass().equals(StateEventBody.class))
		{
			result = StateEventLog.from((StateEventBody)eventBody);
		}
		else
		{
			throw new RuntimeException("unhandled case");
		}

		return result;
	}

	/**
	 * Creates a {@link DateFormat} to format dates according to ISO-8601.
	 *
	 * @return a DateFormat that will format dates according to ISO-8601
	 * @since 4.0
	 */
	public static DateFormat iso8601DateFormat()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));

		return df;
	}

	/**
	 * Creates a Jackson {@link ObjectMapper} for serializing Wildebeest objects to JSON.
	 *
	 * @return 4.0
	 */
	public static ObjectMapper createMapper()
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(EventHelper.iso8601DateFormat());

		return mapper;
	}

	/**
	 * A fallback routine for generating a minimal JSON for {@link Event}s in case the real generation fails.
	 *
	 * @param event the Event for which JSON should be generated.
	 * @return a JSON representation of the event.
	 */
	public static String fallbackEventJson(Event event)
	{
		if (event == null) throw new ArgumentNullException("event");

		DateFormat df = EventHelper.iso8601DateFormat();

		return String.format(
			"{ eventUri: \"%s\"; raisedInstant: \"%s\"; }",
			event.getEventUri(),
			df.format(event.getRaisedInstant().toDate()));
	}
}
