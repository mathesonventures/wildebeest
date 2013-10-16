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
 * An Assertion represents a condition that can be checked to verify a specific part of a stateful resource.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface Assertion
{
	/**
	 * Gets the unique ID of this Assertion.
     * @since                                   1.0
	 */
	UUID getAssertionId();
	
	/**
	 * Gets a description of this Assertion.
	 *
	 * @since                                   1.0
	 */
	String getDescription();
	
	/**
	 * Gets the ordinal index of this Assertion within the container that owns it.
	 *
	 * @since                                   1.0
	 */
	int getSeqNum();
	
	/**
	 * Checks whether this {@link Assertion} can be performed on the supplied {@link Resource}.
	 * 
	 * @param       resource                    the {@link Resource} to check.
	 * @return                                  an indication of whether or not this Assertion can be performed on the
	 *                                          supplied Resource.
	 * @since                                   2.0
	 */
	boolean canPerformOn(Resource resource);

	/**
	 * Evaluates this Assertion against the supplied resource instance.
	 * 
	 * @param       instance                    the instance to apply this Assertion to.
	 * @return                                  an AssertionResponse indicating the outcome of applying this Assertion
	 *                                          to the supplied Instance.
	 * @since                                   1.0
	 */
	AssertionResponse perform(Instance instance);
}