package co.zd.wb.model.base;

import co.zd.wb.model.Assertion;
import co.zd.wb.model.State;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImmutableState implements State
{
	public ImmutableState(
		UUID stateId)
	{
		this.setStateId(stateId);
		this.setAssertions(new ArrayList<Assertion>());
	}
	
	public ImmutableState(
		UUID stateId,
		String label)
	{
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(new ArrayList<Assertion>());
	}

	public ImmutableState(
		UUID stateId,
		List<Assertion> assertions)
	{
		this.setStateId(stateId);
		this.setAssertions(assertions);
	}
	
	public ImmutableState(
		UUID stateId,
		String label,
		List<Assertion> assertions)
	{
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(assertions);
	}

	// <editor-fold desc="StateId" defaultstate="collapsed">

	private UUID m_stateId = null;
	private boolean m_stateId_set = false;

	public UUID getStateId() {
		if(!m_stateId_set) {
			throw new IllegalStateException("stateId not set.  Use the HasStateId() method to check its state before accessing it.");
		}
		return m_stateId;
	}

	private void setStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("stateId cannot be null");
		}
		boolean changing = !m_stateId_set || m_stateId != value;
		if(changing) {
			m_stateId_set = true;
			m_stateId = value;
		}
	}

	private void clearStateId() {
		if(m_stateId_set) {
			m_stateId_set = true;
			m_stateId = null;
		}
	}

	private boolean hasStateId() {
		return m_stateId_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Label" defaultstate="collapsed">

	private String m_label = null;
	private boolean m_label_set = false;

	public String getLabel() {
		if(!m_label_set) {
			throw new IllegalStateException("label not set.  Use the HasLabel() method to check its state before accessing it.");
		}
		return m_label;
	}

	private void setLabel(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("label cannot be null");
		}
		boolean changing = !m_label_set || m_label != value;
		if(changing) {
			m_label_set = true;
			m_label = value;
		}
	}

	private void clearLabel() {
		if(m_label_set) {
			m_label_set = true;
			m_label = null;
		}
	}

	public boolean hasLabel() {
		return m_label_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Assertions" defaultstate="collapsed">

	private List<Assertion> m_assertions = null;
	private boolean m_assertions_set = false;

	public List<Assertion> getAssertions() {
		if(!m_assertions_set) {
			throw new IllegalStateException("assertions not set.  Use the HasAssertions() method to check its state before accessing it.");
		}
		return m_assertions;
	}

	private void setAssertions(List<Assertion> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertions cannot be null");
		}
		boolean changing = !m_assertions_set || m_assertions != value;
		if(changing) {
			m_assertions_set = true;
			m_assertions = value;
		}
	}

	private void clearAssertions() {
		if(m_assertions_set) {
			m_assertions_set = true;
			m_assertions = null;
		}
	}

	private boolean hasAssertions() {
		return m_assertions_set;
	}

	// </editor-fold>
	
	@Override public String getDisplayName()
	{
		if (this.hasLabel())
		{
			return this.getLabel();
		}
		else
		{
			return this.getStateId().toString();
		}
	}
}
