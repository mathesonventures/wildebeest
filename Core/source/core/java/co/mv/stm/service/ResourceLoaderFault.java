package co.mv.stm.service;

public class ResourceLoaderFault extends RuntimeException
{
	public ResourceLoaderFault(Exception cause)
	{
		super(cause);
	}
}
