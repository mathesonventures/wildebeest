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

package co.mv.wb.fixturecreator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Creates &lt;resource&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class ResourceCreator
{
	public ResourceCreator(
		FixtureCreator creator,
		String type,
		UUID resourceId,
		String name)
	{
		this.setCreator(creator);
		this.setType(type);
		this.setResourceId(resourceId);
		this.setName(name);
		this.setStates(new ArrayList<>());
		this.setMigrations(new ArrayList<>());
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
		boolean changing = !_type_set || !_type.equals(value);
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

	// <editor-fold desc="ResourceId" defaultstate="collapsed">

	private UUID _resourceId = null;
	private boolean _resourceId_set = false;

	public UUID getResourceId() {
		if(!_resourceId_set) {
			throw new IllegalStateException("resourceId not set.");
		}
		if(_resourceId == null) {
			throw new IllegalStateException("resourceId should not be null");
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

	// <editor-fold desc="Name" defaultstate="collapsed">

	private String _name = null;
	private boolean _name_set = false;

	public String getName() {
		if(!_name_set) {
			throw new IllegalStateException("name not set.");
		}
		if(_name == null) {
			throw new IllegalStateException("name should not be null");
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

	private List<StateCreator> _states = null;
	private boolean _states_set = false;

	public List<StateCreator> getStates() {
		if(!_states_set) {
			throw new IllegalStateException("states not set.");
		}
		if(_states == null) {
			throw new IllegalStateException("states should not be null");
		}
		return _states;
	}

	private void setStates(List<StateCreator> value) {
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

	private List<MigrationCreator> _migrations = null;
	private boolean _migrations_set = false;

	public List<MigrationCreator> getMigrations() {
		if(!_migrations_set) {
			throw new IllegalStateException("migrations not set.");
		}
		if(_migrations == null) {
			throw new IllegalStateException("migrations should not be null");
		}
		return _migrations;
	}

	private void setMigrations(List<MigrationCreator> value) {
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

	private boolean hasMigrations() {
		return _migrations_set;
	}

	// </editor-fold>
	
	public StateCreator state(
		UUID stateId,
		String label)
	{
		StateCreator stateCreator = new StateCreator(this.getCreator(), this, stateId, label);
		this.getStates().add(stateCreator);
		return stateCreator;
	}
	
	public MigrationCreator migration(
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		MigrationCreator migration = new MigrationCreator(
			this.getCreator(),
			this,
			type,
			migrationId,
			fromStateId,
			toStateId);
		this.getMigrations().add(migration);
		return migration;
	}
	
	public FixtureCreator creator()
	{
		return this.getCreator();
	}
	
	public String render()
	{
		return this.getCreator().render();
	}
}
