package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.SqlScriptTransition;
import co.mv.stm.AssertionFailedException;
import co.mv.stm.IndeterminateStateException;
import co.mv.stm.Instance;
import co.mv.stm.State;
import co.mv.stm.TransitionFailedException;
import co.mv.stm.TransitionNotPossibleException;
import co.mv.stm.AssertExtensions;
import co.mv.stm.Resource;
import co.mv.stm.impl.ImmutableState;
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
	private static final UUID CreatedStateId = UUID.randomUUID();

	@Test public void createDatabaseAddTableInsertRows() throws
		IndeterminateStateException,
		AssertionFailedException,
		TransitionNotPossibleException,
		TransitionFailedException,
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
		
		// Transition: to Created
		resource.getTransitions().add(new MySqlCreateDatabaseTransition(
			UUID.randomUUID(),
			null,
			created.getStateId()));
		
		// Transition: Created to Initial Schema
		resource.getTransitions().add(new SqlScriptTransition(
			UUID.randomUUID(),
			created.getStateId(),
			initialSchema.getStateId(),
			MySqlElementFixtures.realmTypeRefCreateTableStatement()));
		
		// Transition: Initial Schema to Populated
		resource.getTransitions().add(new SqlScriptTransition(
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
			resource.transition(instance, populated.getStateId());
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
			DomPlugins.transitionBuilders(),
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
	
	@Test public void loadMySqlDatabaseResourceAndInstanceAndMigrate() throws IndeterminateStateException, AssertionFailedException, TransitionNotPossibleException, TransitionFailedException, SQLException
	{
		
		//
		// Resource
		//
		
		// Fixture
		DomResourceLoader resourceLoader = new DomResourceLoader(
			DomPlugins.resourceBuilders(),
			DomPlugins.assertionBuilders(),
			DomPlugins.transitionBuilders(),
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
			resource.transition(instance, CreatedStateId);
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
					.openState(CreatedStateId, "Created")
						.openAssertions()
							.openAssertion("MySqlDatabaseExists", UUID.randomUUID(), "Database exists")
							.closeAssertion()
						.closeAssertions()
					.closeState()
				.closeStates()
				.openTransitions()
					.openTransition("MySqlCreateDatabase", ResourceId, null, CreatedStateId)
					.closeTransition()
				.closeTransitions()
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
