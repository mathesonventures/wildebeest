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

/**
 * Indicates that Wildebeest was unable to determine the current state of specified resource Instance.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class IndeterminateStateException extends Exception
{
	/**
	 * Creates a new IndeterminateStateException with the supplied message.
	 * 
	 * @param       message                     the message for the new exception.
	 * @since                                   1.0
	 */
	public IndeterminateStateException(String message)
	{
		super(message);
	}
}