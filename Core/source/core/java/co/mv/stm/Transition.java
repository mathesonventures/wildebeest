package co.mv.stm;

import java.util.UUID;

public interface Transition
{
	UUID getTransitionId();
	
	UUID getFromStateId();
	
	boolean hasFromStateId();
	
	UUID getToStateId();
	
	void perform(ResourceInstance instance) throws TransitionFailedException;
}