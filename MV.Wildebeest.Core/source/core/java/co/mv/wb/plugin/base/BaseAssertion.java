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

package co.mv.wb.plugin.base;

import co.mv.wb.Assertion;
import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * Provides a base implementation of {@link Assertion}
 *
 * @since 1.0
 */
public abstract class BaseAssertion implements Assertion
{
	private final UUID assertionId;
	private final int seqNum;

	/**
	 * Creates a new BaseAssertion.
	 *
	 * @param assertionId the ID of the new Assertion
	 * @param seqNum      the ordinal index of the new Assertion within its owning container
	 */
	protected BaseAssertion(
		UUID assertionId,
		int seqNum)
	{
		if (assertionId == null) throw new ArgumentNullException("assertionId");

		this.assertionId = assertionId;
		this.seqNum = seqNum;
	}

	@Override
	public UUID getAssertionId()
	{
		return this.assertionId;
	}

	@Override
	public int getSeqNum()
	{
		return this.seqNum;
	}
}
