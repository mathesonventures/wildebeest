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

import java.util.UUID;

/**
 * Represents result of a Resource.assertState() operation.
 *
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface AssertionResult
{
	/**
	 * Returns the ID of the {@link co.mv.stm.model.Assertion} that was tested.
	 * 
	 * @since                                   1.0
	 */
	UUID getAssertionId();
	
	/**
	 * Returns the result of the {@link co.mv.stm.model.Assertion}.  The Assertion either passed or it failed.
	 * 
	 * @since                                   1.0
	 */
	boolean getResult();
	
	/**
	 * Returns the message that was generated by the {@link co.mv.stm.model.Assertion} to go along with the boolean
	 * result.
	 * 
	 * @since                                   1.0
	 */
	String getMessage();
}
