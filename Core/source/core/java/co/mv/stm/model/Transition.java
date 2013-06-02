package co.mv.stm.model;

import java.util.UUID;

public interface Transition
{
	UUID getTransitionId();
	
	TransitionType getTransitionType();
	
	UUID getFromStateId();
	
	boolean hasFromStateId();
	
	UUID getToStateId();
	
	void perform(ResourceInstance instance) throws TransitionFaultException;
}