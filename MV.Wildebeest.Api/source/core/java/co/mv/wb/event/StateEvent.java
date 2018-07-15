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
 * Defines an event for State
 *
 * @since 4.0
 */
public class StateEvent extends Event
{
	/**
	 * Provides all events associated with State.
	 *
	 * @since 4.0
	 */
	public enum Name
	{
		PreAssert, PostAssert, AssertionStart, AssertionComplete, ChangeSuccess, ChangeFailed;
	}

	/**
	 * Constructs a new StateEvent with the supplied details.
	 *
	 * @param name    the name of the event
	 * @param message the message of the event
	 * @since 4.0
	 */
	public StateEvent(
		String name,
		String message)
	{
		super(name, message);
	}

	/**
	 * Creates an StateEvent for PreAssert Event.
	 *
	 * @param message the message of the event
	 * @return the StateEvent created for PreAssert Event
	 * @since 4.0
	 */
	public static StateEvent preAssert(String message)
	{
		return new StateEvent(StateEvent.Name.PreAssert.name(), message);
	}

	/**
	 * Creates an StateEvent for PostAssert Event.
	 *
	 * @param message the message of the event
	 * @return the StateEvent created for PreAssert Event
	 * @since 4.0
	 */
	public static StateEvent postAssert(String message)
	{
		return new StateEvent(StateEvent.Name.PostAssert.name(), message);
	}

	/**
	 * Creates an StateEvent for AssertionStart Event.
	 *
	 * @return the StateEvent created for AssertionStart Event
	 * @since 4.0
	 */
	public static Event assertionStart(String message)
	{
		return new StateEvent(Name.AssertionStart.name(), message);
	}

	/**
	 * Creates an StateEvent for AssertionComplete Event.
	 *
	 * @return the StateEvent created for AssertionComplete Event
	 * @since 4.0
	 */
	public static Event assertionComplete(String message)
	{
		return new StateEvent(Name.AssertionComplete.name(), message);
	}

	/**
	 * Creates an StateEvent for ChangeSuccess Event.
	 *
	 * @return the StateEvent created for ChangeSuccess Event
	 * @since 4.0
	 */
	public static Event changeSuccess(String message)
	{
		return new StateEvent(Name.ChangeSuccess.name(), message);
	}

	/**
	 * Creates an StateEvent for ChangeFailed Event.
	 *
	 * @return the StateEvent created for ChangeFailed Event
	 * @since 4.0
	 */
	public static Event changeFailed(String message)
	{
		return new StateEvent(Name.ChangeFailed.name(), message);
	}
}
