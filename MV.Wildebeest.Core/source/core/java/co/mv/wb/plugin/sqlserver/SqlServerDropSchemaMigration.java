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
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that drops a schema from a SQL Server database.
 * 
 * @since                                       2.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerDropSchema",
	description = "Drops a schema from a SQL Server database resource.",
	example =
		"<migration\n" +
		"    type=\"SqlServerDropSchema\"\n" +
		"    id=\"ffe636f4-563f-4725-bcf2-124e7bb38d76\"\n" +
		"    fromStateId=\"0cae6740-cb35-4028-af8a-14d565414078\"\n" +
		"    toStateId=\"cc24394e-0f5b-42b9-8216-c95c81ff07dc\">\n" +
		"    <schemaName>prd</schemaName>\n" +
		"</migration>"
)
public class SqlServerDropSchemaMigration extends BaseMigration
{
	private final String schemaName;

	/**
	 * Creates a new SqlServerDropSchemaMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source state for this migration.
	 * @param       toStateId                   the target state for this migration.
	 * @param       schemaName                  the name of the schema to be dropped.
	 * @since                                   2.0
	 */
	public SqlServerDropSchemaMigration(
		UUID migrationId,
		Optional<String> fromStateId,
		Optional<String> toStateId,
		String schemaName)
	{
		super(migrationId, fromStateId, toStateId);

		if (schemaName == null) throw new ArgumentNullException("schemaName");

		this.schemaName = schemaName;
	}
	
	/**
	 * Returns the name of the schema to be dropped.
	 * 
	 * @return                                  the name of the schema to be dropped
	 * @since                                   2.0
	 */
	public final String getSchemaName()
	{
		return schemaName;
	}

	
	@Override
	public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.SqlServerDatabase);
	}
}
