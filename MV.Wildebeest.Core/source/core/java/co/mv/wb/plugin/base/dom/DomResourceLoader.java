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
import co.mv.wb.EntityType;
import co.mv.wb.LoaderFault;
import co.mv.wb.Messages;
import co.mv.wb.Migration;
import co.mv.wb.MigrationBuilder;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.ResourceLoader;
import co.mv.wb.ResourceType;
import co.mv.wb.ResourceTypeService;
import co.mv.wb.State;
import co.mv.wb.framework.ArgumentNullException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Loads {@link Resource}'s from XML definitions.
 *
 * @since 1.0
 */
public class DomResourceLoader implements ResourceLoader
{
	private static final String XE_RESOURCE = "resource";
	private static final String XA_RESOURCE_TYPE = "type";
	private static final String XA_RESOURCE_ID = "id";
	private static final String XA_RESOURCE_NAME = "name";
	private static final String XA_RESOURCE_DEFAULT_TARGET = "defaultTarget";

	private static final String XE_ASSERTION_GROUPS = "assertionGroups";
	private static final String XA_ASSERTION_GROUP_NAME = "name";
	private static final String XA_ASSERTION_GROUP_ID = "id";

	private static final String XE_STATES = "states";

	private static final String XE_STATE = "state";
	private static final String XA_STATE_ID = "id";
	private static final String XA_STATE_LABEL = "label";
	private static final String XA_STATE_DESCRIPTION = "description";

	private static final String XE_ASSERTIONS = "assertions";
	private static final String XA_ASSERTION_TYPE = "type";
	private static final String XA_ASSERTION_ID = "id";
	private static final String XA_ASSERTION_NAME = "name";

	private static final String XE_ASSERTION_REF = "assertionRef";
	private static final String XA_ASSERTION_REF_TYPE = "type";
	private static final String XA_ASSERTION_REF_TYPE_SINGLE = "single";
	private static final String XA_ASSERTION_REF_TYPE_SELECTOR = "selector";
	private static final String XA_ASSERTION_REF_TYPE_GROUP = "group";

	private static final String XE_MIGRATIONS = "migrations";
	private static final String XA_MIGRATION_TYPE = "type";
	private static final String XA_MIGRATION_ID = "id";
	private static final String XA_MIGRATION_FROM_STATE = "fromState";
	private static final String XA_MIGRATION_TO_STATE = "toState";

	private final ResourceTypeService resourceTypeService;
	private final Map<String, AssertionBuilder> assertionBuilders;
	private final Map<String, MigrationBuilder> migrationBuilders;
	private final String resourceXml;

	/**
	 * Creates a new DomResourceBuilder.
	 *
	 * @param resourceTypeService the {@link ResourceTypeService} to use to look up resource types.
	 * @param assertionBuilders   the set of available {@link AssertionBuilder}s.
	 * @param migrationBuilders   the set of available {@link MigrationBuilder}s.
	 * @param resourceXml         the XML representation of the {@link Resource} to be loaded.
	 * @since 1.0
	 */
	public DomResourceLoader(
		ResourceTypeService resourceTypeService,
		Map<String, AssertionBuilder> assertionBuilders,
		Map<String, MigrationBuilder> migrationBuilders,
		String resourceXml)
	{
		if (resourceTypeService == null) throw new ArgumentNullException("resourceTypeService");
		if (assertionBuilders == null) throw new ArgumentNullException("assertionBuilders");
		if (migrationBuilders == null) throw new ArgumentNullException("migrationBuilders");
		if (resourceXml == null) throw new ArgumentNullException("resourceXml");

		this.resourceTypeService = resourceTypeService;
		this.assertionBuilders = assertionBuilders;
		this.migrationBuilders = migrationBuilders;
		this.resourceXml = resourceXml;
	}

