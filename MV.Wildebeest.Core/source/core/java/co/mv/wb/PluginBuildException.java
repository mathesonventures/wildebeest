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

/**
 * Indicates that an attempt to build a plugin failed.
 *
 * @since 4.0
 */
public class PluginBuildException extends Exception
{
	private final Messages messages;

	public PluginBuildException(
		Messages messages)
	{
		super(PluginBuildException.buildMessage(messages));

		if (messages == null) throw new ArgumentNullException("messages");

		this.messages = messages;
	}

	private static String buildMessage(Messages messages)
	{
		if (messages == null) throw new ArgumentNullException("messages");

		StringBuilder m = new StringBuilder();

		for (String message : messages.getMessages())
		{
			m.append("- ").append(message).append("\n");
		}

		return m.toString();
	}

	public Messages getMessages()
	{
		return messages;
	}
}
