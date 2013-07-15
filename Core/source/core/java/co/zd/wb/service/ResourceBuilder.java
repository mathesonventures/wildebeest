package co.zd.wb.service;

import co.zd.wb.model.Resource;
import java.util.UUID;

public interface ResourceBuilder
{
	Resource build(UUID id, String name);
	
	void reset();
}
