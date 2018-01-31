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

package co.mv.wb.plugin.database;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.ModelExtensions;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.BaseAssertion;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.impl.ImmutableAssertionResponse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a database exists.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class DatabaseExistsAssertion extends BaseAssertion
{
	/**
	 * Creates a new DatabaseExistsAssertion.
	 * 
	 * @param       assertionId                 the ID of the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's containing set.
	 * @since                                   4.0
	 */
	public DatabaseExistsAssertion(
		UUID assertionId,
		int seqNum)
	{
		super(assertionId, seqNum);
	}
	
	@Override public String getDescription()
	{
		return "Database exists";
	}

	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			FactoryResourceTypes.MySqlDatabase,
			FactoryResourceTypes.PostgreSqlDatabase,
			FactoryResourceTypes.SqlServerDatabase);
	}

	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }
		
		AssertionResponse result;
		
		if (db.databaseExists())
		{
			result = new ImmutableAssertionResponse(true, "Database " + db.getDatabaseName() + " exists");
		}
		else
		{
			result = new ImmutableAssertionResponse(false, "Database " + db.getDatabaseName() + " does not exist");
		}
		
		return result;
	}
}
