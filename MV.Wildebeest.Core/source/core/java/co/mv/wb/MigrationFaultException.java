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

/**
 * Indicates an unexpected error occurred during migration.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MigrationFaultException extends RuntimeException
{
	/**
	 * Creates a new MigrationFaultException with the supplied root cause.
	 * 
	 * @param       cause                       the root cause of the migration error
	 * @since                                   1.0
	 */
	public MigrationFaultException(Exception cause)
	{
		super(cause);
	}
	
	/**
	 * Creates a new MigrationFaultException with the supplied message.
	 * 
	 * @param       message                     the migration error message
	 * @since                                   1.0
	 */
	public MigrationFaultException(
		String message)
	{
		super(message);
	}
}
