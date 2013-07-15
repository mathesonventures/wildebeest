package co.zd.wb.model;

import co.zd.wb.service.Logger;
import java.util.List;
import java.util.UUID;

public interface Resource
{
	
	//
	// Properties
	//
	
	/**
	 * Gets the ID of this Resource.
	 * 
	 * @return                                  the ID of this Resource.
	 */
	UUID getResourceId();
	
	/**
	 * Gets the Name of this Resource.
	 * 
	 * @return                                  the Name of this Resource.
	 */
	String getName();
	
	/**
	 * Gets the states that have been defined for this Resource.
	 * 
	 * @return                                  the states that have been defined for this Resource.
	 */
	List<State> getStates();
	
	/**
	 * Gets the migrations that have been defined for this Resource.
	 * 
	 * @return                                  the migrations that have been defined for this Resource.
	 */
	List<Migration> getMigrations();

	//
	// Behaviour
	//
	
	/**
	 * Identifies and returns the defined state that the supplied resource instance currently appears to be in, or is
	 * declared to be in.  Note that this does not guarantee that the resource is valid as compared to the state that it
	 * is identified to be in.  To verify that the resource is valid for the current state, use assertState().
	 * 
	 * If the resource does not exist at all, then this method returns null.
	 * 
	 * If the state of the resource cannot be determined then an IndeterminateStateException is thrown.  For example if
	 * the resource declares itself to be in state "A", but no such state has been defined for the resource, then this
	 * is considered to be an indeterminate state.
	 * 
	 * @param       instance                    the {@link Instance} to get the current state of
	 * @return                                  the defined state that the resource currently appears to be in, or is
	 *                                          declared to be in.
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly.
	 * @since                                   1.0.0
	 */
	State currentState(Instance instance) throws IndeterminateStateException;
	
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
	 * @since                                   1.0.0
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
	 * @param       instance                    the {@link Instance} to migration
	 * @param       targetStateId               the ID of the state to which the {@link Resource} should be migrated
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly at
	 *                                          the commencement of the migration, and at each intermediate state and
	 *                                          at the final state of the migration process.
	 * @exception   MigrationNotPossibleException
	 *                                          when the number of from the current state to the target state is not
	 *                                          exactly one.
	 * @since                                   1.0.0
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
	 * Finds and returns the state with the supplied ID.  If no such state exists, null is returned.
	 * @param stateId
	 * @return 
	 */
	State stateForId(UUID stateId);
	
	/**
	 * Looks for a state with the supplied label, and if one exists returns it's StateId.  If no such state exists, then
	 * null is returned.
	 */
	UUID stateIdForLabel(String label);
}