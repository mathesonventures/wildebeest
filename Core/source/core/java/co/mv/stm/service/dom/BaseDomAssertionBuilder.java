package co.mv.stm.service.dom;

public abstract class BaseDomAssertionBuilder extends BaseDomBuilder implements DomAssertionBuilder
{
	@Override public void reset()
	{
		this.clearElement();
	}
}
