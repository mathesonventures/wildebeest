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

package co.mv.wb.plugin.base.dom;

import co.mv.wb.AssertionBuilder;
import co.mv.wb.Asserts;
import co.mv.wb.ExpectException;
import co.mv.wb.FileLoadException;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationBuilder;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.WildebeestApi;
import co.mv.wb.XmlValidationException;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.fixture.Fixtures;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.impl.WildebeestApiBuilder;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.SetTagMigration;
import co.mv.wb.plugin.fake.TagAssertion;
import co.mv.wb.plugin.fake.dom.DomSetTagMigrationBuilder;
import co.mv.wb.plugin.fake.dom.DomTagAssertionBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link DomResourceLoader}.
 *
 * @since 1.0
 */
public class DomResourceLoaderTests
{
	private static final Logger LOG = LoggerFactory.getLogger(DomResourceLoaderTests.class);

	@Test
	public void loadResource() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.build();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceLoader = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//

		Resource resource = resourceLoader.load(new File("."));

		//
		// Verify
		//

		// Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			resourceId,
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			0,
			resource.getStates().size());

		// Migrations
		assertEquals(
			"resource.migration.size",
			0,
			resource.getMigrations().size());

	}

	@Test
	public void loadResource_stateWithName_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(stateId, "Foo")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceLoader = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
			assertionBuilders,
			migrationBuilders,
			resourceXml);

		//
		// Execute
		//

		Resource resource = resourceLoader.load(new File("."));

		//
		// Verify
		//

		// Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			resourceId,
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			1,
			resource.getStates().size());
		Asserts.assertState(
			stateId,
			Optional.of("Foo"),
			resource.getStates().get(0),
			"state[0]");

		// Migrations
		assertEquals(
			"resource.migrations.size",
			0,
			resource.getMigrations().size());

	}

	@Test
	public void loadResource_stateWithNoName_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(stateId, null)
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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

		Asserts.assertResource(
			resourceId,
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			1,
			resource.getStates().size());
		Asserts.assertState(
			stateId,
			Optional.empty(),
			resource.getStates().get(0),
			"resource.state[0]");

		// Migrations
		assertEquals(
			"resource.migrations.size",
			0,
			resource.getMigrations().size());

	}

	@Test
	public void loadResource_stateWithNameAndDescription_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(stateId, "Some random name", "Some random test description")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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

		Asserts.assertResource(
			resourceId,
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			1,
			resource.getStates().size());
		Asserts.assertState(
			stateId,
			Optional.of("Some random name"),
			Optional.of("Some random test description"),
			resource.getStates().get(0),
			"resource.state[0]");

		// Migrations
		assertEquals(
			"resource.migrations.size",
			0,
			resource.getMigrations().size());

	}

	@Test
	public void loadResource_resourceWithMultipleStates_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.state(state2Id, "Bar")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(
			resourceId,
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			2,
			resource.getStates().size());
		Asserts.assertState(
			state1Id,
			Optional.of("Foo"),
			resource.getStates().get(0),
			"resource.state[0]");
		Asserts.assertState(
			state2Id,
			Optional.of("Bar"),
			resource.getStates().get(1),
			"resource.state[1]");

		// Migrations
		assertEquals(
			"resource.migrations.size",
			0,
			resource.getMigrations().size());

	}

	@Test
	public void loadResource_stateWithOneAssertion_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(stateId, "Foo")
			.assertion(FakeConstants.Fake.getUri(), assertion1Id).withInnerXml("<tag>Foo</tag>")
			.build();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		assertionBuilders.put(FakeConstants.Fake.getUri(), new DomTagAssertionBuilder());

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(
			resourceId,
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());

		Asserts.assertState(stateId, Optional.of("Foo"), resource.getStates().get(0), "state[0]");
		assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		Asserts.assertTagAssertion(
			assertion1Id, 0, "Foo",
			(TagAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");

		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());

	}

	@Test
	public void loadResource_stateWithMultipleAssertions_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		UUID assertion2Id = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(stateId, "Foo")
			.assertion(FakeConstants.Fake.getUri(), assertion1Id).withInnerXml("<tag>Foo</tag>")
			.assertion(FakeConstants.Fake.getUri(), assertion2Id).withInnerXml("<tag>Bar</tag>")
			.build();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		assertionBuilders.put(FakeConstants.Fake.getUri(), new DomTagAssertionBuilder());

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());

		Asserts.assertState(stateId, Optional.of("Foo"), resource.getStates().get(0), "state[0]");
		assertEquals(
			"resource.states[0].assertions.size",
			2,
			resource.getStates().get(0).getAssertions().size());
		Asserts.assertTagAssertion(
			assertion1Id, 0, "Foo",
			(TagAssertion)resource.getStates().get(0).getAssertions().get(0),
			"resource.states[0].assertions[0]");
		Asserts.assertTagAssertion(
			assertion2Id, 1, "Bar",
			(TagAssertion)resource.getStates().get(0).getAssertions().get(1),
			"resource.states[0].assertions[1]");

		// Migrations
		assertEquals("resource.migrations.size", 0, resource.getMigrations().size());

	}

	@Test
	public void loadResource_migrationWithFromStateAsId_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.migration(FakeConstants.Fake.getUri(), migrationId, state1Id.toString(), null)
			.withInnerXml("<tag>Blah</tag>")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		migrationBuilders.put(FakeConstants.Fake.getUri(), new DomSetTagMigrationBuilder());

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(state1Id, Optional.of("Foo"), resource.getStates().get(0), "resource.state[0]");

		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.of(state1Id.toString()), Optional.empty(), "Blah",
			(SetTagMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");

	}


	@Test
	public void loadResource_migrationWithFromStateAsName_succeeds() throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.migration(FakeConstants.Fake.getUri(), migrationId, "Foo", null).withInnerXml("<tag>Blah</tag>")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		migrationBuilders.put(FakeConstants.Fake.getUri(), new DomSetTagMigrationBuilder());

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(state1Id, Optional.of("Foo"), resource.getStates().get(0), "resource.state[0]");

		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.of("Foo"), Optional.empty(), "Blah",
			(SetTagMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");

	}

	@Test
	public void loadResource_migrationsWithToStateAsId_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.migration(FakeConstants.Fake.getUri(), migrationId, null, state1Id.toString())
			.withInnerXml("<tag>Blah</tag>")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		migrationBuilders.put(FakeConstants.Fake.getUri(), new DomSetTagMigrationBuilder());

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(state1Id, Optional.of("Foo"), resource.getStates().get(0), "resource.state[0]");

		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.empty(), Optional.of(state1Id.toString()), "Blah",
			(SetTagMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");

	}


	@Test
	public void loadResource_migrationsWithToStateAsName_succeeds() throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.migration(FakeConstants.Fake.getUri(), migrationId, null, "Foo").withInnerXml("<tag>Blah</tag>")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		migrationBuilders.put(FakeConstants.Fake.getUri(), new DomSetTagMigrationBuilder());

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 1, resource.getStates().size());
		Asserts.assertState(state1Id, Optional.of("Foo"), resource.getStates().get(0), "resource.state[0]");

		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.empty(), Optional.of("Foo"), "Blah",
			(SetTagMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");

	}

	@Test
	public void loadResource_migrationsWithFromStateAndToStateAsIds_succeeds() throws
		LoaderFault,
		InvalidReferenceException,
		PluginBuildException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.state(state2Id, "Bar")
			.migration(FakeConstants.Fake.getUri(), migrationId, state1Id.toString(), state2Id.toString())
			.withInnerXml("<tag>Blah</tag>")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		migrationBuilders.put(FakeConstants.Fake.getUri(), new DomSetTagMigrationBuilder());

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 2, resource.getStates().size());
		Asserts.assertState(state1Id, Optional.of("Foo"), resource.getStates().get(0), "resource.state[0]");
		Asserts.assertState(state2Id, Optional.of("Bar"), resource.getStates().get(1), "resource.state[1]");

		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.of(state1Id.toString()), Optional.of(state2Id.toString()), "Blah",
			(SetTagMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");

	}

	@Test
	public void loadResource_migrationsWithFromStateAndToStateAsName_succeeds() throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{

		//
		// Setup
		//

		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();

		String resourceXml = Fixtures
			.resourceXmlBuilder()
			.resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
			.state(state1Id, "Foo")
			.state(state2Id, "Bar")
			.migration(FakeConstants.Fake.getUri(), migrationId, "Foo", "Bar").withInnerXml("<tag>Blah</tag>")
			.render();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();

		Map<String, MigrationBuilder> migrationBuilders = new HashMap<>();
		migrationBuilders.put(FakeConstants.Fake.getUri(), new DomSetTagMigrationBuilder());

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.with(FakeConstants.Fake)
				.build(),
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
		Asserts.assertResource(resourceId, "Product Catalogue Database", resource, "resource");

		// States
		assertEquals("resource.states.size", 2, resource.getStates().size());
		Asserts.assertState(state1Id, Optional.of("Foo"), resource.getStates().get(0), "resource.state[0]");
		Asserts.assertState(state2Id, Optional.of("Bar"), resource.getStates().get(1), "resource.state[1]");

		// Migrations
		assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		Asserts.assertFakeMigration(
			migrationId, Optional.of("Foo"), Optional.of("Bar"), "Blah",
			(SetTagMigration)resource.getMigrations().get(0),
			"resource.migrations[0]");

	}

	@Test
	public void loadResource_validMysqlAssertionGroup_succeeds() throws
		InvalidReferenceException,
		LoaderFault,
		FileLoadException,
		PluginBuildException,
		XmlValidationException
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "MySqlDatabase/database.wbresources.uses.assertionGroup.xml";

		// Execute
		Resource resource = wildebeestApi.loadResource(new File(resourceFilePath));

		// Verify Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			UUID.fromString("0d39b8fb-5b5c-48cd-845c-9c4d55f94303"),
			"Product Catalogue Database",
			resource,
			"resource");

		// Verify States
		assertEquals(
			"resource.states.size",
			2,
			resource.getStates().size());

		// Verify Assertions
		assertEquals(
			"resource.state[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		assertEquals(
			"resource.state[1].assertions.size",
			6,
			resource.getStates().get(1).getAssertions().size());
	}

	@Test
	public void loadResource_validPostgreAssertionGroup_succeeds() throws
		InvalidReferenceException,
		LoaderFault,
		FileLoadException,
		PluginBuildException,
		XmlValidationException
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "PostgreSqlDatabase/database.wbresources.uses.assertionGroup.xml";

		// Execute
		Resource resource = wildebeestApi.loadResource(new File(resourceFilePath));

		// Verify Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			UUID.fromString("38d0eabd-ab40-4c37-96a7-fcacb43bd059"),
			"Product Catalogue Database",
			resource,
			"resource");

		// Verify States
		assertEquals(
			"resource.states.size",
			3,
			resource.getStates().size());

		// Verify Assertions
		assertEquals(
			"resource.state[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		assertEquals(
			"resource.state[1].assertions.size",
			0,
			resource.getStates().get(1).getAssertions().size());
		assertEquals(
			"resource.state[2].assertions.size",
			1,
			resource.getStates().get(2).getAssertions().size());
	}

	@Test
	public void loadResource_validSqlServerAssertionGroup_succeeds() throws
		InvalidReferenceException,
		LoaderFault,
		FileLoadException,
		PluginBuildException,
		XmlValidationException
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "SqlServerDatabase/database.wbresources.uses.assertionGroup.xml";

		// Execute
		Resource resource = wildebeestApi.loadResource(new File(resourceFilePath));

		// Verify Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			UUID.fromString("58699f8a-22fa-4784-9768-3fcc3b2619b4"),
			"Product Catalogue Database",
			resource,
			"resource");

		// Verify States
		assertEquals(
			"resource.states.size",
			2,
			resource.getStates().size());

		// Verify Assertions
		assertEquals(
			"resource.state[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		assertEquals(
			"resource.state[1].assertions.size",
			5,
			resource.getStates().get(1).getAssertions().size());

	}

	@Test
	public void loadResourceXml_assertionGroup_withInvalidXML_fails()
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "InvalidXml/InvalidSampleResourcesUsesAssertionGroup.xml";

		new ExpectException(XmlValidationException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.loadResource(new File(resourceFilePath));
			}

			@Override public void verify(Exception e)
			{
				Assert.assertTrue(
					"e.message",
					e.getMessage().contains("Attribute 'ref' must appear on element 'assertionRef'"));
			}
		}.perform();
	}

	@Test
	public void loadResourceXml_assertionGroup_withInvalidRef_fails()
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "InvalidXml/InvalidReferenceSampleResourceUsesAssertionGroup.xml";

		new ExpectException(InvalidReferenceException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.loadResource(new File(resourceFilePath));
			}

			@Override public void verify(Exception e)
			{
				Assert.assertTrue(
					"e.message",
					e.getMessage().contains("State:363568f1-aaed-4a50-bea0-9ddee713cc11 has invalid references to: " +
						"[ Assertion Group: group1 ]"));
			}
		}.perform();
	}

	@Test
	public void loadResource_validMysql_withSingleAssertRef_succeeds()
	{
		Resource resource = this.loadResource("MySqlDatabase/database.wbresources.uses.singleAssertionRef.xml");

		//
		// Verify
		//

		// Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			UUID.fromString("0d39b8fb-5b5c-48cd-845c-9c4d55f94303"),
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			2,
			resource.getStates().size());

		// Assertions
		assertEquals(
			"resource.state[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		assertEquals(
			"resource.state[1].assertions.size",
			6,
			resource.getStates().get(1).getAssertions().size());

	}

	@Test
	public void loadResource_validPostgre_withSingleAssertRef_succeeds()
	{
		Resource resource = this.loadResource("PostgreSqlDatabase/database.wbresources.uses.singleAssertionRef.xml");
		//
		// Verify
		//

		// Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			UUID.fromString("38d0eabd-ab40-4c37-96a7-fcacb43bd059"),
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			3,
			resource.getStates().size());

		// Assertions
		assertEquals(
			"resource.state[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		assertEquals(
			"resource.state[1].assertions.size",
			0,
			resource.getStates().get(1).getAssertions().size());
		assertEquals(
			"resource.state[2].assertions.size",
			1,
			resource.getStates().get(2).getAssertions().size());

	}

	@Test
	public void loadResource_validSqlServer_withSingleAssertRef_succeeds()
	{
		Resource resource = this.loadResource("SqlServerDatabase/database.wbresources.uses.singleAssertionRef.xml");
		//
		// Verify
		//

		// Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			UUID.fromString("58699f8a-22fa-4784-9768-3fcc3b2619b4"),
			"Product Catalogue Database",
			resource,
			"resource");

		// States
		assertEquals(
			"resource.states.size",
			2,
			resource.getStates().size());

		// Assertions
		assertEquals(
			"resource.state[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		assertEquals(
			"resource.state[1].assertions.size",
			5,
			resource.getStates().get(1).getAssertions().size());

	}

	@Test
	public void loadResourceXml_withInvalidXML_singleAssertion_fails()
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "InvalidXml/InvalidSampleResourceUsesSingleAssertRef.xml";

		new ExpectException(XmlValidationException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.loadResource(new File(resourceFilePath));
			}

			@Override public void verify(Exception e)
			{
				Assert.assertTrue(
					"e.message",
					e.getMessage().contains("Attribute 'test' is not allowed to appear in element 'assertionRef'"));
			}
		}.perform();
	}

	@Test
	public void loadResourceXml_withInvalidRef_singleAssertion_fails()
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();
		String resourceFilePath = "InvalidXml/InvalidSampleResourceSingleAssertMissingRef.xml";

		new ExpectException(InvalidReferenceException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.loadResource(new File(resourceFilePath));
			}

			@Override public void verify(Exception e)
			{
				Assert.assertTrue(
					"e.message",
					e.getMessage().contains("State:199b7cc1-3cc6-48ca-b012-a70d05d5b5e7 has invalid references to:" +
						" [ Assertion: DatabaseExisting2 ]"));
			}
		}.perform();
	}

	@Test
	public void loadResource_withMissingAssertionGroup_throwsXmlValidationException()
	{
		// Setup
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();

		String resourceFilePath = "InvalidXml/InvalidSampleResourcesUsesAssertionGroup.xml";

		// Execute and Verify
		new ExpectException(XmlValidationException.class)
		{
			@Override public void invoke() throws Exception
			{
				wildebeestApi.loadResource(new File(resourceFilePath));
			}

			@Override public void verify(Exception e)
			{
				Assert.assertTrue(
					"e.message",
					e.getMessage().contains("Attribute 'ref' must appear on element 'assertionRef'"));
			}
		}.perform();
	}

	private Resource loadResource(
		String resourceFilePath)
	{
		if (resourceFilePath == null) throw new ArgumentNullException("resourceFilePath");

		final Resource result;

		final WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(new LoggingEventSink(LOG))
			.get();

		try
		{
			result = wildebeestApi.loadResource(new File(resourceFilePath));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}
}
