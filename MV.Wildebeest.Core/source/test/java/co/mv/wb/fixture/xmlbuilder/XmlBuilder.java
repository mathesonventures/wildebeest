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

package co.mv.wb.fixture.xmlbuilder;

import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * Fluent API for creating XML documents.
 *
 * @since 4.0
 */
public class XmlBuilder
{
	private final StringBuilder stringBuilder;

	public XmlBuilder()
	{
		this.stringBuilder = new StringBuilder();
	}

	public StringBuilder getStringBuilder()
	{
		return this.stringBuilder;
	}

	public XmlBuilder create()
	{
		this.getStringBuilder().append("<?xml version=\"1.0\"?>");

		return this;
	}

	public XmlBuilder element(String name)
	{
		this.getStringBuilder().append("<").append(name).append(" />");

		return this;
	}

	public XmlBuilder element(
		String name,
		String attr1Name,
		String attr1Value,
		String attr2Name,
		String attr2Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\"")
			.append(" />");

		return this;
	}

	public XmlBuilder openElement(String name)
	{
		this.getStringBuilder().append("<").append(name).append(">");

		return this;
	}

	public XmlBuilder openElement(
		String name,
		String attr1Name,
		String attr1Value,
		String attr2Name,
		String attr2Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\"")
			.append(">");

		return this;
	}

	public XmlBuilder openElement(
		String name,
		String attr1Name,
		String attr1Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\"")
			.append(">");

		return this;
	}

	public XmlBuilder openElement(
		String name,
		String attr1Name,
		String attr1Value,
		String attr2Name,
		String attr2Value,
		String attr3Name,
		String attr3Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\" ")
			.append(attr3Name).append("=\"").append(attr3Value).append("\"")
			.append(">");

		return this;
	}

	public XmlBuilder openElement(
		String name,
		String attr1Name,
		String attr1Value,
		String attr2Name,
		String attr2Value,
		String attr3Name,
		String attr3Value,
		String attr4Name,
		String attr4Value)
	{
		this.getStringBuilder()
			.append("<").append(name).append(" ")
			.append(attr1Name).append("=\"").append(attr1Value).append("\" ")
			.append(attr2Name).append("=\"").append(attr2Value).append("\" ")
			.append(attr3Name).append("=\"").append(attr3Value).append("\" ")
			.append(attr4Name).append("=\"").append(attr4Value).append("\"")
			.append(">");

		return this;
	}

	public XmlBuilder closeElement(String name)
	{
		this.getStringBuilder().append("</").append(name).append(">");

		return this;
	}

	public XmlBuilder openCdata()
	{
		this.getStringBuilder().append("<![CDATA[");

		return this;
	}

	public XmlBuilder closeCdata()
	{
		this.getStringBuilder().append("]]>");

		return this;
	}

	public XmlBuilder append(String text)
	{
		this.getStringBuilder().append(text);

		return this;
	}

	public XmlBuilder openResource(
		UUID resourceId,
		String type,
		String name)
	{
		if (resourceId == null) throw new ArgumentNullException("resourceId");
		if (type == null) throw new ArgumentNullException("type");
		if ("".equals(type)) throw new IllegalArgumentException("type cannot be empty");
		if (name == null) throw new ArgumentNullException("name");
		if ("".equals(name)) throw new IllegalArgumentException("name cannot be empty");

		return this.openElement("resource", "id", resourceId.toString(), "type", type, "name", name);
	}

	public XmlBuilder closeResource()
	{
		return this.closeElement("resource");
	}

	public XmlBuilder openStates()
	{
		return this.openElement("states");
	}

	public XmlBuilder closeStates()
	{
		return this.closeElement("states");
	}

	public XmlBuilder state(
		UUID stateId,
		String name)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (name == null) throw new ArgumentNullException("name");
		if ("".equals(name)) throw new IllegalArgumentException("name cannot be empty");

		this.element("state", "id", stateId.toString(), "name", name);

		return this;
	}

	public XmlBuilder openState(
		UUID stateId,
		String name)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (name == null) throw new ArgumentNullException("name");
		if ("".equals(name)) throw new IllegalArgumentException("name cannot be empty");

		return this.openElement("state", "id", stateId.toString(), "name", name);
	}

	public XmlBuilder closeState()
	{
		return this.closeElement("state");
	}

	public XmlBuilder openAssertions()
	{
		return this.openElement("assertions");
	}

	public XmlBuilder closeAssertions()
	{
		return this.closeElement("assertions");
	}

	public XmlBuilder openAssertion(
		String type,
		UUID assertionId)
	{
		if (type == null) throw new ArgumentNullException("type");
		if ("".equals(type)) throw new IllegalArgumentException("type cannot be empty");
		if (assertionId == null) throw new ArgumentNullException("assertionId");

		return this.openElement(
			"assertion",
			"type", type,
			"id", assertionId.toString());
	}

	public XmlBuilder closeAssertion()
	{
		return this.closeElement("assertion");
	}

	public XmlBuilder openMigrations()
	{
		return this.openElement("migrations");
	}

	public XmlBuilder closeMigrations()
	{
		return this.closeElement("migrations");
	}

	public XmlBuilder openMigration(
		String type,
		UUID migrationId,
		String fromState,
		String toState)
	{
		if (type == null) throw new ArgumentNullException("type");
		if ("".equals(type)) throw new IllegalArgumentException("type cannot be empty");
		if (migrationId == null) throw new ArgumentNullException("migrationId");
		if (fromState == null && toState == null)
		{
			throw new IllegalArgumentException("at least one of fromState and toState must be provided");
		}

		if (fromState != null && toState != null)
		{
			this.openElement("migration",
				"type", type,
				"id", migrationId.toString(),
				"fromState", fromState.toString(),
				"toState", toState.toString());
		}
		else if (fromState != null)
		{
			this.openElement("migration",
				"type", type,
				"id", migrationId.toString(),
				"fromState", fromState.toString());
		}
		else if (toState != null)
		{
			this.openElement("migration",
				"type", type,
				"id", migrationId.toString(),
				"toState", toState.toString());
		}

		return this;
	}

	public XmlBuilder closeMigration()
	{
		return this.closeElement("migration");
	}

	@Override public String toString()
	{
		return this.getStringBuilder().toString();
	}
}
