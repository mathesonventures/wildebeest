package co.mv.stm.impl.database;

import co.mv.stm.impl.BaseTransition;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.Transition;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionFaultException;
import co.mv.stm.model.TransitionType;
import java.sql.SQLException;
import java.util.UUID;

public class SqlScriptTransition extends BaseTransition implements Transition
{
	public SqlScriptTransition(
		UUID transitionId,
		UUID toStateId,
		String sql)
	{
		super(transitionId, TransitionType.DatabaseSqlScript, toStateId);
		this.setSql(sql);
	}
	
	public SqlScriptTransition(
		UUID transitionId,
		UUID fromStateId,
		UUID toStateId,
		String sql)
	{
		super(transitionId, TransitionType.DatabaseSqlScript, fromStateId, toStateId);
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
	
	@Override public void perform(ResourceInstance instance) throws TransitionFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseResourceInstance db = ModelExtensions.As(instance, DatabaseResourceInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseResourceInstance"); }

		try
		{
			DatabaseHelper.execute(db.getAppDataSource(), this.getSql());
		}
		catch(SQLException e)
		{
			throw new TransitionFaultException(e);
		}
	}
}
