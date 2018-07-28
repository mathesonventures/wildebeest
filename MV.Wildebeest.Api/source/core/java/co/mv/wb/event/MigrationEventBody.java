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

package co.mv.wb.event;

import co.mv.wb.framework.ArgumentNullException;

import java.util.Optional;
import java.util.UUID;

/**
 * Defines an {@link EventBody} representing a Migration.
 *
 * @since 4.0
 */
public class MigrationEventBody implements EventBody
{
	private final UUID migrationId;
	private final String fromState;
	private final String toState;

	/**
	 * Constructs a new MigrationEventBody with the supplied properties.
	 *
	 * @param migrationId the ID of the Migration.
	 * @param fromState   the optional from-state of the Migration
	 * @param toState     the optional to-state of the Migration
	 * @since 4.0
	 */
	public MigrationEventBody(
		UUID migrationId,
		String fromState,
		String toState)
	{
		if (migrationId == null) throw new ArgumentNullException("migrationId");

		this.migrationId = migrationId;
		this.fromState = fromState;
		this.toState = toState;
	}

	/**
	 * Gets the ID of the Migration that this event relates to.
	 *
	 * @return the ID of the Migration that this event relates to.
	 * @since 4.0
	 */
	public UUID getMigrationId()
	{
		return this.migrationId;
	}

	/**
	 * Gets the optional from-state of the Migration that this event relates to.
	 *
	 * @return the optional from-state of the Migration that this event relates to.
	 * @since 4.0
	 */
	public Optional<String> getFromState()
	{
		return Optional.ofNullable(this.fromState);
	}

	/**
	 * Gets the optional to-state of the Migration that this event relates to.
	 *
	 * @return the optional to-state of the Migration that this event relates to.
	 * @since 4.0
	 */
	public Optional<String> getToState()
	{
		return Optional.ofNullable(this.toState);
	}
}
