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

package co.mv.wb.plugin.generaldatabase;

/**
 * Defines common constant values for use with database plugins to Wildebeest.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class DatabaseConstants
{
	/**
	 * The default name for the schema where Wildebeest should track meta-data.
	 * 
	 * @since                                   4.0
	 */
	
	public static final String DefaultMetaSchemaName = "wb";
	
	/**
	 * The default name for the state-tracking table in Wildebeest-managed databases.  This table name can be overridden
	 * per-instance by supplying a StateTableName in the instance definition.
	 * 
	 * @since                                   1.0
	 */
	public static final String DefaultStateTableName = "wb_state";
}
