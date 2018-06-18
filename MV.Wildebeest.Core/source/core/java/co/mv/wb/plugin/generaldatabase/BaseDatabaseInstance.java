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

package co.mv.wb.plugin.generaldatabase;

/**
 * Base class for building DatabaseInstance implementations.
 *
 * @since 4.0
 */
public abstract class BaseDatabaseInstance implements DatabaseInstance
{
	private final String databaseName;
	private String stateTableName;

	protected BaseDatabaseInstance(
		String databaseName,
		String stateTableName)
	{
		if (stateTableName != null && "".equals(stateTableName.trim()))
		{
			throw new IllegalArgumentException("stateTableName cannot be empty");
		}

		this.databaseName = databaseName;
		this.stateTableName = stateTableName;
	}

	@Override public final String getDatabaseName()
	{
		return this.databaseName;
	}

	@Override public final String getStateTableName()
	{
		if (this.stateTableName == null)
		{
			throw new IllegalStateException(
				"stateTableName not set.  Use the HasStateTableName() method to check its state before accessing it.");
		}

		return this.stateTableName;
	}

	@Override public boolean hasStateTableName()
	{
		return this.stateTableName != null;
	}
}
