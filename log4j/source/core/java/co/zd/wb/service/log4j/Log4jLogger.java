// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

package co.zd.wb.service.log4j;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.AssertionFailedException;
import co.zd.wb.model.AssertionResponse;
import co.zd.wb.model.IndeterminateStateException;
import co.zd.wb.model.Migration;
import co.zd.wb.model.MigrationFailedException;
import co.zd.wb.model.MigrationNotPossibleException;
import co.zd.wb.model.Resource;
import co.zd.wb.model.State;
import co.zd.wb.service.Logger;

public class Log4jLogger implements Logger
{
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Log4jLogger.class);
	
	@Override public void assertionStart(Assertion assertion)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }

		LOG.info(String.format("Starting assertion: %s", assertion.getName()));
	}

	@Override public void assertionComplete(
		Assertion assertion,
		AssertionResponse response)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }
		if (response == null) { throw new IllegalArgumentException("response cannot be null"); }
	
		if (response.getResult())
		{
			LOG.info(String.format(
				"Assertion \"%s\" passed: %s",
				assertion.getName(),
				response.getMessage()));
		}
		
		else
		{
			LOG.warn(String.format(
				"Assertion \"%s\" failed: %s",
				assertion.getName(),
				response.getMessage()));
		}
	}

	@Override public void migrationStart(
		Resource resource,
		Migration migration)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }

		State fromState = migration.hasFromStateId() ? resource.stateForId(migration.getFromStateId()) : null;
		State toState = migration.hasToStateId() ? resource.stateForId(migration.getToStateId()) : null;
		
		if (fromState != null)
		{
			if (toState != null)
			{
				LOG.info(String.format(
					"Migrating from state \"%s\" to \"%s\"",
					fromState.getDisplayName(),
					toState.getDisplayName()));
			}
			else
			{
				LOG.info(String.format(
					"Migrating from state \"%s\" to non-existent",
					fromState.getDisplayName()));
			}
		}
		else if (toState != null)
		{
				LOG.info(String.format(
					"Migrating from non-existent to \"%s\"",
					toState.getDisplayName()));
		}
		else
		{
			// Exception?
		}
	}

	@Override public void migrationComplete(
		Resource resource,
		Migration migration)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		
		LOG.info("Migration complete");
	}

	@Override public void indeterminateState(IndeterminateStateException e)
	{
		LOG.error("Indeterminate state: " + e.getMessage());
	}

	@Override public void assertionFailed(AssertionFailedException e)
	{
		LOG.error("Assertion failed: " + e.getMessage());
	}

	@Override public void migrationNotPossible(MigrationNotPossibleException e)
	{
		LOG.error("Migration not possible: " + e.getMessage());
	}

	@Override public void migrationFailed(MigrationFailedException e)
	{
		LOG.error("Migration failed: " + e.getMessage());
	}

	@Override public void logLine(String string)
	{
		LOG.debug(string);
	}
}
