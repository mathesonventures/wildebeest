package co.mv.stm.model;

import java.util.UUID;

public interface Assertion
{
	UUID getAssertionId();
	
	String getName();
	
	boolean hasName();
	
	int getSeqNum();
	
	AssertionType getAssertionType();
	
	AssertionResponse apply(ResourceInstance instance);
}