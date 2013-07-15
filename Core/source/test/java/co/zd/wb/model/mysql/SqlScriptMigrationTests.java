package co.zd.wb.model.mysql;

import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.database.SqlScriptMigration;
import co.zd.wb.model.MigrationFailedException;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.UUID;
import org.junit.Test;

public class SqlScriptMigrationTests
{
	public SqlScriptMigrationTests()
	{
	}
	
	@Test
	public void performSuccessfully() throws MigrationFailedException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test",
			MySqlElementFixtures.stmStateCreateTableStatement());
		f.setUp();
		
		SqlScriptMigration migration = new SqlScriptMigration(
			UUID.randomUUID(),
			null,
			UUID.randomUUID(),
			MySqlElementFixtures.realmTypeRefCreateTableStatement());
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());
		
		//
		// Execute
		//

		try
		{
			migration.perform(instance);
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//
		
	}
}