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

/**
 * Indicates that the state specified for a migrate or a jumpstate command is invalid.
 *
 * @since 3.0
 */
public class InvalidStateSpecifiedException extends Exception
{
	private final String specifiedState;

	/**
	 * Constructs a new InvalidStateSpecifiedException carrying the specified state.
	 *
	 * @param specifiedState the invalid state that was specified by a request.
	 * @since 3.0
	 */
	public InvalidStateSpecifiedException(String specifiedState)
	{
		super(String.format("Specified state is not valid: \"%s\"", specifiedState));

		this.specifiedState = specifiedState;
	}

	/**
	 * Gets the invalid state that was specified by a request.
	 *
	 * @return the invalid state that was specified by a request.
	 * @since 3.0
	 */
	public String getSpecifiedState()
	{
		return this.specifiedState;
	}
}
