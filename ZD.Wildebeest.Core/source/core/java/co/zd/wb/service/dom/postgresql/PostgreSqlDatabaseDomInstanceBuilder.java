// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.service.dom.postgresql;

import co.zd.wb.Instance;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import co.zd.wb.service.Messages;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.V;
import co.zd.wb.service.dom.BaseDomInstanceBuilder;
import co.zd.wb.service.dom.TryGetResult;

/**
 * An {@link InstanceBuilder} that builds a {@link PostgreSqlDatabaseInstance} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class PostgreSqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build() throws MessagesException
	{
		TryGetResult<String> hostName = this.tryGetString("hostName");
		TryGetResult<Integer> port = this.tryGetInteger("port");
		TryGetResult<String> adminUsername = this.tryGetString("adminUsername");
		TryGetResult<String> adminPassword = this.tryGetString("adminPassword");
		TryGetResult<String> databaseName = this.tryGetString("databaseName");
		TryGetResult<String> metaSchemaName = this.tryGetString("metaSchemaName");
		TryGetResult<String> stateTableName = this.tryGetString("stateTableName");

		Messages messages = new Messages();
		if (!hostName.hasValue()) { V.elementMissing(messages, null, "hostName", PostgreSqlDatabaseInstance.class); }
		if (!port.hasValue()) { V.elementMissing(messages, null, "port", PostgreSqlDatabaseInstance.class); }
		if (!adminUsername.hasValue()) { V.elementMissing(messages, null, "adminUsername", PostgreSqlDatabaseInstance.class); }
		if (!adminPassword.hasValue()) { V.elementMissing(messages, null, "adminPassword", PostgreSqlDatabaseInstance.class); }
		if (!databaseName.hasValue()) { V.elementMissing(messages, null, "databaseName", PostgreSqlDatabaseInstance.class); }

		if (messages.size() > 0)
		{
			throw new MessagesException(messages);
		}

		Instance result = new PostgreSqlDatabaseInstance(
			hostName.getValue(),
			port.getValue(),
			adminUsername.getValue(),
			adminPassword.getValue(),
			databaseName.getValue(),
			metaSchemaName.hasValue() ? metaSchemaName.getValue() : null,
			stateTableName.hasValue() ? stateTableName.getValue() : null);

		return result;
	}
}