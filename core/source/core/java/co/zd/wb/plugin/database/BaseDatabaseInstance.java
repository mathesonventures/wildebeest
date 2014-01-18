package co.zd.wb.plugin.database;

public abstract class BaseDatabaseInstance implements DatabaseInstance
{
	protected BaseDatabaseInstance(
		String databaseName,
		String stateTableName)
	{
		if (stateTableName != null && "".equals(stateTableName.trim()))
		{
			throw new IllegalArgumentException("stateTableName cannot be empty");
		}
		
		this.setDatabaseName(databaseName);
		if (stateTableName != null)
		{
			this.setStateTableName(stateTableName);
		}
	}
	
	// <editor-fold desc="DatabaseName" defaultstate="collapsed">

	private String _databaseName = null;
	private boolean _databaseName_set = false;

	public String getDatabaseName() {
		if(!_databaseName_set) {
			throw new IllegalStateException("databaseName not set.");
		}
		if(_databaseName == null) {
			throw new IllegalStateException("databaseName should not be null");
		}
		return _databaseName;
	}

	private void setDatabaseName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("databaseName cannot be null");
		}
		boolean changing = !_databaseName_set || _databaseName != value;
		if(changing) {
			_databaseName_set = true;
			_databaseName = value;
		}
	}

	private void clearDatabaseName() {
		if(_databaseName_set) {
			_databaseName_set = true;
			_databaseName = null;
		}
	}

	private boolean hasDatabaseName() {
		return _databaseName_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="StateTableName" defaultstate="collapsed">

	private String m_stateTableName = null;
	private boolean m_stateTableName_set = false;

	@Override public String getStateTableName() {
		if(!m_stateTableName_set) {
			throw new IllegalStateException("stateTableName not set.  Use the HasStateTableName() method to check its state before accessing it.");
		}
		return m_stateTableName;
	}

	private void setStateTableName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("stateTableName cannot be null");
		}
		boolean changing = !m_stateTableName_set || m_stateTableName != value;
		if(changing) {
			m_stateTableName_set = true;
			m_stateTableName = value;
		}
	}

	private void clearStateTableName() {
		if(m_stateTableName_set) {
			m_stateTableName_set = true;
			m_stateTableName = null;
		}
	}

	@Override public boolean hasStateTableName() {
		return m_stateTableName_set;
	}

	// </editor-fold>
}
