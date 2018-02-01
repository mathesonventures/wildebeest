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

package co.mv.wb.mock;

import co.mv.wb.Resource;
import co.mv.wb.ResourceHelper;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceHelperMockBuilder
{
	private ResourceHelper _resourceHelper;

	public ResourceHelperMockBuilder()
	{
		_resourceHelper = mock(ResourceHelper.class);
	}

	public ResourceHelperMockBuilder withStateIdForLabel(
		String label,
		UUID stateId)
	{
		when(_resourceHelper.stateIdForLabel(any(Resource.class), eq(label))).thenReturn(stateId);

		return this;
	}

	public ResourceHelper get()
	{
		return _resourceHelper;
	}
}