	@Override
	public Resource load(File baseDir) throws
		LoaderFault,
		PluginBuildException,
		InvalidReferenceException
	{
		if (baseDir == null) throw new ArgumentNullException("baseDir");

		InputSource inputSource = new InputSource(new StringReader(this.resourceXml));
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
			ResourceType type = this.resourceTypeService.forUri(typeUri);
			String name = resourceXe.getAttribute(XA_RESOURCE_NAME);
			Optional<String> defaultTarget = Optional.ofNullable(resourceXe.getAttribute(XA_RESOURCE_DEFAULT_TARGET));

			resource = new ResourceImpl(
				id,
				type,
				name,
				defaultTarget);

			HashMap<String, Assertion> assertionsMap = new HashMap<>();
			HashMap<String, List<Assertion>> assertionGroupsMap = new HashMap<>();
			for (int i = 0; i < resourceXe.getChildNodes().getLength(); i++)
			{
				Element childXe = ModelExtensions.As(resourceXe.getChildNodes().item(i), Element.class);
				if (childXe != null && XE_ASSERTIONS.equals(childXe.getTagName()))
				{
					for (int asrsIndex = 0; asrsIndex < childXe.getChildNodes().getLength(); asrsIndex++)
					{
						Element asrsXe = ModelExtensions.As(childXe.getChildNodes().item(asrsIndex), Element.class);

						if (asrsXe != null)
						{
							Assertion asr = buildAssertion(this.assertionBuilders, asrsXe, asrsIndex);
							assertionsMap.put(asrsXe.getAttribute("id"), asr);
							if (!asrsXe.getAttribute("name").trim().isEmpty())
							{
								assertionsMap.put(asrsXe.getAttribute("name"), asr);
							}
						}
					}
				}

				if (childXe != null && XE_ASSERTION_GROUPS.equals(childXe.getTagName()))
				{
					for (int asrGrpIndex = 0; asrGrpIndex < childXe.getChildNodes().getLength(); asrGrpIndex++)
					{
						Element asrGrpXe = ModelExtensions.As(childXe.getChildNodes().item(asrGrpIndex), Element.class);

						if (asrGrpXe != null)
						{
							List<Assertion> assertions = new ArrayList<>();
							for (int asrIndex = 0; asrIndex < asrGrpXe.getChildNodes().getLength(); asrIndex++)
							{
								Element asrXe = ModelExtensions.As(
									asrGrpXe.getChildNodes().item(asrIndex),
									Element.class);

								if (asrXe != null)
								{
									if (asrXe.getTagName().equals(XE_ASSERTION_REF) &&
										asrXe.getAttribute(XA_ASSERTION_REF_TYPE).equals(XA_ASSERTION_REF_TYPE_SINGLE))
									{
										String ref = asrXe.getAttribute("ref");
										assertions.add(findReferredAssertion(
											ref,
											assertionsMap,
											EntityType.AssertionGroup,
											asrGrpXe.getAttribute(XA_ASSERTION_GROUP_ID)
										));
									}
									else
									{
										assertions.add(buildAssertion(
											this.assertionBuilders,
											asrXe,
											asrIndex));
									}
								}
							}

							assertionGroupsMap.put(asrGrpXe.getAttribute(XA_ASSERTION_GROUP_NAME), assertions);
							assertionGroupsMap.put(asrGrpXe.getAttribute(XA_ASSERTION_GROUP_ID), assertions);
						}
					}
				}

				if (childXe != null && XE_STATES.equals(childXe.getTagName()))
				{
					for (int stateIndex = 0; stateIndex < childXe.getChildNodes().getLength(); stateIndex++)
					{
						Element stateXe = ModelExtensions.As(childXe.getChildNodes().item(stateIndex), Element.class);

						if (stateXe != null)
						{
							State state = buildState(stateXe);
							resource.getStates().add(state);

							for (int stChildIndex = 0;
								 stChildIndex < stateXe.getChildNodes().getLength(); stChildIndex++)
							{
								Element stChildXe = ModelExtensions.As(
									stateXe.getChildNodes().item(stChildIndex),
									Element.class);

								if (stChildXe != null && XE_ASSERTIONS.equals(stChildXe.getTagName()))
								{
									for (int asrIndex = 0; asrIndex < stChildXe.getChildNodes().getLength(); asrIndex++)
									{
										Element asrXe = ModelExtensions.As(
											stChildXe.getChildNodes().item(asrIndex),
											Element.class);

										if (asrXe != null)
										{
											List<Assertion> assertions = new ArrayList<>();
											switch (asrXe.getTagName())
											{
												case XE_ASSERTION_REF:
													String ref = asrXe.getAttribute("ref");
													if (asrXe
														.getAttribute(XA_ASSERTION_REF_TYPE)
														.equals(XA_ASSERTION_REF_TYPE_SINGLE))
													{
														assertions.add(findReferredAssertion(
															ref,
															assertionsMap,
															EntityType.State,
															state.getStateId().toString()
														));
													}
													else
													{
														assertions.addAll(findReferredAssertionGroup(
															ref,
															assertionGroupsMap,
															EntityType.State,
															state.getStateId().toString()
														));
													}

													break;
												default:
													assertions.add(buildAssertion(
														this.assertionBuilders,
														asrXe,
														asrIndex));
													break;
											}

											for (Assertion assertion : assertions)
											{
												verifyAssertionIsApplicable(resource, assertion);
												state.getAssertions().add(assertion);
											}
										}
									}
								}
							}
						}
					}
				}

				if (childXe != null && XE_MIGRATIONS.equals(childXe.getTagName()))
				{
					for (int tranIndex = 0; tranIndex < childXe.getChildNodes().getLength(); tranIndex++)
					{
						Element migrationXe = ModelExtensions.As(
							childXe.getChildNodes().item(tranIndex),
							Element.class);

						if (migrationXe != null)
						{
							Migration migration = buildMigration(
								this.migrationBuilders,
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

	private List<Assertion> findReferredAssertionGroup(
		String ref,
		HashMap<String,List<Assertion>> assertionGroupsMap,
		EntityType referrerEntityType,
		String referrerId) throws
		InvalidReferenceException
	{
		List<Assertion> groupAssertions = assertionGroupsMap.get(ref);
		if (groupAssertions == null)
		{
			throw InvalidReferenceException.oneReference(
				EntityType.AssertionGroup,
				ref,
				referrerEntityType,
				referrerId
			);
		}
		return groupAssertions;
	}

	private Assertion findReferredAssertion(
		String ref,
		HashMap<String, Assertion> assertionsMap,
		EntityType referrerEntityType,
		String referrerId) throws
		InvalidReferenceException
	{
		Assertion asr = assertionsMap.get(ref);
		if (asr == null)
		{
			throw InvalidReferenceException.oneReference(
				EntityType.Assertion,
				ref,
				referrerEntityType,
				referrerId
			);
		}
		return asr;
	}

	private void verifyAssertionIsApplicable(
		Resource resource,
		Assertion assertion)
	{
		// Verify that this assertion can be used with the Resource.
		if (!DomResourceLoader.isApplicable(
			assertion.getApplicableTypes(),
			resource.getType()))
		{
			Messages messages = new Messages();
			messages.addMessage(
				"%s assertions cannot be applied to %s resources",
				assertion.getClass().getName(),
				resource.getClass().getName());
		}
	}

	private static boolean isApplicable(
		List<ResourceType> applicableTypes,
		ResourceType actualType)
	{
		if (applicableTypes == null) throw new ArgumentNullException("applicableTypes");
		if (actualType == null) throw new ArgumentNullException("actualType");

		return applicableTypes.stream().anyMatch(x -> x.getUri().equals(actualType.getUri()));
	}

	private static State buildState(
		Element element)
	{
		if (element == null) throw new ArgumentNullException("element");

		final State result;
		final UUID id = UUID.fromString(element.getAttribute(XA_STATE_ID));
		String label = null;
		String description = null;
		int condition = 0;

		// TODO: Optimize this logic - we can resolve the optionality of label and description at the time of parsing, and then always call the three-arg constructor,

		if (element.hasAttribute(XA_STATE_LABEL))
		{
			label = element.getAttribute(XA_STATE_LABEL);
			condition++;
		}

		if (element.hasAttribute(XA_STATE_DESCRIPTION))
		{
			description = element.getAttribute(XA_STATE_DESCRIPTION);
			condition++;
		}

		switch (condition)
		{
			case 1:
				result = new ImmutableState(id, Optional.of(label));
				break;
			case 2:
				result = new ImmutableState(id, Optional.of(label), Optional.of(description));
				break;
			default:
				result = new ImmutableState(id);
		}

		return result;
	}

	private static Assertion buildAssertion(
		Map<String, AssertionBuilder> assertionBuilders,
		Element element,
		int seqNum) throws
		PluginBuildException,
		LoaderFault
	{
		if (assertionBuilders == null) throw new ArgumentNullException("assertionBuilders");
		if (element == null) throw new ArgumentNullException("element");

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
		if (migrationBuilders == null) throw new ArgumentNullException("migrationBuilders");
		if (element == null) throw new ArgumentNullException("element");
		if (baseDir == null) throw new ArgumentNullException("baseDir");

		String type = element.getAttribute(XA_MIGRATION_TYPE);
		UUID id = UUID.fromString(element.getAttribute(XA_MIGRATION_ID));
		Optional<String> fromState = element.hasAttribute(XA_MIGRATION_FROM_STATE)
			? Optional.of(element.getAttribute(XA_MIGRATION_FROM_STATE))
			: Optional.empty();

		Optional<String> toState = element.hasAttribute(XA_MIGRATION_TO_STATE)
			? Optional.of(element.getAttribute(XA_MIGRATION_TO_STATE))
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
			fromState,
			toState,
			baseDir);
	}
}
