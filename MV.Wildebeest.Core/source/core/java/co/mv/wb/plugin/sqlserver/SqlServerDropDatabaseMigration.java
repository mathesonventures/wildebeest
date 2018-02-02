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

package co.mv.wb.plugin.sqlserver;

import co.mv.wb.Migration;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.plugin.base.BaseMigration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link Migration} that creates a new SQL-Server database.
 * 
 * @author                                      Brendon Matheson
 * @since                                       2.0
 */
public class SqlServerDropDatabaseMigration extends BaseMigration
{
	/**
	 * Creates a new SqlServerCreateDatabseMigration.
	 * 
	 * @param       migrationId                 the ID of the new migration.
	 * @param       fromStateId                 the source state for this migration.
	 * @param       toStateId                   the target state for this migration.
	 * @since                                   2.0
	 */
	public SqlServerDropDatabaseMigration(
		UUID migrationId,
		Optional<UUID> fromStateId,
		Optional<UUID> toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}
	
	@Override public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			FactoryResourceTypes.SqlServerDatabase);
	}
}
