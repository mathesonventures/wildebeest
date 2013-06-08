package co.mv.stm.impl;

import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.ResourceType;
import co.mv.stm.model.State;
import java.util.UUID;

public class FakeResource extends BaseResource
{
	public FakeResource(
		UUID resourceId,
		String name)
	{
		super(resourceId, name, ResourceType.Database);
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