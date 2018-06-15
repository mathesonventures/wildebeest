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
	private Optional<String> fromState = null;
	private boolean fromStateSet = false;
	private Optional<String> toState = null;
	private boolean toStateSet = false;

	/**
	 * Creates a new BaseMigration instance.
	 * 
	 * @param       migrationId                 the ID for the new migration
	 * @param       fromState                 the optional from state for the new migration
	 * @param       toState                   the optional to state for the new migration
	 */
	protected BaseMigration(
		UUID migrationId,
		Optional<String> fromState,
		Optional<String> toState)
	{
		this.setMigrationId(migrationId);
		this.setFromState(fromState);
		this.setToState(toState);
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
	public Optional<String> getFromState() {
		if(!fromStateSet) {
			throw new IllegalStateException("fromState not set.");
		}
		if(fromState == null) {
			throw new IllegalStateException("fromState should not be null");
		}
		return fromState;
	}

	private void setFromState(Optional<String> value) {
		if(value == null) {
			throw new IllegalArgumentException("fromState cannot be null");
		}
		boolean changing = !fromStateSet || fromState != value;
		if(changing) {
			fromStateSet = true;
			fromState = value;
		}
	}


	@Override
	public Optional<String> getToState() {
		if(!toStateSet) {
			throw new IllegalStateException("toState not set.");
		}
		if(toState == null) {
			throw new IllegalStateException("toState should not be null");
		}
		return toState;
	}

	private void setToState(Optional<String> value) {
		if(value == null) {
			throw new IllegalArgumentException("toState cannot be null");
		}
		boolean changing = !toStateSet || toState != value;
		if(changing) {
			toStateSet = true;
			toState = value;
		}
	}

	private void clearToStateId() {
		if(toStateSet) {
			toStateSet = true;
			toState = null;
		}
	}

	private boolean hasToStateId() {
		return toStateSet;
	}

}
