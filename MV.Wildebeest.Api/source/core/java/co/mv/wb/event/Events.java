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

import co.mv.wb.Assertion;
import co.mv.wb.Migration;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Convenience factory for creating Wildebeest events.
 *
 * @since 4.0
 */
public final class Events
{
	private static final String BASE_EVENT_URI = "co.mv.wildebeest:event:";

	/**
	 * The URI for the JumpStateStart event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_JUMPSTATE_START = BASE_EVENT_URI + "JumpStateStart";

	/**
	 * The URI for hte JumpStateComplete event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_JUMPSTATE_COMPLETE = BASE_EVENT_URI + "JumpStateComplete";

	/**
	 * The URI for the AssertionStart event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_ASSERTION_START = BASE_EVENT_URI + "AssertionStart";

	/**
	 * The URI for the AssertionComplete event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_ASSERTION_COMPLETE = BASE_EVENT_URI + "AssertionComplete";

	/**
	 * The URI for the AssertionFailed event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_ASSSERTION_FAILED = BASE_EVENT_URI + "AssertionFailed";

	/**
	 * The URO for the MigrationStart event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_MIGRATION_START = BASE_EVENT_URI + "MigrationStart";

	/**
	 * The URI for the MigrationComplete event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_MIGRATION_COMPLETE = BASE_EVENT_URI + "MigrationComplete";

	/**
	 * The URI for the MigrationFailed event.
	 *
	 * @since 4.0
	 */
	public static final String EVENT_URI_MIGRATION_FAILED = BASE_EVENT_URI + "MigrationFailed";

