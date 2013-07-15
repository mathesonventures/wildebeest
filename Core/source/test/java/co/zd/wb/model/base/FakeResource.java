package co.zd.wb.model.base;

import co.zd.wb.model.base.BaseResource;
import co.zd.wb.model.IndeterminateStateException;
import co.zd.wb.model.ModelExtensions;
import co.zd.wb.model.Instance;
import co.zd.wb.model.State;
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