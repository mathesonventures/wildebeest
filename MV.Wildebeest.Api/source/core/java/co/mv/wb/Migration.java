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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A Migration implements a means of transitioning an Instance of a Resource from one State to another.
 * 
 * @since                                       1.0
 */
public interface Migration
{
	/**
	 * Gets the ID of this Migration
	 * 
	 * @return                                  the unique ID of this Migration
	 * @since                                   1.0
	 */
	UUID getMigrationId();
	
	/**
	 * Gets the optional source State for this Migration.  A Migration without a source State migrates from
	 * the non-existent state (i.e. creates the resource instance from nothing).
	 * 
	 * @return                                  the ID of the source State for this Migration
	 * @since                                   1.0
	 */
	Optional<UUID> getFromStateId();
	
	/**
	 * Gets the optional target State for this Migration.  A Migration without a target state migrates to the
	 * non-existent state (i.e. destroys the resource instance).
	 * 
	 * @return                                  the ID of the to State for this Migration
	 * @since                                   1.0
	 */
	Optional<UUID> getToStateId();
	
	/**
	 * Gets the list of {@link Resource} types that this this {@link Migration} can be applied to.
	 * 
	 * @return                                  the list of resource types that this Migration can be applied to
	 * @since                                   2.0
	 */
	List<ResourceType> getApplicableTypes();
}
