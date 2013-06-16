package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.database.DatabaseHelper;
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
