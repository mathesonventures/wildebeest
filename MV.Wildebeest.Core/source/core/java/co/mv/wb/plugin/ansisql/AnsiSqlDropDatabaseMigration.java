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

package co.mv.wb.plugin.ansisql;

import co.mv.wb.Migration;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that drops a database using ANSI-standard SQL statements.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class AnsiSqlDropDatabaseMigration extends BaseMigration
{
    public AnsiSqlDropDatabaseMigration(
        UUID migrationId,
        Optional<UUID> fromStateId,
        Optional<UUID> toStateId)
    {
        super(migrationId, fromStateId, toStateId);
    }

    @Override public List<ResourceType> getApplicableTypes()
    {
		return Arrays.asList(
			FactoryResourceTypes.PostgreSqlDatabase);
    }
}
