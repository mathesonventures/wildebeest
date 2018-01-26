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
 * The response from an {@link Assertion}'s {@link Assertion#apply()} method.
 * 
 * Note: this is not to be confused with an AssertionResult, which is created from an AssertionResponse but is returned
 * by aggregate functions such as {@link Resource#assertState()}
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface AssertionResponse
{
	/**
	 * Gets the result, a boolean flag indicating whether or not the Assertion evaluation passed or failed.
	 * 
	 * @return                                  a boolean flag indicating whether or not the assertion evaluation passed
	 *                                          or failed
	 * @since                                   1.0
	 */
	boolean getResult();
	
	/**
	 * Gets the message that was raised by the Assertion during evaluation.
	 * 
	 * @return                                  the message that was raised by the assertion during evalulation
	 * @since                                   1.0
	 */
	String getMessage();
}
