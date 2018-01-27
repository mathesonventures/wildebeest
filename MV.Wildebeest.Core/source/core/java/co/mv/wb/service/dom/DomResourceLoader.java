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

package co.mv.wb.service.dom;

import co.mv.wb.Assertion;
import co.mv.wb.Migration;
import co.mv.wb.ModelExtensions;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.State;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import co.mv.wb.service.AssertionBuilder;
import co.mv.wb.service.Messages;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.MigrationBuilder;
import co.mv.wb.service.ResourceLoader;
import co.mv.wb.service.ResourceLoaderFault;
import co.mv.wb.service.ResourcePluginBuilder;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * An {@link ResourcePluginBuilder} deserializes {@link Resource} descriptors from XML.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class DomResourceLoader implements ResourceLoader
{
	private static final String XE_RESOURCE = "resource";
		private static final String XA_RESOURCE_TYPE = "type";
		private static final String XA_RESOURCE_ID = "id";
		private static final String XA_RESOURCE_NAME = "name";

	private static final String XE_STATES = "states";
	
	private static final String XE_STATE = "state";
		private static final String XA_STATE_ID = "id";
		private static final String XA_STATE_LABEL = "label";
		
	private static final String XE_ASSERTIONS = "assertions";
		private static final String XA_ASSERTION_TYPE = "type";
		private static final String XA_ASSERTION_ID = "id";
		private static final String XA_ASSERTION_NAME = "name";
		
	private static final String XE_MIGRATIONS = "migrations";
		private static final String XA_MIGRATION_TYPE = "type";
		private static final String XA_MIGRATION_ID = "id";
		private static final String XA_MIGRATION_FROM_STATE_ID = "fromStateId";
		private static final String XA_MIGRATION_TO_STATE_ID = "toStateId";
	
	/**
	 * Creates a new DomResourceBuilder.
	 * 
	 * @param       resourceBuilders            the set of available {@link ResourcePluginBuilder}s.
	 * @param       assertionBuilders           the set of available {@link AssertionBuilder}s.
	 * @param       migrationBuilders           the set of available {@link MigrationBuilder}s.
	 * @param       resourceXml                 the XML representation of the {@link Resource} to be loaded.
	 * @since                                   1.0
	 */
	public DomResourceLoader(
		Map<String, ResourcePluginBuilder> resourceBuilders,
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

	private Map<String, ResourcePluginBuilder> _resourceBuilders = null;
	private boolean _resourceBuilders_set = false;

	private Map<String, ResourcePluginBuilder> getResourceBuilders() {
		if(!_resourceBuilders_set) {
			throw new IllegalStateException("resourceBuilders not set.  Use the HasResourceBuilders() method to check its state before accessing it.");
		}
		return _resourceBuilders;
	}

	private void setResourceBuilders(Map<String, ResourcePluginBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceBuilders cannot be null");
		}
		boolean changing = !_resourceBuilders_set || _resourceBuilders != value;
		if(changing) {
			_resourceBuilders_set = true;
			_resourceBuilders = value;
		}
	}

	private void clearResourceBuilders() {
		if(_resourceBuilders_set) {
			_resourceBuilders_set = true;
			_resourceBuilders = null;
		}
	}

	private boolean hasResourceBuilders() {
		return _resourceBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="AssertionBuilders" defaultstate="collapsed">

	private Map<String, AssertionBuilder> _assertionBuilders = null;
	private boolean _assertionBuilders_set = false;

	private Map<String, AssertionBuilder> getAssertionBuilders() {
		if(!_assertionBuilders_set) {
			throw new IllegalStateException("assertionBuilders not set.  Use the HasAssertionBuilders() method to check its state before accessing it.");
		}
		return _assertionBuilders;
	}

	private void setAssertionBuilders(Map<String, AssertionBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("assertionBuilders cannot be null");
		}
		boolean changing = !_assertionBuilders_set || _assertionBuilders != value;
		if(changing) {
			_assertionBuilders_set = true;
			_assertionBuilders = value;
		}
	}

	private void clearAssertionBuilders() {
		if(_assertionBuilders_set) {
			_assertionBuilders_set = true;
			_assertionBuilders = null;
		}
	}

	private boolean hasAssertionBuilders() {
		return _assertionBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="MigrationBuilders" defaultstate="collapsed">

	private Map<String, MigrationBuilder> _migrationBuilders = null;
	private boolean _migrationBuilders_set = false;

	private Map<String, MigrationBuilder> getMigrationBuilders() {
		if(!_migrationBuilders_set) {
			throw new IllegalStateException("migrationBuilders not set.  Use the HasMigrationBuilders() method to check its state before accessing it.");
		}
		return _migrationBuilders;
	}

	private void setMigrationBuilders(Map<String, MigrationBuilder> value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationBuilders cannot be null");
		}
		boolean changing = !_migrationBuilders_set || _migrationBuilders != value;
		if(changing) {
			_migrationBuilders_set = true;
			_migrationBuilders = value;
		}
	}

	private void clearMigrationBuilders() {
		if(_migrationBuilders_set) {
			_migrationBuilders_set = true;
			_migrationBuilders = null;
		}
	}

	private boolean hasMigrationBuilders() {
		return _migrationBuilders_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourceXml" defaultstate="collapsed">

	private String _resourceXml = null;
	private boolean _resourceXml_set = false;

	private String getResourceXml() {
		if(!_resourceXml_set) {
			throw new IllegalStateException("resourceXml not set.  Use the HasResourceXml() method to check its state before accessing it.");
		}
		return _resourceXml;
	}

	private void setResourceXml(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceXml cannot be null");
		}
		boolean changing = !_resourceXml_set || !_resourceXml.equals(value);
		if(changing) {
			_resourceXml_set = true;
			_resourceXml = value;
		}
	}

	private void clearResourceXml() {
		if(_resourceXml_set) {
			_resourceXml_set = true;
			_resourceXml = null;
		}
	}

	private boolean hasResourceXml() {
		return _resourceXml_set;
	}

	// </editor-fold>

	@Override public Resource load(File baseDir) throws MessagesException
	{
		if (baseDir == null) { throw new IllegalArgumentException("baseDir cannot be null"); }
		
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
		catch (IOException | SAXException e)
		{
			throw new ResourceLoaderFault(e);
		}
		
		Element resourceXe = resourceXd.getDocumentElement();
		ResourcePlugin resourcePlugin;
		Resource resource = null;

		if (XE_RESOURCE.equals(resourceXe.getTagName()))
		{
			resourcePlugin = buildResourcePlugin(
				this.getResourceBuilders(),
				resourceXe);
			
			UUID id = UUID.fromString(resourceXe.getAttribute(XA_RESOURCE_ID));
			String name = resourceXe.getAttribute(XA_RESOURCE_NAME);
			
			resource = new ResourceImpl(
				id,
				name,
				resourcePlugin);

			for (int i = 0; i < resourceXe.getChildNodes().getLength(); i ++)
			{
				Element childXe =  ModelExtensions.As(resourceXe.getChildNodes().item(i), Element.class);

				if (childXe != null && XE_STATES.equals(childXe.getTagName()))
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
								if (stChildXe != null && XE_ASSERTIONS.equals(stChildXe.getTagName()))
								{
									for (int asrIndex = 0; asrIndex < stChildXe.getChildNodes().getLength(); asrIndex ++)
									{
										Element asrXe = ModelExtensions.As(stChildXe.getChildNodes().item(asrIndex),
											Element.class);
										if (asrXe != null)
										{
											Assertion asr = buildAssertion(
												this.getAssertionBuilders(),
												asrXe,
												asrIndex);
											
											// Verify that this assertion can be used with the Resource.
											if (!asr.canPerformOn(resource))
											{
												Messages messages = new Messages();
												messages.addMessage(
													"%s assertions cannot be applied to %s resources",
													asr.getClass().getName(),
													resource.getClass().getName());
											}

											state.getAssertions().add(asr);
										}
									}
								}
							}
						}
					}
				}
				
				if (childXe != null && XE_MIGRATIONS.equals(childXe.getTagName()))
				{
					for (int tranIndex = 0; tranIndex < childXe.getChildNodes().getLength(); tranIndex ++)
					{
						Element migrationXe = ModelExtensions.As(childXe.getChildNodes().item(tranIndex),
							Element.class);
						if (migrationXe != null)
						{
							Migration migration = buildMigration(
								this.getMigrationBuilders(),
								migrationXe,
								baseDir);
											
							// Verify that this assertion can be used with the Resource.
							if (!migration.canPerformOn(resource))
							{
								Messages messages = new Messages();
								messages.addMessage(
									"%s migrations cannot be applied to %s resources",
									migration.getClass().getName(),
									resource.getClass().getName());
							}
							
							resource.getMigrations().add(migration);
						}
					}
				}
			}
		}
				
		return resource;
	}
	
	private static ResourcePlugin buildResourcePlugin(
		Map<String, ResourcePluginBuilder> resourcePluginBuilders,
		Element resourceXe) throws MessagesException
	{
		if (resourcePluginBuilders == null) { throw new IllegalArgumentException("resourcePluginBuilders cannot be null"); }
		if (resourceXe == null) { throw new IllegalArgumentException("resourceXe cannot be null"); }
		
		String type = resourceXe.getAttribute(XA_RESOURCE_TYPE);

		ResourcePluginBuilder builder = resourcePluginBuilders.get(type);
		
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"resource builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(resourceXe);
		return builder.build();
	}
	
	private static State buildState(
		Element element)
	{
		if (element == null) { throw new IllegalArgumentException("element"); }

		UUID id = UUID.fromString(element.getAttribute(XA_STATE_ID));
		String label = null;
		if (element.hasAttribute(XA_STATE_LABEL))
		{
			label = element.getAttribute(XA_STATE_LABEL);
		}
		
		State result = label == null
			? new ImmutableState(id)
			: new ImmutableState(id, Optional.of(label));
		
		return result;
	}
	
	private static Assertion buildAssertion(
		Map<String, AssertionBuilder> assertionBuilders,
		Element element,
		int seqNum) throws MessagesException
	{
		if (assertionBuilders == null) { throw new IllegalArgumentException("assertionBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		
		String type = element.getAttribute(XA_ASSERTION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(XA_ASSERTION_ID));
		String name = element.getAttribute(XA_ASSERTION_NAME);
		
		AssertionBuilder builder = assertionBuilders.get(type);
		
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"assertion builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(element);
		return builder.build(id, seqNum);
	}
	
	private static Migration buildMigration(
		Map<String, MigrationBuilder> migrationBuilders,
		Element element,
		File baseDir) throws MessagesException
	{
		if (migrationBuilders == null) { throw new IllegalArgumentException("migrationBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		if (baseDir == null) { throw new IllegalArgumentException("baseDir cannot be null"); }
		
		String type = element.getAttribute(XA_MIGRATION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(XA_MIGRATION_ID));
		Optional<UUID> fromStateId = element.hasAttribute(XA_MIGRATION_FROM_STATE_ID)
			? Optional.of(UUID.fromString(element.getAttribute(XA_MIGRATION_FROM_STATE_ID)))
			: Optional.empty();
		
		Optional<UUID> toStateId = element.hasAttribute(XA_MIGRATION_TO_STATE_ID)
			? Optional.of(UUID.fromString(element.getAttribute(XA_MIGRATION_TO_STATE_ID)))
			: Optional.empty();
		
		MigrationBuilder builder = migrationBuilders.get(type);
		
		if (builder == null)
		{
			throw new ResourceLoaderFault(String.format(
				"migration builder of type %s not found",
				type));
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(element);
		return builder.build(
			id,
			fromStateId,
			toStateId,
			baseDir);
	}
}
