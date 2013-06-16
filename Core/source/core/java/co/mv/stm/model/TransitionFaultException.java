package co.mv.stm.model;

public class TransitionFaultException extends RuntimeException
{
	public TransitionFaultException(Exception cause)
	{
		super(cause);
	}
	
	public TransitionFaultException(
		String message)
	{
		super(message);
	}
}