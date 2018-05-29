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
import co.mv.wb.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A {@link State} that cannot be modified after it's initial construction.
 *
 * @author Brendon Matheson
 * @since 1.0
 */
public class ImmutableState implements State {

	private UUID stateId = null;
	private boolean stateIdSet = false;
	private Optional<String> label = null;
	private boolean labelSet = false;
	private List<Assertion> assertions = null;
	private boolean assertionsSet = false;
	private Optional<String> stateDescription = null;
	private boolean stateDescriptionSet = false;

	/**
	 * Creates a new ImmutableState with the supplied ID.
	 *
	 * @param stateId the ID of the new state
	 */
	public ImmutableState(
			UUID stateId) {
		this.setStateId(stateId);
		this.setLabel(Optional.empty());
		this.setAssertions(new ArrayList<>());
	}

	/**
	 * Creates a new ImmutableState with an ID and a label.
	 *
	 * @param stateId the ID of the new state
	 * @param label   the unique label of the new state
	 */
	public ImmutableState(
			UUID stateId,
			Optional<String> label) {
		if (stateId == null || label == null ) {
			throw new IllegalStateException("label should not be null");
		}
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(new ArrayList<>());
	}

	/**
	 * Creates a new ImmutableState with the supplied ID and set of {@link Assertion}s.
	 *
	 * @param stateId    the ID of the new state
	 * @param assertions the assertions that apply to this state
	 */
	public ImmutableState(
			UUID stateId,
			List<Assertion> assertions) {
		if (stateId == null|| assertions == null) {
			throw new IllegalStateException("label should not be null");
		}
		this.setStateId(stateId);
		this.setAssertions(assertions);
	}

	/**
	 * Creates a new ImmutableState with an ID and a label, and with a set of {@link Assertion}s.
	 *
	 * @param stateId    the ID of the new state
	 * @param label      the unique label of the new state
	 * @param assertions the assertions that apply to this state
	 */
	public ImmutableState(
			UUID stateId,
			Optional<String> label,
			List<Assertion> assertions) {
		if (stateId == null || label == null || assertions == null) {
			throw new IllegalStateException("label should not be null");
		}
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(assertions);
	}

	/**
	 * Creates a new ImmutableState with an ID and a label, and with a set of {@link Assertion}s.
	 *
	 * @param stateId     the ID of the new state
	 * @param label       the unique label of the new state
	 * @param description the description that apply to this state
	 */
	public ImmutableState(
			UUID stateId,
			Optional<String> label,
			Optional<String> description) {
		if (stateId == null || label == null || description == null) {
			throw new IllegalStateException("Passed values should not be null");
		}
		this.setStateId(stateId);
		this.setLabel(label);
		this.setStateDescription(description);
	}

	/**
	 * Creates a new ImmutableState with an ID and a label, and with a set of {@link Assertion}s.
	 *
	 * @param stateId     the ID of the new state
	 * @param label       the unique label of the new state
	 * @param assertions  the assertions that apply to this state
	 * @param description the description that apply to this state
	 */
	public ImmutableState(
			UUID stateId,
			Optional<String> label,
			List<Assertion> assertions,
			Optional<String> description) {
		if (stateId == null || label == null || assertions == null || description == null ) {
			throw new IllegalStateException("Passed values should not be null");
		}
		this.setStateId(stateId);
		this.setLabel(label);
		this.setAssertions(assertions);
		this.setStateDescription(description);
	}

	@Override
	public UUID getStateId() {
		return stateId;
	}

	private void setStateId(
			UUID value) {
		boolean changing = !stateIdSet || stateId != value;
		if (changing) {
			stateIdSet = true;
			stateId = value;
		}
	}

	private void clearStateId() {
		if (stateIdSet) {
			stateIdSet = true;
			stateId = null;
		}
	}

	private boolean hasStateId() {
		return stateIdSet;
	}

	public Optional<String> getLabel() {
		return label;
	}

	private void setLabel(Optional<String> value) {
		boolean changing = !labelSet || label != value;
		if (changing) {
			labelSet = true;
			label = value;
		}
	}

	@Override
	public List<Assertion> getAssertions() {
		return assertions;
	}

	private void setAssertions(List<Assertion> value) {
		boolean changing = !assertionsSet || assertions != value;
		if (changing) {
			assertionsSet = true;
			assertions = value;
		}
	}

	private void clearAssertions() {
		if (assertionsSet) {
			assertionsSet = true;
			assertions = null;
		}
	}

	private boolean hasAssertions() {
		return assertionsSet;
	}

	@Override
	public String getDisplayName() {
		return this.getLabel().orElse(this.getStateId().toString());
	}

	@Override
	public Optional<String> getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(Optional<String> value) {
		boolean changing = !stateDescriptionSet || this.stateDescription != value;
		this.stateDescription = value;
	}
}
