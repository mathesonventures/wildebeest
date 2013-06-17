package co.mv.stm.service;

import co.mv.stm.model.Assertion;
import co.mv.stm.model.AssertionResponse;
import co.mv.stm.model.Transition;
import java.io.PrintStream;

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
		
		this.getStream().println("Starting assertion: " + assertion.getName());
	}

	@Override public void assertionComplete(
		Assertion assertion,
		AssertionResponse response)
	{
		if (assertion == null) { throw new IllegalArgumentException("assertion cannot be null"); }
		if (response == null) { throw new IllegalArgumentException("response cannot be null"); }
	
		if (response.getResult())
		{
			this.getStream().println(String.format(
				"Assertion \"%s\" passed: %s",
				assertion.getName(),
				response.getMessage()));
		}
		
		else
		{
			this.getStream().println(String.format(
				"Assertion \"%s\" failed: %s",
				assertion.getName(),
				response.getMessage()));
		}
	}
	
	@Override public void transitionStart(Transition transition)
	{
		if (transition == null) { throw new IllegalArgumentException("transition cannot be null"); }
		
		if (transition.hasFromStateId())
		{
			if (transition.hasToStateId())
			{
				this.getStream().println(String.format(
					"Starting transition \"%s\" from state \"%s\" to \"%s\"",
					"TODO",
					transition.getFromStateId().toString(),
					transition.getToStateId().toString()));
			}
			else
			{
				this.getStream().println(String.format(
					"Starting transition \"%s\" from state \"%s\" to non-existent",
					"TODO",
					transition.getFromStateId().toString()));
			}
		}
		else
		{
			this.getStream().println(String.format(
				"Starting transition \"%s\" from non-existent to \"%s\"",
				"TODO",
				transition.getToStateId().toString()));
		}
	}
	
	@Override public void transitionComplete(Transition transition)
	{
		if (transition == null) { throw new IllegalArgumentException("transition cannot be null"); }
		
		this.getStream().println("Transition complete");
	}
}
