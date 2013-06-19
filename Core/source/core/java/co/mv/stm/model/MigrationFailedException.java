package co.mv.stm.model;

import java.util.UUID;

public class MigrationFailedException extends Exception
{
	public MigrationFailedException(
		UUID migrationId,
		String message)
	{
		super(message);

		this.setMigrationId(migrationId);
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

	public boolean hasMigrationId() {
		return m_migrationId_set;
	}

	// </editor-fold>
}
