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

import java.util.Optional;
import java.util.UUID;

/**
 * Global definitions and functions for Wildebeest.
 *
 * @since 4.0
 */
public class Wildebeest
{
	/**
	 * Attempts to find the {@link State} matching the supplied stateRef reference in the supplied {@link Resource}.
	 *
	 * @param resource the Resource in which to search for the State
	 * @param stateRef the reference to the State to search for.  May be the ID or the name of the State.
	 * @return the State that matches the supplied stateRef reference.
	 */
	public static State findState(
		Resource resource,
		String stateRef) throws InvalidReferenceException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (stateRef == null) throw new ArgumentNullException("stateRef");

		Optional<State> result = resource
			.getStates().stream()
			.filter(s -> s.matchesStateRef(stateRef))
			.findFirst();

		if (!result.isPresent())
		{
			throw InvalidReferenceException.oneReference(
				EntityType.State,
				stateRef);
		}

		return result.get();
	}

	public static String stateDisplayName(
		UUID stateId,
		String name)
	{
		String result;

		if (stateId == null)
		{
			result = "(non-existent)";
		}
		else
		{
			if (name == null)
			{
				result = stateId.toString();
			}
			else
			{
				result = String.format("%s:%s", stateId, name);
			}
		}

		return result;
	}

	public static String getPluginHandlerUri(ResourcePlugin plugin)
	{
		if (plugin == null) throw new ArgumentNullException("plugin");

		return Wildebeest.getPluginHandlerUriInner(plugin);
	}

	public static String getPluginHandlerUri(AssertionPlugin plugin)
	{
		if (plugin == null) throw new ArgumentNullException("plugin");

		return Wildebeest.getPluginHandlerUriInner(plugin);
	}

	public static String getPluginHandlerUri(MigrationPlugin plugin)
	{
		if (plugin == null) throw new ArgumentNullException("plugin");

		return Wildebeest.getPluginHandlerUriInner(plugin);
	}

	private static String getPluginHandlerUriInner(Object plugin)
	{
		if (plugin == null) throw new ArgumentNullException("plugin");

		PluginHandler info = plugin.getClass().getAnnotation(PluginHandler.class);

		if (info == null)
		{
			throw new RuntimeException(String.format(
				"plugin class %s is not annotated with PluginHandler",
				plugin.getClass().getName()));
		}

		return info.uri();
	}
}
