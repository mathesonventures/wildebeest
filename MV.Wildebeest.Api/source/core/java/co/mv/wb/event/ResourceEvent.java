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
 * Defines an ResourceEvent
 *
 * @since 4.0
 */
public class ResourceEvent<T> extends Event
{
	private final T elementResource;

	/**
	 * Constructs a new ResourceEvent with the supplied details.
	 *
	 * @param name            the name of the event, this should be supplied from ResourceEvent
	 * @param message         a message of the event
	 * @param elementResource the element resource considered during the event
	 * @since 4.0
	 */
	public ResourceEvent(
		String name,
		String message,
		T elementResource)
	{
		super(name, message);

		this.elementResource = elementResource;
	}

	public T getElementResource()
	{
		return elementResource;
	}
}
