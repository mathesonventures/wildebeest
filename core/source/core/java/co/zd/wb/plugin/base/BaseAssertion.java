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

package co.zd.wb.plugin.base;

import co.zd.wb.Assertion;
import java.util.UUID;

/**
 * Provides a base implementation of {@link Assertion}
 * 
 * @author                                      Brendon Matheson
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

	private UUID m_assertionId = null;
	private boolean m_assertionId_set = false;

	@Override public UUID getAssertionId() {
		if(!m_assertionId_set) {
			throw new IllegalStateException("assertionId not set.  Use the HasAssertionId() method to check its state before accessing it.");
		}
		return m_assertionId;
	}

	private void setAssertionId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionId cannot be null");
		}
		boolean changing = !m_assertionId_set || m_assertionId != value;
		if(changing) {
			m_assertionId_set = true;
			m_assertionId = value;
		}
	}

	private void clearAssertionId() {
		if(m_assertionId_set) {
			m_assertionId_set = true;
			m_assertionId = null;
		}
	}

	private boolean hasAssertionId() {
		return m_assertionId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="SeqNum" defaultstate="collapsed">

	private int m_seqNum = 0;
	private boolean m_seqNum_set = false;

	@Override public int getSeqNum() {
		if(!m_seqNum_set) {
			throw new IllegalStateException("seqNum not set.  Use the HasSeqNum() method to check its state before accessing it.");
		}
		return m_seqNum;
	}

	private void setSeqNum(
		int value) {
		boolean changing = !m_seqNum_set || m_seqNum != value;
		if(changing) {
			m_seqNum_set = true;
			m_seqNum = value;
		}
	}

	private void clearSeqNum() {
		if(m_seqNum_set) {
			m_seqNum_set = true;
			m_seqNum = 0;
		}
	}

	private boolean hasSeqNum() {
		return m_seqNum_set;
	}

	// </editor-fold>
}