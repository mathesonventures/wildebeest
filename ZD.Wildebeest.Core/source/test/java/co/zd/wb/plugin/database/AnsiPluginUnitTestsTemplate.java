package co.zd.wb.plugin.database;

import co.zd.wb.AssertionResponse;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.plugin.ansisql.AnsiSqlDatabaseInstance;
import co.zd.wb.plugin.ansisql.AnsiSqlTableExistsAssertion;
import java.util.UUID;
import junit.framework.Assert;

public abstract class AnsiPluginUnitTestsTemplate
{
	public abstract void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException;

	protected void ansiSqlCreateDatabaseMigrationSucceeds(
		AnsiSqlDatabaseInstance db,
		Migration create,
		Migration drop) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		if (create == null) { throw new IllegalArgumentException("create cannot be null"); }
		if (drop == null) { throw new IllegalArgumentException("drop cannot be null"); }
		
		try
		{
			// Execute
			create.perform(db);

			// Verify
			Assert.assertEquals("databaseExists", true, db.databaseExists());

		}
		finally
		{
			drop.perform(db);
		}
	}
	
	public abstract void tableExistsForExistentTable() throws MigrationFailedException;
	
	protected void tableExistsForExistentTable(
		DatabaseInstance db,
		Migration createDatabase,
		Migration createTable,
		Migration dropDatabase) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		if (createDatabase == null) { throw new IllegalArgumentException("createDatabase cannot be null"); }
		if (createTable == null) { throw new IllegalArgumentException("createTable cannot be null"); }
		if (dropDatabase == null) { throw new IllegalArgumentException("dropDatabase cannot be null"); }
		
		// Setup
		AnsiSqlTableExistsAssertion tableExists = new AnsiSqlTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"sch",
			"tbl");
		
		try
		{
			createDatabase.perform(db);
			createTable.perform(db);
		
			// Execute
			AssertionResponse response = tableExists.perform(db);

			// Verify
			Assert.assertNotNull("response", response);
			Assert.assertEquals("response.message", "Table tbl exists in schema sch", response.getMessage());
			Assert.assertTrue("respnse.result", response.getResult());
		}
		finally
		{
			dropDatabase.perform(db);
		}
		
	}
	
	public abstract void tableExistsForNonExistentTable() throws MigrationFailedException;
	
	protected void tableExistsForNonExistentTable(
		DatabaseInstance db,
		Migration createDatabase,
		Migration dropDatabase) throws MigrationFailedException
	{
		if (db == null) { throw new IllegalArgumentException("db cannot be null"); }
		if (createDatabase == null) { throw new IllegalArgumentException("createDatabase cannot be null"); }
		if (dropDatabase == null) { throw new IllegalArgumentException("dropDatabase cannot be null"); }
		
		// Setup
		AnsiSqlTableExistsAssertion tableExists = new AnsiSqlTableExistsAssertion(
			UUID.randomUUID(),
			0,
			"sch",
			"tbl");
		
		try
		{
			createDatabase.perform(db);
		
			// Execute
			AssertionResponse response = tableExists.perform(db);

			// Verify
			Assert.assertNotNull("response", response);
			Assert.assertEquals("response.message", "Table tbl does not exist in schema sch", response.getMessage());
			Assert.assertFalse("respnse.result", response.getResult());
		}
		finally
		{
			dropDatabase.perform(db);
		}
	}
}
