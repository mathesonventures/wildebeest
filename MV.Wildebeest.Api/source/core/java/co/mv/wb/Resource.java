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

import java.util.List;
import java.util.UUID;

/**
 * The definition of a stateful resource to be managed by Wildebeest.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface Resource
{
	
	//
	// Properties
	//
	
	/**
	 * Gets the ID of this Resource.
	 * 
	 * @return                                  the unique ID of this Resource
	 * @since                                   1.0
	 */
	UUID getResourceId();
	
	/**
	 * Gets the name of this Resource.
	 * 
	 * @return                                  the name of this Resource
	 * @since                                   1.0
	 */
	String getName();
	
	/**
	 * Gets the states that have been defined for this Resource.
	 * 
	 * @return                                  the set of states defined for this Resource
	 * @since                                   1.0
	 */
	List<State> getStates();
	
	/**
	 * Gets the migrations that have been defined for this Resource.
	 * 
	 * @return                                  the migrations defined for this Resource
	 * @since                                   1.0
	 */
	List<Migration> getMigrations();

	/**
	 * Gets the plugin for this resource.
	 * 
	 * @return                                  the ResourcePlugin for working with this Resource
	 * @since                                   4.0
	 */
	ResourcePlugin getPlugin();
	
	//
	// Behaviour
	//
	
	/**
	 * Applies the {@link Assertion}'s for this {@link Resource} and returns a collection of the results of those
	 * Assertions.
	 * 
	 * {@code assertState()} will first use {@link #currentState()} to determine the current state of the Resource.  If
	 * the current state cannot be determined, then an {@link IndeterminateException} is thrown.  See the
	 * {@code currentState()} method for more details.
	 * 
	 * @param       logger                      an optional Logger service to log the activity of the assert operation.
	 * @param       instance                    the {@link Instance} to assert the current state of
	 * @return                                  a {@link java.util.List} of the {@link AssertionResult}s
	 *                                          that were generated for the Assertions for this Resource's current
	 *                                          state.
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly.
	 * @since                                   1.0
	 */
	List<AssertionResult> assertState(
		Logger logger,
		Instance instance) throws IndeterminateStateException;
	
	/**
	 * Migrates the resource from it's current state to the specified target state.  The migration process is as
	 * follows:
	 * 
	 * <ul>
	 * <li>Determine the resource's current state using {@link #currentState()}.  If the current state cannot be
	 * determined then an {@link IndeterminateStateException} is thrown, as described in the documentation for
	 * {@code currentState()}.
	 * </li>
	 * <li>Verify that the resource is valid for the current state using {@link #assertState()}.</li>
	 * <li>Determine the series of states that the Resource will go through to arrive at the target state, including the
	 * target state itself.
	 * <li>For every state in the series of intermediate states (including the target state):
	 * <ul>
	 * <li>Apply the migration to take the resource from the current state to the next state in the series</li>
	 * <li>Verify that the resource is valid for the new state by applying {@link #assertState()}</li>
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
	 * @param       targetStateId               the ID of the state to which the {@link Resource} should be migrated
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly at
	 *                                          the commencement of the migration, and at each intermediate state and
	 *                                          at the final state of the migration process.
	 * @exception   AssertionFailedException    when one or more post-migration assertions fail
	 * @exception   MigrationNotPossibleException
	 *                                          when the number of paths from the current state to the target state is
	 *                                          not exactly one.
	 * @exception   MigrationFailedException    when the migration failed for some other reason
	 * @since                                   1.0
	 */
	void migrate(
		Logger logger,
		Instance instance,
		UUID targetStateId) throws
			IndeterminateStateException,
			AssertionFailedException,
			MigrationNotPossibleException,
			MigrationFailedException;
	
	/**
	 * Jumps the tracked state on the resource to the specified state without performing a migration.
	 * 
	 * @param       logger                      an optional Logger service to log the actiivty of the jumpstate operation
	 * @param       instance                    the {@link Instance} to jump to a new state
	 * @param       targetStateId               the ID of the state to jump the instance to
	 * @throws                                  AssertionFailedException
	 * @throws                                  JumpStateFailedException 
	 * @since                                   3.0
	 */
	void jumpstate(
		Logger logger,
		Instance instance,
		UUID targetStateId) throws
			AssertionFailedException,
			JumpStateFailedException;
	
	/**
	 * Finds and returns the state with the supplied ID.  If no such state exists, null is returned.
	 * 
	 * @param       stateId                     the ID of the state to find
	 * @return                                  the State identified by the supplied ID if it could be found, or null
	 *                                          otherwise
	 * @since                                   1.0
	 */
	State stateForId(UUID stateId);
	
	/**
	 * Looks for a state with the supplied label, and if one exists returns it's StateId.  If no such state exists, then
	 * null is returned.
	 * 
	 * @param       label                       the label to search for
	 * @return                                  the ID of the state that has the specified label
	 * @since                                   1.0
	 */
	UUID stateIdForLabel(String label);
}
