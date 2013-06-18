package co.mv.stm.service;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.Resource;
import co.mv.stm.model.Transition;

public interface Logger
{
	void assertionStart(Assertion assertion);
	
	void assertionComplete(Assertion assertion, AssertionResponse response);
	
	void transitionStart(
		Resource resource,
		Transition transition);
	
	void transitionComplete(
		Resource resource,
		Transition transition);
}