package co.zd.wb.postgresql;

import co.zd.wb.Instance;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.fixturecreator.FixtureCreator;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseResource;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.InstanceBuilder;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.MigrationBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.dom.DomInstanceLoader;
import co.zd.wb.service.dom.DomResourceLoader;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomInstanceBuilder;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomResourceBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class PostgreSqlDomServiceUnitTests
{
	@Test public void postgreSqlDatabaseResourceLoadFromValidDocumentSucceeds() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		String resourceName = "Foo";
		
		String resourceXml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", resourceId, resourceName)
			.render();
		
		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomResourceBuilder());
		
		DomResourceLoader loader = new DomResourceLoader(
			resourceBuilders,
			new HashMap<String, AssertionBuilder>(),
			new HashMap<String, MigrationBuilder>(),
			resourceXml);
		
		//
		// Execute
		//
		
		Resource resource = loader.load();
		
		//
		// Verify
		//
		
		Assert.assertNotNull("resource", resource);
		PostgreSqlDatabaseResource resourceT = ModelExtensions.As(resource, PostgreSqlDatabaseResource.class);
		Assert.assertNotNull("resource is not a PostgreSqlDatabaseResource", resourceT);
		
		Assert.assertEquals("resource.resourceId", resourceId, resource.getResourceId());
		Assert.assertEquals("resource.name", resourceName, resource.getName());
		
	}
	
	@Test public void postgreSqlDatabaseInstanceLoadFromValidDocumentSucceeds() throws MessagesException
	{
		
		//
		// Setup
		//
		
		StringBuilder xml = new StringBuilder();
		xml.append("<instance type=\"PostgreSqlDatabase\">\n")
			.append("<hostName>127.0.0.1</hostName>\n")
			.append("<port>5432</port>\n")
			.append("<adminUsername>wb</adminUsername>\n")
			.append("<adminPassword>password</adminPassword>\n")
			.append("<databaseName>ProductCatalogueStaging</databaseName>\n")
			.append("</instance>");

		Map<String, InstanceBuilder> instanceBuilders = new HashMap<String, InstanceBuilder>();
		instanceBuilders.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomInstanceBuilder());

		DomInstanceLoader loader = new DomInstanceLoader(
			instanceBuilders,
			xml.toString());
		
		//
		// Execute
		//
		
		Instance instance = loader.load();
		
		//
		// Verify
		//
		
		Assert.assertNotNull("instance", instance);
		PostgreSqlDatabaseInstance instanceT = ModelExtensions.As(instance, PostgreSqlDatabaseInstance.class);
		Assert.assertNotNull("instance must be of type PostgreSqlDatabaseInstance", instanceT);
		assertPostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"wb",
			"password",
			"ProductCatalogueStaging",
			instanceT,
			"instance");
		
	}
	
	private static void assertPostgreSqlDatabaseInstance(
		String expectedHostName,
		int expectedPort,
		String expectedAdminUsername,
		String expectedAdminPassword,
		String expectedDatabaseName,
		PostgreSqlDatabaseInstance actual,
		String name)
	{
		if (expectedHostName == null) { throw new IllegalArgumentException("expectedHostName cannot be null"); }
		if (expectedAdminUsername == null) { throw new IllegalArgumentException("expectedAdminUsername cannot be null"); }
		if (expectedAdminPassword == null) { throw new IllegalArgumentException("expectedAdminPassword cannot be null"); }
		if (expectedDatabaseName == null) { throw new IllegalArgumentException("expectedDatabaseName cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".hostName", expectedHostName, actual.getHostName());
		Assert.assertEquals(name + ".port", expectedPort, actual.getPort());
		Assert.assertEquals(name + ".adminUsername", expectedAdminUsername, actual.getAdminUsername());
		Assert.assertEquals(name + ".adminPassword", expectedAdminPassword, actual.getAdminPassword());
		Assert.assertEquals(name + ".databaseName", expectedDatabaseName, actual.getDatabaseName());
	}
}
