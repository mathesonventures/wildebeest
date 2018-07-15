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
 * Defines an event for Assertion
 *
 * @since 4.0
 */
public class AssertionEvent extends Event
{
	/**
	 * Provides all events associated with Assertion.
	 *
	 * @since 4.0
	 */
	public enum Name
	{
		Start, Complete, Failed;
	}

	/**
	 * Constructs a new AssertionEvent with the supplied details.
	 *
	 * @param name    the name of the event
	 * @param message the message of the event
	 * @since 4.0
	 */
	public AssertionEvent(
		String name,
		String message)
	{
		super(name, message);
	}

	/**
	 * Creates an AssertionEvent for Start Event.
	 *
	 * @param message the message of the event
	 * @return the AssertionEvent created for Start Event
	 * @since 4.0
	 */
	public static AssertionEvent start(String message)
	{
		return new AssertionEvent(Name.Start.name(), message);
	}

	/**
	 * Creates an AssertionEvent for Complete Event.
	 *
	 * @param message the message of the event
	 * @return the AssertionEvent created for Complete Event
	 * @since 4.0
	 */
	public static AssertionEvent complete(String message)
	{
		return new AssertionEvent(Name.Complete.name(), message);
	}

	/**
	 * Creates an AssertionEvent for Failed Event.
	 *
	 * @param message the message of the event
	 * @return the AssertionEvent created for Failed Event
	 * @since 4.0
	 */
	public static AssertionEvent failed(String message)
	{
		return new AssertionEvent(Name.Failed.name(), message);
	}
}
