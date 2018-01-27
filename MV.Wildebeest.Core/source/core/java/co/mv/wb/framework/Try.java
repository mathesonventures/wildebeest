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

package co.mv.wb.framework;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides various try-get-style helpers that make parsing, validating and consuming raw values tidier
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class Try
{
	/**
	 * Tries to parse the supplied raw string value as a UUID, and returns a TryResult<UUID> representing the result of
	 * the attempted parse.
	 * 
	 * @param       value                       the raw value to attempt to parse as a UUID
	 * @return                                  a {@link TryResult<UUID> representing the result of the attempted parse
	 *                                          operation.
	 */
	public static Optional<UUID> tryParseUuid(String value)
	{
		if (value == null) { throw new IllegalArgumentException("value cannot be null"); }
		
		Optional<UUID> result;
		
		try
		{
			result = Optional.of(UUID.fromString(value));
		}
		catch(IllegalArgumentException e)
		{
			result = Optional.empty();
		}

		return result;
	}
}
