// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb.model.base;

import co.zd.wb.AssertExtensions;
import co.zd.wb.ProductCatalogueResource;
import co.zd.wb.model.AssertionFailedException;
import co.zd.wb.model.IndeterminateStateException;
import co.zd.wb.model.Resource;
import co.zd.wb.model.MigrationFailedException;
import co.zd.wb.model.MigrationNotPossibleException;
import co.zd.wb.model.mysql.MySqlDatabaseResource;
import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.mysql.MySqlElementFixtures;
import co.zd.wb.model.mysql.MySqlProperties;
import co.zd.wb.model.mysql.MySqlUtil;
import co.zd.wb.service.PrintStreamLogger;
import co.zd.wb.service.dom.DomPlugins;
import co.zd.wb.service.dom.DomResourceLoader;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Test;

public class ResourceLoaderIntegrationTests
{
	@Test public void loadAndMigrateMySqlResourceFromXml() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		SQLException
	{
		
		//
		// Fixture Setup
		//
		
		ProductCatalogueResource productCatalogueResource = new ProductCatalogueResource();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			productCatalogueResource.getXmlBuilder().toString());

		String databaseName = MySqlElementFixtures.databaseName("StmTest");

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			MySqlProperties.get().getHostName(),
			MySqlProperties.get().getPort(),
			MySqlProperties.get().getUsername(),
			MySqlProperties.get().getPassword(),
			databaseName);
		
		//
		// Execute - Load
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Model
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(
			MySqlDatabaseResource.class, productCatalogueResource.getResourceId(), "Product Catalogue Database",
			resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 3, resource.getStates().size());
		AssertExtensions.assertState(
			ProductCatalogueResource.StateIdDatabaseCreated, "Database created",
			resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(
			ProductCatalogueResource.StateIdCoreSchemaLoaded, "Core Schema Loaded",
			resource.getStates().get(1), "state[1]");
		AssertExtensions.assertState(
			ProductCatalogueResource.StateIdInitialReferenceDataLoaded, "Reference Data Loaded",
			resource.getStates().get(2), "state[2]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 3, resource.getMigrations().size());
		AssertExtensions.assertMigration(
			ProductCatalogueResource.MigrationIdCreateDatabase,
			null,
			ProductCatalogueResource.StateIdDatabaseCreated,
			resource.getMigrations().get(0), "resource.migrations[0]");
		AssertExtensions.assertMigration(
			ProductCatalogueResource.MigrationIdLoadCoreSchema,
			ProductCatalogueResource.StateIdDatabaseCreated,
			ProductCatalogueResource.StateIdCoreSchemaLoaded,
			resource.getMigrations().get(1), "resource.migrations[1]");
		AssertExtensions.assertMigration(
			ProductCatalogueResource.MigrationIdLoadReferenceData,
			ProductCatalogueResource.StateIdCoreSchemaLoaded,
			ProductCatalogueResource.StateIdInitialReferenceDataLoaded,
			resource.getMigrations().get(2), "resource.migrations[2]");
		
		//
		// Execute - Migrate
		//
		
		try
		{
			resource.migrate(
				new PrintStreamLogger(System.out),
				instance,
				ProductCatalogueResource.StateIdInitialReferenceDataLoaded);
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}

	}
}