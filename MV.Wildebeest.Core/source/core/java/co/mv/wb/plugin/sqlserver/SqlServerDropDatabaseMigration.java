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
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A {@link Migration} that creates a new SQL-Server database.
 *
 * @since 2.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerDropDatabase",
	description =
		"Drops the SQL Server database defined by the instance definition.  This migration can be used to transition " +
			"a SQL Server database from a state to non-existant",
	example =
		"<migration\n" +
			"    type=\"SqlServerDropDatabase\"\n" +
			"    id=\"ffe636f4-563f-4725-bcf2-124e7bb38d76\"\n" +
			"    fromState=\"0cae6740-cb35-4028-af8a-14d565414078\">\n" +
			"    <schemaName>prd</schemaName>\n" +
			"</migration>"
)
public class SqlServerDropDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new SqlServerCreateDatabseMigration.
	 *
	 * @param migrationId the ID of the new migration.
	 * @param fromState   the source state for this migration.
	 * @param toState     the target state for this migration.
	 * @since 2.0
	 */
	public SqlServerDropDatabaseMigration(
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
			SqlServerConstants.SqlServerDatabase);
	}
}
