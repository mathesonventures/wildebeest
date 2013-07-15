package co.zd.wb;

import co.zd.wb.cli.CommandType;
import co.zd.wb.cli.WildebeestCommand;
import co.zd.wb.model.mysql.MySqlDatabaseInstance;
import co.zd.wb.model.mysql.MySqlUtil;
import co.zd.wb.service.PrintStreamLogger;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Test;

public class IntegrationTests
{
	@Test public void loadFromFilesAndMigrate() throws SQLException
	{
		
		//
		// Load Command
		//

		System.out.println(new java.io.File(".").getAbsolutePath());
		
		// Fixture Setup
		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"migrate",
			"--resource:integration_test_fixtures/mysql_database/database.wbresource.xml",
			"--instance:integration_test_fixtures/mysql_database/staging_db.wbinstance.xml",
			"--targetState:Core Schema Loaded"
		};
		PrintStreamLogger logger = new PrintStreamLogger(System.out);

		// Execute
		wb.parse(args);

		// Assert Results
		Assert.assertEquals("wb.command", CommandType.Migration, wb.getCommand());
		Assert.assertEquals(
			"wb.resource",
			"integration_test_fixtures/mysql_database/database.wbresource.xml",
			wb.getResource());
		Assert.assertEquals(
			"wb.instance",
			"integration_test_fixtures/mysql_database/staging_db.wbinstance.xml",
			wb.getInstance());

		//
		// Run
		//
		
		try
		{
			wb.run(logger);
		}
		finally
		{
			MySqlDatabaseInstance instance = (MySqlDatabaseInstance)wb.loadInstance();
			MySqlUtil.dropDatabase(instance, instance.getSchemaName());
		}
		
	}
}