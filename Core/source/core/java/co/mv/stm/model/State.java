package co.mv.stm.model;

import java.util.UUID;

public interface State
{
	UUID getStateId();
	
	String getLabel();
	
	boolean hasLabel();
}