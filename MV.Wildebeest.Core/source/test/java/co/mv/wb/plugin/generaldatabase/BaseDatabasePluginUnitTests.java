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
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class BaseDatabasePluginUnitTests
{
	public abstract void databaseExistsAssertionForExistentDatabase() throws MigrationFailedException;

	protected void databaseExistsAssertionForExistentDatabase(
		EventSink eventSink,
		DatabaseInstance instance,
		Migration create,
		MigrationPlugin createRunner,
		Migration drop,
		MigrationPlugin dropRunner) throws MigrationFailedException
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (instance == null) throw new ArgumentNullException("instance");
		if (create == null) throw new ArgumentNullException("create");
		if (createRunner == null) throw new ArgumentNullException("createRunner");
		if (drop == null) throw new ArgumentNullException("drop");
		if (dropRunner == null) throw new ArgumentNullException("dropRunner");

		//
		// Setup
		//

		DatabaseExistsAssertion assertion = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);

		DatabaseExistsAssertionPlugin plugin = new DatabaseExistsAssertionPlugin();

		try
		{
			// Use the migration to create the database
			createRunner.perform(
				eventSink,
				create,
				instance);

			//
			// Execute
			//

			AssertionResponse response = plugin.perform(
				assertion,
				instance);

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
				eventSink,
				drop,
				instance);
		}
	}

	public abstract void databaseExistsAssertionForNonExistentDatabase() throws MigrationFailedException;

	protected void databaseExistsAssertionForNonExistentDatabase(
		DatabaseInstance instance) throws MigrationFailedException
	{
		if (instance == null) throw new ArgumentNullException("instance");

		// Setup
		DatabaseExistsAssertion assertion = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);

		DatabaseExistsAssertionPlugin plugin = new DatabaseExistsAssertionPlugin();

		// Execute
		AssertionResponse response = plugin.perform(
			assertion,
			instance);

		// Verify
		assertNotNull("response", response);
		assertEquals(
			"response.message",
			"Database " + instance.getDatabaseName() + " does not exist",
			response.getMessage());
		assertFalse("respnse.result", response.getResult());
	}

	public abstract void databaseDoesNotExistAssertionForExistentDatabase() throws MigrationFailedException;

	protected void databaseDoesNotExistAssertionForExistentDatabase(
		EventSink eventSink,
		DatabaseInstance instance,
		Migration create,
		MigrationPlugin createRunner,
		Migration drop,
		MigrationPlugin dropRunner) throws MigrationFailedException
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (instance == null) throw new ArgumentNullException("instance");
		if (create == null) throw new ArgumentNullException("create");
		if (createRunner == null) throw new ArgumentNullException("createRunner");
		if (drop == null) throw new ArgumentNullException("drop");
		if (dropRunner == null) throw new ArgumentNullException("dropRunner");

		// Setup
		DatabaseDoesNotExistAssertion assertion = new DatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);

		DatabaseDoesNotExistAssertionPlugin plugin = new DatabaseDoesNotExistAssertionPlugin();

		try
		{
			createRunner.perform(
				eventSink,
				create,
				instance);

			// Execute
			AssertionResponse response = plugin.perform(
				assertion,
				instance);

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
				eventSink,
				drop,
				instance);
		}
	}

	public abstract void databaseDoesNotExistAssertionForNonExistentDatabase() throws MigrationFailedException;

	protected void databaseDoesNotExistAssertionForNonExistentDatabase(
		DatabaseInstance instance) throws MigrationFailedException
	{
		if (instance == null) throw new ArgumentNullException("instance");

		// Setup
		DatabaseDoesNotExistAssertion assertion = new DatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);

		DatabaseDoesNotExistAssertionPlugin plugin = new DatabaseDoesNotExistAssertionPlugin();

		// Execute
		AssertionResponse response = plugin.perform(
			assertion,
			instance);

		// Verify
		assertNotNull("response", response);
		assertEquals(
			"response.message",
			"Database " + instance.getDatabaseName() + " does not exist",
			response.getMessage());
		assertTrue("respnse.result", response.getResult());
	}
}
