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

package co.mv.wb.plugin.sqlserver.dom;

import co.mv.wb.Instance;
import co.mv.wb.InstanceBuilder;
import co.mv.wb.Messages;
import co.mv.wb.PluginBuildException;
import co.mv.wb.V;
import co.mv.wb.plugin.base.dom.BaseDomInstanceBuilder;
import co.mv.wb.plugin.sqlserver.SqlServerDatabaseInstance;

import java.util.Optional;

/**
 * An {@link InstanceBuilder} that builds a {@link SqlServerDatabaseInstance} from a DOM {@link org.w3c.dom.Element}.
 *
 * @since 2.0
 */
public class SqlServerDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build() throws
		PluginBuildException
	{
		Optional<String> hostName = this.tryGetString("hostName");
		Optional<String> instanceName = this.tryGetString("instanceName");
		Optional<Integer> port = this.tryGetInteger("port");
		Optional<String> adminUsername = this.tryGetString("adminUsername");
		Optional<String> adminPassword = this.tryGetString("adminPassword");
		Optional<String> databaseName = this.tryGetString("databaseName");
		Optional<String> stateTableName = this.tryGetString("stateTableName");

		// Validation
		Messages messages = new Messages();
		if (!hostName.isPresent())
		{
			V.elementMissing(messages, null, "hostName", SqlServerDatabaseInstance.class);
		}
		if (!port.isPresent())
		{
			V.elementMissing(messages, null, "port", SqlServerDatabaseInstance.class);
		}
		if (!adminUsername.isPresent())
		{
			V.elementMissing(messages, null, "adminUsername", SqlServerDatabaseInstance.class);
		}
		if (!adminPassword.isPresent())
		{
			V.elementMissing(messages, null, "adminPassword", SqlServerDatabaseInstance.class);
		}
		if (!databaseName.isPresent())
		{
			V.elementMissing(messages, null, "databaseName", SqlServerDatabaseInstance.class);
		}

		if (messages.size() > 0)
		{
			throw new PluginBuildException(messages);
		}

		Instance result = new SqlServerDatabaseInstance(
			hostName.get(),
			instanceName.orElse(null),
			port.get(),
			adminUsername.get(),
			adminPassword.get(),
			databaseName.get(),
			stateTableName.orElse(null));

		return result;
	}
}
