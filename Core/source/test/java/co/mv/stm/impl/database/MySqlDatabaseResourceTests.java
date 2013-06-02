package co.mv.stm.impl.database;

import co.zd.helium.fixture.MySqlDatabaseFixture;
import co.mv.stm.impl.database.mysql.MySqlDatabaseResource;
import co.mv.stm.impl.database.mysql.MySqlDatabaseResourceInstance;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.impl.ImmutableState;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class MySqlDatabaseResourceTests
{
	public MySqlDatabaseResourceTests()
	{
	}
	
	@Test public void currentStateForNonExistentDatabaseSucceds() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlDatabaseResource resource = new MySqlDatabaseResource();
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"non_existent_schema");

		//
		// Execute
		//
		
		UUID stateId = resource.currentState(instance);
		
		//
		// Assert Results
		//
		
		Assert.assertEquals("stateId", null, stateId);
		
	}
	
	@Test public void currentStateForExistentDatabaseSucceds() throws IndeterminateStateException
	{
		
		//
		// Fixture Setup
		//
		
		UUID knownStateId = UUID.randomUUID();
		
		StringBuilder setupScript = new StringBuilder();
		setupScript
			.append("CREATE TABLE `StmState` (`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`)) ")
				.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;")
			.append("INSERT INTO `StmState`(`StateId`) VALUES('").append(knownStateId.toString()).append("');");

		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm",
			setupScript.toString());
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource();
		resource.getStates().add(new ImmutableState(knownStateId));
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			database.getDatabaseName());

		//
		// Execute
		//
		
		UUID stateId = resource.currentState(instance);
		
		//
		// Assert Results
		//
		
		Assert.assertEquals("stateId", knownStateId, stateId);
		
		//
		// Fixture Tear-Down
		//
		
		database.tearDown();
		
	}
	
	@Test public void currentStateForDatabaseWithMultipleStateRowsFails()
	{
		
		//
		// Fixture Setup
		//
		
		StringBuilder setupScript = new StringBuilder();
		setupScript
			.append("CREATE TABLE `StmState` (`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`)) ")
				.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;")
			.append("INSERT INTO `StmState`(`StateId`) VALUES('").append(UUID.randomUUID()).append("');")
			.append("INSERT INTO `StmState`(`StateId`) VALUES('").append(UUID.randomUUID()).append("');");

		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm",
			setupScript.toString());
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource();
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			database.getDatabaseName());

		//
		// Execute
		//
		
		IndeterminateStateException caught = null;
		
		try
		{
			resource.currentState(instance);
			
			Assert.fail("IndeterminateStateException expected");
		}
		catch(IndeterminateStateException e)
		{
			caught = e;
		}
		
		//
		// Assert Results
		//
		
		Assert.assertTrue("exception message", caught.getMessage().startsWith("Multiple rows found"));
		
		//
		// Fixture Tear-Down
		//
		
		database.tearDown();
		
	}
	
	@Test public void currentStateForDatabaseWithUnknownStateIdDeclaredFails()
	{
		
		//
		// Fixture Setup
		//
		
		UUID knownStateId = UUID.randomUUID();
		
		StringBuilder setupScript = new StringBuilder();
		setupScript
			.append("CREATE TABLE `StmState` (`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`)) ")
				.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;")
			.append("INSERT INTO `StmState`(`StateId`) VALUES('").append(knownStateId.toString()).append("');");

		MySqlDatabaseFixture database = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm",
			setupScript.toString());
		database.setUp();

		MySqlDatabaseResource resource = new MySqlDatabaseResource();
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			database.getDatabaseName());

		//
		// Execute
		//
		
		IndeterminateStateException caught = null;
		try
		{
			resource.currentState(instance);
			
			Assert.fail("IndeterminateStateException expected");
		}
		catch(IndeterminateStateException e)
		{
			caught = e;
		}
		
		//
		// Assert Results
		//
		
		Assert.assertTrue(
			"exception message",
			caught.getMessage().startsWith("The resource is declared to be in state"));
		
		//
		// Fixture Tear-Down
		//
		
		database.tearDown();
		
	}
	
	@Test public void currentStateForDatabaseWithInvalidStateTableSchemaFaults()
	{
		throw new UnsupportedOperationException();
	}
}