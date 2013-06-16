package co.mv.stm.service;

import co.mv.stm.model.Transition;
import java.util.UUID;

public interface TransitionBuilder
{
	Transition build(UUID transitionId, UUID fromStateId, UUID toStateId);
	
	void reset();
}