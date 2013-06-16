package co.mv.stm.service.dom.database;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.database.RowExistsAssertion;
import co.mv.stm.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class RowDoesNotExistDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		String sql = this.getString("sql");
		
		return new RowExistsAssertion(assertionId, name, seqNum, sql);
	}
}