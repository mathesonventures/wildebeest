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

import java.util.ArrayList;
import java.util.List;

/**
 * A container for messages raised during some operation.
 *
 * @since 2.0
 */
public class Messages
{
	private final List<String> messages;

	/**
	 * Creates a new Messages container.
	 *
	 * @since 2.0
	 */
	public Messages()
	{
		this.messages = new ArrayList<>();
	}

	/**
	 * Gets the set of messages that have been raised.
	 *
	 * @return the set of messages that have been raised
	 * @since 2.0
	 */
	public List<String> getMessages()
	{
		return this.messages;
	}

	/**
	 * Adds a plain-text message to the collection.
	 *
	 * @param message the message to add to the collection.
	 * @since 2.0
	 */
	public void addMessage(String message)
	{
		if (message == null) throw new ArgumentNullException("message");
		if ("".equals(message.trim())) throw new IllegalArgumentException("message cannot be empty");
		this.messages.add(message);
	}

	/**
	 * Adds a message formatted with the supplied replacement values to the collection.
	 *
	 * @param format the format string for the message to add to the collection.
	 * @param args   the replacement values to be used in the message.
	 * @since 2.0
	 */
	public void addMessage(
		String format,
		Object... args)
	{
		if (format == null) throw new ArgumentNullException("format");
		if ("".equals(format.trim())) throw new IllegalArgumentException("format cannot be empty");

		String message = String.format(format, args);
		this.addMessage(message);
	}

	/**
	 * Gets the number of messages in this collection.
	 *
	 * @return the number of messages in this collection
	 * @since 2.0
	 */
	public int size()
	{
		return this.messages.size();
	}
}
