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

package co.mv.wb.plugin.mysql;

import co.mv.wb.AssertionFailedException;
import co.mv.wb.Asserts;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.PrintStreamLogger;
import co.mv.wb.ProductCatalogueMySqlDatabaseResource;
import co.mv.wb.Resource;
import co.mv.wb.ResourceHelper;
import co.mv.wb.State;
import co.mv.wb.fixturecreator.XmlBuilder;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.impl.ImmutableState;
import co.mv.wb.impl.ResourceHelperImpl;
import co.mv.wb.impl.ResourceImpl;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.database.DatabaseFixtureHelper;
import co.mv.wb.plugin.database.SqlScriptMigration;
import co.mv.wb.plugin.database.SqlScriptMigrationPlugin;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.dom.DomInstanceLoader;
import co.mv.wb.service.dom.DomPlugins;
import co.mv.wb.service.dom.DomResourceLoader;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

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

		ResourceHelper resourceHelper = new ResourceHelperImpl();

		MySqlProperties mySqlProperties = MySqlProperties.get();
		
		// Resource
		MySqlDatabaseResourcePlugin resourcePlugin = new MySqlDatabaseResourcePlugin(
			resourceHelper);

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			FactoryResourceTypes.MySqlDatabase,
			"Database",
			Optional.empty());

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
			Optional.empty(),
			Optional.of(created.getStateId())));
		
		// Migration: Created to Initial Schema
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			Optional.of(created.getStateId()),
			Optional.of(initialSchema.getStateId()),
			MySqlElementFixtures.productCatalogueDatabase()));
		
		// Migration: Initial Schema to Populated
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			Optional.of(initialSchema.getStateId()),
			Optional.of(populated.getStateId()),
			MySqlElementFixtures.productTypeRows()));

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();
		migrationPlugins.put(MySqlCreateDatabaseMigration.class, new MySqlCreateDatabaseMigrationPlugin());
		migrationPlugins.put(SqlScriptMigration.class, new SqlScriptMigrationPlugin());

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
			resourceHelper.migrate(
				new PrintStreamLogger(System.out),
				resource,
				resourcePlugin,
				instance,
				migrationPlugins,
				populated.getStateId());
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
		
		DomResourceLoader resourceLoader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			new PrintStreamLogger(System.out),
			prodCatResource.getResourceXml());

		// Execute
		Resource resource = resourceLoader.load(new File("."));

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

		ResourceHelper resourceHelper = new ResourceHelperImpl();

		//
		// Resource
		//

		ProductCatalogueMySqlDatabaseResource prodCatResource = new ProductCatalogueMySqlDatabaseResource();
		
		// Fixture
		DomResourceLoader resourceLoader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			new PrintStreamLogger(System.out),
			prodCatResource.getResourceXml());
		
		// Execute
		Resource resource = resourceLoader.load(new File("."));

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

		MySqlDatabaseResourcePlugin resourcePlugin = new MySqlDatabaseResourcePlugin(
			resourceHelper);

		Map<Class, MigrationPlugin> migrationPlugins = new HashMap<>();

		try
		{
			resourceHelper.migrate(
				new PrintStreamLogger(System.out),
				resource,
				resourcePlugin,
				instance,
				migrationPlugins,
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
		
		Asserts.assertResource(
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
		
		assertNotNull("instance", instance);
		Asserts.assertInstance(MySqlDatabaseInstance.class, instance, "instance");
		Asserts.assertMySqlDatabaseInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			databaseName,
			instance,
			"instance");
	}
}
