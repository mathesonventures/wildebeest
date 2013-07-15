package co.zd.wb.model;

import java.util.UUID;

public interface Migration
{
	UUID getMigrationId();
	
	UUID getFromStateId();
	
	boolean hasFromStateId();
	
	UUID getToStateId();
	
	boolean hasToStateId();
	
	void perform(Instance instance) throws MigrationFailedException;
}