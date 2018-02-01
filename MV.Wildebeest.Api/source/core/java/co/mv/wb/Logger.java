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

import java.util.Optional;

/**
 * An application-level logging interface for providing feedback on the progress of Wildebeest commands.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public interface Logger
{
	/**
	 * Logs that an {@link Assertion} has been started.
	 * 
	 * @param       assertion                   the Assertion that has been started.
	 * @since                                   1.0
	 */
	void assertionStart(Assertion assertion);
	
	/**
	 * Logs that an {@link Assertion} has been completed.
	 * 
	 * @param       assertion                   the Assertion that has been completed.
	 * @param       response                    the {@link AssertionResponse} from the Assertion.
	 * @since                                   1.0
	 */
	void assertionComplete(
		Assertion assertion,
		AssertionResponse response);
	
    /**
     * Logs that the target state specified in a migrate or jumpstate command was not a valid Wildebeest state
     * specifier.
     * 
	 * @param       e                           the {@link InvalidStateSpecifiedException} that was thrown.
	 * @since                                   3.0
     */
    void invalidStateSpecified(InvalidStateSpecifiedException e);
    
    /**
     * Logs that the target state specified in a migrate or jumpstate command could not be found in the resource.
     * 
	 * @param       e                           the {@link UnknownStateSpecifiedException} that was thrown.
	 * @since                                   3.0
     */
    void unknownStateSpecified(UnknownStateSpecifiedException e);

	/**
	 * Logs that a migration was requested but that no target was specified.
	 *
	 * @param       e                           the {@link TargetNotSpecifiedException} that was thrown.
	 * @since                                   4.0
	 */
	void targetNotSpecified(TargetNotSpecifiedException e);

	/**
	 * Logs that a {@link Migration} has been started.
	 * 
	 * @param       resource                    the {@link Resource} that is being migrated.
	 * @param       migration                   the {@link Migration} that has been started.
	 * @param       fromState                   the {@link State} that is being migrated from.
	 * @param       toState                     the {@link State} that is being migrated to.
	 * @since                                   1.0
	 */
	void migrationStart(
		Resource resource,
		Migration migration,
		Optional<State> fromState,
		Optional<State> toState);

	/**
	 * Logs that a [@link Migration} has been completed.
	 * 
	 * @param       resource                    the {@link Resource} that was migrated.
	 * @param       migration                   the {@link Migration} that was applied.
	 * @since                                   1.0
	 */
	void migrationComplete(
		Resource resource,
		Migration migration);
	
	/**
	 * Logs that the state of a {@link Resource} could not be determined.
	 * 
	 * @param       e                           the @{link IndeterminateStateException} that was thrown.
	 * @since                                   1.0
	 */
	void indeterminateState(IndeterminateStateException e);
	
	/**
	 * Logs that an {@link Assertion} failed.
	 * 
	 * @param       e                           the {@link AssertionFailedException} that was thrown.
	 * @since                                   1.0
	 */
	void assertionFailed(AssertionFailedException e);

	/**
	 * Logs that a requested migration is not possible because no path could be selected.
	 * 
	 * @param       e                           the {@link MigrationNotPossibleException} that was thrown.
	 * @since                                   1.0
	 */
	void migrationNotPossible(MigrationNotPossibleException e);
	
	/**
	 * Logs that a requested migration failed.
	 * 
	 * @param       e                           the {@link MigrationFailedException} that was thrown.
	 * @since                                   1.0
	 */
	void migrationFailed(MigrationFailedException e);
	
	/**
	 * Logs that a requested jumpstate failed
	 * 
	 * @param       e                           the {@link JumpStateFailedException} that was thrown.
	 * @since                                   3.0
	 */
	void jumpStateFailed(JumpStateFailedException e);
		
	/**
	 * Logs a plain text message.
	 * 
	 * @param       message                     the plain text message to be logged.
	 * @since                                   1.0
	 */
	void logLine(String message);
}
