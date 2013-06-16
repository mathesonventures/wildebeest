package co.mv.stm.model;

import java.util.UUID;

public interface Transition
{
	UUID getTransitionId();
	
	UUID getFromStateId();
	
	boolean hasFromStateId();
	
	UUID getToStateId();
	
	boolean hasToStateId();
	
	void perform(Instance instance) throws TransitionFailedException;
}