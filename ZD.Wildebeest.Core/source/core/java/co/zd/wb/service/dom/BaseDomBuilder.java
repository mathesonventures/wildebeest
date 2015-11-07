// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.zd.wb.service.dom;

import co.zd.wb.framework.TryResult;
import co.zd.wb.ModelExtensions;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Base class for DOM-based builders.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public abstract class BaseDomBuilder implements DomBuilder
{
	/**
	 * Creates a new BaseDomBuilder.
	 * 
	 * @since                                   1.0
	 */
	protected BaseDomBuilder()
	{
		this.setXPath(XPathFactory.newInstance().newXPath());
	}
	
	// <editor-fold desc="Element" defaultstate="collapsed">

	private Element _element = null;
	private boolean _element_set = false;

	/**
	 * Gets the root {@link org.w3c.dom.Element} that represents the item to be deserialized.
	 * 
	 * @since                                   1.0
	 */
	protected Element getElement() {
		if(!_element_set) {
			throw new IllegalStateException("element not set.  Use the HasElement() method to check its state before accessing it.");
		}
		return _element;
	}

	@Override public void setElement(
		Element value) {
		if(value == null) {
			throw new IllegalArgumentException("element cannot be null");
		}
		boolean changing = !_element_set || _element != value;
		if(changing) {
			_element_set = true;
			_element = value;
		}
	}

	protected void clearElement() {
		if(_element_set) {
			_element_set = true;
			_element = null;
		}
	}

	private boolean hasElement() {
		return _element_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="XPath" defaultstate="collapsed">

	private XPath _xPath = null;
	private boolean _xPath_set = false;

	private XPath getXPath() {
		if(!_xPath_set) {
			throw new IllegalStateException("xPath not set.  Use the HasXPath() method to check its state before accessing it.");
		}
		return _xPath;
	}

	private void setXPath(
		XPath value) {
		if(value == null) {
			throw new IllegalArgumentException("xPath cannot be null");
		}
		boolean changing = !_xPath_set || _xPath != value;
		if(changing) {
			_xPath_set = true;
			_xPath = value;
		}
	}

	private void clearXPath() {
		if(_xPath_set) {
			_xPath_set = true;
			_xPath = null;
		}
	}

	private boolean hasXPath() {
		return _xPath_set;
	}

	// </editor-fold>
	
	@Override public void reset()
	{
		this.clearElement();
	}
	
	/**
	 * Attempts to retrieve the string value identified by the supplied xpath expression, relative to the Element held
	 * by this builder.
	 * 
	 * @param       xpath                       the xpath expression to the Element that contains the value to be
	 *                                          returned.
	 * @return                                  a TryGetResult containing the value identified by the supplied xpath if
	 *                                          it was able to be obtained, or an empty TryGetResult otherwise
	 * @since                                   2.0
	 */
	protected TryResult<String> tryGetString(String xpath)
	{
		if (xpath == null) { throw new IllegalArgumentException("xpath"); }
		if ("".equals(xpath)) { throw new IllegalArgumentException("xpath"); }
		
		TryResult<String> result = null;
		
		Node node = null;
		try
		{
			node  = (Node)this.getXPath().compile(xpath).evaluate(this.getElement(), XPathConstants.NODE);
			if (node != null)
			{
				Element element = ModelExtensions.As(node, Element.class);
				
				if (element != null)
				{
					String value = element.getTextContent();
					result = new TryResult<String>(value);
				}
			}
		}
		catch (XPathExpressionException e)
		{
		}

		if (result == null)
		{
			result = new TryResult<String>();
		}

		return result;
	}
	
	/**
	 * Attempts to retrieve the integer value identified by the supplied xpath expression, relative to the Element held
	 * by this builder.
	 * 
	 * @param       xpath                       the xpath expression to the Element that contains the value to be
	 *                                          returned.
	 * @return                                  a TryGetResult containing the value identified by the supplied xpath if
	 *                                          it was able to be obtained, or an empty TryGetResult otherwise
	 * @since                                   2.0
	 */
	protected TryResult<Integer> tryGetInteger(String xpath)
	{
		TryResult<Integer> result = null;
		TryResult<String> raw = this.tryGetString(xpath);
		
		if (raw.hasValue())
		{
			try
			{
				int value = Integer.parseInt(raw.getValue());
				result = new TryResult<Integer>(value);
			}
			catch(Exception e)
			{
				result = new TryResult<Integer>();
			}
		}
		
		return result;
	}
}
