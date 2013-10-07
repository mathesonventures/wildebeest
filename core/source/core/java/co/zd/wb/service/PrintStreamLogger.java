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

package co.zd.wb.service;

import co.zd.wb.Assertion;
import co.zd.wb.AssertionFailedException;
import co.zd.wb.AssertionResponse;
import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Resource;
import co.zd.wb.State;
import co.zd.wb.Migration;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationNotPossibleException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintStreamLogger implements Logger
{
	public PrintStreamLogger(PrintStream stream)
	{
		this.setStream(stream);
	}

	// <editor-fold desc="Stream" defaultstate="collapsed">

	private PrintStream m_stream = null;
	private boolean m_stream_set = false;

	private PrintStream getStream() {
		if(!m_stream_set) {
			throw new IllegalStateException("stream not set.  Use the HasStream() method to check its state before accessing it.");
		}
		return m_stream;
	}

	private void setStream(
		PrintStream value) {
		if(value == null) {
			throw new IllegalArgumentException("stream cannot be null");
		}
		boolean changing = !m_stream_set || m_stream != value;
		if(changing) {
			m_stream_set = true;
			m_stream = value;
		}
	}

	private void clearStream() {
		if(m_stream_set) {
			m_stream_set = true;
			m_stream = null;
		}
	}

	private boolean hasStream() {
		return m_stream_set;
	}

	// </editor-fold>

	@Override public void assertionStart(Assertion assertion)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }

		logLine(this.getStream(), String.format("Starting assertion: %s", assertion.getName()));
	}

	@Override public void assertionComplete(
		Assertion assertion,
		AssertionResponse response)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }
		if (response == null) { throw new IllegalArgumentException("response cannot be null"); }
	
		if (response.getResult())
		{
			logLine(this.getStream(), String.format(
				"Assertion \"%s\" passed: %s",
				assertion.getName(),
				response.getMessage()));
		}
		
		else
		{
			logLine(this.getStream(), String.format(
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
				logLine(this.getStream(), String.format(
					"Migrating from state \"%s\" to \"%s\"",
					fromState.getDisplayName(),
					toState.getDisplayName()));
			}
			else
			{
				logLine(this.getStream(), String.format(
					"Migrating from state \"%s\" to non-existent",
					fromState.getDisplayName()));
			}
		}
		else if (toState != null)
		{
				logLine(this.getStream(), String.format(
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
		
		logLine(this.getStream(), "Migration complete");
	}
	
	@Override public void indeterminateState(
		IndeterminateStateException e)
	{
		logLine("Indeterminate state: " + e.getMessage());
	}
	
	@Override public void assertionFailed(
		AssertionFailedException e)
	{
		logLine("Assertion failed: " + e.getMessage());
	}

	@Override public void migrationNotPossible(
		MigrationNotPossibleException e)
	{
		logLine("Migration not possible: " + e.getMessage());
	}
	
	@Override public void migrationFailed(
		MigrationFailedException e)
	{
		logLine("Migration failed: " + e.getMessage());
	}
	
	@Override public void logLine(String message)
	{
		if (message == null) { throw new IllegalArgumentException("message cannot be null"); }
		
		logLine(this.getStream(), message);
	}
	
	private static void logLine(
		PrintStream out,
		String message)
	{
		if (out == null) { throw new IllegalArgumentException("out cannot be null"); }
		if (message == null) { throw new IllegalArgumentException("message cannot be null"); }
		
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		out.print(f.format(new Date()));
		out.print(" - ");
		out.println(message);
	}
}
