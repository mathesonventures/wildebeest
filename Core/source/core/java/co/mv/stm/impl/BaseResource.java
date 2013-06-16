package co.mv.stm.impl;

import co.mv.stm.Assertion;
import co.mv.stm.AssertionFailedException;
import co.mv.stm.AssertionResponse;
import co.mv.stm.AssertionResult;
import co.mv.stm.IndeterminateStateException;
import co.mv.stm.Resource;
import co.mv.stm.Instance;
import co.mv.stm.State;
import co.mv.stm.Transition;
import co.mv.stm.TransitionFailedException;
import co.mv.stm.TransitionFaultException;
import co.mv.stm.TransitionNotPossibleException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseResource implements Resource
{
	protected BaseResource(
		UUID resourceId,
		String name)
	{
		this.setResourceId(resourceId);
		this.setName(name);
		this.setStates(new ArrayList<State>());
		this.setTransitions(new ArrayList<Transition>());
	}

	//
	// Properties
	//
	
	// <editor-fold desc="ResourceId" defaultstate="collapsed">

	private UUID m_resourceId = null;
	private boolean m_resourceId_set = false;

	public UUID getResourceId() {
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

	public String getName() {
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
	
	// <editor-fold desc="Transitions" defaultstate="collapsed">

	private List<Transition> m_transitions = null;
	private boolean m_transitions_set = false;

	@Override public List<Transition> getTransitions() {
		if(!m_transitions_set) {
			throw new IllegalStateException("transitions not set.  Use the HasTransitions() method to check its state before accessing it.");
		}
		return m_transitions;
	}

	private void setTransitions(List<Transition> value) {
		if(value == null) {
			throw new IllegalArgumentException("transitions cannot be null");
		}
		boolean changing = !m_transitions_set || m_transitions != value;
		if(changing) {
			m_transitions_set = true;
			m_transitions = value;
		}
	}

	private void clearTransitions() {
		if(m_transitions_set) {
			m_transitions_set = true;
			m_transitions = null;
		}
	}

	public boolean hasTransitions() {
		return m_transitions_set;
	}

	// </editor-fold>

	@Override public List<AssertionResult> assertState(Instance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }

		List<AssertionResult> results = new ArrayList<AssertionResult>();
		
		State state = this.currentState(instance);
		
		if (state != null)
		{
			for(Assertion assertion : state.getAssertions())
			{
				AssertionResponse response = assertion.apply(instance);
				
				results.add(new ImmutableAssertionResult(
					assertion.getAssertionId(),
					response.getResult(),
					response.getMessage()));
			}
		}
		
		return results;
	}

	@Override public void transition(
		Instance instance,
		UUID targetStateId) throws
			IndeterminateStateException,
			AssertionFailedException,
			TransitionNotPossibleException,
			TransitionFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		
		State currentState = this.currentState(instance);
		UUID currentStateId = currentState == null ? null : currentState.getStateId();
		List<UUID> workList = new ArrayList<UUID>();
		
		List<List<Transition>> paths = new ArrayList<List<Transition>>();
		List<Transition> thisPath = new ArrayList<Transition>();
		
		findPaths(this, paths, thisPath, currentStateId, targetStateId);
		
		if (paths.size() != 1)
		{
			throw new RuntimeException("multiple possible paths found");
		}
		
		List<Transition> path = paths.get(0);
		
		for (Transition transition : path)
		{
			// Transition to the next state
			transition.perform(instance);

			// Basic state check
			State state = this.currentState(instance);
			UUID stateId = state == null ? null : state.getStateId();
			if (!transition.getToStateId().equals(stateId))
			{
				throw new TransitionFaultException(String.format(
					"state expected to be %s after transition but is %s",
					transition.getToStateId(),
					stateId));
			}
			
			// Assert the new state
			List<AssertionResult> assertionResults = this.assertState(instance);

			// If any assertions failed, throw
			for(AssertionResult assertionResult : assertionResults)
			{
				if (!assertionResult.getResult())
				{
					throw new AssertionFailedException(state.getStateId(), assertionResults);
				}
			}
		}
	}
	
	protected State stateForId(UUID stateId)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }
		
		State result = null;
		
		for (State check : this.getStates())
		{
			if (stateId.equals(check.getStateId()))
			{
				result = check;
				break;
			}
		}
		
		return result;
	}
	
	private static void findPaths(
		BaseResource resource,
		List<List<Transition>> paths,
		List<Transition> thisPath,
		UUID fromStateId,
		UUID targetStateId)
	{
		if (resource == null) { throw new IllegalArgumentException("resource"); }
		
		// Have we reached the target state?
		if ((fromStateId == null && targetStateId == null) ||
			(fromStateId != null && fromStateId.equals(targetStateId)))
		{
			paths.add(thisPath);
		}
		
		// If we have not reached the target state, keep traversing the graph
		else
		{
			for (Transition transition : resource.getTransitions())
			{
				if ((!transition.hasFromStateId() && fromStateId == null) ||
					(transition.hasFromStateId() && transition.getFromStateId().equals(fromStateId)))
				{
					State toState = resource.stateForId(transition.getToStateId());
					List<Transition> thisPathCopy = new ArrayList<Transition>(thisPath);
					thisPathCopy.add(transition);
					findPaths(resource, paths, thisPathCopy, toState.getStateId(), targetStateId);
				}
			}
		}
	}
}