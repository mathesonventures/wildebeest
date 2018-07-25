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
import co.mv.wb.AssertionFaultException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.ResourceType;
import co.mv.wb.framework.ArgumentNullException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FaultingAssertion implements Assertion
{
	private final UUID assertionId;

	public FaultingAssertion(
		UUID assertionId)
	{
		if (assertionId == null) throw new ArgumentNullException("assertionId");

		this.assertionId = assertionId;
	}

	@Override public UUID getAssertionId()
	{
		return this.assertionId;
	}

	@Override public String getDescription()
	{
		return "Faulting Assertion";
	}

	@Override public int getSeqNum()
	{
		return 0;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList();
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) throw new ArgumentNullException("instance");

		throw new AssertionFaultException(assertionId, new Exception("root cause"));
	}
}
