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
import org.slf4j.Logger;

import java.io.IOException;

/**
 * An {@link EventSink} that sends the output to the logger at INFO level.
 *
 * @since 4.0
 */
public class LoggingEventSink implements EventSink
{
	private final Logger logger;
	private final ObjectMapper mapper;

	/**
	 * Creates a new LoggingEventSink that will log to the supplied {@link Logger}.
	 *
	 * @param logger the Logger that this event sink should log to.
	 * @since 4.0
	 */
	public LoggingEventSink(Logger logger)
	{
		if (logger == null) throw new ArgumentNullException("logger");

		this.logger = logger;
		this.mapper = EventHelper.createMapper();
	}

	@Override
	public void onEvent(Event event)
	{
		if (event == null) throw new ArgumentNullException("event");

		String logLine;

		// Attempt to format the event with its body as JSON
		try
		{
			logLine = this.mapper.writeValueAsString(event);
		}
		// If we weren't able to format the full event as JSON, use the fallback formatter.
		catch (IOException e)
		{
			logLine = EventHelper.fallbackEventJson(event);
		}

		this.logger.info(logLine);
	}
}
