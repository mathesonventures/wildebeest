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

package co.mv.wb.impl;

import co.mv.wb.*;
import co.mv.wb.fixture.TestContext_SimpleFakeResource;
import co.mv.wb.fixture.TestContext_SimpleFakeResource_Builder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Optional;

import static co.mv.wb.Asserts.assertFakeInstance;
import static org.junit.Assert.*;

/**
 * Unit tests for WildebeestApiImpl.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class WildebeestApiImplUnitTests
{
	private WildebeestApiImpl wildebeestApiImpl = new WildebeestApiImpl(System.out);

	/**
	 * A call to migrate specified a target and the resource does not have a default.  WildebeestApiImpl correctly resolves the
	 * specified target and passes it to ResourceHelperImpl.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetSpecifiedNoDefault_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		// Setup
		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.get();

		TestContext_SimpleFakeResource context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDefaultTarget("bar")
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			Optional.of("foo"));

		// Verify
		assertFakeInstance(
			"Foo",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate specified a target and the resource has a default.  WildebeestApiImpl correctly resolves the specified
	 * target and passes it to ResourceHelperImpl.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetSpecifiedWithDefault_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		// Setup
		TestContext_SimpleFakeResource context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDefaultTarget("bar")
			.get();

		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			Optional.of("foo"));

		// Verify
		assertFakeInstance(
			"Foo",
			context.instance,
			"instance");
	}

	/**
	 * A call to migrate did not specify a target and the resource does not have a default.  WildebeestApiImpl raises the error
	 * by throwing a TargetNotSpecifiedException.
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetNotSpecifiedNoDefault_throws() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		// Setup
		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.get();

		TestContext_SimpleFakeResource context = TestContext_SimpleFakeResource_Builder
			.create()
			.get();

		// Execute and Verify
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			Optional.empty());
	}

	/**
	 * A call to migrate did not specify a target but the resource has a default.  WildebeestApiImpl correctly resolves the
	 * default target and passes it to ResourceHelperImpl
	 *
	 * @since                                   4.0
	 */
	@Test public void migrate_targetNotSpecifiedWithDefault_succeeds() throws
		AssertionFailedException,
		IndeterminateStateException,
		InvalidStateSpecifiedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException
	{
		// Setup
		PrintStream output = System.out;

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(output)
			.get();

		TestContext_SimpleFakeResource context = TestContext_SimpleFakeResource_Builder
			.create()
			.withDefaultTarget("bar")
			.get();

		// Execute
		wildebeestApi.migrate(
			context.resource,
			context.instance,
			Optional.empty());

		// Verify
		assertFakeInstance(
			"Bar",
			context.instance,
			"instance");
	}

	@Test public void validateResourceMySqlDatabaseXml()
	{
		assertFalse(isValid(new File("source/test/etc/MySqlDatabase/database.wbresource.xml"), wildebeestApiImpl.RESOURCES_XSD));
	}

	@Test public void validateResourceSqlServerDatabaseXml()
	{
		assertFalse(isValid(new File("source/test/etc/SqlServerDatabase/database.wbresource.xml"), wildebeestApiImpl.RESOURCES_XSD));
	}

	@Test public void validateResourcePostgreDatabaseXml()
	{
		assertTrue(isValid(new File("source/test/etc/PostgreSqlDatabase/database.wbresource.xml"), wildebeestApiImpl.RESOURCES_XSD));
	}

	@Test public void invalidResourceValidation()
	{
		assertFalse(isValid(new File("source/test/resources/co/mv/wb/impl/InvalidSampleResources.xml"), wildebeestApiImpl.RESOURCES_XSD));
	}

	@Test public void validateInstanceMySqlDatabaseXml()
	{
		assertTrue(isValid(new File("source/test/etc/MySqlDatabase/staging_db.wbinstance.xml"),wildebeestApiImpl.INSTANCE_XSD));
	}

	@Test public void validateInstanceSqlServerDatabaseXml()
	{
		assertFalse(isValid(new File("source/test/etc/SqlServerDatabase/staging_db.wbinstance.xml"),wildebeestApiImpl.INSTANCE_XSD));
	}

	@Test public void validateInstancePostgreDatabaseXml()
	{
		assertTrue(isValid(new File("source/test/etc/PostgreSqlDatabase/staging.wbinstance.xml"),wildebeestApiImpl.INSTANCE_XSD));
	}


	@Test public void invalidInstanceValidation()
	{
		assertFalse(isValid(new File("source/test/resources/co/mv/wb/impl/InvalidInstanceSampleResources.xml"),wildebeestApiImpl.INSTANCE_XSD));
	}

	private boolean isValid(File file, String xsd)
	{
		try
		{
			String content = new String(Files.readAllBytes(file.toPath()));
			wildebeestApiImpl.validateXML(content, xsd);
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (XmlValidationException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
}
