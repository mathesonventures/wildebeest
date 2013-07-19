package co.zd.wb.model.database;

import co.zd.wb.model.AssertionFailedException;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.mysql.MySqlElementFixtures;
import co.zd.wb.model.mysql.MySqlProperties;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class RowDoesNotExistAssertionTests
{
	@Test public void applyForNonExistentRowSucceds() throws AssertionFailedException
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
			MySqlElementFixtures.productCatalogueDatabase());
		f.setUp();
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());
		
		RowDoesNotExistAssertion assertion = new RowDoesNotExistAssertion(
			UUID.randomUUID(),
			"HW ProductType Exists",
			0,
			"SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';");
		
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
		
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			"stm_test",
			MySqlElementFixtures.productCatalogueDatabase() +
			MySqlElementFixtures.productTypeRows());
		f.setUp();
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			f.getDatabaseName());
		
		RowDoesNotExistAssertion assertion = new RowDoesNotExistAssertion(
			UUID.randomUUID(),
			"HW ProductType Exists",
			0,
			"SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';");
		
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
	
	@Ignore @Test public void applyForNonExistentTableFails()
	{
		throw new UnsupportedOperationException();
	}
}
