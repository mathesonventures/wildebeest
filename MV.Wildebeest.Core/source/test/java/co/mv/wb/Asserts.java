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

package co.mv.wb;

import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.ansisql.AnsiSqlTableDoesNotExistAssertion;
import co.mv.wb.plugin.ansisql.AnsiSqlTableExistsAssertion;
import co.mv.wb.plugin.fake.FakeAssertion;
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.FakeMigration;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;
import org.junit.Assert;

import java.util.Optional;
import java.util.UUID;

/**
 * Helpers for asserting the state of Wildebeest entities.
 *
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class Asserts
{
	
	//
	// Core
	//
	
	public static void assertResource(
		UUID expectedResourceId,
		String expectedName,
		Resource actual,
		String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".resourceId", expectedResourceId, actual.getResourceId());
		Assert.assertEquals(name + ".name", expectedName, actual.getName());
	}
	
	public static void assertState(
		UUID expectedStateId,
		Optional<String> expectedLabel,
		State actual,
		String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".stateId", expectedStateId, actual.getStateId());
		Assert.assertEquals(name + ".label", expectedLabel, actual.getLabel());
	}
	
	public static void assertAssertion(
		UUID expectedAssertionId,
		int expectedSeqNum,
		Assertion actual,
		String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
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
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".tag", expectedTag, actual.getTag());
	}
	
	public static void assertMigration(
		UUID expectedMigrationId,
		Optional<UUID> expectedFromStateId,
		Optional<UUID> expectedToStateId,
		Migration actual,
		String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".migrationId", expectedMigrationId, actual.getMigrationId());

		if (expectedFromStateId == null)
		{
			Assert.assertFalse(name + ".fromStateId expected to be unset", actual.getFromStateId().isPresent());
		}
		else
		{
			Assert.assertEquals(name + ".fromStateId", expectedFromStateId, actual.getFromStateId());
		}

		if (expectedToStateId == null)
		{
			Assert.assertFalse(name + ".toStateId expected to be unset", actual.getToStateId().isPresent());
		}
		else
		{
			Assert.assertEquals(name + ".toStateId", expectedToStateId, actual.getToStateId());
		}
	}
	
	public static void assertAssertionResponse(
		boolean expectedResult,
		String expectedMessage,
		AssertionResponse actual,
		String name)
	{
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
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) {throw new IllegalArgumentException("name cannot be blank"); }
		
		Assert.assertEquals("actual.class", expectedClass, actual.getClass());
	}
	
	//
	// Fake
	//

	public static boolean verifyFakeResource(
		UUID expectedResourceId,
		ResourceType expectedResourceType,
		String expectedName,
		Resource actual)
	{
		boolean result = true;

		result &= expectedResourceId.equals(actual.getResourceId());
		result &= expectedResourceType.getUri().equals(actual.getType().getUri());
		result &= expectedName.equals(actual.getName());

		return result;
	}

	public static boolean verifyFakeInstance(
		Instance actual)
	{
		boolean result = true;

		result &= FakeInstance.class.equals(actual.getClass());

		return result;
	}

	public static void assertFakeMigration(
		UUID expectedMigrationId,
		Optional<UUID> expectedFromStateId,
		Optional<UUID> expectedToStateId,
		String expectedTag,
		FakeMigration actual,
		String name)
	{
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Asserts.assertMigration(expectedMigrationId, expectedFromStateId, expectedToStateId, actual, name);
		
		Assert.assertEquals(name + ".tag", expectedTag, actual.getTag());
	}

	//
	// AnsiSql
	//
	
	public static void assertAnsiSqlTableExistsAssertion(
		UUID expectedAssertionId,
		String expectedSchemaName,
		String expectedTableName,
		AnsiSqlTableExistsAssertion actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId cannot be null"); }
		if (expectedSchemaName == null) { throw new IllegalArgumentException("expectedSchemaName cannot be null"); }
		if (expectedTableName == null) { throw new IllegalArgumentException("expectedTableName cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		Assert.assertEquals(name + ".schemaName", expectedSchemaName, actual.getSchemaName());
		Assert.assertEquals(name + ".tableName", expectedTableName, actual.getTableName());
	}
	
	public static void assertAnsiSqlTableDoesNotExistAssertion(
		UUID expectedAssertionId,
		String expectedSchemaName,
		String expectedTableName,
		AnsiSqlTableDoesNotExistAssertion actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId cannot be null"); }
		if (expectedSchemaName == null) { throw new IllegalArgumentException("expectedSchemaName cannot be null"); }
		if (expectedTableName == null) { throw new IllegalArgumentException("expectedTableName cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		Assert.assertEquals(name + ".schemaName", expectedSchemaName, actual.getSchemaName());
		Assert.assertEquals(name + ".tableName", expectedTableName, actual.getTableName());
	}
	
	//
	// MySql
	//
	
	public static void assertMySqlDatabaseInstance(
		String expectedHostName,
		int expectedPort,
		String expectedAdminUsername,
		String expectedAdminPassword,
		String expectedDatabaseName,
		Instance actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");
		if ("".equals(name)) throw new ArgumentNullException("name cannot be blank");
		
		Asserts.assertInstance(MySqlDatabaseInstance.class, actual, name);
		
		MySqlDatabaseInstance db = (MySqlDatabaseInstance)actual;
		
		Assert.assertEquals("hostName", expectedHostName, db.getHostName());
		Assert.assertEquals("port", expectedPort, db.getPort());
		Assert.assertEquals("adminUsername", expectedAdminUsername, db.getAdminUsername());
		Assert.assertEquals("adminPassword", expectedAdminPassword, db.getAdminPassword());
		Assert.assertEquals("databaseName", expectedDatabaseName, db.getDatabaseName());
	}

	public static boolean verifyMySqlDatabaseInstance(
		String expectedHostName,
		int expectedPort,
		String expectedAdminUsername,
		String expectedAdminPassword,
		String expectedDatabaseName,
		Instance actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");
		if ("".equals(name)) throw new ArgumentNullException("name cannot be blank");

		boolean result = true;

		result &= MySqlDatabaseInstance.class.equals(actual.getClass());

		if (result)
		{
			MySqlDatabaseInstance db = (MySqlDatabaseInstance) actual;

			result &= expectedHostName.equals(db.getHostName());
			result &= expectedPort == db.getPort();
			result &= expectedAdminUsername.equals(db.getAdminUsername());
			result &= expectedAdminPassword.equals(db.getAdminPassword());
			result &= expectedDatabaseName.equals(db.getDatabaseName());
		}

		return result;
	}
}
