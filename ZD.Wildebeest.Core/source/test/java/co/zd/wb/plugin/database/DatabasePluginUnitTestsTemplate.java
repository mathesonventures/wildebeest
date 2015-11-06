package co.zd.wb.plugin.database;

import co.zd.wb.AssertionResponse;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import java.util.UUID;
import junit.framework.Assert;

public abstract class DatabasePluginUnitTestsTemplate
{
	public abstract void databaseExistsAssertionForExistentDatabase() throws MigrationFailedException;
	
	protected void databaseExistsAssertionForExistentDatabase(
		DatabaseInstance db,
		Migration create,
		Migration drop) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		if (create == null) { throw new IllegalArgumentException("create cannot be null"); }
		if (drop == null) { throw new IllegalArgumentException("drop cannot be null"); }
		
		//
		// Setup
		//
		
		DatabaseExistsAssertion databaseExists = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);
		
		try
		{
			// Use the migration to create the database
			create.perform(db);
		
			//
			// Execute
			//

			AssertionResponse response = databaseExists.perform(db);

			//
			// Verify
			//

			Assert.assertNotNull("response", response);
			Assert.assertEquals(
				"response.message",
				"Database " + db.getDatabaseName() + " exists",
				response.getMessage());
			Assert.assertTrue("respnse.result", response.getResult());
			
		}
		finally
		{
			drop.perform(db);
		}
		
	}
	
	public abstract void databaseExistsAssertionForNonExistentDatabase() throws MigrationFailedException;
	
	protected void databaseExistsAssertionForNonExistentDatabase(
		DatabaseInstance db) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		
		// Setup
		DatabaseExistsAssertion databaseExists = new DatabaseExistsAssertion(
			UUID.randomUUID(),
			0);

		// Execute
		AssertionResponse response = databaseExists.perform(db);

		// Verify
		Assert.assertNotNull("response", response);
		Assert.assertEquals(
			"response.message",
			"Database " + db.getDatabaseName() + " does not exist",
			response.getMessage());
		Assert.assertFalse("respnse.result", response.getResult());
	}
	
	public abstract void databaseDoesNotExistAssertionForExistentDatabase() throws MigrationFailedException;
	
	protected void databaseDoesNotExistAssertionForExistentDatabase(
		DatabaseInstance db,
		Migration create,
		Migration drop) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		if (create == null) { throw new IllegalArgumentException("create cannot be null"); }
		if (drop == null) { throw new IllegalArgumentException("drop cannot be null"); }
		
		// Setup
		DatabaseDoesNotExistAssertion databaseExists = new DatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);
		
		try
		{
			create.perform(db);
		
			// Execute
			AssertionResponse response = databaseExists.perform(db);

			// Verify
			Assert.assertNotNull("response", response);
			Assert.assertEquals(
				"response.message",
				"Database " + db.getDatabaseName() + " exists",
				response.getMessage());
			Assert.assertFalse("respnse.result", response.getResult());
		}
		finally
		{
			drop.perform(db);
		}
	}
	
	public abstract void databaseDoesNotExistAssertionForNonExistentDatabase() throws MigrationFailedException;
	
	protected void databaseDoesNotExistAssertionForNonExistentDatabase(
		DatabaseInstance db) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		
		// Setup
		DatabaseDoesNotExistAssertion databaseExists = new DatabaseDoesNotExistAssertion(
			UUID.randomUUID(),
			0);
		
		// Execute
		AssertionResponse response = databaseExists.perform(db);

		// Verify
		Assert.assertNotNull("response", response);
		Assert.assertEquals(
			"response.message",
			"Database " + db.getDatabaseName() + " does not exist",
			response.getMessage());
		Assert.assertTrue("respnse.result", response.getResult());
	}
}