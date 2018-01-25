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

package co.mv.wb.service.dom.mysql;

import co.mv.wb.Instance;
import co.mv.wb.plugin.mysql.MySqlDatabaseInstance;
import co.mv.wb.service.Messages;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.V;
import co.mv.wb.service.dom.BaseDomInstanceBuilder;
import co.mv.wb.framework.TryResult;

/**
 * An {@link InstanceBuilder} that builds a {@link MySqlDatabaseInstance} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class MySqlDatabaseDomInstanceBuilder extends BaseDomInstanceBuilder
{
	@Override public Instance build() throws MessagesException
	{
		TryResult<String> hostName = this.tryGetString("hostName");
		TryResult<Integer> port = this.tryGetInteger("port");
		TryResult<String> adminUsername = this.tryGetString("adminUsername");
		TryResult<String> adminPassword = this.tryGetString("adminPassword");
		TryResult<String> databaseName = this.tryGetString("databaseName");
		TryResult<String> stateTableName = this.tryGetString("stateTableName");
		
		Messages messages = new Messages();
		if (!hostName.hasValue()) { V.elementMissing(messages, null, "hostName", MySqlDatabaseInstance.class); }
		if (!port.hasValue()) { V.elementMissing(messages, null, "port", MySqlDatabaseInstance.class); }
		if (!adminUsername.hasValue()) { V.elementMissing(messages, null, "adminUsername", MySqlDatabaseInstance.class); }
		if (!adminPassword.hasValue()) { V.elementMissing(messages, null, "adminPassword", MySqlDatabaseInstance.class); }
		if (!databaseName.hasValue()) { V.elementMissing(messages, null, "databaseName", MySqlDatabaseInstance.class); }

		if (messages.size() > 0)
		{
			throw new MessagesException(messages);
		}
		
		Instance result = new MySqlDatabaseInstance(
			hostName.getValue(),
			port.getValue(),
			adminUsername.getValue(),
			adminPassword.getValue(),
			databaseName.getValue(),
			stateTableName.hasValue() ? stateTableName.getValue() : null);

		return result;
	}
}
