package co.zd.wb.service.dom;

import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import co.zd.wb.model.Assertion;
import co.zd.wb.model.base.FakeAssertion;
import java.util.UUID;

public class DomFakeAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		String tag = this.getElement().getChildNodes().item(0).getTextContent();
		
		return new FakeAssertion(assertionId, name, seqNum, tag);
	}
}