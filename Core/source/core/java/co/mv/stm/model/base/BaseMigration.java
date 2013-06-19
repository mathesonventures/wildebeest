package co.mv.stm.model.base;

import co.mv.stm.model.Migration;
import java.util.UUID;

public abstract class BaseMigration implements Migration
{
	public BaseMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		this.setMigrationId(migrationId);
		if (fromStateId !=  null)
		{
			this.setFromStateId(fromStateId);
		}
		if (toStateId != null)
		{
			this.setToStateId(toStateId);
		}
	}
	
	// <editor-fold desc="MigrationId" defaultstate="collapsed">

	private UUID m_migrationId = null;
	private boolean m_migrationId_set = false;

	public UUID getMigrationId() {
		if(!m_migrationId_set) {
			throw new IllegalStateException("migrationId not set.  Use the HasMigrationId() method to check its state before accessing it.");
		}
		return m_migrationId;
	}

	private void setMigrationId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationId cannot be null");
		}
		boolean changing = !m_migrationId_set || m_migrationId != value;
		if(changing) {
			m_migrationId_set = true;
			m_migrationId = value;
		}
	}

	private void clearMigrationId() {
		if(m_migrationId_set) {
			m_migrationId_set = true;
			m_migrationId = null;
		}
	}

	private boolean hasMigrationId() {
		return m_migrationId_set;
	}

	// </editor-fold>

	// <editor-fold desc="FromStateId" defaultstate="collapsed">

	private UUID m_fromStateId = null;
	private boolean m_fromStateId_set = false;

	public UUID getFromStateId() {
		if(!m_fromStateId_set) {
			throw new IllegalStateException("fromStateId not set.  Use the HasFromStateId() method to check its state before accessing it.");
		}
		return m_fromStateId;
	}

	private void setFromStateId(
		UUID value) {
		boolean changing = !m_fromStateId_set || m_fromStateId != value;
		if(changing) {
			m_fromStateId_set = true;
			m_fromStateId = value;
		}
	}

	private void clearFromStateId() {
		if(m_fromStateId_set) {
			m_fromStateId_set = true;
			m_fromStateId = null;
		}
	}

	public boolean hasFromStateId() {
		return m_fromStateId_set;
	}

	// </editor-fold>

	// <editor-fold desc="ToStateId" defaultstate="collapsed">

	private UUID m_toStateId = null;
	private boolean m_toStateId_set = false;

	public UUID getToStateId() {
		if(!m_toStateId_set) {
			throw new IllegalStateException("toStateId not set.  Use the HasToStateId() method to check its state before accessing it.");
		}
		return m_toStateId;
	}

	private void setToStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("toStateId cannot be null");
		}
		boolean changing = !m_toStateId_set || m_toStateId != value;
		if(changing) {
			m_toStateId_set = true;
			m_toStateId = value;
		}
	}

	private void clearToStateId() {
		if(m_toStateId_set) {
			m_toStateId_set = true;
			m_toStateId = null;
		}
	}

	public boolean hasToStateId() {
		return m_toStateId_set;
	}

	// </editor-fold>
}
