package co.mv.stm.service.dom.mysql;

import co.mv.stm.Assertion;
import co.mv.stm.impl.database.mysql.MySqlDatabaseExistsAssertion;
import co.mv.stm.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class MySqlDatabaseExistsDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		return new MySqlDatabaseExistsAssertion(assertionId, name, seqNum);
	}
}
