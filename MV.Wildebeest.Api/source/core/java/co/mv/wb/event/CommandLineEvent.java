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

import java.util.Optional;

/**
 * Defines an event for WildebeestCommand
 *
 * @since 4.0
 */
public class CommandLineEvent extends Event
{
	/**
	 * Provides all events associated with Wildebeest Command.
	 *
	 * @since 4.0
	 */
	public enum Name
	{
		AboutStart, AboutFinish, StateCheckingFailed, MigrateFailed, JumpStateFailed, PluginsStart, PluginsFinish,
		LoadResourceFailed, LoadInstanceFailed, InvalidArgument;
	}

	/**
	 * Constructs a new CommandLineEvent with the supplied details.
	 *
	 * @param name    the name of the event
	 * @param message the message of the event
	 * @since 4.0
	 */
	public CommandLineEvent(
		String name,
		Optional<String> message)
	{
		super(name, message);
	}


	/**
	 * Creates an CommandLineEvent for AboutStart Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for AboutStart Event
	 * @since 4.0
	 */
	public static CommandLineEvent aboutStart(Optional<String> message)
	{
		return new CommandLineEvent(Name.AboutStart.name(), message);
	}

	/**
	 * Creates an CommandLineEvent for AboutFinish Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for AboutFinish Event
	 * @since 4.0
	 */
	public static CommandLineEvent aboutFinish(String message)
	{
		return new CommandLineEvent(Name.AboutFinish.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for StateCheckingFailed Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for StateCheckingFailed Event
	 * @since 4.0
	 */
	public static Event stateCheckingFailed(String message)
	{
		return new CommandLineEvent(Name.StateCheckingFailed.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for MigrateFailed Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for MigrateFailed Event
	 * @since 4.0
	 */
	public static Event migrateFailed(String message)
	{
		return new CommandLineEvent(Name.MigrateFailed.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for JumpStateFailed Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for JumpStateFailed Event
	 * @since 4.0
	 */
	public static Event jumpStateFailed(String message)
	{
		return new CommandLineEvent(Name.JumpStateFailed.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for PluginsStart Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for PluginsStart Event
	 * @since 4.0
	 */
	public static CommandLineEvent pluginsStart(Optional<String> message)
	{
		return new CommandLineEvent(Name.PluginsStart.name(), message);
	}

	/**
	 * Creates an CommandLineEvent for PluginsFinish Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for PluginsFinish Event
	 * @since 4.0
	 */
	public static CommandLineEvent pluginsFinish(String message)
	{
		return new CommandLineEvent(Name.PluginsFinish.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for LoadResourceFailed Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for LoadResourceFailed Event
	 * @since 4.0
	 */
	public static Event loadResourceFailed(String message)
	{
		return new CommandLineEvent(Name.LoadResourceFailed.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for LoadInstanceFailed Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for LoadInstanceFailed Event
	 * @since 4.0
	 */
	public static Event loadInstanceFailed(String message)
	{
		return new CommandLineEvent(Name.LoadInstanceFailed.name(), Optional.of(message));
	}

	/**
	 * Creates an CommandLineEvent for InvalidArgument Event.
	 *
	 * @param message the message of the event
	 * @return the CommandLineEvent created for InvalidArgument Event
	 * @since 4.0
	 */
	public static CommandLineEvent invalidArgument(String message)
	{
		return new CommandLineEvent(CommandLineEvent.Name.InvalidArgument.name(), Optional.of(message));
	}

}
