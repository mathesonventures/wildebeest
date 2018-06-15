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

import java.util.UUID;

/**
 * Creates &lt;migration&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @since                                       4.0
 */
public class MigrationCreator
{
	private FixtureCreator creator = null;
	private boolean creatorSet = false;
	private ResourceCreator resource = null;
	private boolean resourceSet = false;
	private String type = null;
	private boolean typeSet = false;
	private UUID migrationId = null;
	private boolean migrationIdSet = false;
	private String fromState = null;
	private boolean fromStateSet = false;
	private String toStateId = null;
	private boolean toStateSet = false;
	private String innerXml = null;
	private boolean innerXmlSet = false;


	public MigrationCreator(
		FixtureCreator creator,
		ResourceCreator resource,
		String type,
		UUID migrationId,
		String fromState,
		String toStateId)
	{
		this.setCreator(creator);
		this.setResource(resource);
		this.setType(type);
		this.setMigrationId(migrationId);
		if (fromState != null)
		{
			this.setFromState(fromState);
		}
		if (toStateId != null)
		{
			this.setToState(toStateId);
		}
		this.setInnerXml("");
	}

	private FixtureCreator getCreator() {
		if(!creatorSet) {
			throw new IllegalStateException("creator not set.");
		}
		if(creator == null) {
			throw new IllegalStateException("creator should not be null");
		}
		return creator;
	}

	private void setCreator(
		FixtureCreator value) {
		if(value == null) {
			throw new IllegalArgumentException("creator cannot be null");
		}
		boolean changing = !creatorSet || creator != value;
		if(changing) {
			creatorSet = true;
			creator = value;
		}
	}

	private void clearCreator() {
		if(creatorSet) {
			creatorSet = true;
			creator = null;
		}
	}

	private boolean hasCreator() {
		return creatorSet;
	}

	public ResourceCreator getResource() {
		if(!resourceSet) {
			throw new IllegalStateException("resource not set.");
		}
		if(resource == null) {
			throw new IllegalStateException("resource should not be null");
		}
		return resource;
	}

	private void setResource(
		ResourceCreator value) {
		if(value == null) {
			throw new IllegalArgumentException("resource cannot be null");
		}
		boolean changing = !resourceSet || resource != value;
		if(changing) {
			resourceSet = true;
			resource = value;
		}
	}

	private void clearResource() {
		if(resourceSet) {
			resourceSet = true;
			resource = null;
		}
	}

	private boolean hasResource() {
		return resourceSet;
	}

	public String getType() {
		if(!typeSet) {
			throw new IllegalStateException("type not set.");
		}
		if(type == null) {
			throw new IllegalStateException("type should not be null");
		}
		return type;
	}

	private void setType(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("type cannot be null");
		}
		boolean changing = !typeSet || !type.equals(value);
		if(changing) {
			typeSet = true;
			type = value;
		}
	}

	private void clearType() {
		if(typeSet) {
			typeSet = true;
			type = null;
		}
	}

	private boolean hasType() {
		return typeSet;
	}

	public UUID getMigrationId() {
		if(!migrationIdSet) {
			throw new IllegalStateException("migrationId not set.");
		}
		if(migrationId == null) {
			throw new IllegalStateException("migrationId should not be null");
		}
		return migrationId;
	}

	private void setMigrationId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationId cannot be null");
		}
		boolean changing = !migrationIdSet || migrationId != value;
		if(changing) {
			migrationIdSet = true;
			migrationId = value;
		}
	}

	private void clearMigrationId() {
		if(migrationIdSet) {
			migrationIdSet = true;
			migrationId = null;
		}
	}

	private boolean hasMigrationId() {
		return migrationIdSet;
	}

	public String getFromState() {
		if(!fromStateSet) {
			throw new IllegalStateException("fromState not set.");
		}
		if(fromState == null) {
			throw new IllegalStateException("fromState should not be null");
		}
		return fromState;
	}

	private void setFromState(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("fromState cannot be null");
		}
		boolean changing = !fromStateSet || fromState != value;
		if(changing) {
			fromStateSet = true;
			fromState = value;
		}
	}

	private void clearFromState() {
		if(fromStateSet) {
			fromStateSet = true;
			fromState = null;
		}
	}

	public boolean hasFromState() {
		return fromStateSet;
	}


	public String getToState() {
		if(!toStateSet) {
			throw new IllegalStateException("toStateId not set.");
		}
		if(toStateId == null) {
			throw new IllegalStateException("toStateId should not be null");
		}
		return toStateId;
	}

	private void setToState(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("toStateId cannot be null");
		}
		boolean changing = !toStateSet || toStateId != value;
		if(changing) {
			toStateSet = true;
			toStateId = value;
		}
	}

	private void clearToState() {
		if(toStateSet) {
			toStateSet = true;
			toStateId = null;
		}
	}

	public boolean hasToState() {
		return toStateSet;
	}

	public String getInnerXml() {
		if(!innerXmlSet) {
			throw new IllegalStateException("innerXml not set.");
		}
		if(innerXml == null) {
			throw new IllegalStateException("innerXml should not be null");
		}
		return innerXml;
	}

	private void setInnerXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("innerXml cannot be null");
		}
		boolean changing = !innerXmlSet || !innerXml.equals(value);
		if(changing) {
			innerXmlSet = true;
			innerXml = value;
		}
	}

	private void clearInnerXml() {
		if(innerXmlSet) {
			innerXmlSet = true;
			innerXml = null;
		}
	}

	private boolean hasInnerXml() {
		return innerXmlSet;
	}

	public MigrationCreator innerXml(String innerXml)
	{
		this.setInnerXml(innerXml);
		return this;
	}
	
	public ResourceCreator resource()
	{
		return this.getResource();
	}
	
	public MigrationCreator migration(
		String type,
		UUID migrationId,
		String fromState,
		String toState)
	{
		return this.getResource().migration(type, migrationId, fromState, toState);
	}
	
	public String render()
	{
		return this.getCreator().render();
	}
}
