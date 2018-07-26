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

import co.mv.wb.Migration;
import co.mv.wb.framework.ArgumentNullException;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * An {@link EventSink} that sends the output to the migration-logger (migration.log).
 *
 * @since 4.0
 */
public class MigrationEventSink implements EventSink
{
	private final Logger migrationLogger = LoggerFactory.getLogger("migration-logger");
	private final Logger logger;
	private final ObjectMapper mapper;

	public MigrationEventSink(Logger logger)
	{
		if (logger == null) throw new ArgumentNullException("logger");

		this.logger = logger;
		mapper = new ObjectMapper();
		TimeZone tz = TimeZone.getTimeZone("UTC");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(tz);
		mapper.setDateFormat(df);
	}

	@Override public void onEvent(Event event)
	{
		if (!(event instanceof MigrationEvent))
		{
			return;
		}

		MigrationEvent migrationEvent = (MigrationEvent)event;
		if (migrationEvent.getName().equals(MigrationEvent.Name.START.name()))
		{
			return;
		}

		try
		{
			MigrationLog migrationLog = new MigrationLog(migrationEvent);
			String json = mapper.writeValueAsString(migrationLog);
			migrationLogger.info(json);
		}
		catch (IOException e)
		{
			this.logger.error("Exception encountered while writing to migration log", e);
		}
	}

	@JsonPropertyOrder({"datetime", "migration", "fromState", "toState", "successful"})
	private static class MigrationLog
	{
		private final UUID migration;
		private final String fromState;
		private final String toState;
		private final boolean successful;
		private final Date datetime;

		public MigrationLog(MigrationEvent event)
		{
			Migration _migration = event.getElementResource();
			migration = _migration.getMigrationId();
			fromState = _migration.getFromState().isPresent() ? _migration.getFromState().get() : "";
			toState = _migration.getToState().isPresent() ? _migration.getToState().get() : "";
			datetime = new Date();

			switch (MigrationEvent.Name.valueOf(event.getName()))
			{
				case COMPLETE:
					successful = true;
					break;
				case FAILED:
				default:
					successful = false;
					break;
			}
		}

		public UUID getMigration()
		{
			return migration;
		}

		public String getFromState()
		{
			return fromState;
		}

		public String getToState()
		{
			return toState;
		}

		public boolean isSuccessful()
		{
			return successful;
		}

		public Date getDatetime()
		{
			return datetime;
		}

	}
}
