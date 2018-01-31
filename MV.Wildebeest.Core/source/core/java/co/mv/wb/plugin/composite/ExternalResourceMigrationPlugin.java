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
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.Interface;
import co.mv.wb.Logger;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.framework.Try;
import co.mv.wb.impl.ResourceHelper;
import co.mv.wb.service.MessagesException;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class ExternalResourceMigrationPlugin implements MigrationPlugin
{
	public ExternalResourceMigrationPlugin(
		Map<Class, MigrationPlugin> migrationPlugins)
	{
		this.setMigrationPlugins(migrationPlugins);
	}

	// <editor-fold desc="MigrationPlugins" defaultstate="collapsed">

	private Map<Class, MigrationPlugin> _migrationPlugins = null;
	private boolean _migrationPlugins_set = false;

	private Map<Class, MigrationPlugin> getMigrationPlugins() {
		if(!_migrationPlugins_set) {
			throw new IllegalStateException("migrationPlugins not set.");
		}
		if(_migrationPlugins == null) {
			throw new IllegalStateException("migrationPlugins should not be null");
		}
		return _migrationPlugins;
	}

	private void setMigrationPlugins(Map<Class, MigrationPlugin> value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationPlugins cannot be null");
		}
		boolean changing = !_migrationPlugins_set || _migrationPlugins != value;
		if(changing) {
			_migrationPlugins_set = true;
			_migrationPlugins = value;
		}
	}

	private void clearMigrationPlugins() {
		if(_migrationPlugins_set) {
			_migrationPlugins_set = true;
			_migrationPlugins = null;
		}
	}

	private boolean hasMigrationPlugins() {
		return _migrationPlugins_set;
	}

	// </editor-fold>

	@Override public void perform(
		Logger logger,
		Migration migration,
		Instance instance) throws MigrationFailedException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		ExternalResourceMigration migrationT = ModelExtensions.As(migration, ExternalResourceMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SqlServerCreateSchemaMigration");
		}

		// Load the resource
		Resource externalResource;
		try
		{
			File file = new File(migrationT.getBaseDir(), migrationT.getFileName());
			externalResource = Interface.loadResource(logger, file);
		}
		catch(MessagesException e)
		{
			throw new MigrationFailedException(migration.getMigrationId(), "Unable to load");
		}

		ResourcePlugin externalResourcePlugin = null;

		UUID targetStateId = Try
			.tryParseUuid(migrationT.getTarget())
			.orElseGet(() -> ResourceHelper.stateIdForLabel(
				externalResource,
				migrationT.getTarget()));

		try
		{
			ResourceHelper.migrate(
				logger,
				externalResource,
				externalResourcePlugin,
				instance,
				this.getMigrationPlugins(),
				targetStateId);
		}
		catch (IndeterminateStateException ex)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				"Indeterminate exception in external resource");
		}
		catch (AssertionFailedException ex)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				"Assertion failed in external resource");
		}
		catch (MigrationNotPossibleException ex)
		{
			throw new MigrationFailedException(
				migration.getMigrationId(),
				"Migration not possible in external resource");
		}
	}
}
