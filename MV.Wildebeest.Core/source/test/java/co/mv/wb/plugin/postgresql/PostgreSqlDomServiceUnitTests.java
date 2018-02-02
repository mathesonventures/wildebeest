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

package co.mv.wb.plugin.postgresql;

import co.mv.wb.Instance;
import co.mv.wb.LoaderFault;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.WildebeestFactory;
import co.mv.wb.fixture.FixtureCreator;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.base.dom.DomInstanceLoader;
import co.mv.wb.plugin.base.dom.DomPlugins;
import co.mv.wb.plugin.base.dom.DomResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

/**
 * Unit tests for the DOM persistence services for PostgreSQL plugins.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class PostgreSqlDomServiceUnitTests
{
	@Test public void postgreSqlDatabaseResourceLoadFromValidDocumentSucceeds() throws
		LoaderFault,
		PluginBuildException
	{
		// Setup
		UUID resourceId = UUID.randomUUID();
		String resourceName = "Foo";
		
		String resourceXml = FixtureCreator.create()
			.resource(WildebeestFactory.PostgreSqlDatabase.getUri(), resourceId, resourceName)
			.render();
		
		DomResourceLoader loader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			resourceXml);
		
		// Execute
		Resource resource = loader.load(new File("."));
		
		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.resourceId", resourceId, resource.getResourceId());
		Assert.assertEquals("resource.name", resourceName, resource.getName());
	}
	
	@Test public void postgreSqlDatabaseInstanceLoadFromValidDocumentSucceeds() throws
		LoaderFault,
		PluginBuildException
	{
		// Setup
		StringBuilder xml = new StringBuilder();
		xml.append("<instance type=\"").append(WildebeestFactory.PostgreSqlDatabase.getUri()).append("\">\n")
			.append("<hostName>127.0.0.1</hostName>\n")
			.append("<port>5432</port>\n")
			.append("<adminUsername>wb</adminUsername>\n")
			.append("<adminPassword>password</adminPassword>\n")
			.append("<databaseName>WildebeestTest</databaseName>\n")
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
			"WildebeestTest",
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
