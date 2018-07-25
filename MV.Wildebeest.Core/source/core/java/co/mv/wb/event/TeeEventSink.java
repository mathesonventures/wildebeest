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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An {@link EventSink} that distributes the event to different EventSinks.
 *
 * @since 4.0
 */
public class TeeEventSink implements EventSink
{
	private final List<EventSink> eventSinks;

	public TeeEventSink(
		EventSink eventSink,
		EventSink... moreEventSink)
	{
		eventSinks = new ArrayList<>();
		eventSinks.add(eventSink);
		eventSinks.addAll(Arrays.asList(moreEventSink));
	}

	@Override public void onEvent(Event event)
	{
		eventSinks.forEach(eventSink -> eventSink.onEvent(event));
	}
}
