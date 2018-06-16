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

import java.util.UUID;

/**
 * Creates &lt;assertion&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @since                                       4.0
 */
public class AssertionCreator
{
	private final FixtureBuilder creator;
	private final StateCreator state;
	private final String type;
	private final UUID assertionId;
	private final String innerXml;

	public AssertionCreator(
		FixtureBuilder creator,
		StateCreator state,
		String type,
		UUID assertionId)
	{
		this(
			creator,
			state,
			type,
			assertionId,
			"");
	}

	private AssertionCreator(
		FixtureBuilder creator,
		StateCreator state,
		String type,
		UUID assertionId,
		String innerXml)
	{
		if (creator == null) throw new ArgumentNullException("builder");
		if (state == null) throw new ArgumentNullException("state");
		if (type == null) throw new ArgumentNullException("type");
		if (assertionId == null) throw new ArgumentNullException("assertionId");
		if (innerXml == null) throw new ArgumentNullException("withInnerXml");

		this.creator = creator;
		this.state = state;
		this.type = type;
		this.assertionId = assertionId;
		this.innerXml = innerXml;
	}

	public String getType()
	{
		return type;
	}

	public UUID getAssertionId()
	{
		return assertionId;
	}

	public String getInnerXml()
	{
		return innerXml;
	}

	public AssertionCreator withInnerXml(String innerXml)
	{
		if (innerXml == null) throw new ArgumentNullException("withInnerXml");
		
		return new AssertionCreator(
			this.creator,
			this.state,
			this.type,
			this.assertionId,
			innerXml);
	}
	
	public AssertionCreator appendInnerXml(String innerXml)
	{
		if (innerXml == null) throw new ArgumentNullException("withInnerXml");

		return new AssertionCreator(
			this.creator,
			this.state,
			this.type,
			this.assertionId,
			this.innerXml + innerXml);
	}

	public StateCreator state()
	{
		return this.state;
	}
	
	public AssertionCreator assertion(
		String type,
		UUID assertionId)
	{
		return this.state.assertion(type, assertionId);
	}
	
	public StateCreator state(
		UUID stateId,
		String label)
	{
		return this.state().resource().state(stateId, label);
	}
	
	public MigrationBuilder migration(
		String type,
		UUID migrationId,
		String fromState,
		String toState)
	{
		return this.state().resource().migration(type, migrationId, fromState, toState);
	}
	
	public String build()
	{
		return this.creator.build();
	}
}
