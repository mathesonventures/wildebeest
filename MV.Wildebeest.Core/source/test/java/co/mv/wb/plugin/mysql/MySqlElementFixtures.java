// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb.plugin.mysql;

public class MySqlElementFixtures
{
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
