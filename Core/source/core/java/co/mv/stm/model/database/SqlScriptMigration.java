package co.mv.stm.model.database;

import co.mv.stm.model.base.BaseMigration;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.Instance;
import co.mv.stm.model.Migration;
import co.mv.stm.model.MigrationFailedException;
import co.mv.stm.model.MigrationFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class SqlScriptMigration extends BaseMigration implements Migration
{
	public SqlScriptMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		String sql)
	{
		super(migrationId, fromStateId, toStateId);
		this.setSql(sql);
	}
	
	// <editor-fold desc="Sql" defaultstate="collapsed">

	private String m_sql = null;
	private boolean m_sql_set = false;

	public String getSql() {
		if(!m_sql_set) {
			throw new IllegalStateException("sql not set.  Use the HasSql() method to check its state before accessing it.");
		}
		return m_sql;
	}

	private void setSql(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("sql cannot be null");
		}
		boolean changing = !m_sql_set || m_sql != value;
		if(changing) {
			m_sql_set = true;
			m_sql = value;
		}
	}

	private void clearSql() {
		if(m_sql_set) {
			m_sql_set = true;
			m_sql = null;
		}
	}

	private boolean hasSql() {
		return m_sql_set;
	}

	// </editor-fold>
	
	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }

		try
		{
			// Strip out any comments, and split the block of SQL into individual statements
			String sql = this.getSql().replaceAll("/\\*.*?\\*/", "");
			String[] statements = sql.split(";");

			// Execute each statement
			for(String statement : statements)
			{
				if (!"".equals(statement.trim()))
				{
					DatabaseHelper.execute(db.getAppDataSource(), statement);
				}
			}

			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("UPDATE StmState SET StateId = '").append(this.getToStateId().toString())
				.append("';").toString());
		}
		catch(SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
