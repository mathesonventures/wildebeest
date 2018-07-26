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

public abstract class ExpectException
{
	private final Class expectedType;

	protected ExpectException(
		Class expectedType)
	{
		if (expectedType == null) throw new ArgumentNullException("expectedType");

		this.expectedType = expectedType;
	}

	public void perform()
	{
		try
		{
			this.invoke();

			Assert.fail("Exception expected");
		}
		catch (Exception e)
		{
			if (e.getClass() == expectedType)
			{
				this.verify(e);
			}
			else
			{
				throw new RuntimeException(e);
			}
		}
	}

	public abstract void invoke() throws Exception;

	public abstract void verify(Exception e);
}
