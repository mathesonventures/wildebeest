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

import co.mv.wb.XmlValidationException;
import co.mv.wb.framework.ArgumentNullException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Tests to verify the {@link WildebeestApiImpl#validateResourceXml(String)} and
 * {@link WildebeestApiImpl#validateInstanceXml(String)} methods.
 *
 * @since 4.0
 */
public class WildebeestApiImplValidateXmlUnitTests
{
	@Test
	public void validateResourceXml_invalidMySqlResource_fails()
	{
		// Execute and Verify
		this.validateResourceXml_fails("MySqlDatabase/database.wbresource.xml");
	}

	@Test
	public void validateResourceXml_invalidSqlServerResource_fails()
	{
		// Execute and Verify
		this.validateResourceXml_fails("SqlServerDatabase/database.wbresource.xml");
	}

	@Test
	public void validateResourceXml_validPostgreSqlResource_succeeds()
	{
		// Execute and Verify
		this.validateResourceXml_succeeds("PostgreSqlDatabase/database.wbresource.xml");
	}

	@Test
	public void validateResourceXml_invalidResource_fails()
	{
		// Execute and Verify
		this.validateResourceXml_fails("InvalidXml/InvalidSampleResources.xml");
	}

	@Test
	public void validateInstanceXml_validMySqlInstance_succeeds()
	{
		// Execute and Verify
		this.validateInstanceXml_succeeds("MySqlDatabase/staging_db.wbinstance.xml");
	}

	@Test
	public void validateInstanceXml_invalidSqlServerInstance_fails()
	{
		// Execute and Verify
		this.validateInstanceXml_fails("SqlServerDatabase/staging_db.wbinstance.xml");
	}

	@Test
	public void validateInstanceXml_validPostgreSqlInstance_succeeds()
	{
		// Execute and Verify
		this.validateInstanceXml_succeeds("PostgreSqlDatabase/staging.wbinstance.xml");
	}

	@Test
	public void validateInstanceXml_invalidInstance_fails()
	{
		// Execute and Verify
		this.validateInstanceXml_fails("InvalidXml/InvalidInstanceSampleResources.xml");
	}

	/**
	 * A generic test that expects the supplied XML file to be valid according to the resource XSD schema.
	 *
	 * @param filename the resource XML file to validate.
	 * @since 4.0
	 */
	private void validateResourceXml_succeeds(
		String filename)
	{
		if (filename == null) throw new ArgumentNullException("filename");

		try
		{
			WildebeestApiImpl.validateResourceXml(this.readAllText(filename));
		}
		catch (XmlValidationException e)
		{
			Assert.fail(
				"the XML file was expected to be valid according to the resource schema, but was not: " +
					e.getMessage());
		}
	}

	/**
	 * A generic test that expects the supplied XML file to be INVALID according to the resource XSD schema.
	 *
	 * @param filename the resource XML file to validate.
	 * @since 4.0
	 */
	private void validateResourceXml_fails(
		String filename)
	{
		if (filename == null) throw new ArgumentNullException("filename");

		try
		{
			WildebeestApiImpl.validateResourceXml(this.readAllText(filename));
			Assert.fail("The XML file was expected to be invalid according to the resource schema, but it passed " +
				"validation");
		}
		catch (XmlValidationException e)
		{
			// Expected.
		}
	}

	/**
	 * A generic test that expects the supplied XML file to be valid according to the instance XSD schema.
	 *
	 * @param filename the instance XML file to validate.
	 * @since 4.0
	 */
	private void validateInstanceXml_succeeds(
		String filename)
	{
		if (filename == null) throw new ArgumentNullException("filename");

		try
		{
			WildebeestApiImpl.validateInstanceXml(this.readAllText(filename));
		}
		catch (XmlValidationException e)
		{
			Assert.fail(
				"the XML file was expected to be valid according to the schema, but was not: " +
					e.getMessage());
		}
	}

	/**
	 * A generic test that expects the supplied XML file to be INVALID according to the instance XSD schema.
	 *
	 * @param filename the instance XML file to validate.
	 * @since 4.0
	 */
	private void validateInstanceXml_fails(
		String filename)
	{
		if (filename == null) throw new ArgumentNullException("filename");

		try
		{
			WildebeestApiImpl.validateInstanceXml(this.readAllText(filename));
			Assert.fail("The XML file was expected to be invalid according to the instance schema, but it passed " +
				"validation");
		}
		catch (XmlValidationException e)
		{
			// Expected.
		}
	}

	private String readAllText(String filename)
	{
		if (filename == null) throw new ArgumentNullException("filename");

		final String result;
		try
		{
			result = new String(Files.readAllBytes(new File(filename).toPath()));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}
}
