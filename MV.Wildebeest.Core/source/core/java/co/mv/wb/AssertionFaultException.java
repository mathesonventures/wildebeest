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
 * Indicates that an unexpected error occurred while attempting to apply an Assertion.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class AssertionFaultException extends RuntimeException
{
	/**
	 * Creates a new AssertionFaultException with the supplied ID and root cause.
	 * 
	 * @param       assertionId                 the ID of the Assertion that faulted
	 * @param       cause                       the root cause of the fault
	 * @since                                   1.0
	 */
	public AssertionFaultException(
		UUID assertionId,
		Exception cause)
	{
		super(cause);
	}
	
	// <editor-fold desc="AssertionId" defaultstate="collapsed">

	private UUID _assertionId = null;
	private boolean _assertionId_set = false;

	/**
	 * Gets the ID of the Assertion that was faulted.
	 * 
	 * @since                                   1.0
	 */
	public UUID getAssertionId() {
		if(!_assertionId_set) {
			throw new IllegalStateException("assertionId not set.  Use the HasAssertionId() method to check its state before accessing it.");
		}
		return _assertionId;
	}

	private void setAssertionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionId cannot be null");
		}
		boolean changing = !_assertionId_set || _assertionId != value;
		if(changing) {
			_assertionId_set = true;
			_assertionId = value;
		}
	}

	private void clearAssertionId() {
		if(_assertionId_set) {
			_assertionId_set = true;
			_assertionId = null;
		}
	}

	private boolean hasAssertionId() {
		return _assertionId_set;
	}

	// </editor-fold>
}
