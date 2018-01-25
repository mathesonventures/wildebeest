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

package co.zd.wb.plugin.ansisql;

import co.zd.wb.plugin.database.DatabaseInstance;

/**
 * Marker interface for tagging DatabaseInstance implementations that represent ANSI-compliant database systems.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public interface AnsiSqlDatabaseInstance extends DatabaseInstance
{
	/**
	 * Gets the name of the schema where meta data is tracked by Wildebeest.  If no schema name is specified on this
	 * instance, then Wildebeest will choose the name to use.
	 * 
	 * @return                                  the name of the schema where meta data is tracked.
	 * @since                                   4.0
	 */
	String getMetaSchemaName();
	
	/**
	 * Sets the name of the schema where meta data is tracked by Wildebeest.
	 * 
	 * @param       value                       the name of the schema where meta data is tracked by Wildebeest.
	 * @since                                   4.0
	 */
	void setMetaSchemaName(String value);

	/**
	 * Clears the meta data schema name for this instance, leaving Wildebeest to choose the schema to be used.
	 * 
	 * @since                                   4.0
	 */
	void clearMetaSchemaName();
	
	/**
	 * Returns whether or not this instance has a meta data schema name specified.
	 * 
	 * @return                                  a boolean flag indicating whether or not this instance ha a meta data
	 *                                          schema name specified.
	 * @since                                   4.0
	 */
	boolean hasMetaSchemaName();
}
