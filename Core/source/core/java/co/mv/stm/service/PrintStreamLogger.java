package co.mv.stm.service;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.Resource;
import co.mv.stm.model.State;
import co.mv.stm.model.Transition;
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
	
	@Override public void transitionStart(
		Resource resource,
		Transition transition)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (transition == null) { throw new IllegalArgumentException("transition cannot be null"); }

		State fromState = transition.hasFromStateId() ? resource.stateForId(transition.getFromStateId()) : null;
		State toState = transition.hasToStateId() ? resource.stateForId(transition.getToStateId()) : null;
		
		if (fromState != null)
		{
			if (toState != null)
			{
				logLine(this.getStream(), String.format(
					"Transitioning from state \"%s\" to \"%s\"",
					fromState.getDisplayName(),
					toState.getDisplayName()));
			}
			else
			{
				logLine(this.getStream(), String.format(
					"Transitioning from state \"%s\" to non-existent",
					fromState.getDisplayName()));
			}
		}
		else if (toState != null)
		{
				logLine(this.getStream(), String.format(
					"Transitioning from non-existent to \"%s\"",
					toState.getDisplayName()));
		}
		else
		{
			// Exception?
		}
	}
	
	@Override public void transitionComplete(
		Resource resource,
		Transition transition)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (transition == null) { throw new IllegalArgumentException("transition cannot be null"); }
		
		logLine(this.getStream(), "Transition complete");
	}
	
	private static void logLine(
		PrintStream out,
		String message)
	{
		if (out == null) { throw new IllegalArgumentException("out cannot be null"); }
		if (message == null) { throw new IllegalArgumentException("message cannot be null"); }
		
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
		out.print(f.format(new Date()));
		out.print(" - ");
		out.println(message);
	}
}
