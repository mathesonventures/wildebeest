// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.plugin.base;

import co.zd.wb.Assertion;
import co.zd.wb.State;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A {@link State} that cannot be modified after it's initial construction.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class ImmutableState implements State
{
	/**
	 * Creates a new ImmutableState with the supplied ID.
	 * 
	 * @param       stateId                     the ID of the new state
	 */
	public ImmutableState(
		UUID stateId)
	{
		this.setStateId(stateId);
		this.setAssertions(new ArrayList<Assertion>());
	}
	
	/**
	 * Creates a new ImmutableState with an ID and a label.
	 * 
	 * @param       stateId                     the ID of the new state
	 * @param       label                       the unique label of the new state
	 */
	public ImmutableState(
		UUID stateId,
		String label)
	{
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(new ArrayList<Assertion>());
	}

	/**
	 * Creates a new ImmutableState with the supplied ID and set of {@link Assertion}s.
	 * 
	 * @param       stateId                     the ID of the new state
	 * @param       assertions                  the assertions that apply to this state
	 */
	public ImmutableState(
		UUID stateId,
		List<Assertion> assertions)
	{
		this.setStateId(stateId);
		this.setAssertions(assertions);
	}
	
	/**
	 * Creates a new ImmutableState with an ID and a label, and with a set of {@link Assertion}s.
	 * 
	 * @param       stateId                     the ID of the new state
	 * @param       label                       the unique label of the new state
	 * @param       assertions                  the assertions that apply to this state
	 */
	public ImmutableState(
		UUID stateId,
		String label,
		List<Assertion> assertions)
	{
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(assertions);
	}

	// <editor-fold desc="StateId" defaultstate="collapsed">

	private UUID m_stateId = null;
	private boolean m_stateId_set = false;

	@Override public UUID getStateId() {
		if(!m_stateId_set) {
			throw new IllegalStateException("stateId not set.  Use the HasStateId() method to check its state before accessing it.");
		}
		return m_stateId;
	}

	private void setStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !m_stateId_set || m_stateId != value;
		if(changing) {
			m_stateId_set = true;
			m_stateId = value;
		}
	}

	private void clearStateId() {
		if(m_stateId_set) {
			m_stateId_set = true;
			m_stateId = null;
		}
	}

	private boolean hasStateId() {
		return m_stateId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Label" defaultstate="collapsed">

	private String m_label = null;
	private boolean m_label_set = false;

	@Override public String getLabel() {
		if(!m_label_set) {
			throw new IllegalStateException("label not set.  Use the HasLabel() method to check its state before accessing it.");
		}
		return m_label;
	}

	private void setLabel(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("label cannot be null");
		}
		boolean changing = !m_label_set || m_label != value;
		if(changing) {
			m_label_set = true;
			m_label = value;
		}
	}

	private void clearLabel() {
		if(m_label_set) {
			m_label_set = true;
			m_label = null;
		}
	}

	public boolean hasLabel() {
		return m_label_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Assertions" defaultstate="collapsed">

	private List<Assertion> m_assertions = null;
	private boolean m_assertions_set = false;

	@Override public List<Assertion> getAssertions() {
		if(!m_assertions_set) {
			throw new IllegalStateException("assertions not set.  Use the HasAssertions() method to check its state before accessing it.");
		}
		return m_assertions;
	}

	private void setAssertions(List<Assertion> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertions cannot be null");
		}
		boolean changing = !m_assertions_set || m_assertions != value;
		if(changing) {
			m_assertions_set = true;
			m_assertions = value;
		}
	}

	private void clearAssertions() {
		if(m_assertions_set) {
			m_assertions_set = true;
			m_assertions = null;
		}
	}

	private boolean hasAssertions() {
		return m_assertions_set;
	}

	// </editor-fold>
	
	@Override public String getDisplayName()
	{
		if (this.hasLabel())
		{
			return this.getLabel();
		}
		else
		{
			return this.getStateId().toString();
		}
	}
}
