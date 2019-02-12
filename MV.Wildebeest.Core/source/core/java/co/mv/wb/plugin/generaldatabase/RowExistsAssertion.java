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
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.mysql.MySqlConstants;
import co.mv.wb.plugin.postgresql.PostgreSqlConstants;
import co.mv.wb.plugin.sqlserver.SqlServerConstants;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a given SQL query yields a single row.
 *
 * @since 1.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:RowExists",
	description = "Asserts that a query results in exactly one row.",
	example =
		"<assertion\n" +
			"    type=\"RowExists\"\n" +
			"    id=\"c1ea9cfb-bbf5-4262-8512-4bc13ebb05a4\"\n" +
			"    name=\"ProductType HW exists\">\n" +
			"    <sql><![CDATA[\n" +
			"        SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';\n" +
			"    ]]></sql>\n" +
			"</assertion> "
)
public class RowExistsAssertion extends BaseAssertion implements Assertion
{
	private final String description;
	private final String sql;

	/**
	 * Creates a new RowDoesNotExistAssertion.
	 *
	 * @param assertionId the ID of the assertion
	 * @param description the description of the query that is being asserted
	 * @param seqNum      the ordinal index of the assertion within it's containing set
	 * @param sql         the query to be evaluated
	 */
	public RowExistsAssertion(
		UUID assertionId,
		String description,
		int seqNum,
		String sql)
	{
		super(assertionId, seqNum);

		if (description == null) throw new ArgumentNullException("description");
		if (sql == null) throw new ArgumentNullException("sql");

		this.description = description;
		this.sql = sql;
	}

	@Override public String getDescription()
	{
		return this.description;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			MySqlConstants.MySqlDatabase,
			PostgreSqlConstants.PostgreSqlDatabase,
			SqlServerConstants.SqlServerDatabase);
	}

	public String getSql()
	{
		return this.sql;
	}
}
