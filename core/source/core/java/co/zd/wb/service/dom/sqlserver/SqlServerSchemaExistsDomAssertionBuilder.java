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

package co.zd.wb.service.dom.sqlserver;

import co.zd.wb.Assertion;
import co.zd.wb.plugin.sqlserver.SqlServerSchemaExistsAssertion;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import java.util.UUID;

public class SqlServerSchemaExistsDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(UUID assertionId, String name, int seqNum)
	{
		// SchemaName - Mandatory
		String schemaName = this.getString("schemaName");
		
		Assertion result = new SqlServerSchemaExistsAssertion(
			assertionId,
			name,
			seqNum,
			schemaName);
		
		return result;
	}
}
