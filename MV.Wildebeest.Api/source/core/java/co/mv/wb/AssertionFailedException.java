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

import java.util.List;
import java.util.UUID;

/**
 * Indicates that the application of an Assertion to a resource Instance failed, and provides the state for the
 * Assertion failed and the AssertionResult.
 *
 * @since 1.0
 */
public class AssertionFailedException extends Exception
{
	private final UUID stateId;
	private final List<AssertionResult> assertionResults;

	/**
	 * Creates a new AssertionFailedException for the specified state and AssertionResult.
	 *
	 * @param stateId          the state that was being asserted
	 * @param assertionResults the full set of assertion results for the state including both those that succeeded as
	 *                         well as those that failed to trigger this exception
	 * @since 1.0
	 */
	public AssertionFailedException(
		UUID stateId,
		List<AssertionResult> assertionResults)
	{
		if (stateId == null) throw new ArgumentNullException("stateId");
		if (assertionResults == null) throw new ArgumentNullException("assertionResults");

		this.stateId = stateId;
		this.assertionResults = assertionResults;
	}

	/**
	 * Gets the identity of the State for which Assertion evaluation failed.
	 *
	 * @return the ID of the State for which Assertion evaluation failed
	 * @since 1.0
	 */
	public UUID getStateId()
	{
		return this.stateId;
	}

	/**
	 * Gets the result of the evaluation of the Assertion.
	 *
	 * @return the set of result items from the assertions that were evalulated
	 * @since 1.0
	 */
	public List<AssertionResult> getAssertionResults()
	{
		return this.assertionResults;
	}
}
