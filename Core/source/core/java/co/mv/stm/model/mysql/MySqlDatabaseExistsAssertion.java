package co.mv.stm.model.mysql;

import co.mv.stm.model.base.BaseAssertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.Instance;
import co.mv.stm.model.base.ImmutableAssertionResponse;
import java.util.UUID;

public class MySqlDatabaseExistsAssertion extends BaseAssertion
{
	public MySqlDatabaseExistsAssertion(
		UUID assertionId,
		String name,
		int seqNum)
	{
		super(assertionId, name, seqNum);
	}
	
	@Override public AssertionResponse apply(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }
		
		AssertionResponse result = null;
		
		if (MySqlDatabaseHelper.schemaExists(db, db.getSchemaName()))
		{
			result = new ImmutableAssertionResponse(true, "Database " + db.getSchemaName() + " exists");
		}
		else
		{
			result = new ImmutableAssertionResponse(false, "Database " + db.getSchemaName() + " does not exist");
		}
		
		return result;
	}
}
