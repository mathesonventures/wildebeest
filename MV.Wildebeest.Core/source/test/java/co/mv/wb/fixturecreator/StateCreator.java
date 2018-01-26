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
 * Creates &lt;state&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class StateCreator
{
	public StateCreator(
		FixtureCreator creator,
		ResourceCreator resource,
		UUID stateId,
		String label)
	{
		this.setCreator(creator);
		this.setResource(resource);
		this.setStateId(stateId);
		
		if (label != null)
		{
			this.setLabel(label);
		}

		this.setAssertions(new ArrayList<>());
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

	private ResourceCreator getResource() {
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
	
	// <editor-fold desc="StateId" defaultstate="collapsed">

	private UUID _stateId = null;
	private boolean _stateId_set = false;

	public UUID getStateId() {
		if(!_stateId_set) {
			throw new IllegalStateException("stateId not set.");
		}
		if(_stateId == null) {
			throw new IllegalStateException("stateId should not be null");
		}
		return _stateId;
	}

	private void setStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !_stateId_set || _stateId != value;
		if(changing) {
			_stateId_set = true;
			_stateId = value;
		}
	}

	private void clearStateId() {
		if(_stateId_set) {
			_stateId_set = true;
			_stateId = null;
		}
	}

	private boolean hasStateId() {
		return _stateId_set;
	}

	// </editor-fold>

	// <editor-fold desc="Label" defaultstate="collapsed">

	private String _label = null;
	private boolean _label_set = false;

	public String getLabel() {
		if(!_label_set) {
			throw new IllegalStateException("label not set.");
		}
		if(_label == null) {
			throw new IllegalStateException("label should not be null");
		}
		return _label;
	}

	private void setLabel(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("label cannot be null");
		}
		boolean changing = !_label_set || !_label.equals(value);
		if(changing) {
			_label_set = true;
			_label = value;
		}
	}

	private void clearLabel() {
		if(_label_set) {
			_label_set = true;
			_label = null;
		}
	}

	public boolean hasLabel() {
		return _label_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Assertions" defaultstate="collapsed">

	private List<AssertionCreator> _assertions = null;
	private boolean _assertions_set = false;

	public List<AssertionCreator> getAssertions() {
		if(!_assertions_set) {
			throw new IllegalStateException("assertions not set.");
		}
		if(_assertions == null) {
			throw new IllegalStateException("assertions should not be null");
		}
		return _assertions;
	}

	private void setAssertions(List<AssertionCreator> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertions cannot be null");
		}
		boolean changing = !_assertions_set || _assertions != value;
		if(changing) {
			_assertions_set = true;
			_assertions = value;
		}
	}

	private void clearAssertions() {
		if(_assertions_set) {
			_assertions_set = true;
			_assertions = null;
		}
	}

	private boolean hasAssertions() {
		return _assertions_set;
	}

	// </editor-fold>
	
	public AssertionCreator assertion(
		String type,
		UUID assertionId)
	{
		AssertionCreator assertion = new AssertionCreator(this.getCreator(), this, type, assertionId);
		this.getAssertions().add(assertion);
		return assertion;
	}
	
	public ResourceCreator resource()
	{
		return this.getResource();
	}
	
	public StateCreator state(
		UUID stateId,
		String label)
	{
		return this.getResource().state(stateId, label);
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
