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

package co.zd.wb.service;

import co.zd.wb.Assertion;
import java.util.UUID;

public interface AssertionBuilder
{
	/**
	 * 
	 * @param       assertionId                 the ID of the Assertion
	 * @param       seqNum                      the ordinal sequence number of the Assertion within the set it belongs
	 *                                          to.
	 * @return                                  a deserialized Assertion instance
	 * @throws      MessagesException           containing error messages if for any reason the deserialization could
	 *                                          not be carried out
	 */
	Assertion build(
		UUID assertionId,
		int seqNum) throws MessagesException;
	
	/**
	 * Resets the AssertionBuilder, making it ready to build a new instance.
	 */
	void reset();
}