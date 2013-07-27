package co.zd.wb.model.database;

import co.zd.wb.model.Instance;
import javax.sql.DataSource;

public interface DatabaseInstance extends Instance
{
	String getStateTableName();
	
	void setStateTableName(String value);
	
	boolean hasStateTableName();
	
	DataSource getAppDataSource();
}