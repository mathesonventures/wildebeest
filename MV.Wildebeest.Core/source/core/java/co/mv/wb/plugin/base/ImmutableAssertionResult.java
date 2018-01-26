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

package co.mv.wb.plugin.base;

import co.mv.wb.AssertionResult;
import java.util.UUID;

/**
 * An {@link AssertionResult} that cannot be modified after it's initial construction.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class ImmutableAssertionResult implements AssertionResult
{
	/**
	 * Creates a new ImmutableAssertionResult for the specified assertion, with the supplied result value and message.
	 * 
	 * @param       assertionId                 the ID of the assertion that was applied to get this result
	 * @param       result                      indicator whether the assertion succeeded or failed
	 * @param       message                     the textual message describing the assertion result
	 */
	public ImmutableAssertionResult(
		UUID assertionId,
		boolean result,
		String message)
	{
		this.setAssertionId(assertionId);
		this.setResult(result);
		this.setMessage(message);
	}
	
	// <editor-fold desc="AssertionId" defaultstate="collapsed">

	private UUID _assertionId = null;
	private boolean _assertionId_set = false;

	@Override public UUID getAssertionId() {
		if(!_assertionId_set) {
			throw new IllegalStateException("assertionId not set.  Use the HasAssertionId() method to check its state before accessing it.");
		}
		return _assertionId;
	}

	private void setAssertionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionId cannot be null");
		}
		boolean changing = !_assertionId_set || _assertionId != value;
		if(changing) {
			_assertionId_set = true;
			_assertionId = value;
		}
	}

	private void clearAssertionId() {
		if(_assertionId_set) {
			_assertionId_set = true;
			_assertionId = null;
		}
	}

	private boolean hasAssertionId() {
		return _assertionId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Result" defaultstate="collapsed">

	private boolean _result = false;
	private boolean _result_set = false;

	@Override public boolean getResult() {
		if(!_result_set) {
			throw new IllegalStateException("result not set.  Use the HasResult() method to check its state before accessing it.");
		}
		return _result;
	}

	private void setResult(
		boolean value) {
		boolean changing = !_result_set || _result != value;
		if(changing) {
			_result_set = true;
			_result = value;
		}
	}

	private void clearResult() {
		if(_result_set) {
			_result_set = true;
			_result = false;
		}
	}

	private boolean hasResult() {
		return _result_set;
	}

	// </editor-fold>

	// <editor-fold desc="Message" defaultstate="collapsed">

	private String _message = null;
	private boolean _message_set = false;

	@Override public String getMessage() {
		if(!_message_set) {
			throw new IllegalStateException("message not set.  Use the HasMessage() method to check its state before accessing it.");
		}
		return _message;
	}

	private void setMessage(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		boolean changing = !_message_set || !_message.equals(value);
		if(changing) {
			_message_set = true;
			_message = value;
		}
	}

	private void clearMessage() {
		if(_message_set) {
			_message_set = true;
			_message = null;
		}
	}

	private boolean hasMessage() {
		return _message_set;
	}

	// </editor-fold>
}
