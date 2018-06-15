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
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationBuilder;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.fixture.FixtureBuilder;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.SetTagMigration;
import co.mv.wb.plugin.fake.TagAssertion;
import co.mv.wb.plugin.fake.dom.DomSetTagMigrationBuilder;
import co.mv.wb.plugin.fake.dom.DomTagAssertionBuilder;
import org.junit.Test;

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
 * @since                                       1.0
 */
public class DomResourceLoaderTests
{
    @Test
    public void loadResource() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
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
    public void loadResourceForStateWithLabel() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID stateId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
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
    public void loadResourceForStateWithNoLabel() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID stateId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
                .resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
                .state(stateId, null )
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
    public void loadResourceForStateWithLabelAndDescription() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID stateId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
                .resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
                .state(stateId, "Some random label", "Some random test description")
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
                Optional.of("Some random label"),
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
    public void loadResourceForMultipleStates() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID state1Id = UUID.randomUUID();
        UUID state2Id = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
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
    public void loadResourceForStateWithOneAssertion() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID stateId = UUID.randomUUID();
        UUID assertion1Id = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
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
                assertion1Id, "Tag is Foo", 0, "Foo",
                (TagAssertion) resource.getStates().get(0).getAssertions().get(0),
                "resource.states[0].assertions[0]");

        // Migrations
        assertEquals("resource.migrations.size", 0, resource.getMigrations().size());

    }

    @Test
    public void loadResourceForStateWithMultipleAssertions() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID stateId = UUID.randomUUID();
        UUID assertion1Id = UUID.randomUUID();
        UUID assertion2Id = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
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
                assertion1Id, "Tag is Foo", 0, "Foo",
                (TagAssertion) resource.getStates().get(0).getAssertions().get(0),
                "resource.states[0].assertions[0]");
        Asserts.assertTagAssertion(
                assertion2Id, "Tag is Bar", 1, "Bar",
                (TagAssertion) resource.getStates().get(0).getAssertions().get(1),
                "resource.states[0].assertions[1]");

        // Migrations
        assertEquals("resource.migrations.size", 0, resource.getMigrations().size());

    }

    @Test
    public void loadResourceForMigrationWithFromStateId() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID state1Id = UUID.randomUUID();
        UUID migrationId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
                .resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
                .state(state1Id, "Foo")
                .migration(FakeConstants.Fake.getUri(), migrationId, state1Id, null).withInnerXml("<tag>Blah</tag>")
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
                migrationId, Optional.of(state1Id), Optional.empty(), "Blah",
                (SetTagMigration) resource.getMigrations().get(0),
                "resource.migrations[0]");

    }

    @Test
    public void loadResourceForMigrationsWithToStateId() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID state1Id = UUID.randomUUID();
        UUID migrationId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
                .resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
                .state(state1Id, "Foo")
                .migration(FakeConstants.Fake.getUri(), migrationId, null, state1Id).withInnerXml("<tag>Blah</tag>")
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
                migrationId, Optional.empty(), Optional.of(state1Id), "Blah",
                (SetTagMigration) resource.getMigrations().get(0),
                "resource.migrations[0]");

    }

    @Test
    public void loadResourceForMigrationsWithFromStateIdAndToStateId() throws
            LoaderFault,
            PluginBuildException
    {

        //
        // Setup
        //

        UUID resourceId = UUID.randomUUID();
        UUID state1Id = UUID.randomUUID();
        UUID state2Id = UUID.randomUUID();
        UUID migrationId = UUID.randomUUID();

        String resourceXml = FixtureBuilder.create()
                .resource(FakeConstants.Fake.getUri(), resourceId, "Product Catalogue Database")
                .state(state1Id, "Foo")
                .state(state2Id, "Bar")
                .migration(FakeConstants.Fake.getUri(), migrationId, state1Id, state2Id).withInnerXml("<tag>Blah</tag>")
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
                migrationId, Optional.of(state1Id), Optional.of(state2Id), "Blah",
                (SetTagMigration) resource.getMigrations().get(0),
                "resource.migrations[0]");

    }
}
