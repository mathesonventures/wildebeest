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

package co.mv.wb.plugin.database;

import co.mv.wb.plugin.base.BaseAssertion;
import co.mv.wb.AssertionResponse;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;
import java.util.UUID;

/**
 * An {@link Assertion} that verifies that a database does not exist.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class DatabaseDoesNotExistAssertion extends BaseAssertion
{
	/**
	 * Creates a new MySqlDatabaseDoesNotExistAssertion
	 * 
	 * @param       assertionId                 the ID of the new assertion.
	 * @param       seqNum                      the ordinal index of the new assertion within it's containing set.
	 * @since                                   1.0
	 */
	public DatabaseDoesNotExistAssertion(
		UUID assertionId,
		int seqNum)
	{
		super(assertionId, seqNum);
	}

	@Override public String getDescription()
	{
		return "Database does not exist";
	}
	
	@Override public boolean canPerformOn(Resource resource)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, DatabaseResource.class) != null;
	}
	
	@Override public AssertionResponse perform(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }
		
		AssertionResponse result = null;
		
		if (db.databaseExists())
		{
			result = new ImmutableAssertionResponse(false, "Database " + db.getDatabaseName() + " exists");
		}
		else
		{
			result = new ImmutableAssertionResponse(true, "Database " + db.getDatabaseName() + " does not exist");
		}
		
		return result;
	}
}
