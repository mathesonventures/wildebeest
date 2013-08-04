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

import co.zd.wb.model.Assertion;
import co.zd.wb.model.ModelExtensions;
import co.zd.wb.model.Resource;
import co.zd.wb.model.State;
import co.zd.wb.model.Migration;
import co.zd.wb.model.base.ImmutableState;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.ResourceLoader;
import co.zd.wb.service.ResourceLoaderFault;
import co.zd.wb.service.MigrationBuilder;
import java.io.StringReader;
import java.util.Map;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class DomResourceLoader implements ResourceLoader
{
	private static final String ELT_RESOURCE = "resource";
		private static final String ATT_RESOURCE_TYPE = "type";
		private static final String ATT_RESOURCE_ID = "id";
		private static final String ATT_RESOURCE_NAME = "name";
	private static final String ELT_STATES = "states";
	private static final String ELT_STATE = "state";
		private static final String ATT_STATE_ID = "id";
		private static final String ATT_STATE_LABEL = "label";
	private static final String ELT_ASSERTIONS = "assertions";
		private static final String ATT_ASSERTION_TYPE = "type";
		private static final String ATT_ASSERTION_ID = "id";
		private static final String ATT_ASSERTION_NAME = "name";
	private static final String ELT_MIGRATIONS = "migrations";
		private static final String ATT_MIGRATION_TYPE = "type";
		private static final String ATT_MIGRATION_ID = "id";
		private static final String ATT_MIGRATION_FROM_STATE_ID = "fromStateId";
		private static final String ATT_MIGRATION_TO_STATE_ID = "toStateId";
	
	public DomResourceLoader(
		Map<String, ResourceBuilder> resourceBuilders,
		Map<String, AssertionBuilder> assertionBuilders,
		Map<String, MigrationBuilder> migrationBuilders,
		String resourceXml)
	{
		this.setResourceBuilders(resourceBuilders);
		this.setAssertionBuilders(assertionBuilders);
		this.setMigrationBuilders(migrationBuilders);
		this.setResourceXml(resourceXml);
	}

	// <editor-fold desc="ResourceBuilders" defaultstate="collapsed">

	private Map<String, ResourceBuilder> m_resourceBuilders = null;
	private boolean m_resourceBuilders_set = false;

	private Map<String, ResourceBuilder> getResourceBuilders() {
		if(!m_resourceBuilders_set) {
			throw new IllegalStateException("resourceBuilders not set.  Use the HasResourceBuilders() method to check its state before accessing it.");
		}
		return m_resourceBuilders;
	}

	private void setResourceBuilders(Map<String, ResourceBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceBuilders cannot be null");
		}
		boolean changing = !m_resourceBuilders_set || m_resourceBuilders != value;
		if(changing) {
			m_resourceBuilders_set = true;
			m_resourceBuilders = value;
		}
	}

	private void clearResourceBuilders() {
		if(m_resourceBuilders_set) {
			m_resourceBuilders_set = true;
			m_resourceBuilders = null;
		}
	}

	private boolean hasResourceBuilders() {
		return m_resourceBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="AssertionBuilders" defaultstate="collapsed">

	private Map<String, AssertionBuilder> m_assertionBuilders = null;
	private boolean m_assertionBuilders_set = false;

	private Map<String, AssertionBuilder> getAssertionBuilders() {
		if(!m_assertionBuilders_set) {
			throw new IllegalStateException("assertionBuilders not set.  Use the HasAssertionBuilders() method to check its state before accessing it.");
		}
		return m_assertionBuilders;
	}

	private void setAssertionBuilders(Map<String, AssertionBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionBuilders cannot be null");
		}
		boolean changing = !m_assertionBuilders_set || m_assertionBuilders != value;
		if(changing) {
			m_assertionBuilders_set = true;
			m_assertionBuilders = value;
		}
	}

	private void clearAssertionBuilders() {
		if(m_assertionBuilders_set) {
			m_assertionBuilders_set = true;
			m_assertionBuilders = null;
		}
	}

	private boolean hasAssertionBuilders() {
		return m_assertionBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="MigrationBuilders" defaultstate="collapsed">

	private Map<String, MigrationBuilder> m_migrationBuilders = null;
	private boolean m_migrationBuilders_set = false;

	private Map<String, MigrationBuilder> getMigrationBuilders() {
		if(!m_migrationBuilders_set) {
			throw new IllegalStateException("migrationBuilders not set.  Use the HasMigrationBuilders() method to check its state before accessing it.");
		}
		return m_migrationBuilders;
	}

	private void setMigrationBuilders(Map<String, MigrationBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationBuilders cannot be null");
		}
		boolean changing = !m_migrationBuilders_set || m_migrationBuilders != value;
		if(changing) {
			m_migrationBuilders_set = true;
			m_migrationBuilders = value;
		}
	}

	private void clearMigrationBuilders() {
		if(m_migrationBuilders_set) {
			m_migrationBuilders_set = true;
			m_migrationBuilders = null;
		}
	}

	private boolean hasMigrationBuilders() {
		return m_migrationBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourceXml" defaultstate="collapsed">

	private String m_resourceXml = null;
	private boolean m_resourceXml_set = false;

	private String getResourceXml() {
		if(!m_resourceXml_set) {
			throw new IllegalStateException("resourceXml not set.  Use the HasResourceXml() method to check its state before accessing it.");
		}
		return m_resourceXml;
	}

	private void setResourceXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceXml cannot be null");
		}
		boolean changing = !m_resourceXml_set || m_resourceXml != value;
		if(changing) {
			m_resourceXml_set = true;
			m_resourceXml = value;
		}
	}

	private void clearResourceXml() {
		if(m_resourceXml_set) {
			m_resourceXml_set = true;
			m_resourceXml = null;
		}
	}

	private boolean hasResourceXml() {
		return m_resourceXml_set;
	}

	// </editor-fold>

	@Override public Resource load()
	{
		InputSource inputSource = new InputSource(new StringReader(this.getResourceXml()));
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
		
		Element resourceXe = resourceXd.getDocumentElement();
		Resource resource = null;

		if (ELT_RESOURCE.equals(resourceXe.getTagName()))
		{
			resource = buildResource(this.getResourceBuilders(), resourceXe);

			for (int i = 0; i < resourceXe.getChildNodes().getLength(); i ++)
			{
				Element childXe =  ModelExtensions.As(resourceXe.getChildNodes().item(i), Element.class);

				if (childXe != null && ELT_STATES.equals(childXe.getTagName()))
				{
					for (int stateIndex = 0; stateIndex < childXe.getChildNodes().getLength(); stateIndex ++)
					{
						Element stateXe = ModelExtensions.As(childXe.getChildNodes().item(stateIndex), Element.class);
						if (stateXe != null)
						{
							State state = buildState(stateXe);
							resource.getStates().add(state);

							for (int stChildIndex = 0; stChildIndex < stateXe.getChildNodes().getLength(); stChildIndex ++)
							{
								Element stChildXe = ModelExtensions.As(stateXe.getChildNodes().item(stChildIndex),
									Element.class);
								if (stChildXe != null && ELT_ASSERTIONS.equals(stChildXe.getTagName()))
								{
									for (int asrIndex = 0; asrIndex < stChildXe.getChildNodes().getLength(); asrIndex ++)
									{
										Element asrXe = ModelExtensions.As(stChildXe.getChildNodes().item(asrIndex),
											Element.class);
										if (asrXe != null)
										{
											Assertion asr = buildAssertion(this.getAssertionBuilders(), asrXe, asrIndex);
											state.getAssertions().add(asr);
										}
									}
								}
							}
						}
					}
				}
				
				if (childXe != null && ELT_MIGRATIONS.equals(childXe.getTagName()))
				{
					for (int tranIndex = 0; tranIndex < childXe.getChildNodes().getLength(); tranIndex ++)
					{
						Element migrationXe = ModelExtensions.As(childXe.getChildNodes().item(tranIndex),
							Element.class);
						if (migrationXe != null)
						{
							resource.getMigrations().add(buildMigration(this.getMigrationBuilders(), migrationXe));
						}
					}
				}
			}
		}
				
		return resource;
	}
	
	private static Resource buildResource(
		Map<String, ResourceBuilder> resourceBuilders,
		Element resourceXe)
	{
		if (resourceBuilders == null) { throw new IllegalArgumentException("resourceBuilders cannot be null"); }
		if (resourceXe == null) { throw new IllegalArgumentException("resourceXe cannot be null"); }
		
		String type = resourceXe.getAttribute(ATT_RESOURCE_TYPE);
		UUID id = UUID.fromString(resourceXe.getAttribute(ATT_RESOURCE_ID));
		String name = resourceXe.getAttribute(ATT_RESOURCE_NAME);

		ResourceBuilder builder = resourceBuilders.get(type);
		
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"resource builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(resourceXe);
		return builder.build(id, name);
	}
	
	private static State buildState(
		Element element)
	{
		if (element == null) { throw new IllegalArgumentException("element"); }

		UUID id = UUID.fromString(element.getAttribute(ATT_STATE_ID));
		String label = null;
		if (element.hasAttribute(ATT_STATE_LABEL))
		{
			label = element.getAttribute(ATT_STATE_LABEL);
		}
		
		State result = null;
		
		if (label == null)
		{
			result = new ImmutableState(id);
		}
		else
		{
			result = new ImmutableState(id, label);
		}
		
		return result;
	}
	
	private static Assertion buildAssertion(
		Map<String, AssertionBuilder> assertionBuilders,
		Element element,
		int seqNum)
	{
		if (assertionBuilders == null) { throw new IllegalArgumentException("assertionBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		
		String type = element.getAttribute(ATT_ASSERTION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(ATT_ASSERTION_ID));
		String name = element.getAttribute(ATT_ASSERTION_NAME);
		
		AssertionBuilder builder = assertionBuilders.get(type);
		
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"assertion builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(element);
		return builder.build(id, name, seqNum);
	}
	
	private static Migration buildMigration(
		Map<String, MigrationBuilder> migrationBuilders,
		Element element)
	{
		if (migrationBuilders == null) { throw new IllegalArgumentException("migrationBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		
		String type = element.getAttribute(ATT_MIGRATION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(ATT_MIGRATION_ID));
		UUID fromStateId = null;
		if (element.hasAttribute(ATT_MIGRATION_FROM_STATE_ID))
		{
			fromStateId = UUID.fromString(element.getAttribute(ATT_MIGRATION_FROM_STATE_ID));
		}
		UUID toStateId = null;
		if (element.hasAttribute(ATT_MIGRATION_TO_STATE_ID))
		{
			toStateId = UUID.fromString(element.getAttribute(ATT_MIGRATION_TO_STATE_ID));
		}
		
		MigrationBuilder builder = migrationBuilders.get(type);
		
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"migration builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(element);
		return builder.build(id, fromStateId, toStateId);
	}
}