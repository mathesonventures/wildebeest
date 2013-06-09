package co.mv.stm.service;

import co.mv.stm.Resource;
import java.util.UUID;

public interface ResourceBuilder
{
	Resource build(UUID id, String name);
	
	void reset();
}
