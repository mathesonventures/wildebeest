// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
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

import co.zd.wb.service.InstanceLoaderFault;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;

public abstract class BaseDomBuilder implements DomBuilder
{
	protected BaseDomBuilder()
	{
		this.setXPath(XPathFactory.newInstance().newXPath());
	}
	
	// <editor-fold desc="Element" defaultstate="collapsed">

	private Element m_element = null;
	private boolean m_element_set = false;

	public Element getElement() {
		if(!m_element_set) {
			throw new IllegalStateException("element not set.  Use the HasElement() method to check its state before accessing it.");
		}
		return m_element;
	}

	public void setElement(
		Element value) {
		if(value == null) {
			throw new IllegalArgumentException("element cannot be null");
		}
		boolean changing = !m_element_set || m_element != value;
		if(changing) {
			m_element_set = true;
			m_element = value;
		}
	}

	protected void clearElement() {
		if(m_element_set) {
			m_element_set = true;
			m_element = null;
		}
	}

	private boolean hasElement() {
		return m_element_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="XPath" defaultstate="collapsed">

	private XPath m_xPath = null;
	private boolean m_xPath_set = false;

	private XPath getXPath() {
		if(!m_xPath_set) {
			throw new IllegalStateException("xPath not set.  Use the HasXPath() method to check its state before accessing it.");
		}
		return m_xPath;
	}

	private void setXPath(
		XPath value) {
		if(value == null) {
			throw new IllegalArgumentException("xPath cannot be null");
		}
		boolean changing = !m_xPath_set || m_xPath != value;
		if(changing) {
			m_xPath_set = true;
			m_xPath = value;
		}
	}

	private void clearXPath() {
		if(m_xPath_set) {
			m_xPath_set = true;
			m_xPath = null;
		}
	}

	private boolean hasXPath() {
		return m_xPath_set;
	}

	// </editor-fold>
	
	@Override public void reset()
	{
		this.clearElement();
	}
	
	protected String getString(String xpath)
	{
		if (xpath == null) { throw new IllegalArgumentException("xpath"); }
		if ("".equals(xpath)) { throw new IllegalArgumentException("xpath"); }
		
		String result = null;
		
		try
		{
			result = (String)this.getXPath().compile(xpath).evaluate(this.getElement());
		}
		catch (XPathExpressionException e)
		{
			throw new InstanceLoaderFault(e);
		}
		
		return result;
	}
	
	protected int getInteger(String xpath)
	{
		String raw = this.getString(xpath);
		
		return Integer.parseInt(raw);
	}
}