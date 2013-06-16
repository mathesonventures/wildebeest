package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.SqlScriptTransition;
import co.mv.stm.TransitionFailedException;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.UUID;
import org.junit.Test;

public class SqlScriptTransitionTests
{
	public SqlScriptTransitionTests()
	{
	}
	
	@Test
	public void performSuccessfully() throws TransitionFailedException
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
		
		SqlScriptTransition tr = new SqlScriptTransition(
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
			tr.perform(instance);
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