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
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A {@link Migration} that creates a MySQL database.
 *
 * @since 1.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:MySqlDatabase",
	uri = "co.mv.wb.mysql:MySqlCreateDatabase",
	description =
		"This will usually be the first migration in the definition of any MySQL resource managed by Wildebeest. " +
			"It creates a new schema.\n" +
			"This migration takes the name of the schema to create from the MySqlDatabaseInstance it is applied to.",
	example =
		"<migration\n" +
			"    type=\"MySqlCreateDatabase\"\n" +
			"    id=\"6b21e1e3-ff3a-44b3-84ec-e21fb01c0110\"\n" +
			"    toStateId=\"199b7cc1-3cc6-48ca-b012-a70d05d5b5e7\">\n" +
			"</migration>"
)
public class MySqlCreateDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new MySqlCreateDatabaseMigration.
	 *
	 * @param migrationId the ID of the new migration.
	 * @param fromState   the source-state for the migration, or null if this migration
	 *                    transitions from the non-existent state.
	 * @param toState     the target-state for the migration, or null if this migration
	 *                    transitions to the non-existent state.
	 * @since 1.0
	 */
	public MySqlCreateDatabaseMigration(
		UUID migrationId,
		String fromState,
		String toState)
	{
		super(migrationId, fromState, toState);
	}

	@Override
	public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			MySqlConstants.MySqlDatabase);
	}
}
