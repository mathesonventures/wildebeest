package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.TransitionFailedException;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.sql.SQLException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class MySqlCreateDatabaseTransitionTests
{
	@Test public void performForNonExistantDatabaseSucceeds() throws
		TransitionFailedException,
		SQLException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlCreateDatabaseTransition tr = new MySqlCreateDatabaseTransition(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		String databaseName = MySqlElementFixtures.databaseName("StmTest");

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName);

		//
		// Execute
		//
		
		tr.perform(instance);
		
		//
		// Assert Results
		//

		//
		// Tear-Down
		//

		MySqlUtil.dropDatabase(instance, databaseName);
		
	}

	@Test public void performForExistantDatabaseFails()
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
			"");
		f.setUp();
		
		MySqlCreateDatabaseTransition tr = new MySqlCreateDatabaseTransition(
			UUID.randomUUID(),
			null,
			UUID.randomUUID());

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());

		//
		// Execute
		//

		TransitionFailedException caught = null;
		
		try
		{
			tr.perform(instance);
			
			Assert.fail("TransitionFailedException expected");
		}
		catch (TransitionFailedException e)
		{
			caught = e;
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//

		Assert.assertEquals(
			"caught.message",
			String.format("database \"%s\" already exists",	f.getDatabaseName()),
			caught.getMessage());
		
	}
}
