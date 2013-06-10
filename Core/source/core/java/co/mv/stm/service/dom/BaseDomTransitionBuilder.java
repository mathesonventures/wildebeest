package co.mv.stm.service.dom;

public abstract class BaseDomTransitionBuilder extends BaseDomBuilder implements DomTransitionBuilder
{
	@Override public void reset()
	{
		this.clearElement();
	}
}