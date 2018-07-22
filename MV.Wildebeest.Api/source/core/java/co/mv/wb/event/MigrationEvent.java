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

/**
 * Defines an event for Migration
 *
 * @since 4.0
 */
public class MigrationEvent extends ResourceEvent<Migration>
{
	/**
	 * Provides all events associated with Migration.
	 *
	 * @since 4.0
	 */
	public enum Name
	{
		START,
		COMPLETE,
		FAILED
	}

	/**
	 * Constructs a new MigrationEvent with the supplied details.
	 *
	 * @param name      the name of the event
	 * @param message   the message of the event
	 * @param migration the source {@link Migration} of the event
	 * @since 4.0
	 */
	public MigrationEvent(
		String name,
		String message,
		Migration migration)
	{
		super(name, message, migration);
	}

	/**
	 * Creates an MigrationEvent for Start ResourceEvent.
	 *
	 * @param message   the message of the event
	 * @param migration the source {@link Migration} of the event
	 * @return the MigrationEvent created for Start ResourceEvent
	 * @since 4.0
	 */
	public static MigrationEvent start(
		String message,
		Migration migration)
	{
		return new MigrationEvent(Name.START.name(), message, migration);
	}

	/**
	 * Creates an MigrationEvent for Complete ResourceEvent.
	 *
	 * @param message   the message of the event
	 * @param migration the source {@link Migration} of the event
	 * @return the MigrationEvent created for Complete ResourceEvent
	 * @since 4.0
	 */
	public static MigrationEvent complete(
		String message,
		Migration migration)
	{
		return new MigrationEvent(Name.COMPLETE.name(), message, migration);
	}

	/**
	 * Creates an MigrationEvent for Failed ResourceEvent.
	 *
	 * @param message   the message of the event
	 * @param migration the source {@link Migration} of the event
	 * @return the MigrationEvent created for Failed ResourceEvent
	 * @since 4.0
	 */
	public static MigrationEvent failed(
		String message,
		Migration migration)
	{
		return new MigrationEvent(Name.FAILED.name(), message, migration);
	}
}
