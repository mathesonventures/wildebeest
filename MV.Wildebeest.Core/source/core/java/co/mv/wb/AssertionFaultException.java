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

import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * Indicates that an unexpected error occurred while attempting to apply an Assertion.
 * 
 * @since                                       1.0
 */
public class AssertionFaultException extends RuntimeException
{
	private final UUID assertionId;

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

		if (assertionId == null) throw new ArgumentNullException("assertionId");
		
		this.assertionId = assertionId;
	}
	
	/**
	 * Gets the ID of the Assertion that was faulted.
	 * 
	 * @since                                   1.0
	 */
	public UUID getAssertionId()
	{
		return this.assertionId;
	}
}
