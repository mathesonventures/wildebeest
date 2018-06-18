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

package co.mv.wb.plugin.sqlserver;

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
 * A {@link Migration} that creates a new SQL-Server database.
 *
 * @since 2.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerCreateDatabase",
	description =
		"This will usually be the first migration in the definition of any SQL Server database resource managed by " +
			"Wildebeest. It creates a new database.\n" +
			"This migration takes the name of the database to create from the SqlServerDatabaseInstance it is applied to.",
	example =
		"<migration\n" +
			"    type=\"SqlServerCreateDatabase\"\n" +
			"    id=\"48e9b89a-e3be-4418-ace7-c008fcacc32f\"\n" +
			"    toStateId=\"9aeeba93-6890-4690-b7b2-afa158ae6556\">\n" +
			"</migration>"
)
public class SqlServerCreateDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new SqlServerCreateDatabseMigration.
	 *
	 * @param migrationId the ID of the new migration.
	 * @param fromState   the source state for this migration.
	 * @param toState     the target state for this migration.
	 * @since 2.0
	 */
	public SqlServerCreateDatabaseMigration(
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
			Wildebeest.SqlServerDatabase);
	}
}
