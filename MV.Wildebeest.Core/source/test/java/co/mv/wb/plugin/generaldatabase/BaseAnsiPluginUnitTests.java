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

import co.mv.wb.AssertionResponse;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.framework.ArgumentNullException;

import java.io.PrintStream;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class BaseAnsiPluginUnitTests
{
	public abstract void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException;

	protected void ansiSqlCreateDatabaseMigrationSucceeds(
		PrintStream output,
		AnsiSqlDatabaseInstance instance,
		Migration create,
		MigrationPlugin createRunner,
		Migration drop,
		MigrationPlugin dropRunner) throws MigrationFailedException
	{
		if (output == null) throw new ArgumentNullException("output");
		if (instance == null) throw new ArgumentNullException("instance");
		if (create == null) throw new ArgumentNullException("create");
		if (createRunner == null) throw new ArgumentNullException("createRunner");
		if (drop == null) throw new ArgumentNullException("drop");
		if (dropRunner == null) throw new ArgumentNullException("dropRunner");

		try
		{
			// Execute
			createRunner.perform(
				output,
				create,
				instance);

			// Verify
			assertEquals("databaseExists", true, instance.databaseExists());
		}
		finally
		{
			dropRunner.perform(
				output,
				drop,
				instance);
		}
	}

	public abstract void tableExistsForExistentTable() throws MigrationFailedException;

	protected void tableExistsForExistentTable(
		PrintStream output,
		DatabaseInstance instance,
		Migration createDatabase,
		MigrationPlugin createDatabaseRunner,
		Migration createTable,
		MigrationPlugin createTableRunner,
		Migration dropDatabase,
		MigrationPlugin dropDatabaseRunner) throws
		MigrationFailedException
	{
		if (output == null) throw new ArgumentNullException("output");
		if (instance == null) throw new ArgumentNullException("instance");
		if (createDatabase == null) throw new ArgumentNullException("createDatabase");
		if (createDatabaseRunner == null) throw new ArgumentNullException("createDatabaseRunner");
		if (createTable == null) throw new ArgumentNullException("createTable");
		if (createTableRunner == null) throw new ArgumentNullException("createTableRunner");
		if (dropDatabase == null) throw new ArgumentNullException("dropDatabase");
		if (dropDatabaseRunner == null) throw new ArgumentNullException("dropDatabaseRunner");

		// Setup
		AnsiSqlTableExistsAssertion tableExists = new AnsiSqlTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"sch",
			"tbl");

		try
		{
			createDatabaseRunner.perform(
				output,
				createDatabase,
				instance);

			createTableRunner.perform(
				output,
				createTable,
				instance);

			// Execute
			AssertionResponse response = tableExists.perform(instance);

			// Verify
			assertNotNull("response", response);
			assertEquals("response.message", "Table tbl exists in schema sch", response.getMessage());
			assertTrue("respnse.result", response.getResult());
		}
		finally
		{
			dropDatabaseRunner.perform(
				output,
				dropDatabase,
				instance);
		}
	}

	public abstract void tableExistsForNonExistentTable() throws MigrationFailedException;

	protected void tableExistsForNonExistentTable(
		PrintStream output,
		DatabaseInstance instance,
		Migration createDatabase,
		MigrationPlugin createDatabaseRunner,
		Migration dropDatabase,
		MigrationPlugin dropDatabaseRunner) throws
		MigrationFailedException
	{
		if (output == null) throw new ArgumentNullException("output");
		if (instance == null) throw new ArgumentNullException("instance");
		if (createDatabase == null) throw new ArgumentNullException("createDatabase");
		if (createDatabaseRunner == null) throw new ArgumentNullException("createDatabaseRunner");
		if (dropDatabase == null) throw new ArgumentNullException("dropDatabase");
		if (dropDatabaseRunner == null) throw new ArgumentNullException("dropDatabaseRunner");

		// Setup
		AnsiSqlTableExistsAssertion tableExists = new AnsiSqlTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"sch",
			"tbl");

		try
		{
			createDatabaseRunner.perform(
				output,
				createDatabase,
				instance);

			// Execute
			AssertionResponse response = tableExists.perform(instance);

			// Verify
			assertNotNull("response", response);
			assertEquals("response.message", "Table tbl does not exist in schema sch", response.getMessage());
			assertFalse("respnse.result", response.getResult());
		}
		finally
		{
			dropDatabaseRunner.perform(
				output,
				dropDatabase,
				instance);
		}
	}
}
