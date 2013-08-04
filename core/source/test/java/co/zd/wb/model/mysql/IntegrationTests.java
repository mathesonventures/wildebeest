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

package co.zd.wb.model.mysql;

import co.zd.wb.model.database.SqlScriptMigration;
import co.zd.wb.model.AssertionFailedException;
import co.zd.wb.model.IndeterminateStateException;
import co.zd.wb.model.Instance;
import co.zd.wb.model.State;
import co.zd.wb.model.MigrationFailedException;
import co.zd.wb.model.MigrationNotPossibleException;
import co.zd.wb.AssertExtensions;
import co.zd.wb.ProductCatalogueResource;
import co.zd.wb.model.Resource;
import co.zd.wb.model.base.ImmutableState;
import co.zd.wb.service.PrintStreamLogger;
import co.zd.wb.service.dom.DomInstanceLoader;
import co.zd.wb.service.dom.DomPlugins;
import co.zd.wb.service.dom.DomResourceLoader;
import co.zd.wb.service.dom.XmlBuilder;
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
		// Fixture Setup
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

		String databaseName = MySqlElementFixtures.databaseName("StmTest");

		MySqlDatabaseInstance instance = new MySqlDatabaseInstance(
			mySqlProperties.getHostName(),
			mySqlProperties.getPort(),
			mySqlProperties.getUsername(),
			mySqlProperties.getPassword(),
			databaseName);
		
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
		// Assert Results
		//
		
	}
	
	@Test public void loadMySqlDatabaseResource()
	{
		
		//
		// Fixture Setup
		//
		
		ProductCatalogueResource prodCatResource = new ProductCatalogueResource();
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			prodCatResource.getXmlBuilder().toString());

		//
		// Execute - load
		//
		
		Resource resource = resourceBuilder.load();
		
		//
		// Assert Results
		//

		assertResource(resource);
		
	}
	
	@Test public void loadMySqlDatabaseInstance()
	{
		
		//
		// Fixture Setup
		//
		
		String databaseName = MySqlElementFixtures.databaseName("StmTest");

		DomInstanceLoader instanceLoader = new DomInstanceLoader(
			DomPlugins.instanceBuilders(),
			instance(databaseName).toString());

		//
		// Execute
		//
		
		Instance instance = instanceLoader.load();
		
		//
		// Assert Results
		//
		
		// Instance
		assertInstance(instance, databaseName);

	}
	
	@Test public void loadMySqlDatabaseResourceAndInstanceAndMigrate() throws
		IndeterminateStateException,
		AssertionFailedException,
		MigrationNotPossibleException,
		MigrationFailedException,
		SQLException
	{
		
		//
		// Resource
		//

		ProductCatalogueResource prodCatResource = new ProductCatalogueResource();
		
		// Fixture
		DomResourceLoader resourceLoader = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			prodCatResource.getXmlBuilder().toString());
		
		// Execute
		Resource resource = resourceLoader.load();

		// Assert
		assertResource(resource);
		
		//
		// Instance
		//
		
		// Fixture
		String databaseName = MySqlElementFixtures.databaseName("StmTest");
		
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
				ProductCatalogueResource.StateIdInitialReferenceDataLoaded);
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
			ProductCatalogueResource.ResourceId,
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
			.openElement("instance type=\"MySqlDatabase\"")
				.openElement("hostName").text("127.0.0.1").closeElement("hostName")
				.openElement("port").text("3306").closeElement("port")
				.openElement("adminUsername").text("root").closeElement("adminUsername")
				.openElement("adminPassword").text("password").closeElement("adminPassword")
				.openElement("schemaName").text(databaseName).closeElement("schemaName")
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
