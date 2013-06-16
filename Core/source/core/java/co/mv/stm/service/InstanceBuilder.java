package co.mv.stm.service;

import co.mv.stm.Instance;

public interface InstanceBuilder
{
	Instance build();
	
	void reset();
}