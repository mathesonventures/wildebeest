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

package co.mv.wb.framework;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.function.Function;

public class PredicateMatcher<T> extends BaseMatcher<T>
{
	private Function<T, Boolean> _predicate;

	public PredicateMatcher(Function<T, Boolean> predicate)
	{
		if (predicate == null) { throw new IllegalArgumentException("predicate cannot be null"); }

		_predicate = predicate;
	}

	@Override public boolean matches(Object o)
	{
		T t = (T)o;

		return _predicate.apply(t);
	}

	@Override public void describeTo(Description description)
	{
		throw new RuntimeException("not implemented");
	}
}
