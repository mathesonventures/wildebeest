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
 * Creates &lt;state&gt;'s as part of the fluent API for creating XML fixtures for unit tests.
 *
 * @since                                       4.0
 */
public class StateCreator
{
    private final FixtureBuilder creator;
    private final ResourceBuilder resource;
    private final UUID stateId;
    private final String label;
    private final String description;
    private final List<AssertionCreator> assertions;

    public StateCreator(
        FixtureBuilder creator,
        ResourceBuilder resource,
        UUID stateId,
        String label,
        String description)
    {
        if (creator == null) throw new ArgumentNullException("creator");
        if (resource == null) throw new ArgumentNullException("resource");
        if (stateId == null) throw new ArgumentNullException("stateId");

        this.creator = creator;
        this.resource = resource;
        this.stateId = stateId;
        this.label = label;
        this.description = description;
        this.assertions = new ArrayList<>();
    }

    public UUID getStateId()
    {
        return this.stateId;
    }

    public String getLabel()
    {
        return this.label;
    }

    public boolean hasLabel()
    {
        return this.label != null;
    }

    public String getDescription()
    {
        return this.description;
    }

    public boolean hasDescription()
    {
        return this.description != null;
    }

    public List<AssertionCreator> getAssertions()
    {
        return this.assertions;
    }

    public AssertionCreator assertion(
        String type,
        UUID assertionId)
    {
        AssertionCreator assertion = new AssertionCreator(this.creator, this, type, assertionId);
        this.assertions.add(assertion);
        return assertion;
    }

    public ResourceBuilder resource()
    {
        return this.resource;
    }

    public StateCreator state(
        UUID stateId,
        String label)
    {
        return this.resource.state(stateId, label);
    }

    public MigrationBuilder migration(
        String type,
        UUID migrationId,
        UUID fromStateId,
        UUID toStateId)
    {
        return this.resource().migration(
            type,
            migrationId,
            fromStateId,
            toStateId);
    }

    public String render()
    {
        return this.creator.build();
    }
}
