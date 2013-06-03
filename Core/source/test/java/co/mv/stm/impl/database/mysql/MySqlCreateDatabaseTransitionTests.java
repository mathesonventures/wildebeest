package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionType;
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
		
		MySqlCreateDatabaseTransition tr = new MySqlCreateDatabaseTransition(
			UUID.randomUUID(),
			TransitionType.DatabaseSqlScript,
			null,
			UUID.randomUUID());

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm_test");

		//
		// Execute
		//
		
		tr.perform(instance);
		
		//
		// Assert Results
		//

		
		DatabaseHelper.execute(instance.getInfoDataSource(), "DROP Database `stm_test`");
		
	}

	@Test public void performForExistantDatabaseFails()
	{
		
		//
		// Fixture Setup
		//

		MySqlDatabaseFixture f = new MySqlDatabaseFixture("127.0.0.1", 3306, "root", "password", "stm_test", "");
		f.setUp();
		
		MySqlCreateDatabaseTransition tr = new MySqlCreateDatabaseTransition(
			UUID.randomUUID(),
			TransitionType.DatabaseSqlScript,
			null,
			UUID.randomUUID());

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
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
