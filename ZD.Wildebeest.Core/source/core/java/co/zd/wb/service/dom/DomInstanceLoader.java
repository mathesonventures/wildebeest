// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

import co.zd.wb.Instance;
import co.zd.wb.service.InstanceBuilder;
import co.zd.wb.service.InstanceLoader;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.ResourceLoaderFault;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * An {@link InstanceBuilder} deserializes {@link Instance} descriptors from XML.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class DomInstanceLoader implements InstanceLoader
{
	private static String ELT_INSTANCE = "instance";
		private static String ATT_INSTANCE_TYPE = "type";
	private static String ELT_HOST_NAME = "hostName";
	private static String ELT_PORT = "port";
	private static String ELT_ADMIN_USERNAME = "adminUsername";
	private static String ELT_ADMIN_PASSWORD = "adminPassword";
	private static String ELT_SCHEMA_NAME = "schemaName";
	
	/**
	 * Creates a new DomInstanceLoader.
	 * 
	 * @param       instanceBuilders            the set of available {@link InstanceBuilder}s.
	 * @param       instanceXml                 the XML representation of the {@link Instance} to be loaded.
	 * @since                                   1.0
	 */
	public DomInstanceLoader(
		Map<String, InstanceBuilder> instanceBuilders,
		String instanceXml)
	{
		this.setInstanceBuilders(instanceBuilders);
		this.setInstanceXml(instanceXml);
	}

	// <editor-fold desc="InstanceBuilders" defaultstate="collapsed">

	private Map<String, InstanceBuilder> m_instanceBuilders = null;
	private boolean m_instanceBuilders_set = false;

	private Map<String, InstanceBuilder> getInstanceBuilders() {
		if(!m_instanceBuilders_set) {
			throw new IllegalStateException("instanceBuilders not set.  Use the HasInstanceBuilders() method to check its state before accessing it.");
		}
		return m_instanceBuilders;
	}

	private void setInstanceBuilders(Map<String, InstanceBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceBuilders cannot be null");
		}
		boolean changing = !m_instanceBuilders_set || m_instanceBuilders != value;
		if(changing) {
			m_instanceBuilders_set = true;
			m_instanceBuilders = value;
		}
	}

	private void clearInstanceBuilders() {
		if(m_instanceBuilders_set) {
			m_instanceBuilders_set = true;
			m_instanceBuilders = null;
		}
	}

	private boolean hasInstanceBuilders() {
		return m_instanceBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="InstanceXml" defaultstate="collapsed">

	private String m_instanceXml = null;
	private boolean m_instanceXml_set = false;

	private String getInstanceXml() {
		if(!m_instanceXml_set) {
			throw new IllegalStateException("instanceXml not set.  Use the HasInstanceXml() method to check its state before accessing it.");
		}
		return m_instanceXml;
	}

	private void setInstanceXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("instanceXml cannot be null");
		}
		boolean changing = !m_instanceXml_set || m_instanceXml != value;
		if(changing) {
			m_instanceXml_set = true;
			m_instanceXml = value;
		}
	}

	private void clearInstanceXml() {
		if(m_instanceXml_set) {
			m_instanceXml_set = true;
			m_instanceXml = null;
		}
	}

	private boolean hasInstanceXml() {
		return m_instanceXml_set;
	}

	// </editor-fold>

	@Override public Instance load() throws MessagesException
	{
		InputSource inputSource = new InputSource(new StringReader(this.getInstanceXml()));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new ResourceLoaderFault(e);
		}
		
		Document resourceXd = null;
		try
		{
			resourceXd = db.parse(inputSource);
		}
		catch (Exception e)
		{
			throw new ResourceLoaderFault(e);
		}
		
		Element instanceXe = resourceXd.getDocumentElement();
		Instance instance = null;

		if (ELT_INSTANCE.equals(instanceXe.getTagName()))
		{
			instance = buildInstance(
				this.getInstanceBuilders(),
				instanceXe);
		}
		
		return instance;
	}
	
	private static Instance buildInstance(
		Map<String, InstanceBuilder> instanceBuilders,
		Element instanceXe) throws MessagesException
	{
		if (instanceBuilders == null) { throw new IllegalArgumentException("instanceBuilders"); }
		if (instanceXe == null) { throw new IllegalArgumentException("instanceXe"); }
		
		String type = instanceXe.getAttribute(ATT_INSTANCE_TYPE);
		
		InstanceBuilder builder = instanceBuilders.get(type);
			
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"instance builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(instanceXe);
		return builder.build();
	}
}