	/**
	 * Private constructor to block instantiation of this static code.
	 *
	 * @since 4.0
	 */
	private Events()
	{
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_JUMPSTATE_START} event with a
	 * {@link StateEventBody} payload.
	 *
	 * @param state the target state for the jumpstate operation that we're raising the event for.
	 * @return an Event instance for the JumpStateStart event.
	 * @since 4.0
	 */
	public static Event jumpStateStart(State state)
	{
		if (state == null) throw new ArgumentNullException("state");

		return Events.toStateEvent(
			Events.EVENT_URI_JUMPSTATE_START,
			DateTime.now(),
			state);
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events##EVENT_URI_JUMPSTATE_COMPLETE} event with a
	 * {@link StateEventBody} payload.
	 *
	 * @param state the target state for the jumpstate operation that we're raising the event for.
	 * @return an Event instance for the JumpStateComplete event.
	 * @since 4.0
	 */
	public static Event jumpStateComplete(State state)
	{
		if (state == null) throw new ArgumentNullException("state");

		return Events.toStateEvent(
			Events.EVENT_URI_JUMPSTATE_COMPLETE,
			DateTime.now(),
			state);
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_ASSERTION_START} event with a
	 * {@link AssertionEventBody} payload.
	 *
	 * @param state     the {@link State} that is being asserted.
	 * @param assertion the specific {@link Assertion} that is being applied.
	 * @return an Event instance for the AssertionStart event.
	 * @since 4.0
	 */
	public static Event assertionStart(
		State state,
		Assertion assertion)
	{
		if (state == null) throw new ArgumentNullException("state");
		if (assertion == null) throw new ArgumentNullException("assertion");

		return Events.toAssertionEvent(
			Events.EVENT_URI_ASSERTION_START,
			DateTime.now(),
			state,
			assertion);
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_ASSERTION_COMPLETE} event with a
	 * {@link AssertionEventBody} payload.
	 *
	 * @param state     the {@link State} that is being asserted.
	 * @param assertion the specific {@link Assertion} that is being applied.
	 * @return an Event instance for the AssertionStart event.
	 * @since 4.0
	 */
	public static Event assertionComplete(
		State state,
		Assertion assertion)
	{
		if (state == null) throw new ArgumentNullException("state");
		if (assertion == null) throw new ArgumentNullException("assertion");

		return Events.toAssertionEvent(
			Events.EVENT_URI_ASSERTION_COMPLETE,
			DateTime.now(),
			state,
			assertion);
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_ASSSERTION_FAILED} with a
	 * {@link AssertionWithMessageEventBody} payload.
	 *
	 * @param state     the {@link State} that is being asserted.
	 * @param assertion the specific {@link Assertion} that is being applied.
	 * @param message   the failure message to be included in teh event
	 * @return an Event instance for the AssertionFailed event.
	 * @since 4.0
	 */
	public static Event assertionFailed(
		State state,
		Assertion assertion,
		String message)
	{
		if (state == null) throw new ArgumentNullException("state");
		if (assertion == null) throw new ArgumentNullException("assertion");
		if (message == null) throw new ArgumentNullException("message");

		return new Event(
			Events.EVENT_URI_ASSSERTION_FAILED,
			DateTime.now(),
			new AssertionWithMessageEventBody(
				state.getStateId(),
				assertion.getAssertionId(),
				message));
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_MIGRATION_START} event with a
	 * {@link MigrationEventBody} payload.
	 *
	 * @param migration the {@link Migration} being performed.
	 * @return an Event instance for the MigrationStart event.
	 * @since 4.0
	 */
	public static Event migrationStart(
		Migration migration,
		State fromState,
		State toState)
	{
		if (migration == null) throw new ArgumentNullException("migration");

		return Events.toMigrationEvent(
			Events.EVENT_URI_MIGRATION_START,
			DateTime.now(),
			migration,
			fromState,
			toState);
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_MIGRATION_COMPLETE} event with a
	 * {@link MigrationEventBody} payload.
	 *
	 * @param migration the {@link Migration} being performed.
	 * @return an Event instance for the MigrationComplete event.
	 * @since 4.0
	 */
	public static Event migrationComplete(
		Migration migration,
		State fromState,
		State toState)
	{
		if (migration == null) throw new ArgumentNullException("migration");

		return Events.toMigrationEvent(
			Events.EVENT_URI_MIGRATION_COMPLETE,
			DateTime.now(),
			migration,
			fromState,
			toState);
	}

	/**
	 * Builds an {@link Event} instance for the {@link Events#EVENT_URI_MIGRATION_FAILED} event with a
	 * {@link MigrationWithMessageEventBody} payload.
	 *
	 * @param migration the {@link Migration} being performed.
	 * @param message   the message giving more detail about why the migration failed.
	 * @return 4.0
	 */
	public static Event migrationFailed(
		Migration migration,
		State fromState,
		State toState,
		String message)
	{
		if (migration == null) throw new ArgumentNullException("migration");
		if (message == null) throw new ArgumentNullException("message");

		UUID fromStateId = fromState == null
			? null
			: fromState.getStateId();
		String fromStateName = fromState == null
			? null
			: fromState.getName().orElse(null);

		UUID toStateId = toState == null
			? null
			: toState.getStateId();
		String toStateName = toState == null
			? null
			: toState.getName().orElse(null);

		return new Event(
			Events.EVENT_URI_MIGRATION_FAILED,
			DateTime.now(),
			new MigrationWithMessageEventBody(
				migration.getMigrationId(),
				fromStateId,
				fromStateName,
				toStateId,
				toStateName,
				message));
	}

	/**
	 * Builds an {@link Event} instance with the supplied event URI and a {@link StateEventBody} payload.
	 *
	 * @param eventUri      the identifying URI for the event.
	 * @param raisedInstant the raisedInstant value for the new event.
	 * @param state         the {@link State} that the event relates to.
	 * @return an Event instance with a StateEventBody payload.
	 * @since 4.0
	 */
	private static Event toStateEvent(
		String eventUri,
		DateTime raisedInstant,
		State state)
	{
		if (eventUri == null) throw new ArgumentNullException("eventUri");
		if (state == null) throw new ArgumentNullException("state");

		return new Event(
			eventUri,
			raisedInstant,
			new StateEventBody(
				state.getStateId(),
				state.getName().orElse(null)));
	}

	/**
	 * Builds an {@link Event} instance with an {@link AssertionEventBody} payload.
	 *
	 * @param eventUri      the identifying URI for the event.
	 * @param raisedInstant the raisedInstant value for the new event.
	 * @param state         the {@link State} that the event relates to.
	 * @param assertion     the specific {@link Assertion} within the State that the event relates to
	 * @return an Event instance with an AssertionEventBody payload.
	 * @since 4.0
	 */
	private static Event toAssertionEvent(
		String eventUri,
		DateTime raisedInstant,
		State state,
		Assertion assertion)
	{
		if (eventUri == null) throw new ArgumentNullException("eventUri");
		if (raisedInstant == null) throw new ArgumentNullException("raisedInstant");
		if (state == null) throw new ArgumentNullException("state");
		if (assertion == null) throw new ArgumentNullException("assertion");

		return new Event(
			eventUri,
			raisedInstant,
			new AssertionEventBody(
				state.getStateId(),
				assertion.getAssertionId()));
	}

	/**
	 * Builds an {@link Event} instance with a {@link MigrationEventBody} payload.
	 *
	 * @param eventUri      the identifying URI for the event.
	 * @param raisedInstant the raisedInstant value for the new event.
	 * @param migration     the {@link Migration} that the event relates to.
	 * @return an Event instance with a MigrationEventBody payload.
	 * @since 4.0
	 */
	private static Event toMigrationEvent(
		String eventUri,
		DateTime raisedInstant,
		Migration migration,
		State fromState,
		State toState)
	{
		if (eventUri == null) throw new ArgumentNullException("eventUri");
		if (raisedInstant == null) throw new ArgumentNullException("raisedInstant");
		if (migration == null) throw new ArgumentNullException("migration");

		UUID fromStateId = fromState == null
			? null
			: fromState.getStateId();
		String fromStateName = fromState == null
			? null
			: fromState.getName().orElse(null);

		UUID toStateId = toState == null
			? null
			: toState.getStateId();
		String toStateName = toState == null
			? null
			: toState.getName().orElse(null);

		return new Event(
			eventUri,
			raisedInstant,
			new MigrationEventBody(
				migration.getMigrationId(),
				fromStateId,
				fromStateName,
				toStateId,
				toStateName));
	}
}
