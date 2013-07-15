package co.zd.wb.model.database;

import co.zd.wb.model.database.RowExistsAssertion;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.mysql.MySqlElementFixtures;
import co.zd.wb.model.mysql.MySqlProperties;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class RowExistsAssertionTests
{
	@Test public void applyForNonExistentRowFails()
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
			MySqlElementFixtures.realmTypeRefCreateTableStatement());
		f.setUp();
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());
		
		RowExistsAssertion assertion = new RowExistsAssertion(
			UUID.randomUUID(),
			"UserBase RealmTypeRef Exists",
			0,
			"SELECT * FROM RealmTypeRef WHERE RealmTypeRcd = 'UB';");
		
		//
		// Execute
		//

		AssertionResponse response = null;
		try
		{
			response = assertion.apply(instance);
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//
		
		Assert.assertNotNull("response", response);
		Assert.assertEquals("response.result", false, response.getResult());
		Assert.assertEquals("response.message", "Expected to find exactly one row, but found 0", response.getMessage());
		
	}
	
	@Test public void applyForExistentRowSucceeds()
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
			MySqlElementFixtures.realmTypeRefCreateTableStatement() +
			MySqlElementFixtures.realmTypeRefInsertUserBaseRow());
		f.setUp();
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());
		
		RowExistsAssertion assertion = new RowExistsAssertion(
			UUID.randomUUID(),
			"UserBase RealmTypeRef Exists",
			0,
			"SELECT * FROM RealmTypeRef WHERE RealmTypeRcd = 'UB';");
		
		//
		// Execute
		//

		AssertionResponse response = null;
		try
		{
			response = assertion.apply(instance);
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//
		
		Assert.assertNotNull("response", response);
		Assert.assertEquals("response.result", true, response.getResult());
		Assert.assertEquals("response.message", "Exactly one row exists, as expected", response.getMessage());
		
	}
	
	@Ignore @Test public void applyForNonExistentTableFails()
	{
		throw new UnsupportedOperationException();
	}
}