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

import co.mv.wb.framework.ArgumentNullException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Creates &lt;resource&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 *
 * @since                                       4.0
 */
public class ResourceBuilder
{
    private final String type;
    private final UUID resourceId;
    private final String name;
    private final List<StateCreator> states;
    private final List<MigrationBuilder> migrations;
    private final FixtureBuilder builder;

    public ResourceBuilder(
        FixtureBuilder creator,
        String type,
        UUID resourceId,
        String name)
    {
        if (creator == null) throw new ArgumentNullException("builder");
        if (type == null) throw new ArgumentNullException("type");
        if (resourceId == null) throw new ArgumentNullException("resourceId");
        if (name == null) throw new ArgumentNullException("name");

        this.builder = creator;
        this.type = type;
        this.resourceId = resourceId;
        this.name = name;
        this.states = new ArrayList<>();
        this.migrations = new ArrayList<>();
    }

    public String getType()
    {
        return this.type;
    }

    public UUID getResourceId()
    {
        return this.resourceId;
    }

    public String getName()
    {
        return this.name;
    }

    public List<StateCreator> getStates()
    {
        return this.states;
    }

    public List<MigrationBuilder> getMigrations()
    {
        return this.migrations;
    }

    public StateCreator state(
        UUID stateId,
        String label)
    {
        StateCreator stateCreator = new StateCreator(this.builder, this, stateId, label, null);
        this.getStates().add(stateCreator);
        return stateCreator;
    }

    public StateCreator state(
        UUID stateId,
        String label,
        String description)
    {
        StateCreator stateCreator = new StateCreator(this.builder, this, stateId, label, description);
        this.getStates().add(stateCreator);
        return stateCreator;
    }

    public MigrationBuilder migration(
        String type,
        UUID migrationId,
        String fromStateId,
        String toStateId)
    {
        if (type == null) throw new ArgumentNullException("type");
        if (migrationId == null) throw new ArgumentNullException("migrationId");

        MigrationBuilder migration = new MigrationBuilder(
            this.builder,
            this,
            type,
            migrationId,
            fromStateId,
            toStateId);
        this.getMigrations().add(migration);
        return migration;
    }

    public FixtureBuilder builder()
    {
        return this.builder;
    }

    public String build()
    {
        return this.builder().build();
    }
}
