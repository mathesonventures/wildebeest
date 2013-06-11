package co.mv.stm.service.dom.mysql;

import co.mv.stm.Assertion;
import co.mv.stm.impl.database.mysql.MySqlTableDoesNotExistAssertion;
import co.mv.stm.service.ResourceLoaderFault;
import co.mv.stm.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;
import org.w3c.dom.Element;

public class MySqlTableDoesNotExistDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		Element childXe = (Element)this.getElement().getChildNodes().item(0);
		
		Assertion result = null;
		
		if ("tableName".equals(childXe.getTagName()))
		{
			String tableName = childXe.getTextContent();
			result = new MySqlTableDoesNotExistAssertion(assertionId, name, seqNum, tableName);
		}
		
		if (result == null)
		{
			throw new ResourceLoaderFault("could not build instance due to missing data");
		}
		
		return result;
	}
}
