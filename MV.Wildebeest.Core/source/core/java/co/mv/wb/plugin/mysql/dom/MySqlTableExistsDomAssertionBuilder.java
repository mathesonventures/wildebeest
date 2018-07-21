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

package co.mv.wb.plugin.mysql.dom;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionBuilder;
import co.mv.wb.MessageList;
import co.mv.wb.PluginBuildException;
import co.mv.wb.V;
import co.mv.wb.plugin.base.dom.BaseDomAssertionBuilder;
import co.mv.wb.plugin.mysql.MySqlTableExistsAssertion;

import java.util.Optional;
import java.util.UUID;

/**
 * An {@link AssertionBuilder} that builds a {@link MySqlTableExistsAssertion} from a DOM
 * {@link org.w3c.dom.Element}.
 *
 * @since 1.0
 */
public class MySqlTableExistsDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(
		UUID assertionId,
		int seqNum) throws
		PluginBuildException
	{
		Optional<String> tableName = this.tryGetString("tableName");

		MessageList messages = new MessageList();
		if (!tableName.isPresent())
		{
			V.elementMissing(messages, assertionId, "tableName", MySqlTableExistsAssertion.class);
		}

		if (messages.size() > 0)
		{
			throw new PluginBuildException(messages);
		}

		return new MySqlTableExistsAssertion(
			assertionId,
			seqNum,
			tableName.get());
	}
}
