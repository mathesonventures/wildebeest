package co.mv.stm;

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