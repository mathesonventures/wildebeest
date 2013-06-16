package co.mv.stm.service;

public class InstanceLoaderFault extends RuntimeException
{
	public InstanceLoaderFault(Exception cause)
	{
		super(cause);
	}
	
	public InstanceLoaderFault(String message)
	{
		super(message);
	}
}