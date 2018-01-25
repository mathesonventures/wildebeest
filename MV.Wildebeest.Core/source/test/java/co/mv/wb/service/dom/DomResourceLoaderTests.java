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

package co.zd.wb.service.dom;

import co.zd.wb.fake.DomFakeMigrationBuilder;
import co.zd.wb.fake.DomFakeResourcePluginBuilder;
import co.zd.wb.fake.DomFakeAssertionBuilder;
import co.zd.wb.AssertExtensions;
import co.zd.wb.Resource;
import co.zd.wb.fixturecreator.FixtureCreator;
import co.zd.wb.fake.FakeAssertion;
import co.zd.wb.fake.FakeResourcePlugin;
import co.zd.wb.fake.FakeMigration;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.ResourcePluginBuilder;
import co.zd.wb.service.MigrationBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class DomResourceLoaderTests
{
	@Test public void loadResource() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
			.render();

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 0, resource.getStates().size());
		
		// Migrations
		Assert.assertEquals("resource.migration.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithLabel() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(stateId, "Foo")
			.render();
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithNoLabel() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(stateId, null)
			.render();

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(stateId, resource.getStates().get(0), "state[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForMultipleStates() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(state1Id, "Foo")
				.state(state2Id, "Bar")
			.render();

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 2, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithOneAssertion() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(stateId, "Foo")
					.assertion("Fake", assertion1Id).innerXml("<tag>Foo</tag>")
			.render();

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		
		AssertExtensions.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		AssertExtensions.assertFakeAssertion(
			assertion1Id, "Tag is Foo", 0, "Foo",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithMultipleAssertions() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		UUID assertion2Id = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(stateId, "Foo")
					.assertion("Fake", assertion1Id).innerXml("<tag>Foo</tag>")
					.assertion("Fake", assertion2Id).innerXml("<tag>Bar</tag>")
			.render();

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		
		AssertExtensions.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			2,
			resource.getStates().get(0).getAssertions().size());
		AssertExtensions.assertFakeAssertion(
			assertion1Id, "Tag is Foo", 0, "Foo",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		AssertExtensions.assertFakeAssertion(
			assertion2Id, "Tag is Bar", 1, "Bar",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(1),
			"resource.states[0].assertions[1]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForMigrationWithFromStateId() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(state1Id, "Foo")
				.migration("Fake", migrationId, state1Id, null).innerXml("<tag>Blah</tag>")
			.render();
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("Fake", new DomFakeMigrationBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AssertExtensions.assertFakeMigration(
			migrationId, state1Id, null, "Blah",
			(FakeMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");
		
	}
	
	@Test public void loadResourceForMigrationsWithToStateId() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(state1Id, "Foo")
				.migration("Fake", migrationId, null, state1Id).innerXml("<tag>Blah</tag>")
			.render();
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("Fake", new DomFakeMigrationBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AssertExtensions.assertFakeMigration(
			migrationId, null, state1Id, "Blah",
			(FakeMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");
		
	}
	
	@Test public void loadResourceForMigrationsWithFromStateIdAndToStateId() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("Fake", resourceId, "Product Catalogue Database")
				.state(state1Id, "Foo")
				.state(state2Id, "Bar")
				.migration("Fake", migrationId, state1Id, state2Id).innerXml("<tag>Blah</tag>")
			.render();
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<String, ResourcePluginBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("Fake", new DomFakeMigrationBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load(new File("."));
		
		//
		// Verify
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 2, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AssertExtensions.assertFakeMigration(
			migrationId, state1Id, state2Id, "Blah",
			(FakeMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");
		
	}
}
