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

package co.mv.wb.cli;

import co.mv.wb.Wildebeest;
import co.mv.wb.event.AssertionEventBody;
import co.mv.wb.event.AssertionWithMessageEventBody;
import co.mv.wb.event.Event;
import co.mv.wb.event.EventSink;
import co.mv.wb.event.Events;
import co.mv.wb.event.MigrationEventBody;
import co.mv.wb.event.MigrationWithMessageEventBody;
import co.mv.wb.event.StateEventBody;
import co.mv.wb.framework.ArgumentNullException;

import java.io.PrintStream;

/**
 * An {@link EventSink} that prints messages to a PrintStream.  Intended for use from the WildebeestCommand CLI driver.
 *
 * @since 4.0
 */
public class PrintStreamEventSink implements EventSink
{
	private final PrintStream output;

	public PrintStreamEventSink(PrintStream output)
	{
		if (output == null) throw new ArgumentNullException("output");

		this.output = output;
	}

	@Override public void onEvent(Event event)
	{
		if (event == null) throw new ArgumentNullException("event");

		switch (event.getEventUri())
		{
			case Events.EVENT_URI_JUMPSTATE_START:
			{
				StateEventBody eventT = (StateEventBody)event.getEventBody();
				this.output.println(String.format(
					"Beginning jumpstate to state %s",
					Wildebeest.stateDisplayName(
						eventT.getStateId(),
						eventT.getName().orElse(null))));
				break;
			}

			case Events.EVENT_URI_JUMPSTATE_COMPLETE:
			{
				StateEventBody eventT = (StateEventBody)event.getEventBody();
				this.output.println(String.format(
					"Completed jumpstate to state %s",
					Wildebeest.stateDisplayName(
						eventT.getStateId(),
						eventT.getName().orElse(null))));
				break;
			}

			case Events.EVENT_URI_MIGRATION_START:
			{
				MigrationEventBody eventT = (MigrationEventBody)event.getEventBody();

				this.output.println(String.format(
					"Performing migration %s from %s to %s",
					eventT.getMigrationId(),
					Wildebeest.stateDisplayName(
						eventT.getFromStateId().orElse(null),
						eventT.getFromStateName().orElse(null)),
					Wildebeest.stateDisplayName(
						eventT.getToStateId().orElse(null),
						eventT.getToStateName().orElse(null))));

				break;
			}

			case Events.EVENT_URI_MIGRATION_COMPLETE:
			{
				MigrationEventBody eventT = (MigrationEventBody)event.getEventBody();

				this.output.println(String.format(
					"Migration %s from %s to %s completed successfully",
					eventT.getMigrationId(),
					Wildebeest.stateDisplayName(
						eventT.getFromStateId().orElse(null),
						eventT.getFromStateName().orElse(null)),
					Wildebeest.stateDisplayName(
						eventT.getToStateId().orElse(null),
						eventT.getToStateName().orElse(null))));

				break;
			}

			case Events.EVENT_URI_MIGRATION_FAILED:
			{
				MigrationWithMessageEventBody eventT = (MigrationWithMessageEventBody)event.getEventBody();

				this.output.println(String.format(
					"Migration %s from %s to %s failed: %s",
					eventT.getMigrationId(),
					Wildebeest.stateDisplayName(
						eventT.getFromStateId().orElse(null),
						eventT.getFromStateName().orElse(null)),
					Wildebeest.stateDisplayName(
						eventT.getToStateId().orElse(null),
						eventT.getToStateName().orElse(null)),
					eventT.getMessage()));

				break;
			}

			case Events.EVENT_URI_ASSERTION_START:
			{
				AssertionEventBody eventT = (AssertionEventBody)event.getEventBody();

				this.output.println(String.format(
					"Performing assertion %s on state %s",
					eventT.getAssertionId(),
					eventT.getStateId()));

				break;
			}

			case Events.EVENT_URI_ASSERTION_COMPLETE:
			{
				AssertionEventBody eventT = (AssertionEventBody)event.getEventBody();

				this.output.println(String.format(
					"Assertion %s on state %s passed",
					eventT.getAssertionId(),
					eventT.getStateId()));

				break;
			}

			case Events.EVENT_URI_ASSSERTION_FAILED:
			{
				AssertionWithMessageEventBody eventT = (AssertionWithMessageEventBody)event.getEventBody();

				this.output.println(String.format(
					"Assertion %s on state %s failed: %s",
					eventT.getAssertionId(),
					eventT.getStateId(),
					eventT.getMessage()));

				break;
			}
		}
	}
}
