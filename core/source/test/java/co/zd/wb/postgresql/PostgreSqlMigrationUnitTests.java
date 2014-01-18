package co.zd.wb.postgresql;

import co.zd.wb.MigrationFailedException;
import co.zd.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import java.sql.SQLException;
import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class PostgreSqlMigrationUnitTests
{
	@Test public void ansiSqlCreateDatabaseMigrationSucceeds() throws MigrationFailedException
	{
		
		//
		// Setup
		//
		
		PostgreSqlDatabaseInstance db = new PostgreSqlDatabaseInstance(
			"127.0.0.1",
			5432,
			"postgres",
			"password",
			"SkyfallTest",
			null);
		
		AnsiSqlCreateDatabaseMigration m = new AnsiSqlCreateDatabaseMigration(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID());

		try
		{

			//
			// Execute
			//

			m.perform(db);

			//
			// Verify
			//

			Assert.assertEquals("databaseExists", true, db.databaseExists());

		}
		finally
		{
			try
			{
				DatabaseHelper.execute(
					db.getAdminDataSource(),
					"DROP DATABASE SkyfallTest");
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
		}
		
	}
}