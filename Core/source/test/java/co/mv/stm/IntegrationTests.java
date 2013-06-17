package co.mv.stm;

import co.mv.stm.cli.CommandType;
import co.mv.stm.cli.WildebeestCommand;
import co.mv.stm.model.mysql.MySqlDatabaseInstance;
import co.mv.stm.model.mysql.MySqlUtil;
import co.mv.stm.service.Logger;
import co.mv.stm.service.PrintStreamLogger;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Test;

public class IntegrationTests
{
	@Test public void loadFromFilesAndTransition() throws SQLException
	{
		
		//
		// Load Command
		//

		System.out.println(new java.io.File(".").getAbsolutePath());
		
		// Fixture Setup
		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"transition",
			"--resource=integration_test_fixtures/mysql_database/database.wbresource.xml",
			"--instance=integration_test_fixtures/mysql_database/staging_db.wbinstance.xml",
			"--targetState=UserBase Schema Loaded"
		};
		Logger logger = new PrintStreamLogger(System.out);

		// Execute
		wb.parse(args);

		// Assert Results
		Assert.assertEquals("wb.command", CommandType.Transition, wb.getCommand());
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