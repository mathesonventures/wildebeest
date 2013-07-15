package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Resource;
import co.zd.wb.model.mysql.MySqlDatabaseResource;
import co.zd.wb.service.dom.BaseDomResourceBuilder;
import java.util.UUID;

public class MySqlDatabaseDomResourceBuilder extends BaseDomResourceBuilder
{
	@Override public Resource build(UUID id, String name)
	{
		return new MySqlDatabaseResource(id, name);
	}
}