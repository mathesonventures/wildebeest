// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.plugin.database;

import co.zd.wb.Assertion;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.fixturecreator.FixtureCreator;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.MigrationBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.dom.DomResourceLoader;
import co.zd.wb.service.dom.database.DatabaseExistsDomAssertionBuilder;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomResourceBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit tests for the DOM persistence services for core database plugins.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class DatabaselDomServiceUnitTests
{
	@Test public void databaseExistsAssertionLoadFromValidDocumentSucceeds() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID assertionId = UUID.randomUUID();
		
		String xml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", UUID.randomUUID(), "Product Catalogue Database")
				.state(UUID.randomUUID(), null)
					.assertion("DatabaseExists", assertionId)
			.render();

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("DatabaseExists", new DatabaseExistsDomAssertionBuilder());
		
		DomResourceLoader loader = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			new HashMap<String, MigrationBuilder>(),
			xml);
		
		//
		// Execute
		//
		
		Resource resource = loader.load();
		
		//
		// Verify
		//
		
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		Assertion assertion = resource.getStates().get(0).getAssertions().get(0);
		DatabaseExistsAssertion assertionT = ModelExtensions.As(assertion, DatabaseExistsAssertion.class);
		Assert.assertNotNull("expected to be DatabaseExistsAssertion", assertionT);
		
		Assert.assertEquals("assertion.assertionId", assertionId, assertion.getAssertionId());
		
	}
}
