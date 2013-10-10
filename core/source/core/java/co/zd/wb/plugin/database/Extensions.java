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

package co.zd.wb.plugin.database;

/**
 * Provides convenience methods for working with database resources.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class Extensions
{
	/**
	 * Returns the state table name from the supplied instance if it has one.  Otherwise returns the default state table
	 * name from {@link DatabaseConstants}.
	 * 
	 * @param       instance                    the {@link DatabaseInstance} for which the state table name should be
	 *                                          determined
	 * @return                                  the state table name for the supplied DatabaseInstance.
	 */
	public static String getStateTableName(
		DatabaseInstance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		
		return instance.hasStateTableName() ? instance.getStateTableName() : DatabaseConstants.DefaultStateTableName;
	}
}
