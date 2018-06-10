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
 * Creates &lt;resource&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 *
 * @since                                       4.0
 */
public class ResourceCreator {

    private String type = null;
    private boolean typeSet = false;
    private UUID resourceId = null;
    private boolean resourceIdSet = false;
    private String name = null;
    private boolean nameSet = false;
    private List<StateCreator> states = null;
    private boolean statesSet = false;
    private List<MigrationCreator> migrations = null;
    private boolean migrationsSet = false;
    private FixtureCreator creator = null;
    private boolean creatorSet = false;

    public ResourceCreator(
            FixtureCreator creator,
            String type,
            UUID resourceId,
            String name) {
        this.setCreator(creator);
        this.setType(type);
        this.setResourceId(resourceId);
        this.setName(name);
        this.setStates(new ArrayList<>());
        this.setMigrations(new ArrayList<>());
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

    public String getType() {
        if (!typeSet) {
            throw new IllegalStateException("type not set.");
        }
        if (type == null) {
            throw new IllegalStateException("type should not be null");
        }
        return type;
    }

    private void setType(
            String value) {
        if (value == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        boolean changing = !typeSet || !type.equals(value);
        if (changing) {
            typeSet = true;
            type = value;
        }
    }

    private void clearType() {
        if (typeSet) {
            typeSet = true;
            type = null;
        }
    }

    private boolean hasType() {
        return typeSet;
    }

    public UUID getResourceId() {
        if (!resourceIdSet) {
            throw new IllegalStateException("resourceId not set.");
        }
        if (resourceId == null) {
            throw new IllegalStateException("resourceId should not be null");
        }
        return resourceId;
    }

    private void setResourceId(
            UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("resourceId cannot be null");
        }
        boolean changing = !resourceIdSet || resourceId != value;
        if (changing) {
            resourceIdSet = true;
            resourceId = value;
        }
    }

    private void clearResourceId() {
        if (resourceIdSet) {
            resourceIdSet = true;
            resourceId = null;
        }
    }

    private boolean hasResourceId() {
        return resourceIdSet;
    }

    public String getName() {
        if (!nameSet) {
            throw new IllegalStateException("name not set.");
        }
        if (name == null) {
            throw new IllegalStateException("name should not be null");
        }
        return name;
    }

    private void setName(
            String value) {
        if (value == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        boolean changing = !nameSet || !name.equals(value);
        if (changing) {
            nameSet = true;
            name = value;
        }
    }

    private void clearName() {
        if (nameSet) {
            nameSet = true;
            name = null;
        }
    }

    private boolean hasName() {
        return nameSet;
    }

    public List<StateCreator> getStates() {
        if (!statesSet) {
            throw new IllegalStateException("states not set.");
        }
        if (states == null) {
            throw new IllegalStateException("states should not be null");
        }
        return states;
    }

    private void setStates(List<StateCreator> value) {
        if (value == null) {
            throw new IllegalArgumentException("states cannot be null");
        }
        boolean changing = !statesSet || states != value;
        if (changing) {
            statesSet = true;
            states = value;
        }
    }

    private void clearStates() {
        if (statesSet) {
            statesSet = true;
            states = null;
        }
    }

    private boolean hasStates() {
        return statesSet;
    }

    public List<MigrationCreator> getMigrations() {
        if (!migrationsSet) {
            throw new IllegalStateException("migrations not set.");
        }
        if (migrations == null) {
            throw new IllegalStateException("migrations should not be null");
        }
        return migrations;
    }

    private void setMigrations(List<MigrationCreator> value) {
        if (value == null) {
            throw new IllegalArgumentException("migrations cannot be null");
        }
        boolean changing = !migrationsSet || migrations != value;
        if (changing) {
            migrationsSet = true;
            migrations = value;
        }
    }

    private void clearMigrations() {
        if (migrationsSet) {
            migrationsSet = true;
            migrations = null;
        }
    }

    private boolean hasMigrations() {
        return migrationsSet;
    }

    public StateCreator state(
            UUID stateId,
            String label) {
        StateCreator stateCreator = new StateCreator(this.getCreator(), this, stateId, label);
        this.getStates().add(stateCreator);
        return stateCreator;
    }

    public StateCreator state(
            UUID stateId,
            String label,
            String description) {
        StateCreator stateCreator = new StateCreator(this.getCreator(), this, stateId, label, description);
        this.getStates().add(stateCreator);
        return stateCreator;
    }

    public MigrationCreator migration(
            String type,
            UUID migrationId,
            UUID fromStateId,
            UUID toStateId) {
        MigrationCreator migration = new MigrationCreator(
                this.getCreator(),
                this,
                type,
                migrationId,
                fromStateId,
                toStateId);
        this.getMigrations().add(migration);
        return migration;
    }

    public FixtureCreator creator() {
        return this.getCreator();
    }

    public String render() {
        return this.getCreator().render();
    }

}
