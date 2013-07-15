package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.mysql.MySqlDatabaseDoesNotExistAssertion;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class MySqlDatabaseDoesNotExistDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		return new MySqlDatabaseDoesNotExistAssertion(assertionId, name, seqNum);
	}
}