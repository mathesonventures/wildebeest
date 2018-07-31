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
import java.util.UUID;

/**
 * An Assertion represents a condition that can be checked to verify a specific part of a stateful resource.
 *
 * @since 1.0
 */
public interface Assertion
{
	/**
	 * Gets the unique ID of this Assertion.
	 *
	 * @return the unique ID of this Assertion
	 * @since 1.0
	 */
	UUID getAssertionId();

	/**
	 * Gets a description of this Assertion.
	 *
	 * @return the description of this Assertion
	 * @since 1.0
	 */
	String getDescription();

	/**
	 * Gets the ordinal index of this Assertion within the container that owns it.
	 *
	 * @return the ordinal index of this Assertion within the container that owns it
	 * @since 1.0
	 */
	int getSeqNum();

	/**
	 * Gets the list of {@link Resource} types this this {@link Assertion} can be applied to.
	 *
	 * @return the list of resource types that this Assertion can be applied to
	 * @since 2.0
	 */
	List<ResourceType> getApplicableTypes();
}
