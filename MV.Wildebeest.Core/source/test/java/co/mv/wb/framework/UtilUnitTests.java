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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@link Util} functions.
 *
 * @since 4.0
 */
public class UtilUnitTests
{
	/**
	 * Tests that a single-line string with whitespaces (spaces and tabs) is coalesced correctly.
	 *
	 * @since 4.0
	 */
	@Test
	public void coalesceWhitespace_singleLine_succeeds()
	{
		// Setup
		String text = " Text with    extra spaces.  In the\t start, middle and end ";

		// Execute
		String result = Util.coalesceWhitespace(text);

		// Verify
		Assert.assertEquals("result", "Text with extra spaces. In the start, middle and end", result);
	}

	/**
	 * Tests that a multi-line string with whitespaces (spaces, tabs and newlines both LF and CRLF) is coalesced
	 * correctly.
	 *
	 * @since 4.0
	 */
	@Test
	public void coalesceWhitespace_multiLine_succeeds()
	{
		// Setup
		String text = " Text with    extra\t \t spaces.  \nIn the start, middle\r\n and end \n";

		// Execute
		String result = Util.coalesceWhitespace(text);

		// Verify
		Assert.assertEquals("result", "Text with extra spaces. In the start, middle and end", result);
	}
}
