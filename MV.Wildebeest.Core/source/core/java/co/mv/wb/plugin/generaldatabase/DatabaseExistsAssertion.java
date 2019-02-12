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
 * An {@link Assertion} that verifies that a database exists.
 *
 * @since 4.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:DatabaseExists",
	description =
		"Asserts that the database specified by the instance exists.",
	example =
		"<assertion\n" +
			"    type=\"DatabaseExists\"\n" +
			"    id=\"c5eccbf0-3d2a-4906-8f46-c380517628d7\">\n" +
			"</assertion>"
)
public class DatabaseExistsAssertion extends BaseAssertion
{
	/**
	 * Creates a new DatabaseExistsAssertion.
	 *
	 * @param assertionId the ID of the new assertion.
	 * @param seqNum      the ordinal index of the new assertion within it's containing set.
	 * @since 4.0
	 */
	public DatabaseExistsAssertion(
		UUID assertionId,
		int seqNum)
	{
		super(assertionId, seqNum);
	}

	@Override public String getDescription()
	{
		return "Database exists";
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			MySqlConstants.MySqlDatabase,
			PostgreSqlConstants.PostgreSqlDatabase,
			SqlServerConstants.SqlServerDatabase);
	}
}
