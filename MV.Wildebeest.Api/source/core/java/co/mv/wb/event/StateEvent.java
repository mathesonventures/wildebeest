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

/**
 * Defines an event for State
 *
 * @since 4.0
 */
public class StateEvent extends ResourceEvent<State>
{
	/**
	 * Provides all events associated with State.
	 *
	 * @since 4.0
	 */
	public enum Name
	{
		PRE_ASSERT,
		POST_ASSERT,
		ASSERTION_START,
		ASSERTION_COMPLETE,
		CHANGE_SUCCESS,
		CHANGE_FAILED
	}

	/**
	 * Constructs a new StateEvent with the supplied details.
	 *
	 * @param name    the name of the event
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @since 4.0
	 */
	public StateEvent(
		String name,
		String message,
		State state)
	{
		super(name, message, state);
	}

	/**
	 * Creates an StateEvent for PreAssert ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the StateEvent created for PreAssert ResourceEvent
	 * @since 4.0
	 */
	public static StateEvent preAssert(
		String message,
		State state)
	{
		return new StateEvent(Name.PRE_ASSERT.name(), message, state);
	}

	/**
	 * Creates an StateEvent for PostAssert ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the StateEvent created for PreAssert ResourceEvent
	 * @since 4.0
	 */
	public static StateEvent postAssert(
		String message,
		State state)
	{
		return new StateEvent(Name.POST_ASSERT.name(), message, state);
	}

	/**
	 * Creates an StateEvent for AssertionStart ResourceEvent.
	 *
	 * @param message the message of the event
	 * @return the StateEvent created for AssertionStart ResourceEvent
	 * @since 4.0
	 */
	public static StateEvent assertionStart(String message)
	{
		return new StateEvent(Name.ASSERTION_START.name(), message, null);
	}

	/**
	 * Creates an StateEvent for AssertionComplete ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the StateEvent created for AssertionComplete ResourceEvent
	 * @since 4.0
	 */
	public static StateEvent assertionComplete(
		String message,
		State state)
	{
		return new StateEvent(Name.ASSERTION_COMPLETE.name(), message, state);
	}

	/**
	 * Creates an StateEvent for ChangeSuccess ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the StateEvent created for ChangeSuccess ResourceEvent
	 * @since 4.0
	 */
	public static StateEvent changeSuccess(
		String message,
		State state)
	{
		return new StateEvent(Name.CHANGE_SUCCESS.name(), message, state);
	}

	/**
	 * Creates an StateEvent for ChangeFailed ResourceEvent.
	 *
	 * @param message the message of the event
	 * @param state   the source {@link State} of the event
	 * @return the StateEvent created for ChangeFailed ResourceEvent
	 * @since 4.0
	 */
	public static StateEvent changeFailed(
		String message,
		State state)
	{
		return new StateEvent(Name.CHANGE_FAILED.name(), message, state);
	}
}
