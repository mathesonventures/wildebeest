package co.zd.wb.model.mysql;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MySqlElementFixtures
{
	public static String databaseName(String baseDatabaseName)
	{
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
		return baseDatabaseName + "_" + f.format(new Date());
	}
	
	public static String stateCreateTableStatement()
	{
		StringBuilder sql = new StringBuilder();
		sql
			.append("CREATE TABLE `wb_state` (`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`)) ")
				.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		
		return sql.toString();
	}
	
	public static String stateInsertRow(
		UUID stateId)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO `wb_state`(`StateId`) VALUES('").append(stateId.toString()).append("');");

		return sql.toString();
	}
	
	public static String productCatalogueDatabase()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE  `ProductType` (").append("\n")
			.append("  `ProductTypeCode` char(2) NOT NULL,").append("\n")
			.append("  `Name` varchar(10) NOT NULL,").append("\n")
			.append("  PRIMARY KEY (`ProductTypeCode`)").append("\n")
			.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;").append("\n");

		sql.append("CREATE TABLE `Product` (").append("\n")
			.append("  `ProductId` char(36) NOT NULL,").append("\n")
			.append("  `ProductTypeCode` char(2) NOT NULL,").append("\n")
			.append("  `Name` varchar(50) NOT NULL,").append("\n")
			.append("  `Description` varchar(4000) NOT NULL,").append("\n")
			.append("  PRIMARY KEY (`ProductId`),").append("\n")
			.append("  KEY `FK_Product_ProductTypeCode` (`ProductTypeCode`),").append("\n")
			.append("  CONSTRAINT `FK_Product_ProductTypeCode` FOREIGN KEY (`ProductTypeCode`) REFERENCES `ProductType` (`ProductTypeCode`)").append("\n")
			.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;").append("\n");
		
		return sql.toString();
	}
	
	public static String productTypeRows()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ProductType(ProductTypeCode, Name) VALUES('HW', 'Hardware'), ('SW', 'Software');");
		return sql.toString();
	}
}
