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

import co.mv.wb.framework.ArgumentNullException;

/**
 * Indicates that the state specified for a migrate or a jumpstate command not defined for the current resource.
 * 
 * @since                                       3.0
 */
public class UnknownStateSpecifiedException extends Exception
{
	private final String specifiedState;

    /**
     * Constructs a new UnknownStateException with the supplied specifiedState.
     *
     * @param       specifiedState              the state that was requested but is unknown.
     * @since                                   3.0
     */
    public UnknownStateSpecifiedException(String specifiedState)
    {
        super(String.format("State specified is unknown in this resource: \"%s\"", specifiedState));

        if (specifiedState == null) throw new ArgumentNullException("specifiedState");

        this.specifiedState = specifiedState;
    }

    /**
     * Gets the state that was requested but is unknown, resulting in this exception being thrown.
     *
     * @return                                  the state that was requested but is unknown, resulting in this exception
     *                                          being thrown.
     * @since                                   3.0
     */
	public String getSpecifiedState()
	{
		return this.specifiedState;
	}
}
