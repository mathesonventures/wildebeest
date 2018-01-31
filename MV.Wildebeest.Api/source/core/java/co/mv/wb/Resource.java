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

package co.mv.wb;

import java.util.List;
import java.util.UUID;

/**
 * The definition of a stateful resource to be managed by Wildebeest.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface Resource
{
	/**
	 * Gets the ID of this Resource.
	 * 
	 * @return                                  the unique ID of this Resource
	 * @since                                   1.0
	 */
	UUID getResourceId();

	/**
	 * Gets the type of this Resource.
	 *
	 * @return                                  the type of this Resource
	 */
	ResourceType getType();

	/**
	 * Gets the name of this Resource.
	 * 
	 * @return                                  the name of this Resource
	 * @since                                   1.0
	 */
	String getName();
	
	/**
	 * Gets the states that have been defined for this Resource.
	 * 
	 * @return                                  the set of states defined for this Resource
	 * @since                                   1.0
	 */
	List<State> getStates();
	
	/**
	 * Gets the migrations that have been defined for this Resource.
	 * 
	 * @return                                  the migrations defined for this Resource
	 * @since                                   1.0
	 */
	List<Migration> getMigrations();
}
