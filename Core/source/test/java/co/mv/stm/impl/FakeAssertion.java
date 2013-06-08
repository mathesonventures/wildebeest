package co.mv.stm.impl;

import co.mv.stm.Assertion;
import co.mv.stm.AssertionResponse;
import co.mv.stm.AssertionType;
import co.mv.stm.ResourceInstance;
import java.util.UUID;

public class FakeAssertion extends BaseAssertion implements Assertion
{
	protected FakeAssertion(
		UUID assertionId,
		String name,
		int seqNum,
		AssertionType assertionType,
		boolean result,
		String message)
	{
		super(assertionId, name, seqNum, assertionType);
		
		this.setResult(result);
		this.setMessage(message);
	}

	// <editor-fold desc="Result" defaultstate="collapsed">

	private boolean m_result = false;
	private boolean m_result_set = false;

	public boolean getResult() {
		if(!m_result_set) {
			throw new IllegalStateException("result not set.  Use the HasResult() method to check its state before accessing it.");
		}
		return m_result;
	}

	private void setResult(
		boolean value) {
		boolean changing = !m_result_set || m_result != value;
		if(changing) {
			m_result_set = true;
			m_result = value;
		}
	}

	private void clearResult() {
		if(m_result_set) {
			m_result_set = true;
			m_result = false;
		}
	}

	private boolean hasResult() {
		return m_result_set;
	}

	// </editor-fold>

	// <editor-fold desc="Message" defaultstate="collapsed">

	private String m_message = null;
	private boolean m_message_set = false;

	public String getMessage() {
		if(!m_message_set) {
			throw new IllegalStateException("message not set.  Use the HasMessage() method to check its state before accessing it.");
		}
		return m_message;
	}

	private void setMessage(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		boolean changing = !m_message_set || m_message != value;
		if(changing) {
			m_message_set = true;
			m_message = value;
		}
	}

	private void clearMessage() {
		if(m_message_set) {
			m_message_set = true;
			m_message = null;
		}
	}

	private boolean hasMessage() {
		return m_message_set;
	}

	// </editor-fold>

	@Override public AssertionResponse apply(ResourceInstance instance)
	{
		return new ImmutableAssertionResponse(this.getResult(), this.getMessage());
	}
}
