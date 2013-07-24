package co.zd.wb.service;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.AssertionFailedException;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.IndeterminateStateException;
import co.zd.wb.model.Resource;
import co.zd.wb.model.Migration;
import co.zd.wb.model.MigrationFailedException;
import co.zd.wb.model.MigrationNotPossibleException;

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
	
	void indeterminateState(
		IndeterminateStateException e);
	
	void assertionFailed(
		AssertionFailedException e);

	void migrationNotPossible(
		MigrationNotPossibleException e);
	
	void migrationFailed(
		MigrationFailedException e);
	
	void logLine(String message);
}