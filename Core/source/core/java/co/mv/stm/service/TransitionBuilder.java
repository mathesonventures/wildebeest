package co.mv.stm.service;

import co.mv.stm.Transition;

public interface TransitionBuilder
{
	Transition build();
	
	void reset();
}