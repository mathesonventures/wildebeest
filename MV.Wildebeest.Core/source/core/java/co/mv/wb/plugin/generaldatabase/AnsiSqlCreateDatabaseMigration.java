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

package co.mv.wb.plugin.generaldatabase;

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
 * A {@link Migration} that creates a database using ANSI-standard SQL statements.
 *
 * @since 4.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:AnsiSqlCreateDatabase",
	description =
		"This will usually be the first migration in the definition of any ANSI-SQL database resource managed by " +
			"Wildebeest. It creates a new schema.\n" +
			"This migration takes the name of the schema to create from the MySqlDatabaseInstance it is applied to.",
	example =
		"<migration\n" +
			"    type=\"AnsiSqlCreateDatabase\"\n" +
			"    id=\"7b7c412b-809f-42e7-99ef-434746086e17\"\n" +
			"    toStateId=\"35bc9088-6f44-4889-ba1d-c2b079401694\">\n" +
			"</migration>"
)
public class AnsiSqlCreateDatabaseMigration extends BaseMigration
{
	public AnsiSqlCreateDatabaseMigration(
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
			Wildebeest.PostgreSqlDatabase);
	}
}
