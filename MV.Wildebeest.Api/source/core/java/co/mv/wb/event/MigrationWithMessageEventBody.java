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

import java.util.UUID;

/**
 * An {@link EventBody} representing a {@link Migration} and with an additional informational message.
 *
 * @since 4.0
 */
public class MigrationWithMessageEventBody extends MigrationEventBody
{
	public final String message;

	/**
	 * Creates a new MigrationWithMessageEventBody with the supplied properties.
	 *
	 * @param migrationId   the ID of the Migration.
	 * @param fromStateId   the ID of the optional from-state of the Migration.
	 * @param fromStateName the name of the optional from-stsate of the Migration.
	 * @param toStateId     the ID of the optional to-state of the Migration.
	 * @param toStateName   the name of the optional to-state of the Migration.
	 * @param message       the message to be provided with this event.
	 */
	public MigrationWithMessageEventBody(
		UUID migrationId,
		UUID fromStateId,
		String fromStateName,
		UUID toStateId,
		String toStateName,
		String message)
	{
		super(
			migrationId,
			fromStateId,
			fromStateName,
			toStateId,
			toStateName);

		if (message == null) throw new ArgumentNullException("message");

		this.message = message;
	}

	/**
	 * Gets the message included in this event.
	 *
	 * @return the message included in this event.
	 * @since 4.0
	 */
	public String getMessage()
	{
		return this.message;
	}
}
