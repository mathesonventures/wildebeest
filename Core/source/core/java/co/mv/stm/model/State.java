package co.mv.stm.model;

import java.util.List;
import java.util.UUID;

public interface State
{
	UUID getStateId();
	
	String getLabel();
	
	boolean hasLabel();
	
	List<Assertion> getAssertions();
}