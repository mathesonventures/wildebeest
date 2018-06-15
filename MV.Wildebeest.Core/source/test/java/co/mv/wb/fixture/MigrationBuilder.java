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

package co.mv.wb.fixture;

import co.mv.wb.framework.ArgumentNullException;

import java.util.Optional;
import java.util.UUID;

/**
 * Creates &lt;migration&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @since                                       4.0
 */
public class MigrationBuilder
{
	private final FixtureBuilder creator;
	private final ResourceBuilder resource;
	private final String type;
	private final UUID migrationId;
	private final UUID fromStateId;
	private final UUID toStateId;
	private final String innerXml;

	public MigrationBuilder(
		FixtureBuilder creator,
		ResourceBuilder resource,
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		this(
			creator,
			resource,
			type,
			migrationId,
			fromStateId,
			toStateId,
			"");
	}

	private MigrationBuilder(
		FixtureBuilder creator,
		ResourceBuilder resource,
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		String innerXml)
	{
		if (creator == null) throw new ArgumentNullException("builder");
		if (resource == null) throw new ArgumentNullException("resource");
		if (type == null) throw new ArgumentNullException("type");
		if (migrationId == null) throw new ArgumentNullException("migrationId");

		this.creator = creator;
		this.resource = resource;
		this.type = type;
		this.migrationId = migrationId;
		this.fromStateId = fromStateId;
		this.toStateId = toStateId;
		this.innerXml = innerXml;
	}

	public ResourceBuilder getResource()
	{
		return this.resource;
	}

	public String getType()
	{
		return this.type;
	}

	public UUID getMigrationId()
	{
		return this.migrationId;
	}

	public Optional<UUID> getFromStateId()
	{
		return Optional.ofNullable(this.fromStateId);
	}

	public Optional<UUID> getToStateId()
	{
		return Optional.ofNullable(this.toStateId);
	}

	public String getInnerXml()
	{
		return this.innerXml;
	}

	public MigrationBuilder withInnerXml(String innerXml)
	{
		if (innerXml == null) throw new ArgumentNullException("innerXml");

		return new MigrationBuilder(
			this.creator,
			this.resource,
			this.type,
			this.migrationId,
			this.fromStateId,
			this.toStateId,
			innerXml);
	}
	
	public ResourceBuilder resource()
	{
		return this.getResource();
	}
	
	public MigrationBuilder migration(
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		return this.getResource().migration(type, migrationId, fromStateId, toStateId);
	}
	
	public String render()
	{
		return this.creator.build();
	}
}
