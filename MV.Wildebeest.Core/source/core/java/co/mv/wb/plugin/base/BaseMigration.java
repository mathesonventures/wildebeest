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
import co.mv.wb.framework.ArgumentNullException;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Migration}
 * 
 * @since                                       1.0
 */
public abstract class BaseMigration implements Migration
{
	private final UUID migrationId;
	private final Optional<UUID> fromStateId;
	private final Optional<UUID> toStateId;

	/**
	 * Creates a new BaseMigration instance.
	 * 
	 * @param       migrationId                 the ID for the new migration
	 * @param       fromStateId                 the optional from state for the new migration
	 * @param       toStateId                   the optional to state for the new migration
	 */
	protected BaseMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId)
	{
		if (migrationId == null) throw new ArgumentNullException("migrationId");
		if (fromStateId == null) throw new ArgumentNullException("fromStateId");
		if (toStateId == null) throw new ArgumentNullException("toStateId");

		this.migrationId = migrationId;
		this.fromStateId = fromStateId;
		this.toStateId = toStateId;
	}

	@Override
	public UUID getMigrationId()
	{
		return this.migrationId;
	}

	@Override
	public Optional<UUID> getFromStateId()
	{
		return this.fromStateId;
	}

	@Override
	public Optional<UUID> getToStateId()
	{
		return this.toStateId;
	}
}
