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

import java.util.Optional;

/**
 * Defines an Event
 *
 * @since 4.0
 */
public class Event
{
	private final String name;
	private final Optional<String> message;

	/**
	 * Constructs a new Event with the supplied details.
	 *
	 * @param name    the name of the event, this should be supplied from Event
	 * @param message a message of the event
	 * @since 4.0
	 */
	public Event(
		String name,
		Optional<String> message)
	{
		if (name == null) throw new ArgumentNullException("name");
		if (message == null) throw new ArgumentNullException("message");

		this.name = name;
		this.message = message;
	}

	/**
	 * Gets the name of the event
	 *
	 * @return the name of the event
	 * @since 4.0
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Gets the message of the event
	 *
	 * @return the name of the event
	 * @since 4.0
	 */
	public Optional<String> getMessage()
	{
		return this.message;
	}
}
