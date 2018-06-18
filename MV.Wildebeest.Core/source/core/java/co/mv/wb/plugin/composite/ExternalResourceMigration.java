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

package co.mv.wb.plugin.composite;

import co.mv.wb.MigrationType;
import co.mv.wb.ResourceType;
import co.mv.wb.Wildebeest;
import co.mv.wb.plugin.base.BaseMigration;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Applies a migration in an external resource definition, and tracks both the external resource state and the composite
 * state.
 *
 * @since 4.0
 */
@MigrationType(
	pluginGroupUri = "co.mv.wb:Composite",
	uri = "co.mv.wb.composite:ExternalResourceMigration",
	description =
		"Applies a migration in another resource definition, and tracks both the external resource state and the " +
			"composite resource state",
	example =
		"<migration\n" +
			"    type=\"ExternalResourceMigration\"\n" +
			"    id=\"dd7a708f-bf6d-4e18-aaec-c1fdf3de965d\"\n" +
			"    fromState=\"d364f2ba-3972-4a76-944d-d9767cbfc2d7\"\n" +
			"    toState=\"f488f17b-71d3-4672-9216-afd1e73884cf\">\n" +
			"    <filename>AnotherResource.wbr</filename>\n" +
			"    <target>SomeExternalTarget</target>\n" +
			"</migration>"
)
public class ExternalResourceMigration extends BaseMigration
{
	private final File baseDir;
	private final String fileName;
	private final Optional<String> target;

	/**
	 * Constructs a new ExternalResourceMigration.
	 *
	 * @param migrationId the ID of the migration.
	 * @param fromStateId the from state for the migration, if any.
	 * @param toStateId   the to state for the migration, if any.
	 * @param baseDir     the base directory for resolving external resources for this migration.
	 * @param fileName    the filename of the external resource.
	 * @param target      the target in the external resource that we will migrate to.
	 * @since 4.0
	 */
	public ExternalResourceMigration(
		UUID migrationId,
		Optional<String> fromState,
		Optional<String> toState,
		File baseDir,
		String fileName,
		Optional<String> target)
	{
		super(migrationId, fromState, toState);

		this.baseDir = baseDir;
		this.fileName = fileName;
		this.target = target;
	}

	/**
	 * Gets the base directory for resolving external resources for this migration.
	 *
	 * @return the base directory for resolving external resources for this migration.
	 * @since 4.0
	 */
	public File getBaseDir()
	{
		return this.baseDir;
	}

	/**
	 * Gets the filename of the external resource.
	 *
	 * @return the filename of the external resource.
	 * @since 4.0
	 */
	public String getFileName()
	{
		return this.fileName;
	}

	/**
	 * Gets the target in the external resource that we will migrate to.
	 *
	 * @return the target in the external resource that we will migrate to.
	 * @since 4.0
	 */
	public Optional<String> getTarget()
	{
		return this.target;
	}

	@Override
	public List<ResourceType> getApplicableTypes()
	{
		return Arrays.asList(
			Wildebeest.MySqlDatabase,
			Wildebeest.PostgreSqlDatabase,
			Wildebeest.SqlServerDatabase);
	}
}
