package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.mysql.MySqlTableDoesNotExistAssertion;
import co.zd.wb.service.ResourceLoaderFault;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
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
