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
 * Indicates that a referred resource is missing in the XML
 *
 * @since 4.0
 */
public class InvalidReferenceException extends Exception {
    private final String type;
    private final String ref;
    private final String referrerType;
    private final String referrerId;

    public InvalidReferenceException(
            String type,
            String ref,
            String referrerType,
            String referrerId
    ) {
        super(structureMetadata(type, ref, referrerType, referrerId));
        this.ref = ref;
        this.type = type;
        this.referrerType = referrerType;
        this.referrerId = referrerId;
    }

    public static String structureMetadata
            (
                    String type,
                    String ref,
                    String referrerType,
                    String referrerId
            ) {
        String message = String.format(referrerType + " " + referrerId + " does not contain " + type + " " + ref);

        return message;
    }

    public String getType() {
        return this.type;
    }

    public String getRef() {
        return this.ref;
    }

    public String getReferrerType() {
        return this.referrerType;
    }

    public String getReferrerId() {
        return this.referrerId;
    }

}

