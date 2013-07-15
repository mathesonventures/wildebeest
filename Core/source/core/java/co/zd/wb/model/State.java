package co.zd.wb.model;

import java.util.List;
import java.util.UUID;

public interface State
{
	UUID getStateId();
	
	String getLabel();
	
	boolean hasLabel();
	
	List<Assertion> getAssertions();
	
	/**
	 * Returns the label if the state has one, otherwise returns it's ID.
	 */
	String getDisplayName();
}