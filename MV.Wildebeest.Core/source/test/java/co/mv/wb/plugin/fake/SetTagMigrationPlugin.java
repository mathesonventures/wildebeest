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

package co.mv.wb.plugin.fake;

import co.mv.wb.Instance;
import co.mv.wb.Migration;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.MigrationPluginType;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.Wildebeest;
import co.mv.wb.event.EventSink;
import co.mv.wb.event.MigrationEvent;
import co.mv.wb.framework.ArgumentNullException;

/**
 * {@link MigrationPlugin} for the Fake plugin implementation.
 *
 * @since 1.0
 */
@MigrationPluginType(uri = "co.mv.wb.fake:SetTag")
public class SetTagMigrationPlugin implements MigrationPlugin
{
	private final Resource resource;

	public SetTagMigrationPlugin(Resource resource)
	{
		if (resource == null) throw new ArgumentNullException("resource");

		this.resource = resource;
	}

	@Override
	public void perform(
		EventSink eventSink,
		Migration migration,
		Instance instance)
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (migration == null) throw new ArgumentNullException("migration");
		if (instance == null) throw new ArgumentNullException("instance");

		SetTagMigration migrationT = ModelExtensions.as(migration, SetTagMigration.class);
		if (migrationT == null)
		{
			String msg = "migration must be a SetTagMigration";
			throw new IllegalArgumentException(msg);
		}

		FakeInstance instanceT = ModelExtensions.as(instance, FakeInstance.class);
		if (instanceT == null)
		{
			String msg = "instance must be a FakeInstance";
			throw new IllegalArgumentException(msg);
		}

		instanceT.setTag(migrationT.getTag());
	}
}
