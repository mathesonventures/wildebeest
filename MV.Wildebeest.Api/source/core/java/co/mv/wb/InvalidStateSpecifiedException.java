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
 * Indicates that the state specified for a migrate or a jumpstate command is invalid.
 * 
 * @author                                      Brendon Matheson
 * @since                                       3.0
 */
public class InvalidStateSpecifiedException extends Exception
{
    public InvalidStateSpecifiedException(String specifiedState)
    {
        super(String.format("Specified state is not valid: \"%s\"", specifiedState));
        
        this.setSpecifiedState(specifiedState);
    }
    
	// <editor-fold desc="SpecifiedState" defaultstate="collapsed">

	private String _specifiedState = null;
	private boolean _specifiedState_set = false;

	public String getSpecifiedState() {
		if(!_specifiedState_set) {
			throw new IllegalStateException("specifiedState not set.");
		}
		if(_specifiedState == null) {
			throw new IllegalStateException("specifiedState should not be null");
		}
		return _specifiedState;
	}

	private void setSpecifiedState(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("specifiedState cannot be null");
		}
		boolean changing = !_specifiedState_set || _specifiedState != value;
		if(changing) {
			_specifiedState_set = true;
			_specifiedState = value;
		}
	}

	// </editor-fold>
}
