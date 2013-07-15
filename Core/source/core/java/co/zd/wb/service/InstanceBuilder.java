package co.zd.wb.service;

import co.zd.wb.model.Instance;

public interface InstanceBuilder
{
	Instance build();
	
	void reset();
}