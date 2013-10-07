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

package co.zd.wb.service.dom.mysql;

import co.zd.wb.Instance;
import co.zd.wb.plugin.mysql.MySqlDatabaseInstance;
import co.zd.wb.service.dom.BaseDomInstanceBuilder;

public class MySqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build()
	{
		String hostName = this.getString("hostName");
		int port = this.getInteger("port");
		String adminUsername = this.getString("adminUsername");
		String adminPassword = this.getString("adminPassword");
		String schemaName = this.getString("schemaName");
		String stateTableName = this.getString("stateTableName");
		if (stateTableName == "")
		{
			stateTableName = null;
		}

		Instance result = new MySqlDatabaseInstance(
			hostName,
			port,
			adminUsername,
			adminPassword,
			schemaName,
			stateTableName);
		
		return result;
	}
}