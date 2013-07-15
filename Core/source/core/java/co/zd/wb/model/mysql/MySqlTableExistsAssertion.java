package co.zd.wb.model.mysql;

import co.zd.wb.model.base.BaseAssertion;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.ModelExtensions;
import co.zd.wb.model.Instance;
import co.zd.wb.model.base.ImmutableAssertionResponse;
import java.util.UUID;

public class MySqlTableExistsAssertion extends BaseAssertion
{
	public MySqlTableExistsAssertion(
		UUID assertionId,
		String name,
		int seqNum,
		String tableName)
	{
		super(assertionId, name, seqNum);
		
		this.setTableName(tableName);
	}

	// <editor-fold desc="TableName" defaultstate="collapsed">

	private String m_tableName = null;
	private boolean m_tableName_set = false;

	public String getTableName() {
		if(!m_tableName_set) {
			throw new IllegalStateException("tableName not set.  Use the HasTableName() method to check its state before accessing it.");
		}
		return m_tableName;
	}

	private void setTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("tableName cannot be null");
		}
		boolean changing = !m_tableName_set || m_tableName != value;
		if(changing) {
			m_tableName_set = true;
			m_tableName = value;
		}
	}

	private void clearTableName() {
		if(m_tableName_set) {
			m_tableName_set = true;
			m_tableName = null;
		}
	}

	private boolean hasTableName() {
		return m_tableName_set;
	}

	// </editor-fold>
	
	@Override public AssertionResponse apply(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }
		
		AssertionResponse result = null;

		if (!MySqlDatabaseHelper.schemaExists(db, db.getSchemaName()))
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getSchemaName()));
		}
		
		else if (MySqlDatabaseHelper.tableExists(db, db.getSchemaName(), this.getTableName()))
		{
			result = new ImmutableAssertionResponse(true, "Table " + this.getTableName() + " exists");
		}
		
		else
		{
			result = new ImmutableAssertionResponse(false, "Table " + this.getTableName() + " does not exist");
		}
		
		return result;
	}
}
