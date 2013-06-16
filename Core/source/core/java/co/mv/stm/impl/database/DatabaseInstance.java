package co.mv.stm.impl.database;

import co.mv.stm.Instance;
import javax.sql.DataSource;

public interface DatabaseInstance extends Instance
{
	DataSource getAppDataSource();
}