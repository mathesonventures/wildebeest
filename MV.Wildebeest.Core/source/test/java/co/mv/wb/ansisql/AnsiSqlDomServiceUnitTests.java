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

package co.mv.wb.ansisql;

import co.mv.wb.AssertExtensions;
import co.mv.wb.FakeLogger;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.fixturecreator.FixtureCreator;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlTableDoesNotExistAssertion;
import co.mv.wb.plugin.ansisql.AnsiSqlTableExistsAssertion;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.dom.DomPlugins;
import co.mv.wb.service.dom.DomResourceLoader;
import java.io.File;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the DOM persistence services for ANSI SQL plugins.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class AnsiSqlDomServiceUnitTests
{
	@Test public void ansiSqlCreateDatabaseMigrationLoadFromValidDocument() throws MessagesException
	{
		// Setup
		UUID migrationId = UUID.randomUUID();
		UUID fromStateId = null;
		UUID toStateId = UUID.randomUUID();
		
		String xml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", UUID.randomUUID(), "Foo")
				.migration("AnsiSqlCreateDatabase", migrationId, fromStateId, toStateId)
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(new FakeLogger(), xml);

		// Execute
		Resource resource = loader.load(new File("."));
		
		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AnsiSqlCreateDatabaseMigration mT = ModelExtensions.As(
			resource.getMigrations().get(0),
			AnsiSqlCreateDatabaseMigration.class);
		Assert.assertNotNull("resource.migrations[0] expected to be of type AnsiSqlCreateDatabaseMigration", mT);
		Assert.assertEquals(
			"resource.migrations[0].id",
			migrationId,
			mT.getMigrationId());
		Assert.assertFalse(
			"resource.migrations[0].hasFromStateId",
			mT.hasFromStateId());
		Assert.assertEquals(
			"resource.migrations[0].toStateId",
			toStateId,
			mT.getToStateId());
	}

	@Test public void ansiSqlDropDatabaseMigrationLoadFromValidDocument() throws MessagesException
	{
		// Setup
		UUID migrationId = UUID.randomUUID();
		UUID fromStateId = null;
		UUID toStateId = UUID.randomUUID();
		
		String xml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", UUID.randomUUID(), "Foo")
				.migration("AnsiSqlDropDatabase", migrationId, fromStateId, toStateId)
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(new FakeLogger(), xml);

		// Execute
		Resource resource = loader.load(new File("."));
		
		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AnsiSqlDropDatabaseMigration mT = ModelExtensions.As(
			resource.getMigrations().get(0),
			AnsiSqlDropDatabaseMigration.class);
		Assert.assertNotNull("resource.migrations[0] expected to be of type AnsiSqlDropDatabaseMigration", mT);
		Assert.assertEquals(
			"resource.migrations[0].id",
			migrationId,
			mT.getMigrationId());
		Assert.assertFalse(
			"resource.migrations[0].hasFromStateId",
			mT.hasFromStateId());
		Assert.assertEquals(
			"resource.migrations[0].toStateId",
			toStateId,
			mT.getToStateId());
	}
	
	@Test public void ansiSqlTableExistsAssertionLoadFromValidDocument() throws MessagesException
	{
		// Setup
		UUID assertionId = UUID.randomUUID();
		
		String xml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", UUID.randomUUID(), "Foo")
				.state(UUID.randomUUID(), null)
					.assertion("AnsiSqlTableExists", assertionId)
						.appendInnerXml("<schemaName>sch</schemaName>")
						.appendInnerXml("<tableName>tbl</tableName>")
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(new FakeLogger(), xml);
		
		// Execute
		Resource resource = loader.load(new File("."));
		
		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		AnsiSqlTableExistsAssertion assertionT = ModelExtensions.As(
			resource.getStates().get(0).getAssertions().get(0),
			AnsiSqlTableExistsAssertion.class);
		Assert.assertNotNull("Expected to be an AnsiSqlTableExistsAssertion", assertionT);
		AssertExtensions.assertAnsiSqlTableExistsAssertion(
			assertionId,
			"sch",
			"tbl",
			assertionT,
			"resource.states[0].assertions[0]");
	}
	
	@Test public void ansiSqlTableDoesNotExistAssertionLoadFromValidDocument() throws MessagesException
	{
		// Setup
		UUID assertionId = UUID.randomUUID();
		
		String xml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", UUID.randomUUID(), "Foo")
				.state(UUID.randomUUID(), null)
					.assertion("AnsiSqlTableDoesNotExist", assertionId)
						.appendInnerXml("<schemaName>sch</schemaName>")
						.appendInnerXml("<tableName>tbl</tableName>")
			.render();

		DomResourceLoader loader = DomPlugins.resourceLoader(new FakeLogger(), xml);
		
		// Execute
		Resource resource = loader.load(new File("."));
		
		// Verify
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.states.size", 1, resource.getStates().size());
		Assert.assertEquals(
			"resource.states[0].assertions.size",
			1,
			resource.getStates().get(0).getAssertions().size());
		AnsiSqlTableDoesNotExistAssertion assertionT = ModelExtensions.As(
			resource.getStates().get(0).getAssertions().get(0),
			AnsiSqlTableDoesNotExistAssertion.class);
		Assert.assertNotNull("Expected to be an AnsiSqlTableDoesNotExistAssertion", assertionT);
		AssertExtensions.assertAnsiSqlTableDoesNotExistAssertion(
			assertionId,
			"sch",
			"tbl",
			assertionT,
			"resource.states[0].assertions[0]");
	}
}
