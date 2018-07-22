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

import co.mv.wb.State;

public class JumpStateEvent extends ResourceEvent<State>
{
	/**
	 * Provides all events associated with JumpState ResourceEvent.
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
	 * Constructs a new ResourceEvent with the supplied details.
	 *
	 * @param name    the name of the event, this should be supplied from ResourceEvent
	 * @param message a message of the event
	 * @param state   the source {@link State} of the event
	 * @since 4.0
	 */
	public JumpStateEvent(
		String name,
		String message,
		State state)
	{
		super(name, message, state);
	}

	/**
	 * Creates an JumpStateEvent for Start ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the JumpStateEvent created for Start ResourceEvent
	 * @since 4.0
	 */
	public static JumpStateEvent start(
		String message,
		State state)
	{
		return new JumpStateEvent(Name.START.name(), message, state);
	}

	/**
	 * Creates an JumpStateEvent for Complete ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the JumpStateEvent created for Complete ResourceEvent
	 * @since 4.0
	 */
	public static JumpStateEvent complete(
		String message,
		State state)
	{
		return new JumpStateEvent(Name.COMPLETE.name(), message, state);
	}

	/**
	 * Creates an JumpStateEvent for Failed ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the JumpStateEvent created for Failed ResourceEvent
	 * @since 4.0
	 */
	public static JumpStateEvent failed(
		String message,
		State state)
	{
		return new JumpStateEvent(Name.FAILED.name(), message, state);
	}
}
