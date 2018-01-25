// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.mv.wb.fixturecreator;

import java.util.UUID;

/**
 * Creates &lt;migration&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class MigrationCreator
{
	public MigrationCreator(
		FixtureCreator creator,
		ResourceCreator resource,
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		this.setCreator(creator);
		this.setResource(resource);
		this.setType(type);
		this.setMigrationId(migrationId);
		if (fromStateId != null)
		{
			this.setFromStateId(fromStateId);
		}
		if (toStateId != null)
		{
			this.setToStateId(toStateId);
		}
		this.setInnerXml("");
	}

	// <editor-fold desc="Creator" defaultstate="collapsed">

	private FixtureCreator _creator = null;
	private boolean _creator_set = false;

	private FixtureCreator getCreator() {
		if(!_creator_set) {
			throw new IllegalStateException("creator not set.");
		}
		if(_creator == null) {
			throw new IllegalStateException("creator should not be null");
		}
		return _creator;
	}

	private void setCreator(
		FixtureCreator value) {
		if(value == null) {
			throw new IllegalArgumentException("creator cannot be null");
		}
		boolean changing = !_creator_set || _creator != value;
		if(changing) {
			_creator_set = true;
			_creator = value;
		}
	}

	private void clearCreator() {
		if(_creator_set) {
			_creator_set = true;
			_creator = null;
		}
	}

	private boolean hasCreator() {
		return _creator_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Resource" defaultstate="collapsed">

	private ResourceCreator _resource = null;
	private boolean _resource_set = false;

	public ResourceCreator getResource() {
		if(!_resource_set) {
			throw new IllegalStateException("resource not set.");
		}
		if(_resource == null) {
			throw new IllegalStateException("resource should not be null");
		}
		return _resource;
	}

	private void setResource(
		ResourceCreator value) {
		if(value == null) {
			throw new IllegalArgumentException("resource cannot be null");
		}
		boolean changing = !_resource_set || _resource != value;
		if(changing) {
			_resource_set = true;
			_resource = value;
		}
	}

	private void clearResource() {
		if(_resource_set) {
			_resource_set = true;
			_resource = null;
		}
	}

	private boolean hasResource() {
		return _resource_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Type" defaultstate="collapsed">

	private String _type = null;
	private boolean _type_set = false;

	public String getType() {
		if(!_type_set) {
			throw new IllegalStateException("type not set.");
		}
		if(_type == null) {
			throw new IllegalStateException("type should not be null");
		}
		return _type;
	}

	private void setType(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("type cannot be null");
		}
		boolean changing = !_type_set || _type != value;
		if(changing) {
			_type_set = true;
			_type = value;
		}
	}

	private void clearType() {
		if(_type_set) {
			_type_set = true;
			_type = null;
		}
	}

	private boolean hasType() {
		return _type_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="MigrationId" defaultstate="collapsed">

	private UUID _migrationId = null;
	private boolean _migrationId_set = false;

	public UUID getMigrationId() {
		if(!_migrationId_set) {
			throw new IllegalStateException("migrationId not set.");
		}
		if(_migrationId == null) {
			throw new IllegalStateException("migrationId should not be null");
		}
		return _migrationId;
	}

	private void setMigrationId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationId cannot be null");
		}
		boolean changing = !_migrationId_set || _migrationId != value;
		if(changing) {
			_migrationId_set = true;
			_migrationId = value;
		}
	}

	private void clearMigrationId() {
		if(_migrationId_set) {
			_migrationId_set = true;
			_migrationId = null;
		}
	}

	private boolean hasMigrationId() {
		return _migrationId_set;
	}

	// </editor-fold>

	// <editor-fold desc="FromStateId" defaultstate="collapsed">

	private UUID _fromStateId = null;
	private boolean _fromStateId_set = false;

	public UUID getFromStateId() {
		if(!_fromStateId_set) {
			throw new IllegalStateException("fromStateId not set.");
		}
		if(_fromStateId == null) {
			throw new IllegalStateException("fromStateId should not be null");
		}
		return _fromStateId;
	}

	private void setFromStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("fromStateId cannot be null");
		}
		boolean changing = !_fromStateId_set || _fromStateId != value;
		if(changing) {
			_fromStateId_set = true;
			_fromStateId = value;
		}
	}

	private void clearFromStateId() {
		if(_fromStateId_set) {
			_fromStateId_set = true;
			_fromStateId = null;
		}
	}

	public boolean hasFromStateId() {
		return _fromStateId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="ToStateId" defaultstate="collapsed">

	private UUID _toStateId = null;
	private boolean _toStateId_set = false;

	public UUID getToStateId() {
		if(!_toStateId_set) {
			throw new IllegalStateException("toStateId not set.");
		}
		if(_toStateId == null) {
			throw new IllegalStateException("toStateId should not be null");
		}
		return _toStateId;
	}

	private void setToStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("toStateId cannot be null");
		}
		boolean changing = !_toStateId_set || _toStateId != value;
		if(changing) {
			_toStateId_set = true;
			_toStateId = value;
		}
	}

	private void clearToStateId() {
		if(_toStateId_set) {
			_toStateId_set = true;
			_toStateId = null;
		}
	}

	public boolean hasToStateId() {
		return _toStateId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="InnerXml" defaultstate="collapsed">

	private String _innerXml = null;
	private boolean _innerXml_set = false;

	public String getInnerXml() {
		if(!_innerXml_set) {
			throw new IllegalStateException("innerXml not set.");
		}
		if(_innerXml == null) {
			throw new IllegalStateException("innerXml should not be null");
		}
		return _innerXml;
	}

	private void setInnerXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("innerXml cannot be null");
		}
		boolean changing = !_innerXml_set || _innerXml != value;
		if(changing) {
			_innerXml_set = true;
			_innerXml = value;
		}
	}

	private void clearInnerXml() {
		if(_innerXml_set) {
			_innerXml_set = true;
			_innerXml = null;
		}
	}

	private boolean hasInnerXml() {
		return _innerXml_set;
	}

	// </editor-fold>
	
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
		UUID fromStateId,
		UUID toStateId)
	{
		return this.getResource().migration(type, migrationId, fromStateId, toStateId);
	}
	
	public String render()
	{
		return this.getCreator().render();
	}
}
