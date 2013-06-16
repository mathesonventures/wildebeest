package co.mv.stm.model.database;

import co.mv.stm.model.Instance;
import javax.sql.DataSource;

public interface DatabaseInstance extends Instance
{
	DataSource getAppDataSource();
}