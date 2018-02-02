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

package co.mv.wb.fixture;

import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.plugin.fake.FakeConstants;
import co.mv.wb.plugin.fake.FakeInstance;

import java.util.Optional;
import java.util.UUID;

/**
 * Factory for mocks and fixtures
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class Fixtures
{
	public static WildebeestApiMockBuilder wildebeestApi()
	{
		return new WildebeestApiMockBuilder();
	}

	public static ResourceHelperMockBuilder resourceHelper()
	{
		return new ResourceHelperMockBuilder();
	}

	public static Resource fakeResource()
	{
		return new ResourceImpl(
			UUID.randomUUID(),
			FakeConstants.Fake,
			"Fake",
			Optional.empty());
	}

	public static Instance fakeInstance()
	{
		return new FakeInstance();
	}
}
