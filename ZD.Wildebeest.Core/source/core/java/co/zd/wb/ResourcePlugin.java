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

package co.zd.wb;

import java.util.UUID;

/**
 * Provides resource-type-specific services.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public interface ResourcePlugin
{
	/**
	 * Identifies and returns the defined state that the supplied resource instance currently appears to be in, or is
	 * declared to be in.  Note that this does not guarantee that the resource is valid as compared to the state that it
	 * is identified to be in.  To verify that the resource is valid for the current state, use assertState().
	 * 
	 * If the resource does not exist at all, then this method returns null.
	 * 
	 * If the state of the resource cannot be determined then an IndeterminateStateException is thrown.  For example if
	 * the resource declares itself to be in state "A", but no such state has been defined for the resource, then this
	 * is considered to be an indeterminate state.
	 * 
	 * Note: Formerly currentState() was defined by Resource directly before ResourcePlugin was factored out.
	 * 
	 * @param       resource                    the resource to check in the specified {@link Instance}
	 * @param       instance                    the {@link Instance} to get the current state of
	 * @return                                  the defined state that the resource currently appears to be in, or is
	 *                                          declared to be in.
	 * @exception   IndeterminateStateException when the current state of the resource cannot be determined clearly.
	 * @since                                   1.0
	 */
	State currentState(
		Resource resource,
		Instance instance) throws IndeterminateStateException;

	/**
	 * Registers resource-type-specific meta data to record that it is in the specified state.
	 * @param       logger                      an optional Logger service to log the activity of this setStateId operation
	 * @param       resource                    the resource for which the state should be set in the specified {@link Instance}
	 * @param       instance                    the {@link Instance} to record the state against
	 * @param       stateId                     the state to record against the instance
	 */
	void setStateId(
		Logger logger,
		Resource resource,
		Instance instance,
		UUID stateId);
}
