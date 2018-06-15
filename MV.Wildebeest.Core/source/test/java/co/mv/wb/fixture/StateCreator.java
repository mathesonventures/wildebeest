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

package co.mv.wb.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Creates &lt;state&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 *
 * @since                                       4.0
 */
public class StateCreator {

    private FixtureCreator creator = null;
    private boolean creatorSet = false;
    private ResourceCreator resource = null;
    private boolean resourceSet = false;
    private UUID stateId = null;
    private boolean stateIdSet = false;
    private String label = null;
    private boolean labelSet = false;
    private String description = null;
    private boolean descriptionSet = false;
    private List<AssertionCreator> assertions = null;
    private boolean assertionsSet = false;


    public StateCreator(
            FixtureCreator creator,
            ResourceCreator resource,
            UUID stateId,
            String label) {
        this.setCreator(creator);
        this.setResource(resource);
        this.setStateId(stateId);

        if (label != null) {
            this.setLabel(label);
        }

        this.setAssertions(new ArrayList<>());
    }


    public StateCreator(
            FixtureCreator creator,
            ResourceCreator resource,
            UUID stateId,
            String label,
            String description) {
        this.setCreator(creator);
        this.setResource(resource);
        this.setStateId(stateId);

        if (label != null) {
            this.setLabel(label);
        }

        if (description != null){
            this.setDescription(description);
        }

        this.setAssertions(new ArrayList<>());
    }

    private FixtureCreator getCreator() {
        if (!creatorSet) {
            throw new IllegalStateException("creator not set.");
        }
        if (creator == null) {
            throw new IllegalStateException("creator should not be null");
        }
        return creator;
    }

    private void setCreator(
            FixtureCreator value) {
        if (value == null) {
            throw new IllegalArgumentException("creator cannot be null");
        }
        boolean changing = !creatorSet || creator != value;
        if (changing) {
            creatorSet = true;
            creator = value;
        }
    }

    private void clearCreator() {
        if (creatorSet) {
            creatorSet = true;
            creator = null;
        }
    }

    private boolean hasCreator() {
        return creatorSet;
    }

    private ResourceCreator getResource() {
        if (!resourceSet) {
            throw new IllegalStateException("resource not set.");
        }
        if (resource == null) {
            throw new IllegalStateException("resource should not be null");
        }
        return resource;
    }

    private void setResource(
            ResourceCreator value) {
        if (value == null) {
            throw new IllegalArgumentException("resource cannot be null");
        }
        boolean changing = !resourceSet || resource != value;
        if (changing) {
            resourceSet = true;
            resource = value;
        }
    }

    private void clearResource() {
        if (resourceSet) {
            resourceSet = true;
            resource = null;
        }
    }

    private boolean hasResource() {
        return resourceSet;
    }

    public UUID getStateId() {
        if (!stateIdSet) {
            throw new IllegalStateException("stateId not set.");
        }
        if (stateId == null) {
            throw new IllegalStateException("stateId should not be null");
        }
        return stateId;
    }

    private void setStateId(
            UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("stateId cannot be null");
        }
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

    public String getLabel() {
        if (!labelSet) {
            throw new IllegalStateException("label not set.");
        }
        if (label == null) {
            throw new IllegalStateException("label should not be null");
        }
        return label;
    }

    private void setLabel(
            String value) {
        if (value == null) {
            throw new IllegalArgumentException("label cannot be null");
        }
        boolean changing = !labelSet || !label.equals(value);
        if (changing) {
            labelSet = true;
            label = value;
        }
    }

    private void clearLabel() {
        if (labelSet) {
            labelSet = true;
            label = null;
        }
    }

    public boolean hasLabel() {
        return labelSet;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String value) {
        if (value == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        boolean changing = !labelSet || !label.equals(value);
        if (changing) {
            descriptionSet = true;
            description = value;
        }
    }

    private void clearDescription(){
        if (descriptionSet) {
            descriptionSet = true;
            description = null;
        }
    }

    public boolean hasDescription(){ return descriptionSet; }


    public List<AssertionCreator> getAssertions() {
        if (!assertionsSet) {
            throw new IllegalStateException("assertions not set.");
        }
        if (assertions == null) {
            throw new IllegalStateException("assertions should not be null");
        }
        return assertions;
    }

    private void setAssertions(List<AssertionCreator> value) {
        if (value == null) {
            throw new IllegalArgumentException("assertions cannot be null");
        }
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

    public AssertionCreator assertion(
            String type,
            UUID assertionId) {
        AssertionCreator assertion = new AssertionCreator(this.getCreator(), this, type, assertionId);
        this.getAssertions().add(assertion);
        return assertion;
    }

    public ResourceCreator resource() {
        return this.getResource();
    }

    public StateCreator state(
            UUID stateId,
            String label) {
        return this.getResource().state(stateId, label);
    }

    public MigrationCreator migration(
            String type,
            UUID migrationId,
            String fromState,
            String toState) {
        return this.getResource().migration(type, migrationId, fromState, toState);
    }

    public String render() {
        return this.getCreator().render();
    }
}
