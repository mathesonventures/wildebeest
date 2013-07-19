package co.zd.wb.service.dom;

import co.zd.wb.AssertExtensions;
import co.zd.wb.model.Resource;
import co.zd.wb.model.base.FakeAssertion;
import co.zd.wb.model.base.FakeResource;
import co.zd.wb.model.base.FakeMigration;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.MigrationBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class DomResourceLoaderTests
{
	@Test public void loadResource()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 0, resource.getStates().size());
		
		// Migrations
		Assert.assertEquals("resource.migration.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithLabel()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(stateId, "Foo", resource.getStates().get(0), "state[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithNoLabel()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString()).closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		AssertExtensions.assertState(stateId, resource.getStates().get(0), "state[0]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForMultipleStates()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
					.openElement("state", "id", state2Id.toString(), "label", "Bar").closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 2, resource.getStates().size());
		AssertExtensions.assertState(state1Id, "Foo", resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(state2Id, "Bar", resource.getStates().get(1), "state[1]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 0, resource.getMigrations().size());
		
	}
	
	@Test public void loadResourceForStateWithOneAssertion()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString(), "label", "Foo")
						.openElement("assertions")
							.openElement("assertion",
								"type", "Fake",
								"id", assertion1Id.toString(),
								"name", "Tag is Foo")
								.openElement("tag").text("Foo").closeElement("tag")
							.closeElement("assertion")
						.closeElement("assertions")
					.closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
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
	
	@Test public void loadResourceForStateWithMultipleAssertions()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID stateId = UUID.randomUUID();
		UUID assertion1Id = UUID.randomUUID();
		UUID assertion2Id = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", stateId.toString(), "label", "Foo")
						.openElement("assertions")
							.openElement("assertion",
								"type", "Fake",
								"id", assertion1Id.toString(),
								"name", "Tag is Foo")
								.openElement("tag").text("Foo").closeElement("tag")
							.closeElement("assertion")
							.openElement("assertion",
								"type", "Fake",
								"id", assertion2Id.toString(),
								"name", "Tag is Bar")
								.openElement("tag").text("Bar").closeElement("tag")
							.closeElement("assertion")
						.closeElement("assertions")
					.closeElement("state")
				.closeElement("states")
			.closeElement("resource");

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("Fake", new DomFakeAssertionBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
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
	
	@Test public void loadResourceForMigrationWithFromStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
				.openElement("migrations")
					.openElement("migration",
						"type", "Fake",
						"id", migrationId.toString(),
						"fromStateId", state1Id.toString())
						.openElement("tag").text("Blah").closeElement("tag")
					.closeElement("migration")
				.closeElement("migrations")
			.closeElement("resource");

		
		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("Fake", new DomFakeMigrationBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
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
	
	@Test public void loadResourceForMigrationsWithToStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
				.closeElement("states")
				.openElement("migrations")
					.openElement("migration",
						"type", "Fake",
						"id", migrationId.toString(),
						"toStateId", state1Id.toString())
						.openElement("tag").text("Blah").closeElement("tag")
					.closeElement("migration")
				.closeElement("migrations")
			.closeElement("resource");

		
		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("Fake", new DomFakeMigrationBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
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
	
	@Test public void loadResourceForMigrationsWithFromStateIdAndToStateId()
	{
		
		//
		// Fixture Setup
		//
		
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID migrationId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openElement("resource",
				"id", resourceId.toString(),
				"type", "Fake",
				"name", "Product Catalogue Database")
				.openElement("states")
					.openElement("state", "id", state1Id.toString(), "label", "Foo").closeElement("state")
					.openElement("state", "id", state2Id.toString(), "label", "Bar").closeElement("state")
				.closeElement("states")
				.openElement("migrations")
					.openElement("migration",
						"type", "Fake",
						"id", migrationId.toString(),
						"fromStateId", state1Id.toString(),
						"toStateId", state2Id.toString())
						.openElement("tag").text("Blah").closeElement("tag")
					.closeElement("migration")
				.closeElement("migrations")
			.closeElement("resource");

		
		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("Fake", new DomFakeResourceBuilder());

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("Fake", new DomFakeMigrationBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			migrationBuilders,
			resourceXml.toString());

		//
		// Execute
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(FakeResource.class, resourceId, "Product Catalogue Database", resource, "resource");
		
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