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

package co.zd.wb.service.dom;

/**
 * The result from some try-get method.  A TryGetResult will contain a value if it was successful, or will be empty if
 * the try-get was unsuccessful.
 * 
 * @param           <TValue>                    the type of value that this result conveys.
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
public class TryGetResult<TValue>
{
	/**
	 * Creates a new empty TryGetResult.
	 * 
	 * @since                                   2.0
	 */
	public TryGetResult()
	{
	}

	/**
	 * Creates a new TryGetResult containing the supplied value.
	 * 
	 * @param       value                       the value for this TryGetResult.
	 * @since                                   2.0
	 */
	public TryGetResult(TValue value)
	{
		this.setValue(value);
	}
	
	// <editor-fold desc="Value" defaultstate="collapsed">

	private TValue _value = null;
	private boolean _value_set = false;

	/**
	 * Gets the value for this TryGetResult, or throws an IllegalStateException if this is an empty TryGetResult.
	 * Call hasValue() before calling this method to check if the TryGetResult has a value.
	 * 
	 * @since                                   2.0
	 */
	public TValue getValue() {
		if(!_value_set) {
			throw new IllegalStateException("value not set.  Use the HasValue() method to check its state before accessing it.");
		}
		return _value;
	}

	private void setValue(
		TValue value) {
		if(value == null) {
			throw new IllegalArgumentException("value cannot be null");
		}
		boolean changing = !_value_set || _value != value;
		if(changing) {
			_value_set = true;
			_value = value;
		}
	}

	private void clearValue() {
		if(_value_set) {
			_value_set = true;
			_value = null;
		}
	}

	/**
	 * Indicates if this TryGetResult has a value.
	 * 
	 * @since                                   2.0
	 */
	public boolean hasValue() {
		return _value_set;
	}

	// </editor-fold>
}
