package co.mv.stm.model.base;

import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.Instance;
import co.mv.stm.model.State;
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
		Instance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		FakeInstance fake = ModelExtensions.As(instance, FakeInstance.class);
		if (fake == null) { throw new IllegalArgumentException("instance must be of type FakeInstance"); }

		return fake.hasStateId() ? this.stateForId(fake.getStateId()) : null;
	}
}