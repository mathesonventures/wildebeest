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

package co.mv.wb.service.slf4j;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.Logger;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.Resource;
import co.mv.wb.State;
import co.mv.wb.UnknownStateSpecifiedException;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * A {@link Logger} that sends messages to SLF4J.
 * 
 * @author                                      Brendon Matheson
 * @since                                       3.0
 */
public class Slf4jLogger implements Logger
{
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Slf4jLogger.class);
	
	@Override public void assertionStart(Assertion assertion)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }

		LOG.info(String.format("Starting assertion: %s", assertion.getDescription()));
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
				assertion.getDescription(),
				response.getMessage()));
		}
		
		else
		{
			LOG.warn(String.format(
				"Assertion \"%s\" failed: %s",
				assertion.getDescription(),
				response.getMessage()));
		}
	}

    @Override public void invalidStateSpecified(InvalidStateSpecifiedException e)
    {
        logLine(String.format(
            "The state \"%s\" that was specified is not a valid Wildebeest state identifier",
            e.getSpecifiedState()));
    }
    
    @Override public void unknownStateSpecified(UnknownStateSpecifiedException e)
    {
        logLine(String.format(
            "The state \"%s\" could not be found in this resource",
            e.getSpecifiedState()));
    }
    
	@Override public void migrationStart(
		Resource resource,
		Migration migration,
		Optional<State> fromState,
		Optional<State> toState)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (migration == null) { throw new IllegalArgumentException("migration cannot be null"); }
		if (fromState == null) { throw new IllegalArgumentException("fromState cannot be null"); }
		if (toState == null) { throw new IllegalArgumentException("toState cannot be null"); }

		if (fromState.isPresent())
		{
			if (toState.isPresent())
			{
				LOG.info(String.format(
					"Migrating from state \"%s\" to \"%s\"",
					fromState.get().getDisplayName(),
					toState.get().getDisplayName()));
			}
			else
			{
				LOG.info(String.format(
					"Migrating from state \"%s\" to non-existent",
					fromState.get().getDisplayName()));
			}
		}
		else if (toState.isPresent())
		{
				LOG.info(String.format(
					"Migrating from non-existent to \"%s\"",
					toState.get().getDisplayName()));
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

	@Override public void jumpStateFailed(JumpStateFailedException e)
	{
		LOG.error("JumpState failed: " + e.getMessage());
	}

	@Override public void logLine(String string)
	{
		LOG.debug(string);
	}
}
