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
import co.mv.wb.AssertionType;
import co.mv.wb.ResourceType;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.postgresql.PostgreSqlConstants;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a given table exists in an ANSI-SQL compliant database.
 *
 * @since 4.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:AnsiSqlTableExists",
	description =
		"Asserts that a table exists using an ANSI-SQL compliant query.",
	example =
		"<assertion\n" +
			"    type=\"AnsiSqlTableExists\"\n" +
			"    id=\"bf9fe573-2586-4586-bf7d-a020a33a5f09\"\n" +
			"    name=\"ProductType table does not exist\">\n" +
			"    <schemaName>prdcat</schemaName>\n" +
			"    <tableName>ProductType</tableName>\n" +
			"</assertion>"
)
public class AnsiSqlTableExistsAssertion extends BaseAssertion
{
	private final String schemaName;
	private final String tableName;

	/**
	 * Creates a new AnsiSqlTableExistsAssertion.
	 *
	 * @param assertionId the ID for the new assertion.
	 * @param seqNum      the ordinal index of the new assertion within it's parent container.
	 * @param schemaName  the name of the schema to check.
	 * @param tableName   the name of the table to check.
	 * @since 4.0
	 */
	public AnsiSqlTableExistsAssertion(
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
		return String.format("Table '%s' exists in schema '%s'", this.getTableName(), this.getSchemaName());
	}

	/**
	 * Gets the name of the schema to check.
	 *
	 * @return the name of the schema to check
	 * @since 2.0
	 */
	public String getSchemaName()
	{
		return this.schemaName;
	}

	/**
	 * Gets the name of the table to check
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
			PostgreSqlConstants.PostgreSqlDatabase);
	}
}
