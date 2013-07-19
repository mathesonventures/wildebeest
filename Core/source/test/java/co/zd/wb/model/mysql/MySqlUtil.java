package co.zd.wb.model.mysql;

import co.zd.wb.model.database.DatabaseHelper;
import java.sql.SQLException;

public class MySqlUtil
{
	public static void dropDatabase(
		MySqlDatabaseInstance instance,
		String databaseName) throws SQLException
	{
		DatabaseHelper.execute(instance.getInfoDataSource(), "DROP DATABASE `" + databaseName + "`;");
	}
}
