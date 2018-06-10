// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

package co.mv.wb.plugin.base;

import co.mv.wb.Migration;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Migration}
 * 
 * @since                                       1.0
 */
public abstract class BaseMigration implements Migration
{
	private UUID migrationId = null;
	private boolean migrationIdSet = false;
	private Optional<String> fromStateId = null;
	private boolean fromStateIdSet = false;
	private Optional<String> toStateId = null;
	private boolean toStateIdSet = false;

	/**
	 * Creates a new BaseMigration instance.
	 * 
	 * @param       migrationId                 the ID for the new migration
	 * @param       fromStateId                 the optional from state for the new migration
	 * @param       toStateId                   the optional to state for the new migration
	 */
	protected BaseMigration(
		UUID migrationId,
		Optional<String> fromStateId,
		Optional<String> toStateId)
	{
		this.setMigrationId(migrationId);
		this.setFromStateId(fromStateId);
		this.setToStateId(toStateId);
	}

	@Override
	public UUID getMigrationId() {
		if(!migrationIdSet) {
			throw new IllegalStateException("migrationId not set.  Use the HasMigrationId() method to check its state before accessing it.");
		}
		return migrationId;
	}

	private void setMigrationId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationId cannot be null");
		}
		boolean changing = !migrationIdSet || migrationId != value;
		if(changing) {
			migrationIdSet = true;
			migrationId = value;
		}
	}

	private void clearMigrationId() {
		if(migrationIdSet) {
			migrationIdSet = true;
			migrationId = null;
		}
	}

	private boolean hasMigrationId() {
		return migrationIdSet;
	}



	@Override
	public Optional<String> getFromStateId() {
		if(!fromStateIdSet) {
			throw new IllegalStateException("fromStateId not set.");
		}
		if(fromStateId == null) {
			throw new IllegalStateException("fromStateId should not be null");
		}
		return fromStateId;
	}

	private void setFromStateId(Optional<String> value) {
		if(value == null) {
			throw new IllegalArgumentException("fromStateId cannot be null");
		}
		boolean changing = !fromStateIdSet || fromStateId != value;
		if(changing) {
			fromStateIdSet = true;
			fromStateId = value;
		}
	}


	@Override
	public Optional<String> getToStateId() {
		if(!toStateIdSet) {
			throw new IllegalStateException("toStateId not set.");
		}
		if(toStateId == null) {
			throw new IllegalStateException("toStateId should not be null");
		}
		return toStateId;
	}

	private void setToStateId(Optional<String> value) {
		if(value == null) {
			throw new IllegalArgumentException("toStateId cannot be null");
		}
		boolean changing = !toStateIdSet || toStateId != value;
		if(changing) {
			toStateIdSet = true;
			toStateId = value;
		}
	}

	private void clearToStateId() {
		if(toStateIdSet) {
			toStateIdSet = true;
			toStateId = null;
		}
	}

	private boolean hasToStateId() {
		return toStateIdSet;
	}

}
