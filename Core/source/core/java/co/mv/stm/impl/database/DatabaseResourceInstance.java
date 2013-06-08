package co.mv.stm.impl.database;

import co.mv.stm.ResourceInstance;
import javax.sql.DataSource;

public interface DatabaseResourceInstance extends ResourceInstance
{
	DataSource getAppDataSource();
}