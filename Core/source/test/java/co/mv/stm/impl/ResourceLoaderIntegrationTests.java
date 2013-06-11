package co.mv.stm.impl;

import co.mv.stm.AssertionFailedException;
import co.mv.stm.IndeterminateStateException;
import co.mv.stm.Resource;
import co.mv.stm.TransitionFailedException;
import co.mv.stm.TransitionNotPossibleException;
import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.impl.database.mysql.MySqlDatabaseResource;
import co.mv.stm.impl.database.mysql.MySqlDatabaseResourceInstance;
import co.mv.stm.impl.database.mysql.MySqlElementFixtures;
import co.mv.stm.impl.database.mysql.MySqlProperties;
import co.mv.stm.service.AssertionBuilder;
import co.mv.stm.service.ResourceBuilder;
import co.mv.stm.service.TransitionBuilder;
import co.mv.stm.service.dom.DomResourceLoader;
import co.mv.stm.service.dom.XmlBuilder;
import co.mv.stm.service.dom.database.RowExistsDomAssertionBuilder;
import co.mv.stm.service.dom.database.SqlScriptDomTransitionBuilder;
import co.mv.stm.service.dom.mysql.MySqlCreateDatabaseDomTransitionBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseDoesNotExistDomAssertionBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseDomResourceBuilder;
import co.mv.stm.service.dom.mysql.MySqlDatabaseExistsDomAssertionBuilder;
import co.mv.stm.service.dom.mysql.MySqlTableDoesNotExistDomAssertionBuilder;
import co.mv.stm.service.dom.mysql.MySqlTableExistsDomAssertionBuilder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class ResourceLoaderIntegrationTests
{
	@Test public void loadAndTransitionMySqlResourceFromXml() throws
		IndeterminateStateException,
		AssertionFailedException,
		TransitionNotPossibleException,
		TransitionFailedException,
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
		
		UUID createDatabaseTransitionId = UUID.randomUUID();
		UUID loadSchemaTransitionId = UUID.randomUUID();
		UUID insertRefDataTransitionId = UUID.randomUUID();
		
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
							.openAssertion("RowExistsAssertion", rowExistsAssertionId, "UserBase RealmTypeRef exists")
								.openElement("sql").openCdata()
									.text("SELECT * FROM RealmTypeRef WHERE RealmTypeRcd = 'UB';")
								.closeCdata().closeElement("sql")
							.closeAssertion()
						.closeAssertions()
					.closeState()
				.closeStates()
				.openTransitions()
					.openTransition("MySqlCreateDatabase", createDatabaseTransitionId, null, databaseCreatedStateId)
					.closeTransition()
					.openTransition("SqlScript", loadSchemaTransitionId, databaseCreatedStateId, initialSchemaCreatedStateId)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.realmTypeRefCreateTableStatement())
						.closeCdata().closeElement("sql")
					.closeTransition()
					.openTransition("SqlScript", insertRefDataTransitionId, initialSchemaCreatedStateId, initialReferenceDataLoadedStateId)
						.openElement("sql").openCdata()
							.text(MySqlElementFixtures.realmTypeRefInsertUserBaseRow())
						.closeCdata().closeElement("sql")
					.closeTransition()
				.closeTransitions()
			.closeResource();

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("MySqlDatabase", new MySqlDatabaseDomResourceBuilder());
		
		Map<String, AssertionBuilder> assertionBuilders = new HashMap<String, AssertionBuilder>();
		assertionBuilders.put("RowExistsAssertion", new RowExistsDomAssertionBuilder());
		assertionBuilders.put("MySqlDatabaseDoesNotExist", new MySqlDatabaseDoesNotExistDomAssertionBuilder());
		assertionBuilders.put("MySqlDatabaseExists", new MySqlDatabaseExistsDomAssertionBuilder());
		assertionBuilders.put("MySqlTableDoesNotExist", new MySqlTableDoesNotExistDomAssertionBuilder());
		assertionBuilders.put("MySqlTableExists", new MySqlTableExistsDomAssertionBuilder());
		
		Map<String, TransitionBuilder> transitionBuilders = new HashMap<String, TransitionBuilder>();
		transitionBuilders.put("MySqlCreateDatabase", new MySqlCreateDatabaseDomTransitionBuilder());
		transitionBuilders.put("SqlScript", new SqlScriptDomTransitionBuilder());
		
		DomResourceLoader resourceBuilder = new DomResourceLoader(
			resourceBuilders,
			assertionBuilders,
			transitionBuilders,
			resourceXml.toString());

		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			MySqlProperties.get().getHostName(),
			MySqlProperties.get().getPort(),
			MySqlProperties.get().getUsername(),
			MySqlProperties.get().getPassword(),
			"StmTest");
		
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
		
		// Transitions
		Assert.assertEquals("resource.transitions.size", 3, resource.getTransitions().size());
		AssertExtensions.assertTransition(
			createDatabaseTransitionId, null, databaseCreatedStateId,
			resource.getTransitions().get(0), "resource.transitions[0]");
		AssertExtensions.assertTransition(
			loadSchemaTransitionId, databaseCreatedStateId, initialSchemaCreatedStateId,
			resource.getTransitions().get(1), "resource.transitions[1]");
		AssertExtensions.assertTransition(
			insertRefDataTransitionId, initialSchemaCreatedStateId, initialReferenceDataLoadedStateId,
			resource.getTransitions().get(2), "resource.transitions[2]");
		
		//
		// Execute - Transition
		//
		
		try
		{
			resource.transition(instance, initialReferenceDataLoadedStateId);
		}
		finally
		{
		
			//
			// Tear-Down
			//

			DatabaseHelper.execute(instance.getInfoDataSource(), "DROP Database StmTest");
			
		}

	}
}