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

public class JumpStateEvent extends Event
{
	/**
	 * Provides all events associated with JumpState Event.
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
	 * Constructs a new Event with the supplied details.
	 *
	 * @param name    the name of the event, this should be supplied from Event
	 * @param message a message of the event
	 * @since 4.0
	 */
	public JumpStateEvent(
		String name,
		String message)
	{
		super(name, message);
	}

	/**
	 * Creates an JumpStateEvent for Start Event.
	 *
	 * @return the JumpStateEvent created for Start Event
	 * @since 4.0
	 */
	public static JumpStateEvent start(String message)
	{
		return new JumpStateEvent(Name.START.name(), message);
	}

	/**
	 * Creates an JumpStateEvent for Complete Event.
	 *
	 * @return the JumpStateEvent created for Complete Event
	 * @since 4.0
	 */
	public static JumpStateEvent complete(String message)
	{
		return new JumpStateEvent(Name.COMPLETE.name(), message);
	}

	/**
	 * Creates an JumpStateEvent for Failed Event.
	 *
	 * @param message the message of the event
	 * @return the JumpStateEvent created for Failed Event
	 * @since 4.0
	 */
	public static JumpStateEvent failed(String message)
	{
		return new JumpStateEvent(Name.FAILED.name(), message);
	}
}
