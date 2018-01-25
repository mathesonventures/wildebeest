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

package co.mv.wb.service;

import co.mv.wb.Assertion;
import java.util.UUID;

/**
 * An AssertionBuilder is a factory component for building instances of {@link Assertion}s.  The build method takes the
 * common properties of an Assertion as parameters.
 * 
 * It's expected that AssertionBuilder's will typically be stateful, with additional properties or configuration
 * information being supplied to them as properties.  The reset() method should be implemented to clear such additional
 * state and restore the AssertionBuilder to a clean state ready to be re-used to build another Assertion instance.  The
 * framework will always call reset() before using an AssertionBuilder.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface AssertionBuilder
{
	/**
	 * Builds a new {@link Assertion}.
	 * 
	 * @param       assertionId                 the ID of the Assertion
	 * @param       seqNum                      the ordinal sequence number of the Assertion within the set it belongs
	 *                                          to.
	 * @return                                  a deserialized Assertion instance
	 * @throws      MessagesException           containing error messages if for any reason the deserialization could
	 *                                          not be carried out
	 * @since                                   1.0
	 */
	Assertion build(
		UUID assertionId,
		int seqNum) throws MessagesException;
	
	/**
	 * Resets the AssertionBuilder, making it ready to build a new instance.
	 * 
	 * @since                                   1.0
	 */
	void reset();
}
