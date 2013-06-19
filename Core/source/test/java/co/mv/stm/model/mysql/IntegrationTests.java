package co.mv.stm.model.mysql;

import co.mv.stm.model.database.SqlScriptMigration;
import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.Instance;
import co.mv.stm.model.State;
import co.mv.stm.model.MigrationFailedException;
import co.mv.stm.model.MigrationNotPossibleException;
import co.mv.stm.AssertExtensions;
import co.mv.stm.model.Resource;
import co.mv.stm.model.base.ImmutableState;
import co.mv.stm.service.PrintStreamLogger;
import co.mv.stm.service.dom.DomInstanceLoader;
import co.mv.stm.service.dom.DomPlugins;
import co.mv.stm.service.dom.DomResourceLoader;
import co.mv.stm.service.dom.XmlBuilder;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class IntegrationTests
{
	private static final UUID ResourceId = UUID.randomUUID();
	private static final UUID StateIdCreated = UUID.randomUUID();
	private static final UUID StateIdSchemaLoaded = UUID.randomUUID();
	private static final UUID StateIdReferenceDataLoaded = UUID.randomUUID();
	private static final UUID MigrationIdCreateDatabase = UUID.randomUUID();
	private static final UUID MigrationIdLoadInitialSchema = UUID.randomUUID();
	private static final UUID MigrationIdLoadReferenceData = UUID.randomUUID();

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
			MySqlElementFixtures.realmTypeRefCreateTableStatement()));
		
		// Migration: Initial Schema to Populated
		resource.getMigrations().add(new SqlScriptMigration(
			UUID.randomUUID(),
			initialSchema.getStateId(),
			populated.getStateId(),
			MySqlElementFixtures.realmTypeRefInsertUserBaseRow()));

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
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			resource().toString());
		
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
	
	@Test public void loadMySqlDatabaseResourceAndInstanceAndMigrate() throws IndeterminateStateException, AssertionFailedException, MigrationNotPossibleException, MigrationFailedException, SQLException
	{
		
		//
		// Resource
		//
		
		// Fixture
		DomResourceLoader resourceLoader = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			resource().toString());
		
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
			resource.migrate(new PrintStreamLogger(System.out), instance, StateIdReferenceDataLoaded);
		}
		finally
		{
			MySqlUtil.dropDatabase((MySqlDatabaseInstance)instance, databaseName);
		}
		
	}
	
	private static XmlBuilder resource()
	{
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openResource(ResourceId, "MySqlDatabase", "Database")
				.openStates()
					.openState(StateIdCreated, "Created")
						.openAssertions()
							.openAssertion("MySqlDatabaseExists", UUID.randomUUID(), "Database exists")
							.closeAssertion()
						.closeAssertions()
					.closeState()
					.openState(StateIdSchemaLoaded, "Schema Loaded")
						.openAssertions()
							.openAssertion("MySqlTableExists", UUID.randomUUID(), "Table exists")
								.openElement("tableName").text("realmTypeRef").closeElement("tableName")
							.closeAssertion()
						.closeAssertions()
					.closeState()
					.openState(StateIdReferenceDataLoaded, "Reference Data Loaded")
						.openAssertions()
							.openAssertion("RowExists", UUID.randomUUID(), "RealmTypeRef UB exists")
								.openElement("sql").openCdata()
									.text("SELECT RealmTypeRcd FROM RealmTypeRef WHERE RealmTypeRcd = 'UB';")
								.closeCdata().closeElement("sql")
							.closeAssertion()
						.closeAssertions()
					.closeState()
				.closeStates()
				.openMigrations()
					.openMigration("MySqlCreateDatabase", MigrationIdCreateDatabase, null, StateIdCreated)
					.closeMigration()
					.openMigration("SqlScript", MigrationIdLoadInitialSchema, StateIdCreated, StateIdSchemaLoaded)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.realmTypeRefCreateTableStatement())
						.closeCdata().closeElement("sql")
					.closeMigration()
					.openMigration("SqlScript", MigrationIdLoadReferenceData, StateIdSchemaLoaded,
						StateIdReferenceDataLoaded)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.realmTypeRefInsertUserBaseRow())
						.closeCdata().closeElement("sql")
					.closeMigration()
				.closeMigrations()
			.closeResource();
		
		return resourceXml;
	}
	
	private static void assertResource(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource"); }
		
		AssertExtensions.assertResource(MySqlDatabaseResource.class, ResourceId, "Database", resource, "resource");
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
