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

/**
 * Defines an event for Migration
 *
 * @since 4.0
 */
public class MigrationEvent extends Event
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
		FAILED;
	}

	/**
	 * Constructs a new MigrationEvent with the supplied details.
	 *
	 * @param name    the name of the event
	 * @param message the message of the event
	 * @since 4.0
	 */
	public MigrationEvent(
		String name,
		String message)
	{
		super(name, message);
	}

	/**
	 * Creates an MigrationEvent for Start Event.
	 *
	 * @param message the message of the event
	 * @return the MigrationEvent created for Start Event
	 * @since 4.0
	 */
	public static MigrationEvent start(String message)
	{
		return new MigrationEvent(MigrationEvent.Name.START.name(), message);
	}

	/**
	 * Creates an MigrationEvent for Complete Event.
	 *
	 * @param message the message of the event
	 * @return the MigrationEvent created for Complete Event
	 * @since 4.0
	 */
	public static MigrationEvent complete(String message)
	{
		return new MigrationEvent(MigrationEvent.Name.COMPLETE.name(), message);
	}

	/**
	 * Creates an MigrationEvent for Failed Event.
	 *
	 * @param message the message of the event
	 * @return the MigrationEvent created for Failed Event
	 * @since 4.0
	 */
	public static MigrationEvent failed(String message)
	{
		return new MigrationEvent(MigrationEvent.Name.FAILED.name(), message);
	}
}
