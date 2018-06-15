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

import co.mv.wb.MigrationType;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A Migration that creates a new schema in a SQL-Server database resource.
 * 
 * @since                                       2.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerCreateSchema",
	description = "Creates a schema in a SQL Server database resource.",
	example =
		"<migration\n" +
		"    type=\"SqlServerCreateSchema\"\n" +
		"    id=\"675db93a-bd49-42a0-b36e-eef861c661f7\"\n" +
		"    fromStateId=\"81806637-adbe-4123-9677-b8da2333c1a9\"\n" +
		"    toStateId=\"85819eed-05d8-4cee-a34c-ff9b64f6d72b\">\n" +
		"    <schemaName>prd</schemaName>\n" +
		"</migration>"
)
public class SqlServerCreateSchemaMigration extends BaseMigration
{
	private final String schemaName;

	/**
	 * Creates a new SqlServerCreateSchemaMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source state for this migration.
	 * @param       toStateId                   the target state for this migration.
	 * @param       schemaName                  the name for the new schema.
	 * @since                                   2.0
	 */
	public SqlServerCreateSchemaMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId,
		String schemaName)
	{
		super(migrationId, fromStateId, toStateId);

		this.schemaName = schemaName;
	}
	
	/**
	 * Returns the name for the new schema.
	 * 
	 * @return                                  the name for the new schema
	 * @since                                   2.0
	 */
	public String getSchemaName()
	{
		return this.schemaName;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.SqlServerDatabase);
	}
}
