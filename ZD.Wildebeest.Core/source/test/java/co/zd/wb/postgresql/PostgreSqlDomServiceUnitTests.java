// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.zd.wb.postgresql;

import co.zd.wb.FakeLogger;
import co.zd.wb.Instance;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.fixturecreator.FixtureCreator;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseResourcePlugin;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.dom.DomInstanceLoader;
import co.zd.wb.service.dom.DomPlugins;
import co.zd.wb.service.dom.DomResourceLoader;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the DOM persistence services for PostgreSQL plugins.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlDomServiceUnitTests
{
	@Test public void postgreSqlDatabaseResourceLoadFromValidDocumentSucceeds() throws MessagesException
	{
		// Setup
		UUID resourceId = UUID.randomUUID();
		String resourceName = "Foo";
		
		String resourceXml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", resourceId, resourceName)
			.render();
		
		DomResourceLoader loader = DomPlugins.resourceLoader(new FakeLogger(), resourceXml);
		
		// Execute
		Resource resource = loader.load();
		
		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.resourceId", resourceId, resource.getResourceId());
		Assert.assertEquals("resource.name", resourceName, resource.getName());
		
		PostgreSqlDatabaseResourcePlugin plugin = ModelExtensions.As(
			resource.getPlugin(),
			PostgreSqlDatabaseResourcePlugin.class);
		Assert.assertNotNull("plugin is not a PostgreSqlDatabaseResource", plugin);
	}
	
	@Test public void postgreSqlDatabaseInstanceLoadFromValidDocumentSucceeds() throws MessagesException
	{
		// Setup
		StringBuilder xml = new StringBuilder();
		xml.append("<instance type=\"PostgreSqlDatabase\">\n")
			.append("<hostName>127.0.0.1</hostName>\n")
			.append("<port>5432</port>\n")
			.append("<adminUsername>wb</adminUsername>\n")
			.append("<adminPassword>password</adminPassword>\n")
			.append("<databaseName>ProductCatalogueStaging</databaseName>\n")
			.append("</instance>");

		DomInstanceLoader loader = DomPlugins.instanceLoader(xml.toString());
		
		// Execute
		Instance instance = loader.load();
		
		// Verify
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
