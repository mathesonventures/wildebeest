package co.zd.wb.model.base;

import co.zd.wb.AssertExtensions;
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
import co.zd.wb.service.dom.XmlBuilder;
import java.sql.SQLException;
import java.util.UUID;
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
		
		UUID resourceId = UUID.randomUUID();
		
		UUID databaseCreatedStateId = UUID.randomUUID();
			UUID databaseExistsAssertionId = UUID.randomUUID();
		UUID initialSchemaCreatedStateId = UUID.randomUUID();
			UUID tableExistsAssertionId = UUID.randomUUID();
		UUID initialReferenceDataLoadedStateId = UUID.randomUUID();
			UUID rowExistsAssertionId = UUID.randomUUID();
		
		UUID createDatabaseMigrationId = UUID.randomUUID();
		UUID loadSchemaMigrationId = UUID.randomUUID();
		UUID insertRefDataMigrationId = UUID.randomUUID();
		
		XmlBuilder resourceXml = new XmlBuilder();
		resourceXml
			.processingInstruction()
			.openResource(resourceId, "MySqlDatabase", "MV AAA Database")
				.openStates()
					.openState(databaseCreatedStateId, "Database created")
						.openAssertions()
							.openAssertion("MySqlDatabaseExists", databaseExistsAssertionId, "Database exists")
							.closeAssertion()
						.closeAssertions()
					.closeState()
					.openState(initialSchemaCreatedStateId, "Initial schema created")
						.openAssertions()
							.openAssertion("MySqlTableExists", tableExistsAssertionId, "Table exists")
								.openElement("tableName").text("RealmTypeRef").closeElement("tableName")
							.closeAssertion()
						.closeAssertions()
					.closeState()
					.openState(initialReferenceDataLoadedStateId, "Initial reference data loaded")
						.openAssertions()
							.openAssertion("RowExists", rowExistsAssertionId, "UserBase RealmTypeRef exists")
								.openElement("sql").openCdata()
									.text("SELECT * FROM RealmTypeRef WHERE RealmTypeRcd = 'UB';")
								.closeCdata().closeElement("sql")
							.closeAssertion()
						.closeAssertions()
					.closeState()
				.closeStates()
				.openMigrations()
					.openMigration("MySqlCreateDatabase", createDatabaseMigrationId, null, databaseCreatedStateId)
					.closeMigration()
					.openMigration("SqlScript", loadSchemaMigrationId, databaseCreatedStateId, initialSchemaCreatedStateId)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.realmTypeRefCreateTableStatement())
						.closeCdata().closeElement("sql")
					.closeMigration()
					.openMigration("SqlScript", insertRefDataMigrationId, initialSchemaCreatedStateId, initialReferenceDataLoadedStateId)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.realmTypeRefInsertUserBaseRow())
						.closeCdata().closeElement("sql")
					.closeMigration()
				.closeMigrations()
			.closeResource();

		DomResourceLoader resourceBuilder = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.migrationBuilders(),
			resourceXml.toString());

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
			MySqlDatabaseResource.class, resourceId, "MV AAA Database",
			resource, "resource");
		
		// States
		Assert.assertEquals("resource.states.size", 3, resource.getStates().size());
		AssertExtensions.assertState(
			databaseCreatedStateId, "Database created",
			resource.getStates().get(0), "state[0]");
		AssertExtensions.assertState(
			initialSchemaCreatedStateId, "Initial schema created",
			resource.getStates().get(1), "state[1]");
		AssertExtensions.assertState(
			initialReferenceDataLoadedStateId, "Initial reference data loaded",
			resource.getStates().get(2), "state[2]");
		
		// Migrations
		Assert.assertEquals("resource.migrations.size", 3, resource.getMigrations().size());
		AssertExtensions.assertMigration(
			createDatabaseMigrationId, null, databaseCreatedStateId,
			resource.getMigrations().get(0), "resource.migrations[0]");
		AssertExtensions.assertMigration(
			loadSchemaMigrationId, databaseCreatedStateId, initialSchemaCreatedStateId,
			resource.getMigrations().get(1), "resource.migrations[1]");
		AssertExtensions.assertMigration(
			insertRefDataMigrationId, initialSchemaCreatedStateId, initialReferenceDataLoadedStateId,
			resource.getMigrations().get(2), "resource.migrations[2]");
		
		//
		// Execute - Migrate
		//
		
		try
		{
			resource.migrate(new PrintStreamLogger(System.out), instance, initialReferenceDataLoadedStateId);
		}
		finally
		{
			MySqlUtil.dropDatabase(instance, databaseName);
		}

	}
}