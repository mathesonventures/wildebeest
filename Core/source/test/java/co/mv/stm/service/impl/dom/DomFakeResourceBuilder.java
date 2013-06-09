package co.mv.stm.service.impl.dom;

import co.mv.stm.Resource;
import co.mv.stm.impl.FakeResource;
import java.util.UUID;

public class DomFakeResourceBuilder extends BaseDomResourceBuilder
{
	@Override public Resource build(UUID id, String name)
	{
		return new FakeResource(id, name);
	}
}