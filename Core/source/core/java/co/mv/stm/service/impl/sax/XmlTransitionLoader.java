package co.mv.stm.service.impl.sax;

import co.mv.stm.Transition;
import org.w3c.dom.Element;

public interface XmlTransitionLoader
{
	Transition load(Element transitionXe);
}