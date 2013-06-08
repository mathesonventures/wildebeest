package co.mv.stm.impl.database.mysql;

import co.mv.stm.model.AssertionResponse;
import org.junit.Assert;

public class AssertExtensions
{
	public static void assertAssertionResponse(
		boolean expectedResult,
		String expectedMessage,
		AssertionResponse actual,
		String name)
	{
		if (expectedMessage == null) { throw new IllegalArgumentException("expectedMessage"); }
		if (actual == null) { throw new IllegalArgumentException("actual"); }
		if (name == null) { throw new IllegalArgumentException("name"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be empty"); }
		
		Assert.assertEquals(name + ".result", expectedResult, actual.getResult());
		Assert.assertEquals(name + ".message", expectedMessage, actual.getMessage());
	}
}
