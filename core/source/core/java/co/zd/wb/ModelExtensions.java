// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

public class ModelExtensions
{
	public static <T> T As(
		Instance value,
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