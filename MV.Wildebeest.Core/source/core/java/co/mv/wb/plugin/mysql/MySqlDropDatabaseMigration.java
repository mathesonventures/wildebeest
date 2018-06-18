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
import co.mv.wb.MigrationType;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that drops a MySQL database.
 * 
 * @since                                       1.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:MySqlDatabase",
	uri = "co.mv.wb.mysql:MySqlDropDatabase",
	description =
		"Drops the MySQL database defined by the instance definition.  This migration can be used to transition a " +
			"MySQL database from a state to non-existant",
	example =
		"<migration\n" +
		"    type=\"MySqlDropDatabase\"\n" +
		"    id=\"dfdafb03-4653-4641-9eb6-07f63aadb2af\"\n" +
		"    fromState=\"3bab9e8c-4ede-4a61-b682-62cec77f8a10\">\n" +
		"</migration>"
)
public class MySqlDropDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new MySqlDropDatabaseMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromState                   the source-state for the migration, or null if this migration
	 *                                          transitions from the non-existent state.
	 * @param       toState                     the target-state for the migration, or null if this migration
	 *                                          transitions to the non-existent state.
	 * @since                                   1.0
	 */
	public MySqlDropDatabaseMigration(
		UUID migrationId,
		Optional<String> fromState,
		Optional<String> toState)
	{
		super(migrationId, fromState, toState);
	}
	
	@Override
	public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.MySqlDatabase);
	}
}
