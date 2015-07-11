// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

import co.zd.wb.Instance;
import javax.sql.DataSource;

/**
 * A {@link Resource} {@link Instance} that is a database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface DatabaseInstance extends Instance
{
	/**
	 * Gets the name of the database represented by this DatabaseInstance.
	 * 
	 * @return                                  the name of the database represented by this DatabaseInstance.
	 * @since                                   4.0
	 */
	String getDatabaseName();
	
	/**
	 * Sets the name of the database represented by this DatabaseInstance.
	 * 
	 * @param       value                       the new value for the DatabaseName property.
	 * @since                                   4.0
	 */
	void setDatabaseName(String value);
	
	/**
	 * Gets the name to use for the state tracking table in this database instance, if specified.  If not specified this
	 * method throws an InvalidStateException.  Before calling getStateTableName(), call hasStateTableName() to check if
	 * a state table name has been set.
	 * 
	 * @since                                   1.0
	 */
	String getStateTableName();
	
	/**
	 * Sets the name to use for the state tracking table in this database instance.
	 * 
	 * @param       value                       4.0
	 */
	void setStateTableName(String value);

	/**
	 * Clears the state table name for this instance, leaving Wildebeest to determine the name to use.
	 * 
	 * @since                                   4.0
	 */
	void clearStateTableName();
	
	/**
	 * Returns whether or not this instance has a state table name set.
	 * 
	 * @since                                   1.0
	 */
	boolean hasStateTableName();

	/**
	 * Returns a DataSource for performing administrative operations related to this DatabaseInstance.
	 * 
	 * @return                                  a DataSource for performing administrative operations related to this
	 *                                          DatabaseInstance.
	 * @since                                   4.0
	 */
	DataSource getAdminDataSource();

	/**
	 * Returns a DataSource for the database represented by this DatabaseInstance.
	 * 
	 * @since                                   1.0
	 */
	DataSource getAppDataSource();
	
	/**
	 * Returns a boolean flag indicating whether or not the catalog (database) represented by this DatabaseInstance
	 * exists.
	 * 
	 * @return                                  a boolean flag indicating whether or not the catalog (database)
	 *                                          represented by this DatabaseIntance exists.
	 * @since                                   4.0
	 */
	boolean databaseExists();
}