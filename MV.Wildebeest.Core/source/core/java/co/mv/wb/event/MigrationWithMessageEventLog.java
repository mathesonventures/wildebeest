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

import java.util.UUID;

/**
 * Wire format object for serializing migration-with-message log lines to JSON via Jackson.
 *
 * @since 4.0
 */
@JsonPropertyOrder(
	{
		"migrationId",
		"fromStateId",
		"fromStateName",
		"toStateId",
		"toStateName",
		"message"
	})
public class MigrationWithMessageEventLog
{
	private final UUID migrationId;
	private final UUID fromStateId;
	private final String fromStateName;
	private final UUID toStateId;
	private final String toStateName;
	private final String message;

	public static MigrationWithMessageEventLog from(MigrationWithMessageEventBody eventBody)
	{
		if (eventBody == null) throw new ArgumentNullException("eventBody");

		return new MigrationWithMessageEventLog(
			eventBody.getMigrationId(),
			eventBody.getFromStateId().orElse(null),
			eventBody.getFromStateName().orElse(null),
			eventBody.getToStateId().orElse(null),
			eventBody.getToStateName().orElse(null),
			eventBody.getMessage());
	}

	/**
	 * Constructs a new MigrationEventLog with the supplied properties.
	 *
	 * @param migrationId   the ID of the {@link Migration}.
	 * @param fromStateId   the ID of the optional from-state of the Migration.
	 * @param fromStateName the name of the optional from-state of the Migration.
	 * @param toStateId     the ID of the optional to-state of the Migration.
	 * @param toStateName   the name of the optional to-state of the Migration.
	 * @param message       the message.
	 */
	public MigrationWithMessageEventLog(
		UUID migrationId,
		UUID fromStateId,
		String fromStateName,
		UUID toStateId,
		String toStateName,
		String message)
	{
		if (migrationId == null) throw new ArgumentNullException("migrationId");
		if (message == null) throw new ArgumentNullException("message");

		this.migrationId = migrationId;
		this.fromStateId = fromStateId;
		this.fromStateName = fromStateName;
		this.toStateId = toStateId;
		this.toStateName = toStateName;
		this.message = message;
	}

	/**
	 * Gets the ID of the {@link Migration}.
	 *
	 * @return the ID of the Migration.
	 * @since 4.0
	 */
	public UUID getMigrationId()
	{
		return this.migrationId;
	}

	/**
	 * Gets the ID of the optional from-state of the Migration.
	 *
	 * @return the ID of the optional from-state of the Migration.
	 * @since 4.0
	 */
	public UUID getFromStateId()
	{
		return this.fromStateId;
	}

	/**
	 * Gets the name of the optional from-state of the Migration.
	 *
	 * @return the name of the optional from-state of the Migration.
	 * @since 4.0
	 */
	public String getFromStateName()
	{
		return this.fromStateName;
	}

	/**
	 * Gets the ID of the optional to-state of the Migration.
	 *
	 * @return the ID of the optional to-state of the Migration.
	 * @since 4.0
	 */
	public UUID getToStateId()
	{
		return this.toStateId;
	}

	/**
	 * Gets the name of the optional to-state of the Migration.
	 *
	 * @return the name of the optional to-state of the Migration.
	 * @since 4.0
	 */
	public String getToStateName()
	{
		return this.toStateName;
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
