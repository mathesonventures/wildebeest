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

package co.mv.wb.plugin.base;

import co.mv.wb.AssertExtensions;
import co.mv.wb.ProductCatalogueMySqlDatabaseResource;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.FakeLogger;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Resource;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.plugin.database.DatabaseFixtureHelper;
import co.mv.wb.plugin.mysql.MySqlDatabaseResourcePlugin;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;
import co.mv.wb.plugin.mysql.MySqlProperties;
import co.mv.wb.plugin.mysql.MySqlUtil;
import co.mv.wb.service.MessagesException;
import co.mv.wb.PrintStreamLogger;
import co.mv.wb.service.dom.DomPlugins;
import co.mv.wb.service.dom.DomResourceLoader;
import java.io.File;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoaderIntegrationTests
{
	private static final Logger LOG = LoggerFactory.getLogger(ResourceLoaderIntegrationTests.class);

	@Test public void loadAndMigrateMySqlResourceFromXml() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		SQLException,
		MessagesException
	{
		
		//
		// Setup
		//
		
		ProductCatalogueMySqlDatabaseResource productCatalogueResource = new ProductCatalogueMySqlDatabaseResource();

		DomResourceLoader resourceBuilder = DomPlugins.resourceLoader(
			new FakeLogger(),
			productCatalogueResource.getResourceXml());

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			MySqlProperties.get().getHostName(),
			MySqlProperties.get().getPort(),
			MySqlProperties.get().getUsername(),
			MySqlProperties.get().getPassword(),
			databaseName,
			null);
		
		//
		// Execute - Load
		//
		
		Resource resource = null;
		try
		{
			resource = resourceBuilder.load(new File("."));
		}
		catch (MessagesException e)
		{
			for (String message : e.getMessages().getMessages())
			{
				LOG.error(message);
			}
			
			throw e;
		}
		
		//
		// Verify - Model
		//
		
		// Resource
		Assert.assertNotNull("resource", resource);
		AssertExtensions.assertResource(MySqlDatabaseResourcePlugin.class, productCatalogueResource.getResourceId(), "Product Catalogue Database",
			resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 3, resource.getStates().size());
		AssertExtensions.assertState(
			ProductCatalogueMySqlDatabaseResource.StateIdDatabaseCreated, "Database created",
			resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(
			ProductCatalogueMySqlDatabaseResource.StateIdCoreSchemaLoaded, "Core Schema Loaded",
			resource.getStates().get(1), "state[1]");
		AssertExtensions.assertState(
			ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded, "Reference Data Loaded",
			resource.getStates().get(2), "state[2]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 3, resource.getMigrations().size());
		AssertExtensions.assertMigration(
			ProductCatalogueMySqlDatabaseResource.MigrationIdCreateDatabase,
			null,
			ProductCatalogueMySqlDatabaseResource.StateIdDatabaseCreated,
			resource.getMigrations().get(0), "resource.migrations[0]");
		AssertExtensions.assertMigration(
			ProductCatalogueMySqlDatabaseResource.MigrationIdLoadCoreSchema,
			ProductCatalogueMySqlDatabaseResource.StateIdDatabaseCreated,
			ProductCatalogueMySqlDatabaseResource.StateIdCoreSchemaLoaded,
			resource.getMigrations().get(1), "resource.migrations[1]");
		AssertExtensions.assertMigration(
			ProductCatalogueMySqlDatabaseResource.MigrationIdLoadReferenceData,
			ProductCatalogueMySqlDatabaseResource.StateIdCoreSchemaLoaded,
			ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded,
			resource.getMigrations().get(2), "resource.migrations[2]");
		
		//
		// Execute - Migrate
		//
		
		try
		{
			resource.migrate(
				new PrintStreamLogger(System.out),
				instance,
				ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded);
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}

	}
}
