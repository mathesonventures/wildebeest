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

import co.mv.wb.Migration;
import co.mv.wb.Resource;
import co.mv.wb.ResourceType;
import co.mv.wb.State;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Resource}
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public final class ResourceImpl implements Resource
{
	/**
	 * Creates a new BaseResource instance.
	 * 
	 * @param       resourceId                  the ID of the new Resource
	 * @param       type                        the type of the new Resource
	 * @param       name                        the name of the new Resource
	 * @since                                   1.0
	 */
	public ResourceImpl(
		UUID resourceId,
		ResourceType type,
		String name)
	{
		this.setResourceId(resourceId);
		this.setType(type);
		this.setName(name);
		this.setStates(new ArrayList<>());
		this.setMigrations(new ArrayList<>());
	}

	//
	// Properties
	//
	
	// <editor-fold desc="ResourceId" defaultstate="collapsed">

	private UUID _resourceId = null;
	private boolean _resourceId_set = false;

	@Override public UUID getResourceId() {
		if(!_resourceId_set) {
			throw new IllegalStateException("resourceId not set.  Use the HasResourceId() method to check its state before accessing it.");
		}
		return _resourceId;
	}

	private void setResourceId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceId cannot be null");
		}
		boolean changing = !_resourceId_set || _resourceId != value;
		if(changing) {
			_resourceId_set = true;
			_resourceId = value;
		}
	}

	private void clearResourceId() {
		if(_resourceId_set) {
			_resourceId_set = true;
			_resourceId = null;
		}
	}

	private boolean hasResourceId() {
		return _resourceId_set;
	}

	// </editor-fold>

	// <editor-fold desc="Type" defaultstate="collapsed">

	private ResourceType _type = null;
	private boolean _type_set = false;

	public ResourceType getType() {
		if(!_type_set) {
			throw new IllegalStateException("type not set.");
		}
		if(_type == null) {
			throw new IllegalStateException("type should not be null");
		}
		return _type;
	}

	private void setType(
			ResourceType value) {
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

	// <editor-fold desc="Name" defaultstate="collapsed">

	private String _name = null;
	private boolean _name_set = false;

	@Override public String getName() {
		if(!_name_set) {
			throw new IllegalStateException("name not set.  Use the HasName() method to check its state before accessing it.");
		}
		return _name;
	}

	private void setName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		boolean changing = !_name_set || !_name.equals(value);
		if(changing) {
			_name_set = true;
			_name = value;
		}
	}

	private void clearName() {
		if(_name_set) {
			_name_set = true;
			_name = null;
		}
	}

	private boolean hasName() {
		return _name_set;
	}

	// </editor-fold>

	// <editor-fold desc="States" defaultstate="collapsed">

	private List<State> _states = null;
	private boolean _states_set = false;

	@Override public List<State> getStates() {
		if(!_states_set) {
			throw new IllegalStateException("states not set.  Use the HasStates() method to check its state before accessing it.");
		}
		return _states;
	}

	private void setStates(List<State> value) {
		if(value == null) {
			throw new IllegalArgumentException("states cannot be null");
		}
		boolean changing = !_states_set || _states != value;
		if(changing) {
			_states_set = true;
			_states = value;
		}
	}

	private void clearStates() {
		if(_states_set) {
			_states_set = true;
			_states = null;
		}
	}

	private boolean hasStates() {
		return _states_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Migrations" defaultstate="collapsed">

	private List<Migration> _migrations = null;
	private boolean _migrations_set = false;

	@Override public List<Migration> getMigrations() {
		if(!_migrations_set) {
			throw new IllegalStateException("migrations not set.  Use the HasMigrations() method to check its state before accessing it.");
		}
		return _migrations;
	}

	private void setMigrations(List<Migration> value) {
		if(value == null) {
			throw new IllegalArgumentException("migrations cannot be null");
		}
		boolean changing = !_migrations_set || _migrations != value;
		if(changing) {
			_migrations_set = true;
			_migrations = value;
		}
	}

	private void clearMigrations() {
		if(_migrations_set) {
			_migrations_set = true;
			_migrations = null;
		}
	}

	public boolean hasMigrations() {
		return _migrations_set;
	}

	// </editor-fold>
}
