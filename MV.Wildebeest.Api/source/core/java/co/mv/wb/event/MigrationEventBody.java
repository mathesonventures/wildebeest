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
	private final UUID fromStateId;
	private final String fromStateName;
	private final UUID toStateId;
	private final String toStateName;

	/**
	 * Constructs a new MigrationEventBody with the supplied properties.
	 *
	 * @param migrationId   the ID of the Migration.
	 * @param fromStateId   the ID of the optional from-state of the Migration.
	 * @param fromStateName the name of the optional from-stsate of the Migration.
	 * @param toStateId     the ID of the optional to-state of the Migration.
	 * @param toStateName   the name of the optional to-state of the Migration.
	 * @since 4.0
	 */
	public MigrationEventBody(
		UUID migrationId,
		UUID fromStateId,
		String fromStateName,
		UUID toStateId,
		String toStateName)
	{
		if (migrationId == null) throw new ArgumentNullException("migrationId");

		this.migrationId = migrationId;
		this.fromStateId = fromStateId;
		this.fromStateName = fromStateName;
		this.toStateId = toStateId;
		this.toStateName = toStateName;
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
	 * Gets the ID of the optional from-state of the Migration that this event relates to.
	 *
	 * @return the ID of the optional from-state of the Migration that this event relates to.
	 * @since 4.0
	 */
	public Optional<UUID> getFromStateId()
	{
		return Optional.ofNullable(this.fromStateId);
	}

	/**
	 * Gets the name of hte optional from-state of tjhe Migration that this event relates to.
	 *
	 * @return the name of the optional from-state of the Migration that this event relates to.
	 * @since 4.0
	 */
	public Optional<String> getFromStateName()
	{
		return Optional.ofNullable(this.fromStateName);
	}

	/**
	 * Gets the ID of the optional to-state of the Migration that this event relates to.
	 *
	 * @return the ID of the optional to-state of the Migration that this event relates to.
	 * @since 4.0
	 */
	public Optional<UUID> getToStateId()
	{
		return Optional.ofNullable(this.toStateId);
	}

	/**
	 * Gets the name of the optional to-state of the Migration that this event relates to.
	 *
	 * @return the name of the optional to-state of the Migration that this event relates to.
	 * @since 4.0
	 */
	public Optional<String> getToStateName()
	{
		return Optional.ofNullable(this.toStateName);
	}
}
