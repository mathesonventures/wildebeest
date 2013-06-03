package co.mv.stm.impl.database;

import co.mv.stm.model.ResourceInstance;
import javax.sql.DataSource;

public interface DatabaseResourceInstance extends ResourceInstance
{
	DataSource getAppDataSource();
}