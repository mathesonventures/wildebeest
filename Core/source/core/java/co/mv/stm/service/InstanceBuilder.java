package co.mv.stm.service;

import co.mv.stm.model.Instance;

public interface InstanceBuilder
{
	Instance build();
	
	void reset();
}