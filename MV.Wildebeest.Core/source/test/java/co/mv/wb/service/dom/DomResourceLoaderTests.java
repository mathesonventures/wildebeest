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

package co.mv.wb.service.dom;

import co.mv.wb.fake.DomFakeMigrationBuilder;
import co.mv.wb.fake.DomFakeResourcePluginBuilder;
import co.mv.wb.fake.DomFakeAssertionBuilder;
import co.mv.wb.Asserts;
import co.mv.wb.Resource;
import co.mv.wb.fixturecreator.FixtureCreator;
import co.mv.wb.fake.FakeAssertion;
import co.mv.wb.fake.FakeResourcePlugin;
import co.mv.wb.fake.FakeMigration;
import co.mv.wb.service.AssertionBuilder;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.ResourcePluginBuilder;
import co.mv.wb.service.MigrationBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import static org.junit.Assert.*;
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

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 0, resource.getStates().size());
		
		// Migrations
		assertEquals("resource.migration.size", 0, resource.getMigrations().size());
		
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
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
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

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(stateId, resource.getStates().get(0), "state[0]");
		
		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
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

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 2, resource.getStates().size());
		Asserts.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		Asserts.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
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

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		
		Asserts.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		Asserts.assertFakeAssertion(
			assertion1Id, "Tag is Foo", 0, "Foo",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		
		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
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

		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		
		Asserts.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		assertEquals(
			"resource.states[0].assertions.size",
			2,
			resource.getStates().get(0).getAssertions().size());
		Asserts.assertFakeAssertion(
			assertion1Id, "Tag is Foo", 0, "Foo",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		Asserts.assertFakeAssertion(
			assertion2Id, "Tag is Bar", 1, "Bar",
			(FakeAssertion)resource.getStates().get(0).getAssertions().get(1),
			"resource.states[0].assertions[1]");
		
		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
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
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.of(state1Id), Optional.empty(), "Blah",
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
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.empty(), Optional.of(state1Id), "Blah",
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
		
		Map<String, ResourcePluginBuilder> resourceBuilders = new HashMap<>();
		resourceBuilders.put("Fake", new DomFakeResourcePluginBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
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
		assertNotNull("resource", resource);
		Asserts.assertResource(FakeResourcePlugin.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		assertEquals("resource.states.size", 2, resource.getStates().size());
		Asserts.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		Asserts.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.of(state1Id), Optional.of(state2Id), "Blah",
			(FakeMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");
		
	}
}
