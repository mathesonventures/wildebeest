package co.mv.stm.impl;

import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.AssertionResult;
import java.util.UUID;
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
	
	public static void assertAssertionResult(
		UUID expectedAssertionId,
		boolean expectedResult,
		String expectedMessage,
		AssertionResult actual,
		String name)
	{
		if (expectedAssertionId == null) { throw new IllegalArgumentException("expectedAssertionId"); }
		if (expectedMessage == null) { throw new IllegalArgumentException("expectedMessage"); }
		if (actual == null) { throw new IllegalArgumentException("actual"); }
		if (name == null) { throw new IllegalArgumentException("name"); }
		if ("".equals(name)) { throw new IllegalArgumentException("name cannot be blank"); }

		Assert.assertEquals(name + ".assertionId", expectedAssertionId, actual.getAssertionId());
		Assert.assertEquals(name + ".result", expectedResult, actual.getResult());
		Assert.assertEquals(name + ".message", expectedMessage, actual.getMessage());
	}
}
