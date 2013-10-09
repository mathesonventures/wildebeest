// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb;

import java.util.List;
import java.util.UUID;

/**
 * Represents an observable and verifiable state of a {@link Resource}/
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface State
{
	/**
	 * Gets the ID of this State.
	 * 
	 * @since                                   1.0
	 */
	UUID getStateId();
	
	/**
	 * Gets the label of this State, if it has one.
	 * 
	 * @since                                   1.0
	 */
	String getLabel();
	
	/**
	 * Indicates whether or not this State has a label.
	 * 
	 * @state                                   1.0
	 */
	boolean hasLabel();
	
	/**
	 * Gets the set of Assertions that should be used to verify this State.
	 * 
	 * @since                                   1.0
	 */
	List<Assertion> getAssertions();
	
	/**
	 * Returns the label if the state has one, otherwise returns it's ID.
	 * 
	 * @since                                   1.0
	 */
	String getDisplayName();
}