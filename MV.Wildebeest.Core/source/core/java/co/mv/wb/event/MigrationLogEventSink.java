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
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * An {@link EventSink} that sends the output to the migration-logger (migration.log).
 *
 * @since 4.0
 */
public class MigrationLogEventSink implements EventSink
{
	private final Logger migrationLogger = LoggerFactory.getLogger("migration-logger");
	private final Logger logger;
	private final ObjectMapper mapper;

	/**
	 * Creates a new MigrationEventSink that will log to the supplied Logger.
	 *
	 * @param logger the Logger that this event sink should log to
	 * @since 4.0
	 */
	public MigrationLogEventSink(Logger logger)
	{
		if (logger == null) throw new ArgumentNullException("logger");

		this.logger = logger;

		this.mapper = EventHelper.createMapper();
	}

	@Override
	public void onEvent(Event event)
	{
		if (event == null) throw new ArgumentNullException("event");

		if (event.getEventUri().equals(Events.EVENT_URI_MIGRATION_START) ||
			event.getEventUri().equals(Events.EVENT_URI_MIGRATION_COMPLETE) ||
			event.getEventUri().equals(Events.EVENT_URI_MIGRATION_FAILED))
		{
			MigrationEventBody migrationEventBody = (MigrationEventBody)event.getEventBody();
			MigrationLog migrationLog = new MigrationLog(migrationEventBody);
			String logLine;

			// Attempt to format the migration log entry as JSON
			try
			{
				logLine = this.mapper.writeValueAsString(migrationLog);
			}
			// If we weren't able to format the full event as JSON, use the fallback formatter.
			catch (IOException e)
			{
				logLine = EventHelper.fallbackEventJson(event);
			}

			migrationLogger.info(logLine);
		}
	}

	@JsonPropertyOrder({"datetime", "migration", "fromState", "toState", "successful"})
	private static class MigrationLog
	{
		private final UUID migrationId;
		private final String fromState;
		private final String toState;

		public MigrationLog(MigrationEventBody event)
		{
			this.migrationId = event.getMigrationId();
			this.fromState = event.getFromState().orElse("(non-existent)");
			this.toState = event.getToState().orElse("(non-existent");
		}

		public UUID getMigrationId()
		{
			return migrationId;
		}

		public String getFromState()
		{
			return fromState;
		}

		public String getToState()
		{
			return toState;
		}
	}
}
