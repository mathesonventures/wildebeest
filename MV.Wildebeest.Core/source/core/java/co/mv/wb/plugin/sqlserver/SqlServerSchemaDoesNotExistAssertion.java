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

import co.mv.wb.Assertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.MigrationType;
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A {@link Assertion} that verifies that a given schema does not exist in a SQL Server database.
 *
 * @since 2.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerSchemaDoesNotExist",
	description =
		"Verifies that a schema does not exist in a SQL Server database resource.",
	example =
		"<assertion\n" +
			"    type=\"SqlServerSchemaDoesNotExist\"\n" +
			"    id=\"b7b780b0-ed01-4f5d-b1b1-f2f503ebfeaf\"\n" +
			"    <schemaName>prd</schemaName>\n" +
			"</assertion>"
)
public class SqlServerSchemaDoesNotExistAssertion extends BaseAssertion
{
	private final String schemaName;

	/**
	 * Creates a new SqlServerSchemaDoesNotExistAssertion.
	 *
	 * @param assertionId the ID of the new assertion.
	 * @param seqNum      the ordinal index of the new assertion within it's parent container.
	 * @param schemaName  the name of the schema to check,
	 * @since 2.0
	 */
	public SqlServerSchemaDoesNotExistAssertion(
		UUID assertionId,
		int seqNum,
		String schemaName)
	{
		super(assertionId, seqNum);

		if (schemaName == null) throw new ArgumentNullException("schemaName");

		this.schemaName = schemaName;
	}

	@Override public String getDescription()
	{
		return String.format("Schema '%s' does not exist", this.getSchemaName());
	}

	/**
	 * Gets the name of the schema to check.
	 *
	 * @return the name of the schema to check
	 * @since 2.0
	 */
	public final String getSchemaName()
	{
		return this.schemaName;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.SqlServerDatabase);
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) throw new ArgumentNullException("instance");

		SqlServerDatabaseInstance db = ModelExtensions.as(instance, SqlServerDatabaseInstance.class);
		if (db == null)
		{
			throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance");
		}

		AssertionResponse result;

		if (!db.databaseExists())
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getDatabaseName()));
		}

		else if (SqlServerDatabaseHelper.schemaExists(
			db,
			this.getSchemaName()))
		{
			result = new ImmutableAssertionResponse(false, "Schema " + this.getSchemaName() + " exists");
		}

		else
		{
			result = new ImmutableAssertionResponse(true, "Schema " + this.getSchemaName() + " does not exist");
		}

		return result;
	}
}
