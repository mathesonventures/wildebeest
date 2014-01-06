package co.zd.wb;

import co.mv.protium.system.NotImplementedException;

public class StdoutLogger implements Logger
{
	@Override public void assertionStart(Assertion assertion)
	{
		throw new NotImplementedException();
	}
	
	@Override public void assertionComplete(
		Assertion assertion,
		AssertionResponse response)
	{
		throw new NotImplementedException();
	}
	
	@Override public void migrationStart(
		Resource resource,
		Migration migration)
	{
		throw new NotImplementedException();
	}

	@Override public void migrationComplete(
		Resource resource,
		Migration migration)
	{
		throw new NotImplementedException();
	}
	
	@Override public void indeterminateState(
		IndeterminateStateException e)
	{
		throw new NotImplementedException();
	}
	
	@Override public void assertionFailed(
		AssertionFailedException e)
	{
		throw new NotImplementedException();
	}

	@Override public void migrationNotPossible(
		MigrationNotPossibleException e)
	{
		throw new NotImplementedException();
	}
	
	@Override public void migrationFailed(
		MigrationFailedException e)
	{
		throw new NotImplementedException();
	}
	
	@Override public void logLine(String message)
	{
		throw new NotImplementedException();
	}
}
