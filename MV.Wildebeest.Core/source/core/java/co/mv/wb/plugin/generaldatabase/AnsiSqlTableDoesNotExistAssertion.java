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

import co.mv.wb.Assertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.AssertionType;
import co.mv.wb.Instance;
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
 * An {@link Assertion} that verifies that a given table does not exist in an ANSI-SQL compliant database system.
 *
 * @since 4.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:AnsiSqlTableDoesNotExist",
	description =
		"Asserts that a specific table does not exist within the database using an ANSI-SQL compliant query.",
	example =
		"<assertion\n" +
			"    type=\"AnsiSqlTableDoesNotExist\"\n" +
			"    id=\"f54689cb-3f92-4178-aa2f-8ecd1daaeb18\"\n" +
			"    name=\"ProductType table exists\">\n" +
			"    <schemaName>prdcat</schemaName>\n" +
			"    <tableName>ProductType</tableName>\n" +
			"</assertion>"
)
public class AnsiSqlTableDoesNotExistAssertion extends BaseAssertion
{
	private final String schemaName;
	private final String tableName;

	/**
	 * Creates a new AnsiSqlTableDoesNotExistAssertion.
	 *
	 * @param assertionId the ID for the new assertion.
	 * @param seqNum      the ordinal index of the new assertion within it's parent container.
	 * @param schemaName  the name of the schema to check.
	 * @param tableName   the name of the table to check.
	 * @since 4.0
	 */
	public AnsiSqlTableDoesNotExistAssertion(
		UUID assertionId,
		int seqNum,
		String schemaName,
		String tableName)
	{
		super(assertionId, seqNum);

		this.schemaName = schemaName;
		this.tableName = tableName;
	}

	@Override public String getDescription()
	{
		return String.format("Table '%s' does not exist in schema '%s'", this.getTableName(), this.getSchemaName());
	}

	/**
	 * Gets the name of the schema to check.
	 *
	 * @return the schema name to check
	 * @since 2.0
	 */
	public String getSchemaName()
	{
		return this.schemaName;
	}

	/**
	 * Gets the name of the table to check.
	 *
	 * @return the name of the table to check
	 * @since 2.0
	 */
	public String getTableName()
	{
		return this.tableName;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.PostgreSqlDatabase);
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) throw new ArgumentNullException("instance");

		AnsiSqlDatabaseInstance db = ModelExtensions.as(instance, AnsiSqlDatabaseInstance.class);
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

		else if (AnsiSqlDatabaseHelper.tableExists(
			db,
			this.getSchemaName(),
			this.getTableName()))
		{
			result = new ImmutableAssertionResponse(false, "Table " + this.getTableName() +
				" exists in schema" + this.getSchemaName());
		}

		else
		{
			result = new ImmutableAssertionResponse(true, "Table " + this.getTableName() +
				" does not exist in schema " + this.getSchemaName());
		}

		return result;
	}
}
