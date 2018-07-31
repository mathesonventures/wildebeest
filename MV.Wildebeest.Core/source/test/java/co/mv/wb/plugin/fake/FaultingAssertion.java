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

package co.mv.wb.plugin.fake;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionType;
import co.mv.wb.ResourceType;
import co.mv.wb.framework.ArgumentNullException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that always throws a RuntimeException with the message "root cause" - for automated tests.
 *
 * @since 4.0
 */
@AssertionType(
	pluginGroupUri = "co.mv.wb:Fake",
	uri = "co.mv.wb.fake:Faulting",
	description =
		"Always faults out.",
	example =
		"<assertion\n" +
			"    type=\"Faulting\"\n" +
			"    id=\"5ad24640-3c2d-42a5-9bc2-1f49dbfced61\"\n" +
			"</assertion>"
)
public class FaultingAssertion implements Assertion
{
	private final UUID assertionId;

	/**
	 * Creates a new FaultingAssertion with the supplied assertion ID.
	 *
	 * @param assertionId the ID for this {@link Assertion}.
	 */
	public FaultingAssertion(
		UUID assertionId)
	{
		if (assertionId == null) throw new ArgumentNullException("assertionId");

		this.assertionId = assertionId;
	}

	@Override
	public UUID getAssertionId()
	{
		return this.assertionId;
	}

	@Override
	public String getDescription()
	{
		return "Faulting Assertion";
	}

	@Override
	public int getSeqNum()
	{
		return 0;
	}

	@Override
	public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList();
	}

}
