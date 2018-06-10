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

import co.mv.wb.AssertionFailedException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.XmlValidationException;
import co.mv.wb.fixture.TestContext_SimpleFakeResource;
import co.mv.wb.fixture.TestContext_SimpleFakeResource_Builder;
import co.mv.wb.framework.ArgumentNullException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Optional;

import static co.mv.wb.Asserts.assertFakeInstance;

/**
 * Unit tests for WildebeestApiImpl.
 *
 * @since                                       4.0
 */
public class WildebeestApiImplUnitTests
{
	/**
	 * A call to migrate specified a target and the resource does not have a default.  WildebeestApiImpl correctly
	 * resolves the specified target and passes it to ResourceHelperImpl.
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
	 * A call to migrate specified a target and the resource has a default.  WildebeestApiImpl correctly resolves the
	 * specified target and passes it to ResourceHelperImpl.
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
	 * A call to migrate did not specify a target and the resource does not have a default.  WildebeestApiImpl raises
	 * the error by throwing a TargetNotSpecifiedException.
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
	 * A call to migrate did not specify a target but the resource has a default.  WildebeestApiImpl correctly resolves
	 * the default target and passes it to ResourceHelperImpl
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

	@Test
	public void validateXml_invalidMySqlResource_fails()
	{
		// Execute and Verify
		this.validateXml_fails(
			new File("MySqlDatabase/database.wbresource.xml"),
			"resource.xsd");
	}

	@Test
	public void validateXml_invalidSqlServerResource_fails()
	{
		// Execute and Verify
		this.validateXml_fails(
			new File("SqlServerDatabase/database.wbresource.xml"),
			"resource.xsd");
	}

	@Test
	public void validateXml_validPostgreSqlResource_succeeds()
	{
		this.validateXml_succeeds(
			new File("PostgreSqlDatabase/database.wbresource.xml"),
			"resource.xsd");
	}

	@Test
	public void validateXml_invalidResource_fails()
	{
		this.validateXml_fails(
			new File("InvalidXml/InvalidSampleResources.xml"),
			"resource.xsd");
	}

	@Test
	public void validateXml_validMySqlInstance_succeeds()
	{
		this.validateXml_succeeds(
			new File("MySqlDatabase/staging_db.wbinstance.xml"),
			"instance.xsd");
	}

	@Test
	public void validateXml_invalidSqlServerInstance_fails()
	{
		this.validateXml_fails(
			new File("SqlServerDatabase/staging_db.wbinstance.xml"),
			"instance.xsd");
	}

	@Test
	public void validateXml_validPostgreSqlInstance_succeeds()
	{
		this.validateXml_succeeds(
			new File("PostgreSqlDatabase/staging.wbinstance.xml"),
			"instance.xsd");
	}

	@Test
	public void validateXml_invalidInstance_fails()
	{
		this.validateXml_fails(
			new File("InvalidXml/InvalidInstanceSampleResources.xml"),
			"instance.xsd");
	}

	/**
	 * A generic test that expects the supplied XML file to be valid according to the specified XSD schema.
	 *
	 * @param       file                        the XML file to validate against the supplied schema.
	 * @param       xsd                         the XSD schema to validate the XML against.
	 * @since                                   4.0
	 */
	private void validateXml_succeeds(
		File file,
		String xsd)
	{
		if (file == null) throw new ArgumentNullException("file");
		if (xsd == null) throw new ArgumentNullException("xsd");

		try
		{
			WildebeestApiImpl.validateXML(this.readAllText(file), xsd);
		}
		catch (XmlValidationException e)
		{
			Assert.fail(
				"the XML file was expected to be valid according to the schema, but was not: " +
				e.getMessage());
		}
	}

	private void validateXml_fails(
		File file,
		String xsd)
	{
		if (file == null) throw new ArgumentNullException("file");
		if (xsd == null) throw new ArgumentNullException("xsd");

		try
		{
			WildebeestApiImpl.validateXML(this.readAllText(file), xsd);
			Assert.fail("The XML file was expected to be invalid according to the schema, but it passed validation");
		}
		catch (XmlValidationException e)
		{
			// Expected.
		}
	}

	private String readAllText(File file)
	{
		if (file == null) throw new ArgumentNullException("file");

		final String result;
		try
		{
			result = new String(Files.readAllBytes(file.toPath()));
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}
}
