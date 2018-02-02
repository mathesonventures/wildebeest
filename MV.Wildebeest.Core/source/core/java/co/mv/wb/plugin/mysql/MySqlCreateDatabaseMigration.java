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

package co.mv.wb.plugin.mysql;

import co.mv.wb.Migration;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that creates a MySQL database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MySqlCreateDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new MySqlCreateDatabaseMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source-state for the migration, or null if this migration
	 *                                          transitions from the non-existent state.
	 * @param       toStateId                   the target-state for the migration, or null if this migration
	 *                                          transitions to the non-existent state.
	 * @since                                   1.0
	 */
	public MySqlCreateDatabaseMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}
	
	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			FactoryResourceTypes.MySqlDatabase);
	}
}
