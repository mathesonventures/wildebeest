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

import co.mv.wb.framework.ArgumentException;
import co.mv.wb.framework.ArgumentNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An {@link EventSink} that distributes the event to a set of downstream EventSinks.
 *
 * @since 4.0
 */
public class TeeEventSink implements EventSink
{
	private static final Logger LOG = LoggerFactory.getLogger(TeeEventSink.class);

	private final List<EventSink> eventSinks;

	/**
	 * Creates a new TeeEventSink with the supplied downstream EventSinks.
	 *
	 * @param eventSinks the downstream EventSinks that this TeeEventSink will distribute event
	 */
	public TeeEventSink(
		EventSink... eventSinks)
	{
		if (eventSinks == null) throw new ArgumentNullException("eventSinks");
		if (eventSinks.length == 0)
		{
			throw new ArgumentException("eventSinks", "at least one EventSink is required");
		}

		this.eventSinks = new ArrayList<>();
		this.eventSinks.addAll(Arrays.asList(eventSinks));
	}

	@Override
	public void onEvent(Event event)
	{
		this.eventSinks.forEach(eventSink ->
		{
			try
			{
				eventSink.onEvent(event);
			}
			catch (Throwable t)
			{
				// Tolerate but log any excepiton from an event sink so that one breaking does not stop others from
				// receiving the event.
				LOG.error("Exception occurred while delegating to event sink", t);
			}
		});
	}
}
