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

public class UtilTest
{
	@Test
	public void coalesceWhiteSpacesSingleLine()
	{
		String text = " Text with    extra spaces.  In the start, middle and end ";
		String expected = "Text with extra spaces. In the start, middle and end";
		String result = Util.coalesceWhitespace(text);
		Assert.assertEquals("White spaces should be coalesce to single white space", expected, result);
	}

	@Test
	public void coalesceWhiteSpacesMultiLine()
	{
		String text = " Text with    extra spaces.  \nIn the start, middle and end \n";
		String expected = "Text with extra spaces. In the start, middle and end";
		String result = Util.coalesceWhitespace(text);
		Assert.assertEquals("White spaces should be coalesce to single white space", expected, result);
	}
}
