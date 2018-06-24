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

package co.mv.wb.plugin.generaldatabase;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionBuilder;
import co.mv.wb.LoaderFault;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.Wildebeest;
import co.mv.wb.fixture.FixtureBuilder;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.base.dom.DomResourceLoader;
import co.mv.wb.plugin.generaldatabase.dom.DatabaseDoesNotExistDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.dom.DatabaseExistsDomAssertionBuilder;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the DOM persistence services for core database plugins.
 *
 * @since 4.0
 */
public class DatabaseDomServiceUnitTests
{
	@Test
	public void databaseExistsAssertionLoadFromValidDocumentSucceeds() throws
		LoaderFault,
		PluginBuildException,
            InvalidReferenceException
	{
		// Setup
		UUID assertionId = UUID.randomUUID();

		String xml = FixtureBuilder.create()
			.resource(Wildebeest.PostgreSqlDatabase.getUri(), UUID.randomUUID(), "Product Catalogue Database")
			.state(UUID.randomUUID(), null)
			.assertion("DatabaseExists", assertionId)
			.build();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		assertionBuilders.put("DatabaseExists", new DatabaseExistsDomAssertionBuilder());

		DomResourceLoader loader = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			assertionBuilders,
			new HashMap<>(),
			xml);

		// Execute
		Resource resource = loader.load(new File("."));

		// Verify
		assertNotNull("resource", resource);
		assertEquals("resource.states.size", 1, resource.getStates().size());
		assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		Assertion assertion = resource.getStates().get(0).getAssertions().get(0);
		DatabaseExistsAssertion assertionT = ModelExtensions.As(assertion, DatabaseExistsAssertion.class);
		assertNotNull("expected to be DatabaseExistsAssertion", assertionT);

		assertEquals("assertion.assertionId", assertionId, assertion.getAssertionId());
	}

	@Test
	public void databaseDoesNotExistAssertionLoadFromValidDocumentSucceeds() throws
		LoaderFault,
		PluginBuildException,
            InvalidReferenceException
	{
		// Setup
		UUID assertionId = UUID.randomUUID();

		String xml = FixtureBuilder.create()
			.resource(Wildebeest.PostgreSqlDatabase.getUri(), UUID.randomUUID(), "Product Catalogue Database")
			.state(UUID.randomUUID(), null)
			.assertion("DatabaseDoesNotExist", assertionId)
			.build();

		Map<String, AssertionBuilder> assertionBuilders = new HashMap<>();
		assertionBuilders.put("DatabaseDoesNotExist", new DatabaseDoesNotExistDomAssertionBuilder());

		DomResourceLoader loader = new DomResourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			assertionBuilders,
			new HashMap<>(),
			xml);

		// Execute
		Resource resource = loader.load(new File("."));

		// Verify
		assertNotNull("resource", resource);
		assertEquals("resource.states.size", 1, resource.getStates().size());
		assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		Assertion assertion = resource.getStates().get(0).getAssertions().get(0);
		DatabaseDoesNotExistAssertion assertionT = ModelExtensions.As(assertion, DatabaseDoesNotExistAssertion.class);
		assertNotNull("expected to be DatabaseDoesNotExistAssertion", assertionT);

		assertEquals("assertion.assertionId", assertionId, assertion.getAssertionId());
	}
}
