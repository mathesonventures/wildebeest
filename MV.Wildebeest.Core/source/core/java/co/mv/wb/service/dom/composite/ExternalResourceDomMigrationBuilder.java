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

package co.mv.wb.service.dom.composite;

import co.mv.wb.Logger;
import co.mv.wb.Migration;
import co.mv.wb.plugin.composite.ExternalResourceMigration;
import co.mv.wb.service.Messages;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.V;
import co.mv.wb.service.dom.BaseDomMigrationBuilder;
import co.mv.wb.framework.TryResult;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link MigrationBuilder} that builds a {@link ExternalResourceMigration} from a DOM {@link org.w3c.dom.Element}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class ExternalResourceDomMigrationBuilder extends BaseDomMigrationBuilder
{
	private Logger _logger = null;
	
	public ExternalResourceDomMigrationBuilder(
		Logger logger)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		
		_logger = logger;
	}
	
	@Override public Migration build(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId,
		File baseDir) throws MessagesException
	{
		Migration result;

		TryResult<String> filename = this.tryGetString("filename");
		TryResult<String> target = this.tryGetString("target");
		
		// Validation
		Messages messages = new Messages();
		if (!filename.hasValue())
		{
			V.elementMissing(messages, migrationId, "filename", ExternalResourceMigration.class);
		}
		if (!target.hasValue())
		{
			V.elementMissing(messages, migrationId, "target", ExternalResourceMigration.class);
		}
		
		if (messages.size() > 0)
		{
			throw new MessagesException(messages);
		}

		result = new ExternalResourceMigration(
			migrationId, fromStateId, toStateId,
			baseDir,
			_logger,
			filename.getValue(),
			target.getValue());
		
		return result;
	}
}
