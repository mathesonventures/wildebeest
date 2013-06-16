package co.mv.stm.model;

import co.mv.stm.model.Assertion;
import java.util.List;
import java.util.UUID;

public interface State
{
	UUID getStateId();
	
	String getLabel();
	
	boolean hasLabel();
	
	List<Assertion> getAssertions();
}