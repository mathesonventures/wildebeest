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

import co.mv.wb.AssertionFailedException;
import co.mv.wb.Asserts;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.PluginBuildException;
import co.mv.wb.PluginNotFoundException;
import co.mv.wb.Resource;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.fixture.ProductCatalogueMySqlDatabaseResource;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;
import co.mv.wb.plugin.mysql.MySqlProperties;
import co.mv.wb.plugin.mysql.MySqlUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceLoaderIntegrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(ResourceLoaderIntegrationTests.class);

	@Test
	public void loadAndMigrateMySqlResourceFromXml() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidReferenceException,
		LoaderFault,
		MigrationFailedException,
		MigrationNotPossibleException,
		PluginBuildException,
		PluginNotFoundException,
		SQLException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{

		//
		// Setup
		//

		ProductCatalogueMySqlDatabaseResource productCatalogueResource = new ProductCatalogueMySqlDatabaseResource();

		DomResourceLoader resourceBuilder = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			productCatalogueResource.getResourceXml());

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			MySqlProperties.get().getHostName(),
			MySqlProperties.get().getPort(),
			MySqlProperties.get().getUsername(),
			MySqlProperties.get().getPassword(),
			databaseName,
			null);

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(new LoggingEventSink(LOG))
			.withFactoryResourcePlugins()
			.withFactoryAssertionPlugins()
			.withFactoryMigrationPlugins()
			.get();

		//
		// Execute - Load
		//

		Resource resource = resourceBuilder.load(new File("."));

		//
		// Verify - Model
		//

		// Resource
		assertNotNull("resource", resource);
		Asserts.assertResource(
			productCatalogueResource.getResourceId(),
			"Product Catalogue Database",
			resource, "resource");

		// States
		assertEquals("resource.states.size", 3, resource.getStates().size());
		Asserts.assertState(
			ProductCatalogueMySqlDatabaseResource.StateIdDatabaseCreated, Optional.of("Database created"),
			resource.getStates().get(0), "resource.state[0]");
		Asserts.assertState(
			ProductCatalogueMySqlDatabaseResource.StateIdCoreSchemaLoaded, Optional.of("Core Schema Loaded"),
			resource.getStates().get(1), "resource.state[1]");
		Asserts.assertState(
			ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded,
			Optional.of("Reference Data Loaded"),
			resource.getStates().get(2),
			"resource.state[2]");

		// Migrations
		assertEquals("resource.migrations.size", 3, resource.getMigrations().size());
		Asserts.assertMigration(
			ProductCatalogueMySqlDatabaseResource.MigrationIdCreateDatabase,
			Optional.empty(),
			Optional.of(ProductCatalogueMySqlDatabaseResource.StateIdDatabaseCreated.toString()),
			resource.getMigrations().get(0), "resource.migrations[0]");
		Asserts.assertMigration(
			ProductCatalogueMySqlDatabaseResource.MigrationIdLoadCoreSchema,
			Optional.of(ProductCatalogueMySqlDatabaseResource.StateIdDatabaseCreated.toString()),
			Optional.of(ProductCatalogueMySqlDatabaseResource.StateIdCoreSchemaLoaded.toString()),
			resource.getMigrations().get(1), "resource.migrations[1]");
		Asserts.assertMigration(
			ProductCatalogueMySqlDatabaseResource.MigrationIdLoadReferenceData,
			Optional.of(ProductCatalogueMySqlDatabaseResource.StateIdCoreSchemaLoaded.toString()),
			Optional.of(ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded.toString()),
			resource.getMigrations().get(2), "resource.migrations[2]");

		//
		// Execute - Migrate
		//

		try
		{
			wildebeestApi.migrate(
				resource,
				instance,
				ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded.toString());
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}
	}
}
