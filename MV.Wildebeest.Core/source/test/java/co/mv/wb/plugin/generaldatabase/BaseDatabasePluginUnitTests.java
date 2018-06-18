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

public abstract class BaseDatabasePluginUnitTests
{
	public abstract void databaseExistsAssertionForExistentDatabase() throws MigrationFailedException;

	protected void databaseExistsAssertionForExistentDatabase(
		PrintStream output,
		DatabaseInstance instance,
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

		//
		// Setup
		//

		DatabaseExistsAssertion databaseExists = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);

		try
		{
			// Use the migration to create the database
			createRunner.perform(
				output,
				create,
				instance);

			//
			// Execute
			//

			AssertionResponse response = databaseExists.perform(instance);

			//
			// Verify
			//

			assertNotNull("response", response);
			assertEquals(
				"response.message",
				"Database " + instance.getDatabaseName() + " exists",
				response.getMessage());
			assertTrue("respnse.result", response.getResult());

		}
		finally
		{
			dropRunner.perform(
				output,
				drop,
				instance);
		}
	}

	public abstract void databaseExistsAssertionForNonExistentDatabase() throws MigrationFailedException;

	protected void databaseExistsAssertionForNonExistentDatabase(
		DatabaseInstance db) throws MigrationFailedException
	{
		if (db == null) throw new ArgumentNullException("db");

		// Setup
		DatabaseExistsAssertion databaseExists = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);

		// Execute
		AssertionResponse response = databaseExists.perform(db);

		// Verify
		assertNotNull("response", response);
		assertEquals(
			"response.message",
			"Database " + db.getDatabaseName() + " does not exist",
			response.getMessage());
		assertFalse("respnse.result", response.getResult());
	}

	public abstract void databaseDoesNotExistAssertionForExistentDatabase() throws MigrationFailedException;

	protected void databaseDoesNotExistAssertionForExistentDatabase(
		PrintStream output,
		DatabaseInstance instance,
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

		// Setup
		DatabaseDoesNotExistAssertion databaseExists = new DatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);

		try
		{
			createRunner.perform(
				output,
				create,
				instance);

			// Execute
			AssertionResponse response = databaseExists.perform(instance);

			// Verify
			assertNotNull("response", response);
			assertEquals(
				"response.message",
				"Database " + instance.getDatabaseName() + " exists",
				response.getMessage());
			assertFalse("respnse.result", response.getResult());
		}
		finally
		{
			dropRunner.perform(
				output,
				drop,
				instance);
		}
	}

	public abstract void databaseDoesNotExistAssertionForNonExistentDatabase() throws MigrationFailedException;

	protected void databaseDoesNotExistAssertionForNonExistentDatabase(
		DatabaseInstance db) throws MigrationFailedException
	{
		if (db == null) throw new ArgumentNullException("db");

		// Setup
		DatabaseDoesNotExistAssertion databaseExists = new DatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);

		// Execute
		AssertionResponse response = databaseExists.perform(db);

		// Verify
		assertNotNull("response", response);
		assertEquals(
			"response.message",
			"Database " + db.getDatabaseName() + " does not exist",
			response.getMessage());
		assertTrue("respnse.result", response.getResult());
	}
}
