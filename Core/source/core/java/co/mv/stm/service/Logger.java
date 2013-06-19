package co.mv.stm.service;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.Resource;
import co.mv.stm.model.Migration;

public interface Logger
{
	void assertionStart(Assertion assertion);
	
	void assertionComplete(Assertion assertion, AssertionResponse response);
	
	void migrationStart(
		Resource resource,
		Migration migration);
	
	void migrationComplete(
		Resource resource,
		Migration migration);
}