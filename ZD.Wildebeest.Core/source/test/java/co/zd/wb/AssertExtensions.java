// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb;

import co.zd.wb.fake.FakeAssertion;
import co.zd.wb.fake.FakeMigration;
import co.zd.wb.plugin.ansisql.AnsiSqlTableDoesNotExistAssertion;
import co.zd.wb.plugin.ansisql.AnsiSqlTableExistsAssertion;
import co.zd.wb.plugin.mysql.MySqlDatabaseInstance;
import java.util.UUID;
import org.junit.Assert;

public class AssertExtensions
{
	
	//
	// Core
	//
	
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
		int expectedSeqNum,
		Assertion actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
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
	
	public static void assertMigration(
		UUID expectedMigrationId,
		UUID expectedFromStateId,
		UUID expectedToStateId,
		Migration actual,
		String name)
	{
		if (expectedMigrationId == null) { throw new IllegalArgumentException("expectedMigrationId cannot be null"); }
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		Assert.assertEquals(name + ".migrationId", expectedMigrationId, actual.getMigrationId());

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
	
	//
	// Fake
	//
	
	public static void assertFakeMigration(
		UUID expectedMigrationId,
		UUID expectedFromStateId,
		UUID expectedToStateId,
		String expectedTag,
		FakeMigration actual,
		String name)
	{
		if (actual == null) { throw new IllegalArgumentException("actual cannot be null"); }
		if (name == null) { throw new IllegalArgumentException("name cannot be null"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }

		AssertExtensions.assertMigration(expectedMigrationId, expectedFromStateId, expectedToStateId, actual, name);
		
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
		Assert.assertEquals("databaseName", expectedDatabaseName, db.getDatabaseName());
	}
}
