package co.mv.stm.service.dom;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.base.FakeAssertion;
import java.util.UUID;

public class DomFakeAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		String tag = this.getElement().getChildNodes().item(0).getTextContent();
		
		return new FakeAssertion(assertionId, name, seqNum, tag);
	}
}