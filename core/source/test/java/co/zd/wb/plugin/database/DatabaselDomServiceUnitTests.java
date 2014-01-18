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
