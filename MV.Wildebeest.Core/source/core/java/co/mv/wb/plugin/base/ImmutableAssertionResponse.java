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

import co.mv.wb.AssertionResponse;
import co.mv.wb.framework.ArgumentNullException;

/**
 * An {@link AssertionResponse} that cannot be modified after it's initial construction.
 *
 * @since 1.0
 */
public class ImmutableAssertionResponse implements AssertionResponse
{
	private final boolean result;
	private final String message;

	/**
	 * Creates a new ImmutableAssertionResponse instance.
	 *
	 * @param result  the result for the new AssertionResponse
	 * @param message the message for the new AssertionResponse
	 */
	public ImmutableAssertionResponse(
		boolean result,
		String message)
	{
		if (message == null) throw new ArgumentNullException("message");

		this.result = result;
		this.message = message;
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
