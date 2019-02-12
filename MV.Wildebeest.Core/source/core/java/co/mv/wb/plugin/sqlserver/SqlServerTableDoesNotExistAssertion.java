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

import co.mv.wb.AssertionType;
import co.mv.wb.ResourceType;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.BaseAssertion;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link co.mv.wb.Assertion} that verifies that a given table does not exist in a SQL Server database.
 *
 * @since 2.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:SqlServerDatabase",
	uri = "co.mv.wb.sqlserver:SqlServerTableDoesNotExist",
	description =
		"Verifies that a table does not exist in a SQL Server database resource.",
	example =
		"<assertion\n" +
			"    type=\"SqlServerTableDoesNotExist\"\n" +
			"    id=\"76455254-dc96-4707-8348-41213bb4aa58\"\n" +
			"    <schemaName>prdcat</schemaName>\n" +
			"    <tableName>Product</tableName>\n" +
			"</assertion>"
)
public class SqlServerTableDoesNotExistAssertion extends BaseAssertion
{
	private final String schemaName;
	private final String tableName;

	/**
	 * Creates a new SqlServerTableDoesNotExistAssertion.
	 *
	 * @param assertionId the ID for the new assertion.
	 * @param seqNum      the ordinal index of the new assertion within it's parent container.
	 * @param schemaName  the name of the schema to check.
	 * @param tableName   the name of the table to check.
	 * @since 2.0
	 */
	public SqlServerTableDoesNotExistAssertion(
		UUID assertionId,
		int seqNum,
		String schemaName,
		String tableName)
	{
		super(assertionId, seqNum);

		if (schemaName == null) throw new ArgumentNullException("schemaName");

		if (schemaName == null) throw new ArgumentNullException("schemaName");
		if (tableName == null) throw new ArgumentNullException("tableName");

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
	 * @return the name of the schema to check
	 * @since 2.0
	 */
	public final String getSchemaName()
	{
		return this.schemaName;
	}

	/**
	 * Gets the name of the table to check.
	 *
	 * @return the name of the table to check
	 * @since 2.0
	 */
	public final String getTableName()
	{
		return tableName;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			SqlServerConstants.SqlServerDatabase);
	}


}
