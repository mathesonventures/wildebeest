// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

package co.mv.wb.plugin.generaldatabase;

import co.mv.wb.Migration;
import co.mv.wb.MigrationType;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that performs a SQL script to transition between states.
 * 
 * @since                                       1.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:GeneralDatabase",
	uri = "co.mv.wb.generaldatabase:SqlScript",
	description = "Migrates a database resource by applying a SQL script.  This migration can be used for any " +
			"dialect of SQL, as long as the DBMS supports it.",
	example =
		"<migration\n" +
		"    type=\"SqlScript\"\n" +
		"    id=\"8b57f16d-c690-4f10-b68f-6f1ee75fe32b\"\n" +
		"    fromStateId=\"199b7cc1-3cc6-48ca-b012-a70d05d5b5e7\"\n" +
		"    toStateId=\"363568f1-aaed-4a50-bea0-9ddee713cc11\">\n" +
		"    <sql><![CDATA[\n" +
		"\n" +
		"/* ProductType */\n" +
		"CREATE TABLE  `ProductType` (\n" +
		"  `ProductTypeCode` char(2) NOT NULL,\n" +
		"  `Name` varchar(10) NOT NULL,\n" +
		"  PRIMARY KEY (`ProductTypeCode`)\n" +
		") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
		"\n" +
		"INSERT INTO\n" +
		"    ProductType(ProductTypeCode, Name)\n" +
		"VALUES\n" +
		"    ('HW', 'Hardware'),\n" +
		"    ('SW', 'Software');\n" +
		"\n" +
		"</migration>"
)
public class SqlScriptMigration extends BaseMigration implements Migration
{
	private final String sql;

	/**
	 * Creates a new SqlScriptMigration.
	 * 
	 * @param       migrationId                 the ID of the migration
	 * @param       fromStateId                 the ID of the source state that this migration applies to, or null if
	 *                                          this migration transitions from the non-existent state.
	 * @param       toStateId                   the ID of the target state that the migration applies to, or null if
	 *                                          this migration transitions to the non-existent state.
	 * @param       sql                         the SQL script that performs the migration from the fron-state to the
	 *                                          to-state.
	 */
	public SqlScriptMigration(
		UUID migrationId,
		Optional<String> fromStateId,
		Optional<String> toStateId,
		String sql)
	{
		super(migrationId, fromStateId, toStateId);

		if (sql == null) throw new ArgumentNullException("sql");

		this.sql = sql;
	}
	
	public String getSql()
	{
		return sql;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.MySqlDatabase,
			Wildebeest.PostgreSqlDatabase,
			Wildebeest.SqlServerDatabase);
	}
}
