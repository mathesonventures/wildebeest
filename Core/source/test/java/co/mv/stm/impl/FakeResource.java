package co.mv.stm.impl;

import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.ResourceType;
import co.mv.stm.model.State;
import co.mv.stm.model.TransitionNotPossibleException;
import java.util.UUID;

public class FakeResource extends BaseResource
{
	public FakeResource(
		UUID resourceId,
		String name,
		State currentState)
	{
		super(resourceId, name, ResourceType.Database);
		
		this.setCurrentState(currentState);
	}

	// <editor-fold desc="CurrentState" defaultstate="collapsed">

	private State m_currentState = null;
	private boolean m_currentState_set = false;

	private State getCurrentState() {
		if(!m_currentState_set) {
			throw new IllegalStateException("currentState not set.  Use the HasCurrentState() method to check its state before accessing it.");
		}
		return m_currentState;
	}

	private void setCurrentState(
		State value) {
		if(value == null) {
			throw new IllegalArgumentException("currentState cannot be null");
		}
		boolean changing = !m_currentState_set || m_currentState != value;
		if(changing) {
			m_currentState_set = true;
			m_currentState = value;
		}
	}

	private void clearCurrentState() {
		if(m_currentState_set) {
			m_currentState_set = true;
			m_currentState = null;
		}
	}

	private boolean hasCurrentState() {
		return m_currentState_set;
	}

	// </editor-fold>

	@Override public State currentState(
		ResourceInstance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeResourceInstance fake = ModelExtensions.As(instance, FakeResourceInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be of type FakeResourceInstance"); }

		return this.getCurrentState();
	}

	@Override public void transitionTo(
		ResourceInstance instance,
		UUID targetStateId) throws
			IndeterminateStateException,
			AssertionFailedException,
			TransitionNotPossibleException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeResourceInstance fake = ModelExtensions.As(instance, FakeResourceInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be of type FakeResourceInstance"); }

		throw new UnsupportedOperationException("Not supported yet.");
	}
}