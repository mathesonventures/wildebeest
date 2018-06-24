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

/**
 * A {@link org.hamcrest.Matcher} that applies a predicate to determine the result.
 *
 * @param <T> the input type for the predicate.
 * @since 4.0
 */
public class PredicateMatcher<T> extends BaseMatcher<T>
{
	private final Function<T, Boolean> predicate;

	/**
	 * Creates a new PredicateMatcher with the supplied predicate.
	 *
	 * @param predicate the predicate that will be applied to determine the match result.
	 * @since 4.0
	 */
	public PredicateMatcher(Function<T, Boolean> predicate)
	{
		if (predicate == null) throw new ArgumentNullException("predicate");

		this.predicate = predicate;
	}

	@Override
	public boolean matches(Object o)
	{
		T t = (T)o;

		return predicate.apply(t);
	}

	@Override
	public void describeTo(Description description)
	{
	}
}
