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

import co.mv.wb.framework.ArgumentNullException;

import java.util.Arrays;
import java.util.Optional;

/**
 * Indicates that a referred resource is missing in the XML
 *
 * @since 4.0
 */
public class InvalidReferenceException extends Exception
{
	private final Reference[] refs;
	private final Reference referrer;

	/**
	 * Creates an InvalidReferenceException for a single referenced entity where the reference was not from another
	 * entity defined within Wildebeest.  For example if an API request for a non-existant state is received, this is a
	 * reference without a referring entity.
	 *
	 * @param refType  the type of the entity referenced.
	 * @param refValue the reference value that does not exist.
	 * @return a new InvalidReferenceException with a single referenced entity and no referrer.
	 */
	public static InvalidReferenceException oneReference(
		EntityType refType,
		String refValue)
	{
		if (refType == null) throw new ArgumentNullException("refType");
		if (refValue == null) throw new ArgumentNullException("refValue");

		return new InvalidReferenceException(
			new Reference[]
				{
					new Reference(refType, refValue)
				},
			null);
	}

	public static InvalidReferenceException oneReference(
		EntityType refType,
		String refValue,
		EntityType referrerType,
		String referrerValue)
	{
		if (refType == null) throw new ArgumentNullException("refType");
		if (refValue == null) throw new ArgumentNullException("refValue");
		if (referrerType == null) throw new ArgumentNullException("referrerType");
		if (referrerValue == null) throw new ArgumentNullException("referrerValue");

		return new InvalidReferenceException(
			new Reference[]
				{
					new Reference(refType, refValue)
				},
			new Reference(referrerType, referrerValue));
	}

	public static InvalidReferenceException twoReferences(
		EntityType refType0,
		String refValue0,
		EntityType refType1,
		String refValue1,
		EntityType referrerType,
		String referrerValue)
	{
		if (refType0 == null) throw new ArgumentNullException("refType0");
		if (refValue0 == null) throw new ArgumentNullException("refValue0");
		if (refType1 == null) throw new ArgumentNullException("refType1");
		if (refValue1 == null) throw new ArgumentNullException("refValue1");
		if (referrerType == null) throw new ArgumentNullException("referrerType");
		if (referrerValue == null) throw new ArgumentNullException("referrerValue");

		return new InvalidReferenceException(
			new Reference[]
				{
					new Reference(refType0, refValue0),
					new Reference(refType1, refValue1)
				},
			new Reference(referrerType, referrerValue));
	}

	/**
	 * Constructs a new InvalidReferenceException.  This constructor is private as we push all constructions through the
	 * static factory methods in order to manage the permutations that are valid,
	 *
	 * @param refs     the invalid references to entities.
	 * @param referrer the optional referrer that made the invalid references.
	 */
	private InvalidReferenceException(
		Reference[] refs,
		Reference referrer)
	{
		super(buildMessage(refs, referrer));

		if (refs == null) throw new ArgumentNullException("refs");

		if (Arrays.asList(refs).contains(null))
		{
			throw new IllegalArgumentException("refs must not contain null");
		}

		this.refs = refs;
		this.referrer = referrer;
	}

	private static String buildMessage(
		Reference[] refs,
		Reference referrer)
	{
		if (refs == null) throw new ArgumentNullException("refs");

		StringBuilder message = new StringBuilder();

		if (referrer == null)
		{
			message.append("Invalid references found to: [ ");
		}
		else
		{
			message
				.append(referrer.getType().getName()).append(":").append(referrer.getRef())
				.append(" has invalid references to: [ ");
		}

		for (int i = 0; i < refs.length; i++)
		{
			Reference ref = refs[i];

			if (i > 0)
			{
				message.append(", ");
			}

			message.append(ref.getType().getName()).append(": ").append(ref.getRef());
		}

		message.append(" ]");

		return message.toString();
	}

	public Reference[] getRefs()
	{
		return this.refs;
	}

	public Optional<Reference> getReferrer()
	{
		return Optional.ofNullable(this.referrer);
	}
}
