package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.mysql.MySqlDatabaseExistsAssertion;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class MySqlDatabaseExistsDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		return new MySqlDatabaseExistsAssertion(assertionId, name, seqNum);
	}
}
