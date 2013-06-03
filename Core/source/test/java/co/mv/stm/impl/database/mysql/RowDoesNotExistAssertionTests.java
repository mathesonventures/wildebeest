package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.RowDoesNotExistAssertion;
import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.AssertionResponse;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class RowDoesNotExistAssertionTests
{
	@Test public void applyForNonExistentRowSucceds() throws AssertionFailedException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm_test",
			MySqlElementFixtures.realmTypeRefCreateTableStatement());
		f.setUp();
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			f.getDatabaseName());
		
		RowDoesNotExistAssertion assertion = new RowDoesNotExistAssertion(
			UUID.randomUUID(),
			"UserBase RealmTypeFef Exists",
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
		Assert.assertEquals("response.message", "Row does not exist, as expected", response.getMessage());
		
	}
	
	@Test public void applyForExistentRowFails()
	{
		
		//
		// Fixture Setup
		//
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm_test",
			MySqlElementFixtures.realmTypeRefCreateTableStatement() +
			MySqlElementFixtures.realmTypeRefInsertUserBaseRow());
		f.setUp();
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			f.getDatabaseName());
		
		RowDoesNotExistAssertion assertion = new RowDoesNotExistAssertion(
			UUID.randomUUID(),
			"UserBase RealmTypeFef Exists",
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
		Assert.assertEquals("response.message", "Expected to find no rows but found 1", response.getMessage());
		
	}
	
	@Test public void applyForNonExistentTableFails()
	{
		throw new UnsupportedOperationException();
	}
}
