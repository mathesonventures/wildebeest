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

package co.zd.wb.service.dom.database;

import co.zd.wb.Assertion;
import co.zd.wb.plugin.database.RowDoesNotExistAssertion;
import co.zd.wb.plugin.database.RowExistsAssertion;
import co.zd.wb.service.Messages;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.V;
import co.zd.wb.service.dom.BaseDomAssertionBuilder;
import co.zd.wb.service.dom.TryGetResult;
import java.util.UUID;

/**
 * An {@link AssertionBuilder} that builds a {@link RowDoesNotExistAssertion} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class RowDoesNotExistDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(
		UUID assertionId,
		int seqNum) throws MessagesException
	{
		TryGetResult<String> sql = this.tryGetString("sql");
		TryGetResult<String> description = this.tryGetString("description");

		// Validate
		Messages messages = new Messages();
		if (!sql.hasValue())
		{
			V.elementMissing(messages, assertionId, "sql", RowExistsAssertion.class);
		}
		
		if (!description.hasValue())
		{
			V.elementMissing(messages, assertionId, "description", RowExistsAssertion.class);
		}

		if (messages.size() > 0)
		{
			throw new MessagesException(messages);
		}
		
		return new RowDoesNotExistAssertion(assertionId, description.getValue(), seqNum, sql.getValue());
	}
}
