package co.mv.stm.service.impl.dom;

import co.mv.stm.service.ResourceBuilder;
import org.w3c.dom.Element;

public interface DomResourceBuilder extends ResourceBuilder
{
	public void setResourceElement(Element resourceElement);
}