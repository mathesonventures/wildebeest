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

import co.mv.wb.Assertion;

import java.util.UUID;

/**
 * Provides a base implementation of {@link Assertion}
 * 
 * @since                                       1.0
 */
public abstract class BaseAssertion implements Assertion
{
	/**
	 * Creates a new BaseAssertion.
	 * 
	 * @param       assertionId                 the ID of the new Assertion
	 * @param       seqNum                      the ordinal index of the new Assertion within its owning container
	 */
	protected BaseAssertion(
		UUID assertionId,
		int seqNum)
	{
		this.setAssertionId(assertionId);
		this.setSeqNum(seqNum);
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
	
	// <editor-fold desc="SeqNum" defaultstate="collapsed">

	private int _seqNum = 0;
	private boolean _seqNum_set = false;

	@Override public int getSeqNum() {
		if(!_seqNum_set) {
			throw new IllegalStateException("seqNum not set.  Use the HasSeqNum() method to check its state before accessing it.");
		}
		return _seqNum;
	}

	private void setSeqNum(
		int value) {
		boolean changing = !_seqNum_set || _seqNum != value;
		if(changing) {
			_seqNum_set = true;
			_seqNum = value;
		}
	}

	private void clearSeqNum() {
		if(_seqNum_set) {
			_seqNum_set = true;
			_seqNum = 0;
		}
	}

	private boolean hasSeqNum() {
		return _seqNum_set;
	}

	// </editor-fold>
}
