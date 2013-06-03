package co.mv.stm.impl.database;

import co.mv.stm.impl.database.mysql.MySqlDatabaseResourceInstance;
import co.mv.stm.model.TransitionFailedException;
import co.zd.helium.fixture.MySqlDatabaseFixture;
import java.util.UUID;
import org.junit.Test;

public class SqlScriptTransitionTests
{
	public SqlScriptTransitionTests()
	{
	}
	
	@Test
	public void performSuccessfully() throws TransitionFailedException
	{
		
		//
		// Fixture Setup
		//
		
		MySqlDatabaseFixture f = new MySqlDatabaseFixture(
			"127.0.0.1",
			3306,
			"root",
			"password",
			"stm_test",
			"");
		f.setUp();
		
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE  `RealmTypeRef` (").append("\n")
			.append("  `RealmTypeRcd` char(2) NOT NULL,").append("\n")
			.append("  `Name` varchar(10) NOT NULL,").append("\n")
			.append("  PRIMARY KEY (`RealmTypeRcd`)").append("\n")
			.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;").append("\n");

		SqlScriptTransition tr = new SqlScriptTransition(
			UUID.randomUUID(),
			UUID.randomUUID(),
			sql.toString());
		
		MySqlDatabaseResourceInstance instance = new MySqlDatabaseResourceInstance(
			"127.0.0.1",
			3306,
			"root",
			"password",
			f.getDatabaseName());
		
		//
		// Execute
		//

		try
		{
			tr.perform(instance);
		}
		finally
		{
			f.tearDown();
		}
		
		//
		// Assert Results
		//
		
		
	}
}