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
import co.mv.wb.plugin.fake.FakeInstance;
import co.mv.wb.plugin.fake.SetTagMigration;
import co.mv.wb.plugin.fake.TagAssertion;
import co.mv.wb.plugin.generaldatabase.AnsiSqlTableDoesNotExistAssertion;
import co.mv.wb.plugin.generaldatabase.AnsiSqlTableExistsAssertion;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;
import org.junit.Assert;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Helpers for asserting the state of Wildebeest entities.
 *
 * @since 1.0
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
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".resourceId", expectedResourceId, actual.getResourceId());
		assertEquals(name + ".name", expectedName, actual.getName());
	}

	public static void assertState(
		UUID expectedStateId,
		Optional<String> expectedLabel,
		State actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".stateId", expectedStateId, actual.getStateId());
		assertEquals(name + ".label", expectedLabel, actual.getLabel());
	}

	public static void assertState(
		UUID expectedStateId,
		Optional<String> expectedLabel,
		Optional<String> expectedDescription,
		State actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".stateId", expectedStateId, actual.getStateId());
		assertEquals(name + ".label", expectedLabel, actual.getLabel());
		assertEquals(name + ".description", expectedDescription, actual.getDescription());
	}

	public static void assertAssertion(
		UUID expectedAssertionId,
		int expectedSeqNum,
		Assertion actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		assertEquals(name + ".seqNum", expectedSeqNum, actual.getSeqNum());
	}

	public static void assertTagAssertion(
		UUID expectedAssertionId,
		int expectedSeqNum,
		String expectedTag,
		TagAssertion actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		Asserts.assertAssertion(
			expectedAssertionId,
			expectedSeqNum,
			actual,
			name);

		assertEquals(name + ".tag", expectedTag, actual.getTag());
	}

	public static void assertMigration(
		UUID expectedMigrationId,
		Optional<UUID> expectedFromStateId,
		Optional<UUID> expectedToStateId,
		Migration actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".migrationId", expectedMigrationId, actual.getMigrationId());

		if (expectedFromStateId == null)
		{
			Assert.assertFalse(name + ".fromStateId expected to be unset", actual.getFromState().isPresent());
		}
		else
		{
			assertEquals(name + ".fromStateId", expectedFromStateId, actual.getFromState());
		}

		if (expectedToStateId == null)
		{
			Assert.assertFalse(name + ".toStateId expected to be unset", actual.getToState().isPresent());
		}
		else
		{
			assertEquals(name + ".toStateId", expectedToStateId, actual.getToState());
		}
	}

	public static void assertAssertionResponse(
		boolean expectedResult,
		String expectedMessage,
		AssertionResponse actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".result", expectedResult, actual.getResult());
		assertEquals(name + ".message", expectedMessage, actual.getMessage());
	}

	public static void assertAssertionResult(
		UUID expectedAssertionId,
		boolean expectedResult,
		String expectedMessage,
		AssertionResult actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		assertEquals(name + ".result", expectedResult, actual.getResult());
		assertEquals(name + ".message", expectedMessage, actual.getMessage());
	}

	public static void assertInstance(
		Class expectedClass,
		Instance actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals("actual.class", expectedClass, actual.getClass());
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

	public static void assertFakeInstance(
		String tag,
		Instance actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".class", FakeInstance.class, actual.getClass());

		FakeInstance actualT = (FakeInstance)actual;

		assertEquals(name + ".tag", tag, actualT.getTag());
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
		SetTagMigration actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		Asserts.assertMigration(expectedMigrationId, expectedFromStateId, expectedToStateId, actual, name);

		assertEquals(name + ".tag", expectedTag, actual.getTag());
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
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		assertEquals(name + ".schemaName", expectedSchemaName, actual.getSchemaName());
		assertEquals(name + ".tableName", expectedTableName, actual.getTableName());
	}

	public static void assertAnsiSqlTableDoesNotExistAssertion(
		UUID expectedAssertionId,
		String expectedSchemaName,
		String expectedTableName,
		AnsiSqlTableDoesNotExistAssertion actual,
		String name)
	{
		if (name == null) throw new ArgumentNullException("name");

		assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		assertEquals(name + ".schemaName", expectedSchemaName, actual.getSchemaName());
		assertEquals(name + ".tableName", expectedTableName, actual.getTableName());
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

		assertEquals("hostName", expectedHostName, db.getHostName());
		assertEquals("port", expectedPort, db.getPort());
		assertEquals("adminUsername", expectedAdminUsername, db.getAdminUsername());
		assertEquals("adminPassword", expectedAdminPassword, db.getAdminPassword());
		assertEquals("databaseName", expectedDatabaseName, db.getDatabaseName());
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
			MySqlDatabaseInstance db = (MySqlDatabaseInstance)actual;

			result &= expectedHostName.equals(db.getHostName());
			result &= expectedPort == db.getPort();
			result &= expectedAdminUsername.equals(db.getAdminUsername());
			result &= expectedAdminPassword.equals(db.getAdminPassword());
			result &= expectedDatabaseName.equals(db.getDatabaseName());
		}

		return result;
	}
}
