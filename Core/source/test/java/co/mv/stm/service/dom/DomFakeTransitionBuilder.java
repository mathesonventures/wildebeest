package co.mv.stm.service.dom;

import co.mv.stm.model.Transition;
import co.mv.stm.model.base.FakeTransition;
import java.util.UUID;

public class DomFakeTransitionBuilder extends BaseDomTransitionBuilder
{
	@Override public Transition build(UUID transitionId, UUID fromStateId, UUID toStateId)
	{
		String tag = this.getElement().getChildNodes().item(0).getTextContent();
		
		return new FakeTransition(transitionId, fromStateId, toStateId, tag);
	}
}