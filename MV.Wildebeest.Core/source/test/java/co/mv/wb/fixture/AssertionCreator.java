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
 * Creates &lt;assertion&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 * 
 * @since                                       4.0
 */
public class AssertionCreator
{
	public AssertionCreator(
		FixtureCreator creator,
		StateCreator state,
		String type,
		UUID assertionId)
	{
		this.setCreator(creator);
		this.setState(state);
		this.setType(type);
		this.setAssertionId(assertionId);
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
	
	// <editor-fold desc="State" defaultstate="collapsed">

	private StateCreator _state = null;
	private boolean _state_set = false;

	private StateCreator getState() {
		if(!_state_set) {
			throw new IllegalStateException("state not set.");
		}
		if(_state == null) {
			throw new IllegalStateException("state should not be null");
		}
		return _state;
	}

	private void setState(
		StateCreator value) {
		if(value == null) {
			throw new IllegalArgumentException("state cannot be null");
		}
		boolean changing = !_state_set || _state != value;
		if(changing) {
			_state_set = true;
			_state = value;
		}
	}

	private void clearState() {
		if(_state_set) {
			_state_set = true;
			_state = null;
		}
	}

	private boolean hasState() {
		return _state_set;
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
	
	// <editor-fold desc="AssertionId" defaultstate="collapsed">

	private UUID _assertionId = null;
	private boolean _assertionId_set = false;

	public UUID getAssertionId() {
		if(!_assertionId_set) {
			throw new IllegalStateException("assertionId not set.");
		}
		if(_assertionId == null) {
			throw new IllegalStateException("assertionId should not be null");
		}
		return _assertionId;
	}

	private void setAssertionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionId cannot be null");
		}
		boolean changing = !_assertionId_set || _assertionId != value;
		if(changing) {
			_assertionId_set = true;
			_assertionId = value;
		}
	}

	private void clearAssertionId() {
		if(_assertionId_set) {
			_assertionId_set = true;
			_assertionId = null;
		}
	}

	private boolean hasAssertionId() {
		return _assertionId_set;
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
		boolean changing = !_innerXml_set || !_innerXml.equals(value);
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
	
	public AssertionCreator innerXml(String innerXml)
	{
		this.setInnerXml(innerXml);
		return this;
	}
	
	public AssertionCreator appendInnerXml(String innerXml)
	{
		if (innerXml == null) { throw new IllegalArgumentException("innerXml cannot be null"); }
		
		this.setInnerXml(this.getInnerXml() + innerXml);
		return this;
	}
	
	public StateCreator state()
	{
		return this.getState();
	}
	
	public AssertionCreator assertion(
		String type,
		UUID assertionId)
	{
		return this.getState().assertion(type, assertionId);
	}
	
	public StateCreator state(
		UUID stateId,
		String label)
	{
		return this.state().resource().state(stateId, label);
	}
	
	public MigrationCreator migration(
		String type,
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		return this.state().resource().migration(type, migrationId, fromStateId, toStateId);
	}
	
	public String render()
	{
		return this.getCreator().render();
	}
}
