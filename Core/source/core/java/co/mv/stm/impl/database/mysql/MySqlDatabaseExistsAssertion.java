package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.BaseAssertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.AssertionType;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.impl.ImmutableAssertionResponse;
import java.util.UUID;

public class MySqlDatabaseExistsAssertion extends BaseAssertion
{
	public MySqlDatabaseExistsAssertion(
		UUID assertionId,
		String name,
		int seqNum)
	{
		super(assertionId, name, seqNum, AssertionType.DatabaseRowDoesNotExist);
	}
	
	@Override public AssertionResponse apply(ResourceInstance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseResourceInstance db = ModelExtensions.As(instance, MySqlDatabaseResourceInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseResourceInstance"); }
		
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
