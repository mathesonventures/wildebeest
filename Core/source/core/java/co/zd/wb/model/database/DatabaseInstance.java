package co.zd.wb.model.database;

import co.zd.wb.model.Instance;
import javax.sql.DataSource;

public interface DatabaseInstance extends Instance
{
	DataSource getAppDataSource();
}