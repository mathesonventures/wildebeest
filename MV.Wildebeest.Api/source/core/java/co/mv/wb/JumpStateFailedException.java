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
 * Indicates that a JumpState operation failed for some normal reason that should be presented to the user.
 * 
 * @since                                       3.0
 */
public class JumpStateFailedException extends Exception
{
	/**
	 * Creates a new JumpStateFailedException with the specified failure messages.
	 * 
	 * @param       message                     the failure message
	 * @since                                   3.0
	 */
	public JumpStateFailedException(
		String message)
	{
		super(message);
	}
}
