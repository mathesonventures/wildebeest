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

import java.util.UUID;

/**
 * Global functions for Wildebeest.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class Wildebeest
{
	public static State stateForId(
		Resource resource,
		UUID stateId)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (stateId == null) { throw new IllegalArgumentException("stateId cannot be null"); }

		State result = null;

		for(State check : resource.getStates())
		{
			if (stateId.equals(check.getStateId()))
			{
				result = check;
				break;
			}
		}

		return result;
	}
}
