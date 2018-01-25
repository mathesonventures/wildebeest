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

package co.mv.wb.service.dom.database;

import co.mv.wb.Migration;
import co.mv.wb.plugin.database.SqlScriptMigration;
import co.mv.wb.service.Messages;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.V;
import co.mv.wb.service.dom.BaseDomMigrationBuilder;
import co.mv.wb.framework.TryResult;
import java.io.File;
import java.util.UUID;

/**
 * A {@link MigrationBuilder} that builds a {@link SqlScriptMigration} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class SqlScriptDomMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId,
		File baseDir) throws MessagesException
	{
		Migration result = null;
		
		TryResult<String> sql = this.tryGetString("sql");
		
		// Validation
		Messages messages = new Messages();
		if (!sql.hasValue())
		{
			V.elementMissing(messages, migrationId, "sql", SqlScriptMigration.class);
		}
		
		if (messages.size() > 0)
		{
			throw new MessagesException(messages);
		}

		result = new SqlScriptMigration(migrationId, fromStateId, toStateId, sql.getValue());
		
		return result;
	}
}
