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

package co.mv.wb.plugin.generaldatabase.dom;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionBuilder;
import co.mv.wb.Messages;
import co.mv.wb.PluginBuildException;
import co.mv.wb.V;
import co.mv.wb.plugin.base.dom.BaseDomAssertionBuilder;
import co.mv.wb.plugin.generaldatabase.AnsiSqlTableDoesNotExistAssertion;

import java.util.Optional;
import java.util.UUID;

/**
 * An {@link AssertionBuilder} that builds a {@link AnsiSqlTableDoesNotExistAssertion} from a DOM
 * {@link org.w3c.dom.Element}.
 * 
 * @since                                       2.0
 */
public class AnsiSqlTableDoesNotExistDomAssertionBuilder extends BaseDomAssertionBuilder
{
	@Override public Assertion build(
		UUID assertionId,
		int seqNum) throws
			PluginBuildException
	{
		Optional<String> schemaName = this.tryGetString("schemaName");
		Optional<String> tableName = this.tryGetString("tableName");

		// Validation
		Messages messages = new Messages();
		if (!schemaName.isPresent()) { V.elementMissing(messages, assertionId, "schemaName", AnsiSqlTableDoesNotExistAssertion.class); }
		if (!tableName.isPresent()) { V.elementMissing(messages, assertionId, "tableName", AnsiSqlTableDoesNotExistAssertion.class); }
		
		if (messages.size() > 0)
		{
			throw new PluginBuildException(messages);
		}
		
		Assertion result = new AnsiSqlTableDoesNotExistAssertion(
			assertionId,
			seqNum,
			schemaName.get(),
			tableName.get());
		
		return result;
	}
}
