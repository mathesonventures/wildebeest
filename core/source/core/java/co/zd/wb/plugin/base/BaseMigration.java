// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb.plugin.base;

import co.zd.wb.Migration;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Migration}
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public abstract class BaseMigration implements Migration
{
	/**
	 * Creates a new BaseMigration instance.
	 * 
	 * @param       migrationId                 the ID for the new migration
	 * @param       fromStateId                 the optional from state for the new migration
	 * @param       toStateId                   the optional to state for the new migration
	 */
	protected BaseMigration(
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

	@Override public UUID getMigrationId() {
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

	@Override public UUID getFromStateId() {
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

	@Override public UUID getToStateId() {
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
