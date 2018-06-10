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

import co.mv.wb.*;

import java.io.File;
import java.io.PrintStream;

/**
 * The {@link MigrationPlugin} for {@link ExternalResourceMigration}'s.
 *
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
@MigrationPluginType(uri = "co.mv.wb.composite:ExternalResourceMigration")
public class ExternalResourceMigrationPlugin implements MigrationPlugin
{
	private static String ExceptionFormatString = "Migration of external resource failed: %s";

	public ExternalResourceMigrationPlugin(
		WildebeestApi wildebeestApi)
	{
		this.setWildebeestApi(wildebeestApi);
	}

	// <editor-fold desc="WildebeestApi" defaultstate="collapsed">

	private WildebeestApi _wildebeestApi = null;
	private boolean _wildebeestApi_set = false;

	private WildebeestApi getWildebeestApi() {
		if(!_wildebeestApi_set) {
			throw new IllegalStateException("wildebeestApi not set.");
		}
		if(_wildebeestApi == null) {
			throw new IllegalStateException("wildebeestApi should not be null");
		}
		return _wildebeestApi;
	}

	private void setWildebeestApi(
		WildebeestApi value) {
		if(value == null) {
			throw new IllegalArgumentException("wildebeestApi cannot be null");
		}
		boolean changing = !_wildebeestApi_set || _wildebeestApi != value;
		if(changing) {
			_wildebeestApi_set = true;
			_wildebeestApi = value;
		}
	}

	private void clearWildebeestApi() {
		if(_wildebeestApi_set) {
			_wildebeestApi_set = true;
			_wildebeestApi = null;
		}
	}

	private boolean hasWildebeestApi() {
		return _wildebeestApi_set;
	}

	// </editor-fold>

	@Override public void perform(
		PrintStream output,
		Migration migration,
		Instance instance) throws
			MigrationFailedException
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		ExternalResourceMigration migrationT = ModelExtensions.As(migration, ExternalResourceMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SqlServerCreateSchemaMigration");
		}

		Resource resource;

		try
		{
			resource = this.getWildebeestApi().loadResource(new File(
				migrationT.getBaseDir(),
				migrationT.getFileName()));
		}
		catch (FileLoadException | LoaderFault | PluginBuildException | XmlValidationException e)
		{
			throw new MigrationFailedException(migration.getMigrationId(), "Unable to load");
		}

		try
		{
			this.getWildebeestApi().migrate(
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
					OutputFormatter.targetNotSpecified(e)));
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
	}
}
