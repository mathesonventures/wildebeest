package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.BaseAssertion;
import co.mv.stm.AssertionResponse;
import co.mv.stm.ModelExtensions;
import co.mv.stm.Instance;
import co.mv.stm.impl.ImmutableAssertionResponse;
import java.util.UUID;

public class MySqlDatabaseDoesNotExistAssertion extends BaseAssertion
{
	public MySqlDatabaseDoesNotExistAssertion(
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
			result = new ImmutableAssertionResponse(false, "Database " + db.getSchemaName() + " exists");
		}
		else
		{
			result = new ImmutableAssertionResponse(true, "Database " + db.getSchemaName() + " does not exist");
		}
		
		return result;
	}
}
