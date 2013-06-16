package co.mv.stm.service.dom.mysql;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.mysql.MySqlDatabaseDoesNotExistAssertion;
import co.mv.stm.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class MySqlDatabaseDoesNotExistDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		return new MySqlDatabaseDoesNotExistAssertion(assertionId, name, seqNum);
	}
}