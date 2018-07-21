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

package co.mv.wb;

import co.mv.wb.event.EventSink;

/**
 * MigrationPlugins actually perform {@link Migration}'s.  Runner plugins are separated from {@link Migration}'s so that
 * additional services required when running the Migration can be injected into the runner.
 */
public interface MigrationPlugin
{
	/**
	 * Performs the migration, transitioning the supplied Instance from the Migration's from state to it's to state.
	 *
	 * @param eventSink    the event sink that will process all events for user output
	 * @param migration the Migration to apply to the supplied instance.
	 * @param instance  the instance to be migrated
	 * @throws MigrationFailedException if the migration fails
	 * @since 1.0
	 */
	void perform(
		EventSink eventSink,
		Migration migration,
		Instance instance) throws
		MigrationFailedException;
}
