package co.zd.wb.service.dom.database;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.database.RowExistsAssertion;
import co.zd.wb.service.ResourceLoaderFault;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class RowExistsDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		String sql = this.getString("sql");

		if (sql == null)
		{
			throw new ResourceLoaderFault("sql not found in RowExists");
		}
		
		return new RowExistsAssertion(assertionId, name, seqNum, sql);
	}
}
