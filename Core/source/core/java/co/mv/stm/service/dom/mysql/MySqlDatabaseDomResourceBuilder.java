package co.mv.stm.service.dom.mysql;

import co.mv.stm.model.Resource;
import co.mv.stm.model.mysql.MySqlDatabaseResource;
import co.mv.stm.service.dom.BaseDomResourceBuilder;
import java.util.UUID;

public class MySqlDatabaseDomResourceBuilder extends BaseDomResourceBuilder
{
	@Override public Resource build(UUID id, String name)
	{
		return new MySqlDatabaseResource(id, name);
	}
}