// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb.plugin.base;

import co.zd.wb.Assertion;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.AssertionResponse;
import co.zd.wb.AssertionResult;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Resource;
import co.zd.wb.Instance;
import co.zd.wb.State;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationFaultException;
import co.zd.wb.MigrationNotPossibleException;
import co.zd.wb.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Resource}
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public abstract class BaseResource implements Resource
{
	/**
	 * Creates a new BaseResource instance.
	 * 
	 * @param       resourceId                  the ID of the new resource
	 * @param       name                        the name of the new resource
	 * @since                                   1.0
	 */
	protected BaseResource(
		UUID resourceId,
		String name)
	{
		this.setResourceId(resourceId);
		this.setName(name);
		this.setStates(new ArrayList<State>());
		this.setMigrations(new ArrayList<Migration>());
	}

	//
	// Properties
	//
	
	// <editor-fold desc="ResourceId" defaultstate="collapsed">

	private UUID m_resourceId = null;
	private boolean m_resourceId_set = false;

	@Override public UUID getResourceId() {
		if(!m_resourceId_set) {
			throw new IllegalStateException("resourceId not set.  Use the HasResourceId() method to check its state before accessing it.");
		}
		return m_resourceId;
	}

	private void setResourceId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceId cannot be null");
		}
		boolean changing = !m_resourceId_set || m_resourceId != value;
		if(changing) {
			m_resourceId_set = true;
			m_resourceId = value;
		}
	}

	private void clearResourceId() {
		if(m_resourceId_set) {
			m_resourceId_set = true;
			m_resourceId = null;
		}
	}

	private boolean hasResourceId() {
		return m_resourceId_set;
	}

	// </editor-fold>

	// <editor-fold desc="Name" defaultstate="collapsed">

	private String m_name = null;
	private boolean m_name_set = false;

	@Override public String getName() {
		if(!m_name_set) {
			throw new IllegalStateException("name not set.  Use the HasName() method to check its state before accessing it.");
		}
		return m_name;
	}

	private void setName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		boolean changing = !m_name_set || m_name != value;
		if(changing) {
			m_name_set = true;
			m_name = value;
		}
	}

	private void clearName() {
		if(m_name_set) {
			m_name_set = true;
			m_name = null;
		}
	}

	private boolean hasName() {
		return m_name_set;
	}

	// </editor-fold>

	// <editor-fold desc="States" defaultstate="collapsed">

	private List<State> m_states = null;
	private boolean m_states_set = false;

	@Override public List<State> getStates() {
		if(!m_states_set) {
			throw new IllegalStateException("states not set.  Use the HasStates() method to check its state before accessing it.");
		}
		return m_states;
	}

	private void setStates(List<State> value) {
		if(value == null) {
			throw new IllegalArgumentException("states cannot be null");
		}
		boolean changing = !m_states_set || m_states != value;
		if(changing) {
			m_states_set = true;
			m_states = value;
		}
	}

	private void clearStates() {
		if(m_states_set) {
			m_states_set = true;
			m_states = null;
		}
	}

	private boolean hasStates() {
		return m_states_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Migrations" defaultstate="collapsed">

	private List<Migration> m_migrations = null;
	private boolean m_migrations_set = false;

	@Override public List<Migration> getMigrations() {
		if(!m_migrations_set) {
			throw new IllegalStateException("migrations not set.  Use the HasMigrations() method to check its state before accessing it.");
		}
		return m_migrations;
	}

	private void setMigrations(List<Migration> value) {
		if(value == null) {
			throw new IllegalArgumentException("migrations cannot be null");
		}
		boolean changing = !m_migrations_set || m_migrations != value;
		if(changing) {
			m_migrations_set = true;
			m_migrations = value;
		}
	}

	private void clearMigrations() {
		if(m_migrations_set) {
			m_migrations_set = true;
			m_migrations = null;
		}
	}

	public boolean hasMigrations() {
		return m_migrations_set;
	}

	// </editor-fold>

	@Override public List<AssertionResult> assertState(
		Logger logger,
		Instance instance) throws IndeterminateStateException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		State state = this.currentState(instance);
		
		List<AssertionResult> results = this.assertState(logger, instance, state);
		
		return results;
	}
	
	private List<AssertionResult> assertState(
		Logger logger,
		Instance instance,
		State state)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (state == null) { throw new IllegalArgumentException("state cannot be null"); }

		List<AssertionResult> results = new ArrayList<AssertionResult>();
		
		for(Assertion assertion : state.getAssertions())
		{
			AssertionResponse response = assertion.perform(instance);

			if (logger != null)
			{
				logger.assertionComplete(assertion, response);
			}

			results.add(new ImmutableAssertionResult(
				assertion.getAssertionId(),
				response.getResult(),
				response.getMessage()));
		}
		
		return results;
	}

	@Override public void migrate(
		Logger logger,
		Instance instance,
		UUID targetStateId) throws
			IndeterminateStateException,
			AssertionFailedException,
			MigrationNotPossibleException,
			MigrationFailedException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		
		State currentState = this.currentState(instance);
		UUID currentStateId = currentState == null ? null : currentState.getStateId();
		List<UUID> workList = new ArrayList<UUID>();
		
		List<List<Migration>> paths = new ArrayList<List<Migration>>();
		List<Migration> thisPath = new ArrayList<Migration>();
		
		findPaths(this, paths, thisPath, currentStateId, targetStateId);
		
		if (paths.size() != 1)
		{
			throw new RuntimeException("multiple possible paths found");
		}
		
		List<Migration> path = paths.get(0);
		
		for (Migration migration : path)
		{
			// Migrate to the next state
			logger.migrationStart(this, migration);
			migration.perform(instance);
			logger.migrationComplete(this, migration);

			// Basic state check
			State state = this.currentState(instance);
			UUID stateId = state == null ? null : state.getStateId();
			if (!migration.getToStateId().equals(stateId))
			{
				throw new MigrationFaultException(String.format(
					"state expected to be %s after migration but is %s",
					migration.getToStateId(),
					stateId));
			}
			
			// Assert the new state
			List<AssertionResult> assertionResults = this.assertState(
				logger,
				instance);

			throwIfFailed(migration.getToStateId(), assertionResults);
		}
	}
	
	@Override public void jumpstate(
		Logger logger,
		Instance instance,
		UUID targetStateId) throws AssertionFailedException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (targetStateId == null) { throw new IllegalArgumentException("targetStateId cannot be null"); }
		
		State targetState = this.stateForId(targetStateId);
		
		// Assert the new state
		List<AssertionResult> assertionResults = this.assertState(
			logger,
			instance,
			targetState);

		throwIfFailed(targetState.getStateId(), assertionResults);
		
		this.setStateId(logger, instance, targetStateId);
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
	
	private static void findPaths(
		BaseResource resource,
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
			for (Migration migration : resource.getMigrations())
			{
				if ((!migration.hasFromStateId() && fromStateId == null) ||
					(migration.hasFromStateId() && migration.getFromStateId().equals(fromStateId)))
				{
					State toState = resource.stateForId(migration.getToStateId());
					List<Migration> thisPathCopy = new ArrayList<Migration>(thisPath);
					thisPathCopy.add(migration);
					findPaths(resource, paths, thisPathCopy, toState.getStateId(), targetStateId);
				}
			}
		}
	}
	
	@Override public State stateForId(UUID stateId)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }
		
		State result = null;
		
		for(State check : this.getStates())
		{
			if (stateId.equals(check.getStateId()))
			{
				result = check;
				break;
			}
		}
		
		return result;
	}

	@Override public UUID stateIdForLabel(String label)
	{
		if (label == null) { throw new IllegalArgumentException("label cannot be null"); }
		if ("".equals(label)) { throw new IllegalArgumentException("label cannot be empty"); }
		
		State result = null;
		
		for (State check : this.getStates())
		{
			if (label.equals(check.getLabel()))
			{
				result = check;
			}
		}
		
		return result == null ? null : result.getStateId();
	}
}