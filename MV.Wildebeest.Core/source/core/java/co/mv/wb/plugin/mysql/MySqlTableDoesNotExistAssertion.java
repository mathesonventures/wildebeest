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

package co.mv.wb.plugin.mysql;

import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.MigrationType;
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link co.mv.wb.Assertion} that verifies that a given table does not exist in a MySQL database.
 * 
 * @since                                       1.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:MySqlDatabase",
	uri = "co.mv.wb.mysql:MySqlTableDoesNotExist",
	description =
		"Used to assert that a specific table does not exist within the MySQL database.",
	example =
		"<assertion\n" +
		"    type=\"MySqlTableDoesNotExist\"\n" +
		"    id=\"f112b36f-8312-4f8a-b7a5-e30d17be3c9b\"\n" +
		"    name=\"ProductType table does not exist\">\n" +
		"    <tableName>ProductType</tableName>\n" +
		"</assertion>"
)
public class MySqlTableDoesNotExistAssertion extends BaseAssertion
{
	private final String tableName;

	/**
	 * Creates a new MySqlTableDoesNotExistAssertion.
	 * 
	 * @param       assertionId                 the ID of the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's parent container.
	 * @param       tableName                   the name of the table that this assertion will check for.
	 * @since                                   1.0
	 */
	public MySqlTableDoesNotExistAssertion(
		UUID assertionId,
		int seqNum,
		String tableName)
	{
		super(assertionId, seqNum);

		if (tableName == null) throw new ArgumentNullException("tableName");

		this.tableName = tableName;
	}
	
	@Override public String getDescription()
	{
		return String.format("Table '%s' does not exist", this.getTableName());
	}
	
	/**
	 * Gets the name of the table that this assertion will check for.
	 * 
	 * @return                                  the name of the table that this assertion will check for.
	 * @since                                   1.0
	 */
	public String getTableName()
	{
		return this.tableName;
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.MySqlDatabase);
	}
	
	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }
		
		AssertionResponse result;

		if (!db.databaseExists())
		{
			result = new ImmutableAssertionResponse(
				false,
				String.format("Database %s does not exist", db.getDatabaseName()));
		}
		
		else if (MySqlDatabaseHelper.tableExists(db, this.getTableName()))
		{
			result = new ImmutableAssertionResponse(false, "Table " + this.getTableName() + " exists");
		}
		
		else
		{
			result = new ImmutableAssertionResponse(true, "Table " + this.getTableName() + " does not exist");
		}
		
		return result;
	}
}
