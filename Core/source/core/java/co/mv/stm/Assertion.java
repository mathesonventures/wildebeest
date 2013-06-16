package co.mv.stm;

import java.util.UUID;

public interface Assertion
{
	UUID getAssertionId();
	
	String getName();
	
	boolean hasName();
	
	int getSeqNum();
	
	AssertionResponse apply(Instance instance);
}