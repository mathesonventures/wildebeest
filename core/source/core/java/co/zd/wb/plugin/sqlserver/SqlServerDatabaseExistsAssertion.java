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

import co.zd.wb.plugin.base.BaseAssertion;
import co.zd.wb.AssertionResponse;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.plugin.base.ImmutableAssertionResponse;
import java.util.UUID;

public class SqlServerDatabaseExistsAssertion extends BaseAssertion
{
	public SqlServerDatabaseExistsAssertion(
		UUID assertionId,
		int seqNum)
	{
		super(assertionId, seqNum);
	}
	
	@Override public String getDescription()
	{
		return "Database exists";
	}
	
	@Override public AssertionResponse apply(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		SqlServerDatabaseInstance db = ModelExtensions.As(instance, SqlServerDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a SqlServerDatabaseInstance"); }
		
		AssertionResponse result = null;
	
		if (SqlServerDatabaseHelper.databaseExists(db))
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
