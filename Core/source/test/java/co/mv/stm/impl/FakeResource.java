package co.mv.stm.impl;

import co.mv.stm.IndeterminateStateException;
import co.mv.stm.ModelExtensions;
import co.mv.stm.ResourceInstance;
import co.mv.stm.State;
import java.util.UUID;

public class FakeResource extends BaseResource
{
	public FakeResource(
		UUID resourceId,
		String name)
	{
		super(resourceId, name);
	}

	@Override public State currentState(
		ResourceInstance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeResourceInstance fake = ModelExtensions.As(instance, FakeResourceInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be of type FakeResourceInstance"); }

		return fake.hasStateId() ? this.stateForId(fake.getStateId()) : null;
	}
}