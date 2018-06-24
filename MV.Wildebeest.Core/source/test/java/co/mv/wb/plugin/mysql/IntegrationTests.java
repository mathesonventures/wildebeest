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
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.fixture.ProductCatalogueMySqlDatabaseResource;
import co.mv.wb.fixture.XmlBuilder;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.base.dom.DomInstanceLoader;
import co.mv.wb.plugin.base.dom.DomPlugins;
import co.mv.wb.plugin.base.dom.DomResourceLoader;
import co.mv.wb.plugin.generaldatabase.DatabaseFixtureHelper;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigration;
import co.mv.wb.plugin.generaldatabase.SqlScriptMigrationPlugin;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for the MySQL plugin suite.
 *
 * @since 1.0
 */
public class IntegrationTests
{
	@Test
	public void createDatabaseAddTableInsertRows() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		SQLException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
            InvalidReferenceException
	{

		//
		// Setup
		//

		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

		MySqlProperties mySqlProperties = MySqlProperties.get();

		// Resource
		MySqlDatabaseResourcePlugin resourcePlugin = new MySqlDatabaseResourcePlugin();

		Resource resource = new ResourceImpl(
			UUID.randomUUID(),
			Wildebeest.MySqlDatabase,
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
			Optional.of(created.getStateId().toString())));

		// Migration: Created to Initial Schema
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			Optional.of(created.getStateId().toString()),
			Optional.of(initialSchema.getStateId().toString()),
			MySqlElementFixtures.productCatalogueDatabase()));

		// Migration: Initial Schema to Populated
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			Optional.of(initialSchema.getStateId().toString()),
			Optional.of(populated.getStateId().toString()),
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
			wildebeestApi.migrate(
				resource,
				instance,
				Optional.of(populated.getStateId().toString()));
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

	@Test
	public void loadMySqlDatabaseResource() throws
		LoaderFault,
		PluginBuildException,
            InvalidReferenceException
	{
		// Setup
		ProductCatalogueMySqlDatabaseResource prodCatResource = new ProductCatalogueMySqlDatabaseResource();

		DomResourceLoader resourceLoader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			prodCatResource.getResourceXml());

		// Execute
		Resource resource = resourceLoader.load(new File("."));

		// Verify
		assertResource(resource);
	}

	@Test
	public void loadMySqlDatabaseInstance() throws
		LoaderFault,
		PluginBuildException
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

	@Test
	public void loadMySqlDatabaseResourceAndInstanceAndMigrate() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		LoaderFault,
		MigrationFailedException,
		MigrationNotPossibleException,
		PluginBuildException,
		SQLException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{

		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.withFactoryResourcePlugins()
			.withFactoryPluginManager()
			.get();

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

		try
		{
			wildebeestApi.migrate(
				resource,
				instance,
				Optional.of(ProductCatalogueMySqlDatabaseResource.StateIdInitialReferenceDataLoaded.toString()));
		}
		finally
		{
			MySqlUtil.dropDatabase((MySqlDatabaseInstance)instance, databaseName);
		}

	}

	private static void assertResource(Resource resource)
	{
		if (resource == null) throw new ArgumentNullException("resource");

		Asserts.assertResource(
			ProductCatalogueMySqlDatabaseResource.ResourceId,
			"Product Catalogue Database",
			resource,
			"resource");
	}

	private static XmlBuilder instance(
		String databaseName)
	{
		if (databaseName == null) throw new ArgumentNullException("databaseName");
		if ("".equals(databaseName)) throw new IllegalArgumentException("databaseName cannot be blank");

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
		if (databaseName == null) throw new ArgumentNullException("databaseName");
		if ("".equals(databaseName)) throw new IllegalArgumentException("databaseName");

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
