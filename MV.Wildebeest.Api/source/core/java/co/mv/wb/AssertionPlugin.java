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

/**
 * AssertionPlugins perform {@link Assertion}'s.
 *
 * @since 4.0
 */
public interface AssertionPlugin
{
	/**
	 * Evaluates the supplied {@link Assertion} against the supplied resource {@link Instance}.
	 *
	 * @param assertion the Assertion to apply.
	 * @param instance  the Instance to apply the Assertion to.
	 * @return an AssertionResponse indicating the outcome of applying the Assertion to the supplied Instance.
	 * @since 1.0
	 */
	AssertionResponse perform(
		Assertion assertion,
		Instance instance);
}
