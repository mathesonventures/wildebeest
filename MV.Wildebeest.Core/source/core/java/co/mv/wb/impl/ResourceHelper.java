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
import co.mv.wb.AssertionResult;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.Logger;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides function for working with {@link Resource}'s
 */
public class ResourceHelper
{
	/**
	 * Applies the {@link Assertion}'s for this {@link Resource} and returns a collection of the results of those
	 * Assertions.
	 *
	 * {@code assertState()} will first use {@link ResourcePlugin#currentState(Resource, Instance)} to determine the
	 * current state of the Resource.  If the current state cannot be determined, then an
	 * {@link IndeterminateStateException} is thrown.  See the {@code currentState()} method for more details.
	 *
	 * @param       logger                      an optional Logger service to log the activity of the assert operation.
	 * @param       instance                    the {@link Instance} to assert the current state of
	 * @return                                  a {@link java.util.List} of the {@link AssertionResult}s
	 *                                          that were generated for the Assertions for this Resource's current
	 *                                          state.
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly.
	 * @since                                   1.0
	 */
	public static List<AssertionResult> assertState(
		Logger logger,
		Resource resource,
		ResourcePlugin resourcePlugin,
		Instance instance) throws IndeterminateStateException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (resourcePlugin == null) { throw new IllegalArgumentException("resourcePlugin cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		State state = resourcePlugin.currentState(
			resource,
			instance);

		List<AssertionResult> results = ResourceHelper.assertState(logger, instance, state);

		return results;
	}

	private static List<AssertionResult> assertState(
		Logger logger,
		Instance instance,
		State state)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (state == null) { throw new IllegalArgumentException("state cannot be null"); }

		List<AssertionResult> results = new ArrayList<>();

		state.getAssertions().forEach(
			assertion ->
			{
				AssertionResponse response = assertion.perform(instance);

				logger.assertionComplete(assertion, response);

				results.add(new ImmutableAssertionResult(
					assertion.getAssertionId(),
					response.getResult(),
					response.getMessage()));
			});

		return results;
	}

	/**
	 * Migrates the resource from it's current state to the specified target state.  The migration process is as
	 * follows:
	 *
	 * <ul>
	 * <li>Determine the resource's current state using {@link ResourcePlugin#currentState(Resource, Instance)}.  If the
	 * current state cannot be determined then an {@link IndeterminateStateException} is thrown, as described in the
	 * documentation for {@code currentState()}.
	 * </li>
	 * <li>Verify that the resource is valid for the current state using {@link ResourcePlugin#assertState()}.</li>
	 * <li>Determine the series of states that the Resource will go through to arrive at the target state, including the
	 * target state itself.
	 * <li>For every state in the series of intermediate states (including the target state):
	 * <ul>
	 * <li>Apply the migration to take the resource from the current state to the next state in the series</li>
	 * <li>Verify that the resource is valid for the new state by applying {@link ResourcePlugin#assertState()}</li>
	 * <li>If at any state the resource is found not to be valid, then the migration is aborted with an
	 * AssertionFailedException which conveys the state that failed validation, along with the full set of
	 * AssertionResults that were generated.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 * If exactly one path cannot be found that will enable migration from the current state to the target state, then
	 * a MigrationNotPossibleException is thrown.
	 *
	 * @param       logger                      an optional Logger service to log the activity of the assert operation.
	 * @param       instance                    the {@link Instance} to migrate
	 * @param       migrationPlugins            the set of available MigrationPlugins that can be used to run
	 *                                          Migrations.
	 * @param       targetStateId               the ID of the state to which the {@link Resource} should be migrated
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly at
	 *                                          the commencement of the migration, and at each intermediate state and
	 *                                          at the final state of the migration process.
	 * @exception AssertionFailedException    when one or more post-migration assertions fail
	 * @exception MigrationNotPossibleException
	 *                                          when the number of paths from the current state to the target state is
	 *                                          not exactly one.
	 * @exception MigrationFailedException    when the migration failed for some other reason
	 * @since                                   1.0
	 */
	public static void migrate(
		Logger logger,
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
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
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

		findPaths(resource, paths, thisPath, currentStateId, targetStateId);

		if (paths.size() != 1)
		{
			throw new RuntimeException("multiple possible paths found");
		}

		List<Migration> path = paths.get(0);

		for (Migration migration : path)
		{
			MigrationPlugin migrationPlugin = migrationPlugins.get(migration.getClass());

			Optional<State> fromState = migration.getFromStateId().map(stateId -> ResourceHelper.stateForId(
				resource,
				stateId));
			Optional<State> toState = migration.getToStateId().map(stateId -> ResourceHelper.stateForId(
				resource,
				stateId));

			// Migrate to the next state
			logger.migrationStart(
				resource,
				migration,
				fromState,
				toState);

			migrationPlugin.perform(
				logger,
				migration,
				instance);

			logger.migrationComplete(resource, migration);

			// Update the state
			resourcePlugin.setStateId(
				logger,
				resource,
				instance,
				migration.getToStateId().get());

			// Assert the new state
			List<AssertionResult> assertionResults = ResourceHelper.assertState(
				logger,
				resource,
				resourcePlugin,
				instance);

			throwIfFailed(migration.getToStateId().get(), assertionResults);
		}
	}

	/**
	 * Jumps the tracked state on the resource to the specified state without performing a migration.
	 *
	 * @param       logger                      an optional Logger service to log the actiivty of the jumpstate operation
	 * @param       instance                    the {@link Instance} to jump to a new state
	 * @param       targetStateId               the ID of the state to jump the instance to
	 * @throws                                  AssertionFailedException
	 * @throws JumpStateFailedException
	 * @since                                   3.0
	 */
	public static void jumpstate(
		Logger logger,
		Resource resource,
		ResourcePlugin resourcePlugin,
		Instance instance,
		UUID targetStateId) throws
			AssertionFailedException,
			JumpStateFailedException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (resourcePlugin == null) { throw new IllegalArgumentException("resourcePlugin cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (targetStateId == null) { throw new IllegalArgumentException("targetStateId cannot be null"); }

		State targetState = ResourceHelper.stateForId(
			resource,
			targetStateId);

		if (targetState == null)
		{
			throw new JumpStateFailedException("This resource does not have a state with ID " +
				targetStateId.toString());
		}

		// Assert the new state
		List<AssertionResult> assertionResults = ResourceHelper.assertState(
			logger,
			instance,
			targetState);

		throwIfFailed(targetState.getStateId(), assertionResults);

		resourcePlugin.setStateId(
			logger,
			resource,
			instance,
			targetStateId);
	}

	/**
	 * Finds and returns the state with the supplied ID.  If no such state exists, null is returned.
	 *
	 * @param       stateId                     the ID of the state to find
	 * @return                                  the State identified by the supplied ID if it could be found, or null
	 *                                          otherwise
	 * @since                                   1.0
	 */
	public static State stateForId(
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

	/**
	 * Looks for a state with the supplied label, and if one exists returns it's StateId.  If no such state exists, then
	 * null is returned.
	 *
	 * @param       label                       the label to search for
	 * @return                                  the ID of the state that has the specified label
	 * @since                                   1.0
	 */
	public static UUID stateIdForLabel(
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
	private static void findPaths(
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
						State toState = ResourceHelper.stateForId(
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
