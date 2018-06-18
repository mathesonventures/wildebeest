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

import co.mv.wb.AssertionResult;
import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * An {@link AssertionResult} that cannot be modified after it's initial construction.
 *
 * @since 1.0
 */
public class ImmutableAssertionResult implements AssertionResult
{
	private final UUID assertionId;
	private final boolean result;
	private final String message;

	/**
	 * Creates a new ImmutableAssertionResult for the specified assertion, with the supplied result value and message.
	 *
	 * @param assertionId the ID of the assertion that was applied to get this result
	 * @param result      indicator whether the assertion succeeded or failed
	 * @param message     the textual message describing the assertion result
	 */
	public ImmutableAssertionResult(
		UUID assertionId,
		boolean result,
		String message)
	{
		if (assertionId == null) throw new ArgumentNullException("assertionId");
		if (message == null) throw new ArgumentNullException("message");

		this.assertionId = assertionId;
		this.result = result;
		this.message = message;
	}

	@Override
	public UUID getAssertionId()
	{
		return this.assertionId;
	}

	@Override
	public boolean getResult()
	{
		return this.result;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}
}
