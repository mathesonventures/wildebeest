package co.mv.stm.service;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.AssertionResponse;

public interface Logger
{
	void assertionStart(Assertion assertion);
	
	void assertionComplete(Assertion assertion, AssertionResponse response);
}
