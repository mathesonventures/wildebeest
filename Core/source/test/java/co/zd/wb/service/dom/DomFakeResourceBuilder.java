package co.zd.wb.service.dom;

import co.zd.wb.service.dom.BaseDomResourceBuilder;
import co.zd.wb.model.Resource;
import co.zd.wb.model.base.FakeResource;
import java.util.UUID;

public class DomFakeResourceBuilder extends BaseDomResourceBuilder
{
	@Override public Resource build(UUID id, String name)
	{
		return new FakeResource(id, name);
	}
}