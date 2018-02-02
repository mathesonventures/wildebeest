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

package co.mv.wb.impl;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.FileLoadException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;

import java.util.Optional;

public class OutputFormatter
{

	//
	// Loader
	//

	public static String fileLoad(
		FileLoadException e,
		String loadType)
	{
		return String.format(
			"Unable to load %s file \"%s\"",
			loadType,
			e.getFile().getAbsolutePath());
	}

	public static String loaderFault(
		LoaderFault e,
		String loadType)
	{
		return String.format("Unable to load %s", loadType);
	}

	public static String pluginBuild(PluginBuildException e)
	{
		StringBuilder result = new StringBuilder();

		for (String message : e.getMessages().getMessages())
		{
			result.append(message).append("\n");
		}

		return result.toString();
	}

	//
	// State
	//

	public static String invalidStateSpecified(InvalidStateSpecifiedException e)
	{
		return String.format(
			"The state \"%s\" that was specified is not a valid Wildebeest state identifier",
			e.getSpecifiedState());
	}

	public static String unknownStateSpecified(UnknownStateSpecifiedException e)
	{
		return String.format(
			"The state \"%s\" could not be found in this resource",
			e.getSpecifiedState());
	}

	public static String targetNotSpecified(TargetNotSpecifiedException e)
	{
		return String.format(
			"No target was specified and the resource does not have a default target set");
	}

	public static String indeterminateState(IndeterminateStateException e)
	{
		return "Indeterminate state: " + e.getMessage();
	}

	//
	// Migration
	//

	public static String migrationStart(
		Resource resource,
		Migration migration,
		Optional<State> fromState,
		Optional<State> toState)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (fromState == null) { throw new IllegalArgumentException("fromState cannot be null"); }
		if (toState == null) { throw new IllegalArgumentException("toState cannot be null"); }

		StringBuilder result = new StringBuilder();

		if (fromState.isPresent())
		{
			if (toState.isPresent())
			{
				result.append(String.format(
					"Migrating from state \"%s\" to \"%s\"",
					fromState.get().getDisplayName(),
					toState.get().getDisplayName()));
			}
			else
			{
				result.append(String.format(
					"Migrating from state \"%s\" to non-existent",
					fromState.get().getDisplayName()));
			}
		}
		else if (toState.isPresent())
		{
			result.append(String.format(
				"Migrating from non-existent to \"%s\"",
				toState.get().getDisplayName()));
		}
		else
		{
			// Exception?
		}

		return result.toString();
	}

	public static String migrationComplete(
		Resource resource,
		Migration migration)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }

		return "Migration complete";
	}

	public static String migrationNotPossible(MigrationNotPossibleException e)
	{
		return String.format("Migration not possible: %s", e.getMessage());
	}

	public static String migrationFailed(MigrationFailedException e)
	{
		return String.format("Migration failed: %s", e.getMessage());
	}

	//
	// Assertion
	//

	public static String assertionStart(Assertion assertion)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }

		return String.format("Starting assertion: %s", assertion.getDescription());
	}

	public static String assertionComplete(
		Assertion assertion,
		AssertionResponse response)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }
		if (response == null) { throw new IllegalArgumentException("response cannot be null"); }

		String format = response.getResult()
			? "Assertion \"%s\" passed: %s"
			: "Assertion \"%s\" failed: %s";

		return String.format(
			format,
			assertion.getDescription(),
			response.getMessage());
	}

	public static String assertionFailed(AssertionFailedException e)
	{
		return "Assertion failed: " + e.getMessage();
	}

	//
	// JumpState
	//

	public static String jumpStateFailed(JumpStateFailedException e)
	{
		return String.format("JumpState failed: %s", e.getMessage());
	}
}
