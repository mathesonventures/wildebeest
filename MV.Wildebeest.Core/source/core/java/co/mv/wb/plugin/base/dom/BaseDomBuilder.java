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

package co.mv.wb.plugin.base.dom;

import co.mv.wb.ModelExtensions;
import co.mv.wb.framework.ArgumentNullException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Optional;

/**
 * Base class for DOM-based builders.
 *
 * @since 1.0
 */
public abstract class BaseDomBuilder implements DomBuilder
{
	private final XPath xpath;
	private Element element = null;

	/**
	 * Creates a new BaseDomBuilder.
	 *
	 * @since 1.0
	 */
	protected BaseDomBuilder()
	{
		this.xpath = XPathFactory.newInstance().newXPath();
	}

	/**
	 * Gets the root {@link org.w3c.dom.Element} that represents the item to be deserialized.
	 *
	 * @return the Element that represents the item to be deserialized
	 * @since 1.0
	 */
	protected final Element getElement()
	{
		return this.element;
	}

	@Override
	public void setElement(Element value)
	{
		if (value == null) throw new ArgumentNullException("value");

		this.element = value;
	}

	@Override
	public void reset()
	{
		this.element = null;
	}

	/**
	 * Attempts to retrieve the string value identified by the supplied xpath expression, relative to the Element held
	 * by this builder.
	 *
	 * @param xpath the xpath expression to the Element that contains the value to be
	 *              returned.
	 * @return a TryGetResult containing the value identified by the supplied xpath if
	 * it was able to be obtained, or an empty TryGetResult otherwise
	 * @since 2.0
	 */
	protected Optional<String> tryGetString(String xpath)
	{
		if (xpath == null) throw new ArgumentNullException("xpath");
		if ("".equals(xpath)) throw new IllegalArgumentException("xpath");

		Optional<String> result = null;

		Node node;

		try
		{
			node = (Node)this.xpath.compile(xpath).evaluate(this.getElement(), XPathConstants.NODE);
			if (node != null)
			{
				Element element = ModelExtensions.As(node, Element.class);

				if (element != null)
				{
					String value = element.getTextContent();
					result = Optional.of(value);
				}
			}
		}
		catch (XPathExpressionException e)
		{
			// TODO: This should be handled.
		}

		if (result == null)
		{
			result = Optional.empty();
		}

		return result;
	}

	/**
	 * Attempts to retrieve the integer value identified by the supplied xpath expression, relative to the Element held
	 * by this builder.
	 *
	 * @param xpath the xpath expression to the Element that contains the value to be
	 *              returned.
	 * @return a TryGetResult containing the value identified by the supplied xpath if
	 * it was able to be obtained, or an empty TryGetResult otherwise
	 * @since 2.0
	 */
	protected Optional<Integer> tryGetInteger(String xpath)
	{
		Optional<Integer> result = null;
		Optional<String> raw = this.tryGetString(xpath);

		if (raw.isPresent())
		{
			try
			{
				int value = Integer.parseInt(raw.get());
				result = Optional.of(value);
			}
			catch (NumberFormatException e)
			{
				result = Optional.empty();
			}
		}

		return result;
	}
}
