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
import co.mv.wb.AssertionPlugin;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.PluginHandler;
import co.mv.wb.framework.ArgumentNullException;

/**
 * Handler for {@link FaultingAssertion}.
 *
 * @since 4.0
 */
@PluginHandler(
	uri = "co.mv.wb.fake:Faulting"
)
public class FaultingAssertionPlugin implements AssertionPlugin
{
	@Override
	public AssertionResponse perform(
		Assertion assertion,
		Instance instance)
	{
		if (assertion == null) throw new ArgumentNullException("assertion");
		if (instance == null) throw new ArgumentNullException("instance");

		FaultingAssertion assertionT = (FaultingAssertion)assertion;

		throw new RuntimeException("root cause");
	}
}
