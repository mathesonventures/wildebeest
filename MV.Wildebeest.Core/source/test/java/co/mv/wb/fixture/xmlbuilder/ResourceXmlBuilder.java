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

import java.util.UUID;

/**
 * Entry point for the fluent API for creating XML fixtures for unit tests.
 *
 * @since 4.0
 */
public class ResourceXmlBuilder
{
	private boolean built;
	private ResourceBuilder resourceBuilder;

	public static ResourceXmlBuilder create()
	{
		return new ResourceXmlBuilder();
	}

	private ResourceXmlBuilder()
	{
		this.built = false;
		this.resourceBuilder = null;
	}

	public ResourceBuilder resource(
		String type,
		UUID resourceId,
		String name)
	{
		if (this.resourceBuilder != null)
		{
			throw new RuntimeException("resource already created");
		}

		this.resourceBuilder = new ResourceBuilder(this, type, resourceId, name);
		return this.resourceBuilder;
	}

	public String build()
	{
		// TODO: Is this check really necessary?  What's wrong with building a fixture more than once?
		if (this.built)
		{
			throw new RuntimeException("already rendered");
		}

		XmlBuilder xml = new XmlBuilder().create();

		// Resource
		xml.openElement(
			"resource",
			"type", this.resourceBuilder.getType(),
			"id", this.resourceBuilder.getResourceId().toString(),
			"name", this.resourceBuilder.getName());

		xml.openElement("states");

		// States
		for (StateBuilder state : this.resourceBuilder.getStates())
		{
			if (state.hasDescription() && state.hasName())
			{
				xml.openElement(
					"state",
					"id",
					state.getStateId().toString(),
					"name",
					state.getName(),
					"description",
					state.getDescription());
			}
			else if (state.hasDescription() && !state.hasName())
			{
				xml.openElement(
					"state",
					"id",
					state.getStateId().toString(),
					"description",
					state.getDescription());
			}
			else if (state.hasName())
			{
				xml.openElement("state", "id", state.getStateId().toString(), "name", state.getName());
			}
			else
			{
				xml.openElement("state", "id", state.getStateId().toString());
			}

			// Assertions
			xml.openElement("assertions");
			for (AssertionBuilder assertion : state.getAssertions())
			{
				xml.openAssertion(assertion.getType(), assertion.getAssertionId());
				xml.append(assertion.getInnerXml());
				xml.closeAssertion();
			}

			xml.closeElement("assertions");

			xml.closeElement("state");
		}

		xml.closeElement("states");

		// Migrations
		xml.openElement("migrations");

		for (MigrationBuilder migration : this.resourceBuilder.getMigrations())
		{
			xml.openMigration(
				migration.getType(),
				migration.getMigrationId(),
				migration.getFromState().orElse(null),
				migration.getToState().orElse(null));
			xml.append(migration.getInnerXml());
			xml.closeMigration();
		}

		xml.closeElement("migrations");

		xml.closeElement("resource");

		return xml.toString();
	}
}
