package co.mv.stm.service.dom;

public abstract class BaseDomResourceBuilder extends BaseDomBuilder implements DomResourceBuilder
{
	@Override public void reset()
	{
		this.clearElement();
	}
}