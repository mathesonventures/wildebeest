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
import java.util.UUID;

/**
 * A {@link Migration} that drops a database using ANSI-standard SQL statements.
 *
 * @since 4.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:AnsiSqlDropDatabase",
	description =
		"Drops the database defined by the instance definition.  This migration can be used to transition a database " +
			"from a state to non-existant",
	example =
		"<migration\n" +
			"    type=\"AnsiSqlDropDatabase\"\n" +
			"    id=\"c3376639-40c1-4795-adc4-258de2b07176\"\n" +
			"    fromState=\"a7d7f4c8-ea65-447f-bd59-aa73c00cd8c2\">\n" +
			"</migration>"
)
public class AnsiSqlDropDatabaseMigration extends BaseMigration
{
	public AnsiSqlDropDatabaseMigration(
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
			Wildebeest.PostgreSqlDatabase);
	}
}
