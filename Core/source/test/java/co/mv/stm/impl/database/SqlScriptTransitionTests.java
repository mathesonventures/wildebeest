package co.mv.stm.impl.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlScriptTransitionTests
{
	public SqlScriptTransitionTests()
	{
	}
	
	@Test
	public void performSuccessfully()
	{
		
		//
		// Fixture Setup
		//
		
		String hostName = "127.0.0.1";
		int port = 3306;
		String username = "root";
		String password = "password";
		
		MysqlDataSource rootDs = new MysqlDataSource();
		rootDs.setServerName(hostName);
		rootDs.setPort(port);
		rootDs.setUser(username);
		rootDs.setPassword(password);
		rootDs.setDatabaseName("mysql");

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE  `RealmTypeRef` (").append("\n")
			.append("  `RealmTypeRcd` char(2) NOT NULL,").append("\n")
			.append("  `Name` varchar(10) NOT NULL,").append("\n")
			.append("  PRIMARY KEY (`RealmTypeRcd`)").append("\n")
			.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;").append("\n");
		
		SqlScriptTransition t = new SqlScriptTransition(
			UUID.randomUUID(),
			UUID.randomUUID(),
			sql.toString());
		
		//
		// Execute
		//
		
		//
		// Assert Results
		//
		
		
	}
}