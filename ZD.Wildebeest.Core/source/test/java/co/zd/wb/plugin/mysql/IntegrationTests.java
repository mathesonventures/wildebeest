// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.plugin.mysql;

import co.zd.wb.plugin.database.SqlScriptMigration;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Instance;
import co.zd.wb.State;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;
import co.zd.wb.AssertExtensions;
import co.zd.wb.ProductCatalogueMySqlDatabaseResource;
import co.zd.wb.Resource;
import co.zd.wb.plugin.base.ImmutableState;
import co.zd.wb.plugin.database.DatabaseFixtureHelper;
import co.zd.wb.service.MessagesException;
import co.zd.wb.PrintStreamLogger;
import co.zd.wb.service.dom.DomInstanceLoader;
import co.zd.wb.service.dom.DomPlugins;
import co.zd.wb.service.dom.DomResourceLoader;
import co.zd.wb.fixturecreator.XmlBuilder;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class IntegrationTests
{
	@Test public void createDatabaseAddTableInsertRows() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		SQLException
	{
	
		//
		// Setup
		//

		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		// Resource
		MySqlDatabaseResource resource = new MySqlDatabaseResource(UUID.randomUUID(), "Database");
		
		// State: Created
		State created = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(created);
		
		// State: Initial Schema
		State initialSchema = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(initialSchema);
		
		// State: Populated
		State populated = new ImmutableState(UUID.randomUUID());
		resource.getStates().add(populated);
		
		// Migration: to Created
		resource.getMigrations().add(new MySqlCreateDatabaseMigration(
			UUID.randomUUID(),
			null,
			created.getStateId()));
		
		// Migration: Created to Initial Schema
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			created.getStateId(),
			initialSchema.getStateId(),
			MySqlElementFixtures.productCatalogueDatabase()));
		
		// Migration: Initial Schema to Populated
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			initialSchema.getStateId(),
			populated.getStateId(),
			MySqlElementFixtures.productTypeRows()));

		String databaseName = DatabaseFixtureHelper.databaseName();

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName,
			null);
		
		//
		// Execute
		//
		
		try
		{
			resource.migrate(new PrintStreamLogger(System.out), instance, populated.getStateId());
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}
		
		//
		// Verify
		//
		
		// (none)
		
	}
	
	@Test public void loadMySqlDatabaseResource() throws MessagesException
	{
		// Setup
		ProductCatalogueMySqlDatabaseResource prodCatResource = new ProductCatalogueMySqlDatabaseResource();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			prodCatResource.getResourceXml());

		// Execute
		Resource resource = resourceBuilder.load();
		
		// Verify
		assertResource(resource);
	}
	
	@Test public void loadMySqlDatabaseInstance() throws MessagesException
	{
		// Setup
		String databaseName = DatabaseFixtureHelper.databaseName();

		DomInstanceLoader instanceLoader = new DomInstanceLoader(
			DomPlugins.instanceBuilders(),
			instance(databaseName).toString());

		// Execute
		Instance instance = instanceLoader.load();
		
		// Verify
		assertInstance(instance, databaseName);
	}
	
	@Test public void loadMySqlDatabaseResourceAndInstanceAndMigrate() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		SQLException,
		MessagesException
	{
		
		//
		// Resource
		//

		ProductCatalogueMySqlDatabaseResource prodCatResource = new ProductCatalogueMySqlDatabaseResource();
		
		// Fixture
		DomResourceLoader resourceLoader = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			prodCatResource.getResourceXml());
		
		// Execute
		Resource resource = resourceLoader.load();

		// Assert
		assertResource(resource);
		
		//
		// Instance
		//
		
		// Fixture
		String databaseName = DatabaseFixtureHelper.databaseName();
		
		DomInstanceLoader instanceLoader = new DomInstanceLoader(
			DomPlugins.instanceBuilders(),
			instance(databaseName).toString());
		
		// Execute
		Instance instance = instanceLoader.load();
		
		// Assert
		assertInstance(instance, databaseName);
		
		//
		// Migrate
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
			MySqlUtil.dropDatabase((MySqlDatabaseInstance)instance, databaseName);
		}
		
	}
	
	private static void assertResource(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource"); }
		
		AssertExtensions.assertResource(
			MySqlDatabaseResource.class,
			ProductCatalogueMySqlDatabaseResource.ResourceId,
			"Product Catalogue Database",
			resource,
			"resource");
	}
	
	private static XmlBuilder instance(
		String databaseName)
	{
		if (databaseName == null) { throw new IllegalArgumentException("databaseName cannot be null"); }
		if ("".equals(databaseName)) { throw new IllegalArgumentException("databaseName cannot be blank"); }
		
		XmlBuilder instanceXml = new XmlBuilder();
		instanceXml
			.processingInstruction()
			.openElement("instance type=\"MySqlDatabase\" id=\"" + UUID.randomUUID() + "\"")
				.openElement("hostName").append("127.0.0.1").closeElement("hostName")
				.openElement("port").append("3306").closeElement("port")
				.openElement("adminUsername").append("root").closeElement("adminUsername")
				.openElement("adminPassword").append("password").closeElement("adminPassword")
				.openElement("databaseName").append(databaseName).closeElement("databaseName")
			.closeElement("instance");
		
		return instanceXml;
	}
	
	private static void assertInstance(
		Instance instance,
		String databaseName)
	{
		if (databaseName == null) { throw new IllegalArgumentException("databaseName"); }
		if ("".equals(databaseName)) { throw new IllegalArgumentException("databaseName"); }
		
		Assert.assertNotNull("instance", instance);
		AssertExtensions.assertInstance(MySqlDatabaseInstance.class, instance, "instance");
		AssertExtensions.assertMySqlDatabaseInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			databaseName,
			instance,
			"instance");
	}
}
