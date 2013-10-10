// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb.plugin.sqlserver;

import co.zd.wb.plugin.database.DatabaseConstants;
import java.util.UUID;

public class SqlServerElementFixtures
{
	public static String stateCreateTableStatement()
	{
		StringBuilder sql = new StringBuilder();
		sql
			.append("CREATE TABLE [dbo].[").append(DatabaseConstants.DefaultStateTableName)
				.append("]([StateId] [uniqueidentifier] NOT NULL,")
				.append("CONSTRAINT [PK_").append(DatabaseConstants.DefaultStateTableName)
					.append("] PRIMARY KEY CLUSTERED([StateId] ASC) ")
					.append("WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, ")
					.append("IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ")
					.append("ON [PRIMARY]) ON [PRIMARY];");
	
		return sql.toString();
	}
	
	public static String stateInsertRow(
		UUID stateId)
	{
		if (stateId == null) { throw new IllegalArgumentException("stateId"); }

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO [").append(DatabaseConstants.DefaultStateTableName).append("]")
			.append("([StateId]) VALUES('").append(stateId.toString()).append("');");

		return sql.toString();
	}
	
	public static String productCatalogueDatabase()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE [dbo].[ProductType](")
			.append("[ProductTypeCode] [char](2) NOT NULL,")
			.append("[Name] [varchar](10) NOT NULL,")
			.append("CONSTRAINT [PK_ProductType] PRIMARY KEY CLUSTERED")
			.append("(")
			.append("[ProductTypeCode] ASC")
			.append(")")
			.append("WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ")
			.append("ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]) ON [PRIMARY];");
		
		sql.append("CREATE TABLE [dbo].[Product](")
			.append("[ProductId] [uniqueidentifier] NOT NULL,")
			.append("[ProductTypeCode] [char](2) NOT NULL,")
			.append("[Name] [varchar](50) NOT NULL,")
			.append("[Description] [varchar](4000) NOT NULL,")
			.append("CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED ")
			.append("(")
			.append("[ProductId] ASC ")
			.append(")")
			.append("WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]")
			.append(") ON [PRIMARY];")
			
			.append("SET ANSI_PADDING OFF;")

			.append("ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_ProductTypeCode] FOREIGN KEY([ProductTypeCode])")
			.append("REFERENCES [dbo].[ProductType] ([ProductTypeCode]);")

			.append("ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_ProductTypeCode];");
		
		return sql.toString();
	}
	
	public static String productTypeRows()
	{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ProductType(ProductTypeCode, Name) VALUES('HW', 'Hardware'), ('SW', 'Software');");
		return sql.toString();
	}
}
