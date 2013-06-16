package co.mv.stm.model.database;

import co.mv.stm.model.base.BaseTransition;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.Instance;
import co.mv.stm.model.Transition;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class SqlScriptTransition extends BaseTransition implements Transition
{
	public SqlScriptTransition(
		UUID transitionId,
		UUID fromStateId,
		UUID toStateId,
		String sql)
	{
		super(transitionId, fromStateId, toStateId);
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
	
	@Override public void perform(Instance instance) throws TransitionFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }

		try
		{
			DatabaseHelper.execute(db.getAppDataSource(), this.getSql());
			
			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("UPDATE StmState SET StateId = '").append(this.getToStateId().toString())
				.append("';").toString());
		}
		catch(SQLException e)
		{
			throw new TransitionFaultException(e);
		}
	}
}
