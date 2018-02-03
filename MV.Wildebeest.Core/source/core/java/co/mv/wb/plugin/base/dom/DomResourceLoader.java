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

import co.mv.wb.Assertion;
import co.mv.wb.AssertionBuilder;
import co.mv.wb.LoaderFault;
import co.mv.wb.Messages;
import co.mv.wb.Migration;
import co.mv.wb.MigrationBuilder;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginBuildException;
import co.mv.wb.PluginManager;
import co.mv.wb.Resource;
import co.mv.wb.ResourceLoader;
import co.mv.wb.ResourceType;
import co.mv.wb.ResourceTypeService;
import co.mv.wb.State;
import co.mv.wb.plugin.base.ImmutableState;
import co.mv.wb.plugin.base.ResourceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Loads {@link Resource}'s from XML definitions.
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
		private static final String XA_RESOURCE_DEFAULT_TARGET = "defaultTarget";

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
	 * @param       resourceTypeService         the {@link ResourceTypeService} to use to look up resource types.
	 * @param       assertionBuilders           the set of available {@link AssertionBuilder}s.
	 * @param       migrationBuilders           the set of available {@link MigrationBuilder}s.
	 * @param       resourceXml                 the XML representation of the {@link Resource} to be loaded.
	 * @since                                   1.0
	 */
	public DomResourceLoader(
		ResourceTypeService resourceTypeService,
		Map<String, AssertionBuilder> assertionBuilders,
		Map<String, MigrationBuilder> migrationBuilders,
		String resourceXml)
	{
		this.setResourceTypeService(resourceTypeService);
		this.setAssertionBuilders(assertionBuilders);
		this.setMigrationBuilders(migrationBuilders);
		this.setResourceXml(resourceXml);
	}

	// <editor-fold desc="ResourceTypeService" defaultstate="collapsed">

	private ResourceTypeService _resourceTypeService = null;
	private boolean _resourceTypeService_set = false;

	public ResourceTypeService getResourceTypeService() {
		if(!_resourceTypeService_set) {
			throw new IllegalStateException("resourceTypeService not set.");
		}
		if(_resourceTypeService == null) {
			throw new IllegalStateException("resourceTypeService should not be null");
		}
		return _resourceTypeService;
	}

	private void setResourceTypeService(
		ResourceTypeService value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceTypeService cannot be null");
		}
		boolean changing = !_resourceTypeService_set || _resourceTypeService != value;
		if(changing) {
			_resourceTypeService_set = true;
			_resourceTypeService = value;
		}
	}

	private void clearResourceTypeService() {
		if(_resourceTypeService_set) {
			_resourceTypeService_set = true;
			_resourceTypeService = null;
		}
	}

	private boolean hasResourceTypeService() {
		return _resourceTypeService_set;
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

	@Override public Resource load(File baseDir) throws
		LoaderFault,
		PluginBuildException
	{
		if (baseDir == null) { throw new IllegalArgumentException("baseDir cannot be null"); }
		
		InputSource inputSource = new InputSource(new StringReader(this.getResourceXml()));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new LoaderFault(e);
		}
		
		Document resourceXd;
		try
		{
			resourceXd = db.parse(inputSource);
		}
		catch (IOException | SAXException e)
		{
			throw new LoaderFault(e);
		}
		
		Element resourceXe = resourceXd.getDocumentElement();
		Resource resource = null;

		if (XE_RESOURCE.equals(resourceXe.getTagName()))
		{
			UUID id = UUID.fromString(resourceXe.getAttribute(XA_RESOURCE_ID));
			String typeUri = resourceXe.getAttribute(XA_RESOURCE_TYPE);
			ResourceType type = this.getResourceTypeService().forUri(typeUri);
			String name = resourceXe.getAttribute(XA_RESOURCE_NAME);
			Optional<String> defaultTarget = Optional.ofNullable(resourceXe.getAttribute(XA_RESOURCE_DEFAULT_TARGET));
			
			resource = new ResourceImpl(
				id,
				type,
				name,
				defaultTarget);

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
											if (!DomResourceLoader.isApplicable(
												asr.getApplicableTypes(),
												resource.getType()))
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
							if (!DomResourceLoader.isApplicable(
								migration.getApplicableTypes(),
								resource.getType()))
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

	private static boolean isApplicable(List<ResourceType> applicableTypes, ResourceType actualType)
	{
		if (applicableTypes == null) { throw new IllegalArgumentException("applicableTypes cannot be null"); }
		if (actualType == null) { throw new IllegalArgumentException("actualType cannot be null"); }

		return applicableTypes.stream().anyMatch(x -> x.getUri().equals(actualType.getUri()));
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
		int seqNum) throws
		PluginBuildException,
		LoaderFault
	{
		if (assertionBuilders == null) { throw new IllegalArgumentException("assertionBuilders cannot be null"); }
		if (element == null) { throw new IllegalArgumentException("element cannot be null"); }
		
		String type = element.getAttribute(XA_ASSERTION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(XA_ASSERTION_ID));
		String name = element.getAttribute(XA_ASSERTION_NAME);
		
		AssertionBuilder builder = assertionBuilders.get(type);
		
		if (builder == null)
		{
			Messages messages = new Messages();
			messages.addMessage(String.format(
				"assertion builder of type %s not found",
				type));
			throw new PluginBuildException(messages);
		}
		
		builder.reset();
		((DomBuilder)builder).setElement(element);
		return builder.build(id, seqNum);
	}
	
	private static Migration buildMigration(
		Map<String, MigrationBuilder> migrationBuilders,
		Element element,
		File baseDir) throws
			LoaderFault,
			PluginBuildException
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
			Messages messages = new Messages();
			messages.addMessage(String.format(
				"migration builder of type %s not found",
				type));
			throw new PluginBuildException(messages);
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
