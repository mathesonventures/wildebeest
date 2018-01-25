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

import java.util.UUID;

/**
 * A Migration implements a means of transitioning an Instance of a Resource from one State to another.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface Migration
{
	/**
	 * Gets the ID of this Migration
	 * 
	 * @since                                   1.0
	 */
	UUID getMigrationId();
	
	/**
	 * Gets the ID of the source State for this Migration
	 * 
	 * @since                                   1.0
	 */
	UUID getFromStateId();
	
	/**
	 * Indicates whether or not this Migration specifies a source State.  A Migration without a source State migrates
	 * from the non-existent state (i.e. creates the resource instance).
	 * 
	 * @since                                   1.0
	 */
	boolean hasFromStateId();
	
	/**
	 * Gets the ID of the target State for this Migration.
	 * 
	 * @since                                   1.0
	 */
	UUID getToStateId();
	
	/**
	 * Indicates whether or not this Migration specifies a target State.  A Migration without a target State migrates to
	 * the non-existent state (i.e. destroys the resource instance).
	 * 
	 * @since                                   1.0
	 */
	boolean hasToStateId();
	
	/**
	 * Checks whether this {@link Migration} can be performed on the supplied {@link Resource}.
	 * 
	 * @param       resource                    the {@link Resource} to check.
	 * @return                                  an indication of whether or not this Migration can be performed on the
	 *                                          supplied Resource.
	 * @since                                   2.0
	 */
	boolean canPerformOn(Resource resource);

	/**
	 * Performs the migration, transitioning the supplied Instance from this Migration's from state to it's to state.
	 * 
	 * @param       instance                    the instance to be migrated
	 * @throws      MigrationFailedException    if the migration fails
	 * @since                                   1.0
	 */
	void perform(Instance instance) throws MigrationFailedException;
}
