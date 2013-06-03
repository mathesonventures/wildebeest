package co.mv.stm.impl;

import co.mv.stm.model.Resource;
import co.mv.stm.model.State;
import co.mv.stm.model.Transition;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseResource implements Resource
{
	public BaseResource()
	{
		this.setStates(new ArrayList<State>());
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
}