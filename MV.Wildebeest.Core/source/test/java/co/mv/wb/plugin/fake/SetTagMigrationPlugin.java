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
import co.mv.wb.ModelExtensions;

import java.io.PrintStream;

/**
 * {@link MigrationPlugin} for the Fake plugin implementation.
 *
 * @since                                       1.0
 */
public class SetTagMigrationPlugin implements MigrationPlugin
{
	@Override
	public void perform(
		PrintStream output,
		Migration migration,
		Instance instance)
	{
		if (output == null) { throw new IllegalArgumentException("output cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		SetTagMigration migrationT = ModelExtensions.As(migration, SetTagMigration.class);
		if (migrationT == null)
		{
			throw new IllegalArgumentException("migration must be a SetTagMigration");
		}

		FakeInstance instanceT = ModelExtensions.As(instance, FakeInstance.class);
		if (instanceT == null)
		{
			throw new IllegalArgumentException("instance must be a FakeInstance");
		}

		instanceT.setTag(migrationT.getTag());

		instanceT.setStateId(migration.getToState().get());
	}
}
