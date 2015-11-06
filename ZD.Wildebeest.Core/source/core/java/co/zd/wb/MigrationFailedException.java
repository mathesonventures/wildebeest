// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.zd.wb;

import java.util.UUID;

/**
 * Indicates that a Migration failed for some normal reason that should be presented to the user.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MigrationFailedException extends Exception
{
	/**
	 * Creates a new MigrationFailedException for the specified ID with the specified failure messages.
	 * 
	 * @param       migrationId                 the ID of the Migration that failed
	 * @param       message                     the failure message
	 * @since                                   1.0
	 */
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

	/**
	 * Gets the ID of the Migration that failed
	 * 
	 * @since                                   1.0
	 */
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
