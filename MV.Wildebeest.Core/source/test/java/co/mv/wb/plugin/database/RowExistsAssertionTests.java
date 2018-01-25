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

package co.mv.wb.plugin.database;

import co.mv.wb.AssertionResponse;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;
import co.mv.wb.plugin.mysql.MySqlElementFixtures;
import co.mv.wb.plugin.mysql.MySqlProperties;
import co.mv.wb.plugin.mysql.MySqlUtil;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class RowExistsAssertionTests
{
	@Test public void applyForNonExistentRowFails()
	{
		// Setup
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		String databaseName = MySqlUtil.createDatabase(
			mySqlProperties,
			"stm_test",
			MySqlElementFixtures.productCatalogueDatabase());
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);
		
		RowExistsAssertion assertion = new RowExistsAssertion(
			UUID.randomUUID(),
			"ProductType HW Exists",
			0,
			"SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';");
		
		// Execute
		AssertionResponse response = null;
		try
		{
			response = assertion.perform(instance);
		}
		finally
		{
			MySqlUtil.dropDatabase(mySqlProperties, databaseName);
		}
		
		// Verify
		Assert.assertNotNull("response", response);
		Assert.assertEquals("response.result", false, response.getResult());
		Assert.assertEquals("response.message", "Expected to find exactly one row, but found 0", response.getMessage());
	}
	
	@Test public void applyForExistentRowSucceeds()
	{
		// Setup
		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		String databaseName = MySqlUtil.createDatabase(
			mySqlProperties,
			"stm_test",
			MySqlElementFixtures.productCatalogueDatabase() +
				MySqlElementFixtures.productTypeRows());
		
		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);
		
		RowExistsAssertion assertion = new RowExistsAssertion(
			UUID.randomUUID(),
			"ProductType HW Exists",
			0,
			"SELECT * FROM ProductType WHERE ProductTypeCode = 'HW';");
		
		//
		// Execute
		//

		AssertionResponse response = null;
		try
		{
			response = assertion.perform(instance);
		}
		finally
		{
			MySqlUtil.dropDatabase(mySqlProperties, databaseName);
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
