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

import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.AssertionResult;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.OutputFormatter;
import co.mv.wb.Resource;
import co.mv.wb.ResourceHelper;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.State;
import co.mv.wb.plugin.base.ImmutableAssertionResult;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides function for working with {@link Resource}'s
 */
public class ResourceHelperImpl implements ResourceHelper
{
	public List<AssertionResult> assertState(
		PrintStream output,
		Resource resource,
		ResourcePlugin resourcePlugin,
		Instance instance) throws IndeterminateStateException
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (resourcePlugin == null) { throw new IllegalArgumentException("resourcePlugin cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		State state = resourcePlugin.currentState(
			resource,
			instance);

		return this.assertState(output, instance, state);
	}

	public void migrate(
		PrintStream output,
		Resource resource,
		ResourcePlugin resourcePlugin,
		Instance instance,
		Map<Class, MigrationPlugin> migrationPlugins,
		UUID targetStateId) throws
			IndeterminateStateException,
			AssertionFailedException,
			MigrationNotPossibleException,
			MigrationFailedException
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (resourcePlugin == null) { throw new IllegalArgumentException("resourcePlugin cannot be null"); }
		if (migrationPlugins == null) { throw new IllegalArgumentException("migrationPlugins cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance"); }

		State currentState = resourcePlugin.currentState(
			resource,
			instance);
		UUID currentStateId = currentState == null ? null : currentState.getStateId();
		List<UUID> workList = new ArrayList<>();

		List<List<Migration>> paths = new ArrayList<>();
		List<Migration> thisPath = new ArrayList<>();

		this.findPaths(resource, paths, thisPath, currentStateId, targetStateId);

		if (paths.size() != 1)
		{
			throw new RuntimeException("multiple possible paths found");
		}

		List<Migration> path = paths.get(0);

		for (Migration migration : path)
		{
			MigrationPlugin migrationPlugin = migrationPlugins.get(migration.getClass());
			if (migrationPlugin == null)
			{
				throw new RuntimeException(String.format(
					"No MigrationPlugin found for migration of type %s",
					migration.getClass().getName()));
			}

			Optional<State> fromState = migration.getFromStateId().map(stateId -> this.stateForId(
				resource,
				stateId));
			Optional<State> toState = migration.getToStateId().map(stateId -> this.stateForId(
				resource,
				stateId));

			// Migrate to the next state
			output.println(OutputFormatter.migrationStart(
				resource,
				migration,
				fromState,
				toState));

			migrationPlugin.perform(
				output,
				migration,
				instance);

			output.println(OutputFormatter.migrationComplete(
				resource,
				migration));

			// Update the state
			resourcePlugin.setStateId(
				output,
				resource,
				instance,
				migration.getToStateId().get());

			// Assert the new state
			List<AssertionResult> assertionResults = this.assertState(
				output,
				resource,
				resourcePlugin,
				instance);

			ResourceHelperImpl.throwIfFailed(migration.getToStateId().get(), assertionResults);
		}
	}

	public void jumpstate(
		PrintStream output,
		Resource resource,
		ResourcePlugin resourcePlugin,
		Instance instance,
		UUID targetStateId) throws
			AssertionFailedException,
			JumpStateFailedException
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (resourcePlugin == null) { throw new IllegalArgumentException("resourcePlugin cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (targetStateId == null) { throw new IllegalArgumentException("targetStateId cannot be null"); }

		State targetState = this.stateForId(
			resource,
			targetStateId);

		if (targetState == null)
		{
			throw new JumpStateFailedException("This resource does not have a state with ID " +
				targetStateId.toString());
		}

		// Assert the new state
		List<AssertionResult> assertionResults = this.assertState(
			output,
			instance,
			targetState);

		ResourceHelperImpl.throwIfFailed(targetState.getStateId(), assertionResults);

		resourcePlugin.setStateId(
			output,
			resource,
			instance,
			targetStateId);
	}

	public State stateForId(
		Resource resource,
		UUID stateId)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }

		State result = null;

		for(State check : resource.getStates())
		{
			if (stateId.equals(check.getStateId()))
			{
				result = check;
				break;
			}
		}

		return result;
	}

	public UUID stateIdForLabel(
		Resource resource,
		String label)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (label == null) { throw new IllegalArgumentException("label cannot be null"); }
		if ("".equals(label)) { throw new IllegalArgumentException("label cannot be empty"); }

		State result = null;

		for (State check : resource.getStates())
		{
			if (check.getLabel().map(label::equals).orElse(false))
			{
				result = check;
			}
		}

		return result == null ? null : result.getStateId();
	}


	private List<AssertionResult> assertState(
		PrintStream output,
		Instance instance,
		State state)
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (state == null) { throw new IllegalArgumentException("state cannot be null"); }

		List<AssertionResult> results = new ArrayList<>();

		state.getAssertions().forEach(
			assertion ->
			{
				AssertionResponse response = assertion.perform(instance);

				output.println(OutputFormatter.assertionComplete(
					assertion,
					response));

				results.add(new ImmutableAssertionResult(
					assertion.getAssertionId(),
					response.getResult(),
					response.getMessage()));
			});

		return results;
	}

	private void findPaths(
		Resource resource,
		List<List<Migration>> paths,
		List<Migration> thisPath,
		UUID fromStateId,
		UUID targetStateId)
	{
		if (resource == null) { throw new IllegalArgumentException("resource"); }
		if (paths == null) { throw new IllegalArgumentException("paths"); }
		if (thisPath == null) { throw new IllegalArgumentException("thisPath"); }

		// Have we reached the target state?
		if ((fromStateId == null && targetStateId == null) ||
			(fromStateId != null && fromStateId.equals(targetStateId)))
		{
			paths.add(thisPath);
		}

		// If we have not reached the target state, keep traversing the graph
		else
		{
			resource.getMigrations()
				.stream()
				.filter(m ->
					(!m.getFromStateId().isPresent() && fromStateId == null) ||
						(m.getFromStateId().isPresent() && m.getFromStateId().equals(fromStateId)))
				.forEach(
					migration ->
					{
						State toState = this.stateForId(
							resource,
							migration.getToStateId().get());
						List<Migration> thisPathCopy = new ArrayList<>(thisPath);
						thisPathCopy.add(migration);
						findPaths(resource, paths, thisPathCopy, toState.getStateId(), targetStateId);
					});
		}
	}

	private static void throwIfFailed(
		UUID stateId,
		List<AssertionResult> assertionResults) throws AssertionFailedException
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }
		if (assertionResults == null) { throw new IllegalArgumentException("assertionResults cannot be null"); }

		// If any assertions failed, throw
		for(AssertionResult assertionResult : assertionResults)
		{
			if (!assertionResult.getResult())
			{
				throw new AssertionFailedException(stateId, assertionResults);
			}
		}
	}
}
