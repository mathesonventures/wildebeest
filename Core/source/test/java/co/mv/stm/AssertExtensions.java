package co.mv.stm;

import co.mv.stm.model.Transition;
import co.mv.stm.model.Assertion;
import co.mv.stm.model.Instance;
import co.mv.stm.model.AssertionResult;
import co.mv.stm.model.Resource;
import co.mv.stm.model.State;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.base.FakeAssertion;
import co.mv.stm.model.base.FakeTransition;
import co.mv.stm.model.mysql.MySqlDatabaseInstance;
import java.util.UUID;
import org.junit.Assert;

public class AssertExtensions
{
	public static void assertResource(
		Class expectedClass,
		UUID expectedResourceId,
		String expectedName,
		Resource actual,
		String name)
	{
		if (expectedClass == null) { throw new IllegalArgumentException("expectedClass cannot be null"); }
		if (expectedResourceId == null) { throw new IllegalArgumentException("expectedResourceId cannot be null"); }
		if (expectedName == null) { throw new IllegalArgumentException("expectedName cannot be null"); }
		if ("".equals(expectedName)) { throw new IllegalArgumentException("expectedName cannot be empty"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".class", expectedClass, actual.getClass());
		Assert.assertEquals(name + ".resourceId", expectedResourceId, actual.getResourceId());
		Assert.assertEquals(name + ".name", expectedName, actual.getName());
	}
	
	public static void assertState(
		UUID expectedStateId,
		String expectedLabel,
		State actual,
		String name)
	{
		if (expectedStateId == null) { throw new IllegalArgumentException("expectedStateId cannot be null"); }
		if (expectedLabel == null) { throw new IllegalArgumentException("expectedLabel cannot be null"); }
		if ("".equals(expectedLabel)) { throw new IllegalArgumentException("expectedLabel cannot be empty"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".stateId", expectedStateId, actual.getStateId());
		Assert.assertTrue("name.label expected to be set", actual.hasLabel());
		Assert.assertEquals(name + ".label", expectedLabel, actual.getLabel());
	}
	
	public static void assertState(
		UUID expectedStateId,
		State actual,
		String name)
	{
		if (expectedStateId == null) { throw new IllegalArgumentException("expectedStateId cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".stateId", expectedStateId, actual.getStateId());
		Assert.assertFalse("name.label expected to be unset", actual.hasLabel());
	}
	
	public static void assertAssertion(
		UUID expectedAssertionId,
		String expectedName,
		int expectedSeqNum,
		Assertion actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId cannot be null"); }
		if (expectedName == null) { throw new IllegalArgumentException("expectedName cannot be null"); }
		if ("".equals(expectedName)) { throw new IllegalArgumentException("expectedName cannot be empty"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		Assert.assertEquals(name + ".name", expectedName, actual.getName());
		Assert.assertEquals(name + ".seqNum", expectedSeqNum, actual.getSeqNum());
	}
	
	public static void assertFakeAssertion(
		UUID expectedAssertionId,
		String expectedName,
		int expectedSeqNum,
		String expectedTag,
		FakeAssertion actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId cannot be null"); }
		if (expectedName == null) { throw new IllegalArgumentException("expectedName cannot be null"); }
		if ("".equals(expectedName)) { throw new IllegalArgumentException("expectedName cannot be empty"); }
		if (expectedTag == null) { throw new IllegalArgumentException("expectedTag cannot be null"); }
		if ("".equals(expectedTag)) { throw new IllegalArgumentException("expectedTag cannot be empty"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".tag", expectedTag, actual.getTag());
	}
	
	public static void assertTransition(
		UUID expectedTransitionId,
		UUID expectedFromStateId,
		UUID expectedToStateId,
		Transition actual,
		String name)
	{
		if (expectedTransitionId == null) { throw new IllegalArgumentException("expectedTransitionId cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".transitionId", expectedTransitionId, actual.getTransitionId());

		if (expectedFromStateId == null)
		{
			Assert.assertFalse(name + ".fromStateId expected to be unset", actual.hasFromStateId());
		}
		else
		{
			Assert.assertEquals(name + ".fromStateId", expectedFromStateId, actual.getFromStateId());
		}

		if (expectedToStateId == null)
		{
			Assert.assertFalse(name + ".toStateId expected to be unset", actual.hasToStateId());
		}
		else
		{
			Assert.assertEquals(name + ".toStateId", expectedToStateId, actual.getToStateId());
		}
	}
	
	public static void assertFakeTransition(
		UUID expectedTransitionId,
		UUID expectedFromStateId,
		UUID expectedToStateId,
		String expectedTag,
		FakeTransition actual,
		String name)
	{
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		AssertExtensions.assertTransition(expectedTransitionId, expectedFromStateId, expectedToStateId, actual, name);
		
		Assert.assertEquals(name + ".tag", expectedTag, actual.getTag());
	}
	
	public static void assertAssertionResponse(
		boolean expectedResult,
		String expectedMessage,
		AssertionResponse actual,
		String name)
	{
		if (expectedMessage == null) { throw new IllegalArgumentException("expectedMessage"); }
		if (actual == null) { throw new IllegalArgumentException("actual"); }
		if (name == null) { throw new IllegalArgumentException("name"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".result", expectedResult, actual.getResult());
		Assert.assertEquals(name + ".message", expectedMessage, actual.getMessage());
	}
	
	public static void assertAssertionResult(
		UUID expectedAssertionId,
		boolean expectedResult,
		String expectedMessage,
		AssertionResult actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId"); }
		if (expectedMessage == null) { throw new IllegalArgumentException("expectedMessage"); }
		if (actual == null) { throw new IllegalArgumentException("actual"); }
		if (name == null) { throw new IllegalArgumentException("name"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be blank"); }

		Assert.assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		Assert.assertEquals(name + ".result", expectedResult, actual.getResult());
		Assert.assertEquals(name + ".message", expectedMessage, actual.getMessage());
	}
	
	public static void assertInstance(
		Class expectedClass,
		Instance actual,
		String name)
	{
		if (expectedClass == null) { throw new IllegalArgumentException("expectedClass cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) {throw new IllegalArgumentException("name cannot be blank"); }
		
		Assert.assertEquals("actual.class", expectedClass, actual.getClass());
	}
	
	public static void assertMySqlDatabaseInstance(
		String expectedHostName,
		int expectedPort,
		String expectedAdminUsername,
		String expectedAdminPassword,
		String expectedSchemaName,
		Instance actual,
		String name)
	{
		if (expectedHostName == null) { throw new IllegalArgumentException("expectedHostName cannot be null"); }
		if ("".equals(expectedHostName)) { throw new IllegalArgumentException("expectedHostName cannot be empty"); }
		if (expectedAdminUsername == null) { throw new IllegalArgumentException("expectedAdminUsername cannot be null"); }
		if ("".equals(expectedAdminUsername)) { throw new IllegalArgumentException("expectedAdminUsername cannot be empty"); }
		if (expectedAdminPassword == null) { throw new IllegalArgumentException("expectedAdminPassword cannot be null"); }
		if ("".equals(expectedAdminPassword)) { throw new IllegalArgumentException("expectedAdminPassword cannot be empty"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) {throw new IllegalArgumentException("name cannot be blank"); }
		
		AssertExtensions.assertInstance(MySqlDatabaseInstance.class, actual, name);
		
		MySqlDatabaseInstance db = (MySqlDatabaseInstance)actual;
		
		Assert.assertEquals("hostName", expectedHostName, db.getHostName());
		Assert.assertEquals("port", expectedPort, db.getPort());
		Assert.assertEquals("adminUsername", expectedAdminUsername, db.getAdminUsername());
		Assert.assertEquals("adminPassword", expectedAdminPassword, db.getAdminPassword());
		Assert.assertEquals("schemaName", expectedSchemaName, db.getSchemaName());
		
	}
}
