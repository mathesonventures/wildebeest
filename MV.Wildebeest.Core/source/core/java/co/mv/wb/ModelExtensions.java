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

/**
 * Provides helpers for working with plugin instances.
 * 
 * @since                                       1.0
 */
public class ModelExtensions
{
	/**
	 * Attempts to cast the supplied value to the specified type, and returns the result.  If the cast cannot be
	 * performed because of a type mismatch, then null is returned.
	 * 
	 * @param       <T>                         the type to which the supplied value should be cast
	 * @param       value                       the value to be cast
	 * @param       type                        the type to which the supplied value should be cast
	 * @return                                  the original value, cast as the specified type, or null if no cast can
	 *                                          be performed
	 * @since                                   1.0
	 */
	public static <T> T As(
		Object value,
		Class<T> type)
	{
		if (value == null) { throw new IllegalArgumentException("value"); }
		if (type == null) { throw new IllegalArgumentException("type"); }
		
		T result = null;
		
		if (type.isAssignableFrom(value.getClass()))
		{
			result = (T)value;
		}
		
		return result;
	}
}
