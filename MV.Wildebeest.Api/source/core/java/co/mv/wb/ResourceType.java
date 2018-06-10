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

package co.mv.wb;

/**
 * Identifies a type of {@link Resource}.  The resource's type is identified in the XML file, and is used to verify
 * which {@link Assertion}'s and {@link Migration}'s can be applied to the Resource.
 *
 * @since                                       4.0
 */
public class ResourceType {

    private String uri = null;
    private boolean uriSet = false;
    private String name = null;
    private boolean nameSet = false;

    public ResourceType(
            String uri,
            String name) {
        this.setUri(uri);
        this.setName(name);
    }

    public String getUri() {
        if (!uriSet) {
            throw new IllegalStateException("uri not set.");
        }
        if (uri == null) {
            throw new IllegalStateException("uri should not be null");
        }
        return uri;
    }

    private void setUri(
            String value) {
        if (value == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }
        boolean changing = !uriSet || uri != value;
        if (changing) {
            uriSet = true;
            uri = value;
        }
    }

    private void clearUri() {
        if (uriSet) {
            uriSet = true;
            uri = null;
        }
    }

    private boolean hasUri() {
        return uriSet;
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
        boolean changing = !nameSet || name != value;
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

}
