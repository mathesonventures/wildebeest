package co.mv.stm.impl;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.AssertionResult;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.Resource;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.State;
import co.mv.stm.model.Transition;
import co.mv.stm.model.impl.ImmutableAssertionResult;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseResource implements Resource
{
	protected BaseResource()
	{
		List<State> states = new ArrayList<State>();
		this.setStates(states);
	}
	
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

	@Override public List<AssertionResult> assertState(
		ResourceInstance instance) throws IndeterminateStateException
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
}