package co.zd.wb.service;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.Resource;
import co.zd.wb.model.Migration;

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