package co.mv.stm.impl.database.mysql;

public class MySqlElementFixtures
{
	public static String realmTypeRefCreateTableStatement()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE  `RealmTypeRef` (").append("\n")
			.append("  `RealmTypeRcd` char(2) NOT NULL,").append("\n")
			.append("  `Name` varchar(10) NOT NULL,").append("\n")
			.append("  PRIMARY KEY (`RealmTypeRcd`)").append("\n")
			.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;").append("\n");

		return sql.toString();
	}
	
	public static String realmTypeRefInsertUserBaseRow()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO `RealmTypeRef`(RealmTypeRcd, Name) VALUES('UB', 'UserBase');");
		return sql.toString();
	}
}
