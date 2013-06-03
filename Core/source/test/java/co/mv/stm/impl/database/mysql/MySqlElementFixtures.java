package co.mv.stm.impl.database.mysql;

import java.util.UUID;

public class MySqlElementFixtures
{
	public static String stmStateCreateTableStatement()
	{
		StringBuilder sql = new StringBuilder();
		sql
			.append("CREATE TABLE `StmState` (`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`)) ")
				.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		return sql.toString();
	}
	
	public static String stmStateInsertRow(
		UUID stateId)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO `StmState`(`StateId`) VALUES('").append(stateId.toString()).append("');");

		return sql.toString();
	}
	
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
