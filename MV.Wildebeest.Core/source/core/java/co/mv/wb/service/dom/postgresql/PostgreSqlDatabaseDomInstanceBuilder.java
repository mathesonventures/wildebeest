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

package co.mv.wb.service.dom.postgresql;

import co.mv.wb.Instance;
import co.mv.wb.PluginBuildException;
import co.mv.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import co.mv.wb.service.InstanceBuilder;
import co.mv.wb.service.Messages;
import co.mv.wb.service.V;
import co.mv.wb.service.dom.BaseDomInstanceBuilder;

import java.util.Optional;

/**
 * An {@link InstanceBuilder} that builds a {@link PostgreSqlDatabaseInstance} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class PostgreSqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build() throws
		PluginBuildException
	{
		Optional<String> hostName = this.tryGetString("hostName");
		Optional<Integer> port = this.tryGetInteger("port");
		Optional<String> adminUsername = this.tryGetString("adminUsername");
		Optional<String> adminPassword = this.tryGetString("adminPassword");
		Optional<String> databaseName = this.tryGetString("databaseName");
		Optional<String> metaSchemaName = this.tryGetString("metaSchemaName");
		Optional<String> stateTableName = this.tryGetString("stateTableName");

		Messages messages = new Messages();
		if (!hostName.isPresent()) { V.elementMissing(messages, null, "hostName", PostgreSqlDatabaseInstance.class); }
		if (!port.isPresent()) { V.elementMissing(messages, null, "port", PostgreSqlDatabaseInstance.class); }
		if (!adminUsername.isPresent()) { V.elementMissing(messages, null, "adminUsername", PostgreSqlDatabaseInstance.class); }
		if (!adminPassword.isPresent()) { V.elementMissing(messages, null, "adminPassword", PostgreSqlDatabaseInstance.class); }
		if (!databaseName.isPresent()) { V.elementMissing(messages, null, "databaseName", PostgreSqlDatabaseInstance.class); }

		if (messages.size() > 0)
		{
			throw new PluginBuildException(messages);
		}

		Instance result = new PostgreSqlDatabaseInstance(
			hostName.get(),
			port.get(),
			adminUsername.get(),
			adminPassword.get(),
			databaseName.get(),
			metaSchemaName.orElse(null),
			stateTableName.orElse(null));

		return result;
	}
}
