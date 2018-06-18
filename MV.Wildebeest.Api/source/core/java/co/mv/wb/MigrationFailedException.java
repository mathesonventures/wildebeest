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

package co.mv.wb;

import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * Indicates that a Migration failed for some normal reason that should be presented to the user.
 *
 * @since 1.0
 */
public class MigrationFailedException extends Exception
{
	private final UUID migrationId;

	/**
	 * Creates a new MigrationFailedException for the specified ID with the specified failure messages.
	 *
	 * @param migrationId the ID of the Migration that failed
	 * @param message     the failure message
	 * @since 1.0
	 */
	public MigrationFailedException(
		UUID migrationId,
		String message)
	{
		super(message);

		if (migrationId == null) throw new ArgumentNullException("migrationId");
		if (message == null) throw new ArgumentNullException("message");

		this.migrationId = migrationId;
	}

	/**
	 * Gets the ID of the Migration that failed
	 *
	 * @return the ID of the Migration that failed
	 * @since 1.0
	 */
	public UUID getMigrationId()
	{
		return this.migrationId;
	}
}
