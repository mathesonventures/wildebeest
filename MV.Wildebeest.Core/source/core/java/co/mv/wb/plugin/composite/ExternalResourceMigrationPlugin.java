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

import co.mv.wb.AssertionFailedException;
import co.mv.wb.FileLoadException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.MigrationPluginType;
import co.mv.wb.ModelExtensions;
import co.mv.wb.OutputFormatter;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.WildebeestApi;
import co.mv.wb.XmlValidationException;
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;

import java.io.File;

/**
 * The {@link MigrationPlugin} for {@link ExternalResourceMigration}'s.
 *
 * @since 4.0
 */
@MigrationPluginType(uri = "co.mv.wb.composite:ExternalResourceMigration")
public class ExternalResourceMigrationPlugin implements MigrationPlugin
{
	private static String ExceptionFormatString = "Migration of external resource failed: %s";

	private final WildebeestApi wildebeestApi;

	/**
	 * Constructs a new ExternalResourceMigrationPlugin with the supplied {@link WildebeestApi}.
	 *
	 * @param wildebeestApi the WildebeestApi instance that this plugin should use when
	 *                      orchestrating migrations on external resources.
	 */
	public ExternalResourceMigrationPlugin(
		WildebeestApi wildebeestApi)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");

		this.wildebeestApi = wildebeestApi;
	}

	@Override public void perform(
		EventSink eventSink,
		Migration migration,
		Instance instance) throws
		MigrationFailedException
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (migration == null) throw new ArgumentNullException("migration");
		if (instance == null) throw new ArgumentNullException("instance");

		ExternalResourceMigration migrationT = ModelExtensions.as(migration, ExternalResourceMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SqlServerCreateSchemaMigration");
		}

		Resource resource;

		try
		{
			resource = this.wildebeestApi.loadResource(new File(
				migrationT.getBaseDir(),
				migrationT.getFileName()));
		}
		catch (FileLoadException | LoaderFault | PluginBuildException | XmlValidationException | InvalidReferenceException e)
		{
			throw new MigrationFailedException(migration.getMigrationId(), "Unable to load");
		}

		try
		{
			this.wildebeestApi.migrate(
				resource,
				instance,
				migrationT.getTarget());
		}
		catch (TargetNotSpecifiedException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.targetNotSpecified()));
		}
		catch (UnknownStateSpecifiedException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.unknownStateSpecified(e)));
		}
		catch (InvalidStateSpecifiedException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.invalidStateSpecified(e)));
		}
		catch (MigrationNotPossibleException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.migrationNotPossible(e)));
		}
		catch (IndeterminateStateException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.indeterminateState(e)));
		}
		catch (AssertionFailedException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.assertionFailed(e)));
		}
		catch (InvalidReferenceException e)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				String.format(
					ExternalResourceMigrationPlugin.ExceptionFormatString,
					OutputFormatter.invalidReferenceException(e)));
		}
	}
}
