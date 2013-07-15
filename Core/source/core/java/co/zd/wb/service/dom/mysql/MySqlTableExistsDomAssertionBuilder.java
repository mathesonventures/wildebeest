package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.mysql.MySqlTableExistsAssertion;
import co.zd.wb.service.ResourceLoaderFault;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class MySqlTableExistsDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		Assertion result = null;
		
		String tableName = this.getString("tableName");
		if (tableName != null)
		{
			result = new MySqlTableExistsAssertion(assertionId, name, seqNum, tableName);
		}
		
		if (result == null)
		{
			throw new ResourceLoaderFault("could not build instance due to missing data");
		}
		
		return result;
	}
}
