package co.mv.stm.service.dom.database;

import co.mv.stm.model.Transition;
import co.mv.stm.model.database.SqlScriptTransition;
import co.mv.stm.service.ResourceLoaderFault;
import co.mv.stm.service.dom.BaseDomTransitionBuilder;
import java.util.UUID;
import org.w3c.dom.Element;

public class SqlScriptDomTransitionBuilder extends BaseDomTransitionBuilder
{
	@Override public Transition build(UUID transitionId, UUID fromStateId, UUID toStateId)
	{
		Element childXe = (Element)this.getElement().getChildNodes().item(0);
		
		Transition result = null;
		
		if ("sql".equals(childXe.getTagName()))
		{
			String sql = childXe.getTextContent();
			result = new SqlScriptTransition(transitionId, fromStateId, toStateId, sql);
		}
		
		if (result == null)
		{
			throw new ResourceLoaderFault("could not build instance due to missing data");
		}
		
		return result;
	}
}