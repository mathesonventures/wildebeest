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
 * Indicates that an unexpected system error occurred while loading a {@link Resource}.
 *
 * @since 1.0
 */
public class LoaderFault extends Exception
{
	/**
	 * Creates a new LoaderFault.
	 *
	 * @since 1.0
	 */
	public LoaderFault()
	{
	}

	/**
	 * Creates a new LoaderFault triggered by the supplied cause.
	 *
	 * @param cause the Exception which is the root cause of the LoaderFault.
	 * @since 1.0
	 */
	public LoaderFault(Exception cause)
	{
		super(cause);
	}
}
