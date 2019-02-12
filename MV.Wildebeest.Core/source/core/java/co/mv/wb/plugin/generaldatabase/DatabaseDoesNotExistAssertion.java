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
import co.mv.wb.plugin.mysql.MySqlConstants;
import co.mv.wb.plugin.postgresql.PostgreSqlConstants;
import co.mv.wb.plugin.sqlserver.SqlServerConstants;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a database does not exist.
 *
 * @since 1.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:DatabaseDoesNotExist",
	description =
		"Asserts that the database specified by the instance does not exist.",
	example =
		"<assertion\n" +
			"    type=\"DatabaseDoesNotExist\"\n" +
			"    id=\"42a51154-cafe-4ec1-8a99-45918e9e1837\">\n" +
			"</assertion>"
)
public class DatabaseDoesNotExistAssertion extends BaseAssertion
{
	/**
	 * Creates a new DatabaseDoesNotExistAssertion
	 *
	 * @param assertionId the ID of the new assertion.
	 * @param seqNum      the ordinal index of the new assertion within it's containing set.
	 * @since 1.0
	 */
	public DatabaseDoesNotExistAssertion(
		UUID assertionId,
		int seqNum)
	{
		super(assertionId, seqNum);
	}

	@Override public String getDescription()
	{
		return "Database does not exist";
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			MySqlConstants.MySqlDatabase,
			PostgreSqlConstants.PostgreSqlDatabase,
			SqlServerConstants.SqlServerDatabase);
	}
}
