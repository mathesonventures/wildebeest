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
import co.mv.wb.AssertionPlugin;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginHandler;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;

/**
 * Handler for {@link AnsiSqlTableDoesNotExistAssertion}.
 *
 * @since 4.0
 */
@PluginHandler(
	uri = "co.mv.wb.generaldatabase:AnsiSqlTableDoesNotExist"
)
public class AnsiSqlTableDoesNotExistAssertionPlugin implements AssertionPlugin
{
	@Override public AssertionResponse perform(
		Assertion assertion,
		Instance instance)
	{
		if (assertion == null) throw new ArgumentNullException("assertion");
		if (instance == null) throw new ArgumentNullException("instance");

		AnsiSqlTableDoesNotExistAssertion assertionT = (AnsiSqlTableDoesNotExistAssertion)assertion;

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
			assertionT.getSchemaName(),
			assertionT.getTableName()))
		{
			result = new ImmutableAssertionResponse(false, "Table " + assertionT.getTableName() +
				" exists in schema" + assertionT.getSchemaName());
		}

		else
		{
			result = new ImmutableAssertionResponse(true, "Table " + assertionT.getTableName() +
				" does not exist in schema " + assertionT.getSchemaName());
		}

		return result;
	}
}
