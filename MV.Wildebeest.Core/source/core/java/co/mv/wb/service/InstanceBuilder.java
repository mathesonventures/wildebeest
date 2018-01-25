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

package co.mv.wb.service;

import co.mv.wb.Instance;

/**
 * An InstanceBuilder is a factory component for building {@link Intances}s.
 * 
 * It's expected that InstanceBuilders's will typically be stateful, with properties or configuration information being
 * supplied to them as properties.  The reset() method should be implemented to clear such additional state and restore
 * the InstanceBuilder to a clean state ready to be re-used to build a different Instance.  The framework will always
 * call reset() before using an InstanceBuilder.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface InstanceBuilder
{
	/**
	 * Builds and returns a new {@link Instance}.
	 * 
	 * @return                                  the newly built instance.
	 * @throws      MessagesException           contains any user-resolvable errors that occur when attempting to build
	 *                                          the Instance.
	 * @since                                   1.0
	 */
	Instance build() throws MessagesException;

	/**
	 * Resets the InstanceBuilder, ready to build a new instance.
	 */
	void reset();
}
