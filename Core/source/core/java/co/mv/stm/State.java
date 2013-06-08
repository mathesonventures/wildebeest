package co.mv.stm;

import co.mv.stm.Assertion;
import java.util.List;
import java.util.UUID;

public interface State
{
	UUID getStateId();
	
	String getLabel();
	
	boolean hasLabel();
	
	List<Assertion> getAssertions();
}