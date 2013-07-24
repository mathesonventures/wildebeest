package co.zd.wb.cli;

import co.zd.wb.Interface;
import co.zd.wb.model.Instance;
import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.mysql.MySqlUtil;
import java.io.File;
import java.sql.SQLException;
import org.junit.Test;

public class IntegrationTests
{
	@Test public void loadFromFilesAndMigrate() throws SQLException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"migrate",
			"--resource:integration_test_fixtures/mysql_database/database.wbresource.xml",
			"--instance:integration_test_fixtures/mysql_database/staging_db.wbinstance.xml",
			"--targetState:Core Schema Loaded"
		};
		
		Instance instance = Interface.loadInstance(
			new File("integration_test_fixtures/mysql_database/staging_db.wbinstance.xml"));
		
		//
		// Execute
		//

		try
		{
			wb.run(args);
		}
		finally
		{
			MySqlDatabaseInstance instanceT = (MySqlDatabaseInstance)instance;
			MySqlUtil.dropDatabase(instanceT, instanceT.getSchemaName());
		}

	}
}