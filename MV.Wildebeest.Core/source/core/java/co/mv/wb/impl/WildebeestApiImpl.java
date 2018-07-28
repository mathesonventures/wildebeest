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

package co.mv.wb.impl;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.AssertionFaultException;
import co.mv.wb.AssertionResponse;
import co.mv.wb.AssertionResult;
import co.mv.wb.AssertionType;
import co.mv.wb.EntityType;
import co.mv.wb.FileLoadException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.Migration;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.MigrationType;
import co.mv.wb.MigrationTypeInfo;
import co.mv.wb.PluginBuildException;
import co.mv.wb.PluginGroup;
import co.mv.wb.Resource;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.ResourceType;
import co.mv.wb.State;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.XmlValidationException;
import co.mv.wb.event.EventSink;
import co.mv.wb.event.Events;
import co.mv.wb.framework.ArgumentException;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.Util;
import co.mv.wb.plugin.base.ImmutableAssertionResult;
import co.mv.wb.plugin.base.dom.DomInstanceLoader;
import co.mv.wb.plugin.base.dom.DomPlugins;
import co.mv.wb.plugin.base.dom.DomResourceLoader;
import org.reflections.Reflections;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Provides a generic interface that can be adapted to different environments.  For example the WildebeestCommand
 * command-line interface delegates to WildebeestApiImpl to drive commands.
 *
 * @since 1.0
 */
public class WildebeestApiImpl implements WildebeestApi
{
	private static final String RESOURCE_XSD = "resource.xsd";
	private static final String INSTANCE_XSD = "instance.xsd";

	private final EventSink eventSink;

	private List<PluginGroup> pluginGroups;
	private Map<ResourceType, ResourcePlugin> resourcePlugins;
	private Map<String, MigrationPlugin> migrationPlugins;


	/**
	 * Creates a new WildebeestApiImpl using the supplied {@link EventSink} for user output and the supplied
	 * ResourceHelper.
	 *
	 * @param eventSink the event sink that should be used to output all events to the user.
	 * @since 1.0
	 */
	public WildebeestApiImpl(EventSink eventSink)
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");

		this.eventSink = eventSink;
		this.pluginGroups = null;
		this.resourcePlugins = null;
		this.migrationPlugins = null;
	}

	private List<PluginGroup> getPluginGroups()
	{
		if (this.pluginGroups == null)
		{
			throw new IllegalStateException("pluginGroups not set");
		}

		return this.pluginGroups;
	}

	public void setPluginGroups(List<PluginGroup> pluginGroups)
	{
		if (pluginGroups == null) throw new ArgumentNullException("pluginGroups");

		this.pluginGroups = pluginGroups;
	}

	private Map<ResourceType, ResourcePlugin> getResourcePlugins()
	{
		if (this.resourcePlugins == null)
		{
			throw new IllegalStateException("resourcePlugins not set");
		}

		return this.resourcePlugins;
	}

	public void setResourcePlugins(Map<ResourceType, ResourcePlugin> resourcePlugins)
	{
		if (resourcePlugins == null) throw new ArgumentNullException("resourcePlugins");

		this.resourcePlugins = resourcePlugins;
	}

	private Map<String, MigrationPlugin> getMigrationPlugins()
	{
		if (this.migrationPlugins == null)
		{
			throw new IllegalStateException("migrationPlugins not set");
		}

		return this.migrationPlugins;
	}

	public void setMigrationPlugins(Map<String, MigrationPlugin> migrationPlugins)
	{
		if (migrationPlugins == null) throw new ArgumentNullException("migrationPlugins");

		this.migrationPlugins = migrationPlugins;
	}

	public Resource loadResource(
		File resourceFile) throws
		FileLoadException,
		LoaderFault,
		PluginBuildException,
		XmlValidationException,
		InvalidReferenceException
	{
		if (resourceFile == null) throw new ArgumentNullException("resourceFile");

		// Get the absolute file for this resource - this ensures that getParentFile works correctly
		resourceFile = resourceFile.getAbsoluteFile();

		// Load Resource
		String resourceXml;
		try
		{
			resourceXml = readAllText(resourceFile);
		}
		catch (IOException ex)
		{
			throw new FileLoadException(resourceFile);
		}

		Resource resource = null;

		if (resourceXml != null)
		{
			WildebeestApiImpl.validateResourceXml(resourceXml);
			DomResourceLoader resourceLoader = DomPlugins.resourceLoader(
				ResourceTypeServiceBuilder
					.create()
					.withFactoryResourceTypes()
					.build(),
				resourceXml);

			resource = resourceLoader.load(resourceFile.getParentFile());
		}

		return resource;
	}

	/**
	 * Validates the supplied XML string as a resource definition.
	 *
	 * @param resourceXml the XML document to validate as a resource.
	 * @throws XmlValidationException if the supplied XML document is not a valid resource according to the
	 *                                XML schema.
	 * @since 4.0
	 */
	public static void validateResourceXml(
		String resourceXml) throws
		XmlValidationException
	{
		if (resourceXml == null) throw new ArgumentNullException("resourceXml");

		WildebeestApiImpl.validateXml(resourceXml, WildebeestApiImpl.RESOURCE_XSD);
	}

	public Instance loadInstance(
		File instanceFile) throws
		FileLoadException,
		LoaderFault,
		PluginBuildException,
		XmlValidationException
	{
		if (instanceFile == null) throw new ArgumentNullException("instanceFile");

		// Load Instance
		String instanceXml;
		try
		{
			instanceXml = readAllText(instanceFile);
		}
		catch (IOException ex)
		{
			throw new FileLoadException(instanceFile);
		}

		Instance instance = null;

		if (instanceXml != null)
		{
			validateInstanceXml(instanceXml);
			DomInstanceLoader instanceLoader = new DomInstanceLoader(
				DomPlugins.instanceBuilders(),
				instanceXml);
			instance = instanceLoader.load();
		}

		return instance;
	}

	/**
	 * Validates the supplied XML string as an instance definition.
	 *
	 * @param instanceXml the XML document to validate as an instance.
	 * @throws XmlValidationException if the supplied XML document is not a valid instance according to the
	 *                                XML schema.
	 * @since 4.0
	 */
	public static void validateInstanceXml(
		String instanceXml) throws
		XmlValidationException
	{
		if (instanceXml == null) throw new ArgumentNullException("instanceXml");

		WildebeestApiImpl.validateXml(instanceXml, WildebeestApiImpl.INSTANCE_XSD);
	}

	public void assertStateAndThrowIfFailed(
		Resource resource,
		Instance instance) throws
		AssertionFailedException,
		IndeterminateStateException
	{
		if (instance == null) throw new ArgumentNullException("instance");

		State state = fetchState(resource, instance);

		List<AssertionResult> assertionResults = assertState(resource, instance);

		// If any assertions failed, throw
		if (assertionResults.stream().anyMatch(x -> !x.getResult()))
		{
			throw new AssertionFailedException(state.getStateId(), assertionResults);
		}
	}

	private State fetchState(
		Resource resource,
		Instance instance) throws IndeterminateStateException
	{
		ResourcePlugin resourcePlugin = this.getResourcePlugin(
			resource.getType());

		return resourcePlugin.currentState(
			resource,
			instance);
	}

	public List<AssertionResult> assertState(
		Resource resource,
		Instance instance) throws
		IndeterminateStateException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (instance == null) throw new ArgumentNullException("instance");

		State state = fetchState(resource, instance);

		List<AssertionResult> result = new ArrayList<>();

		for (Assertion assertion : state.getAssertions())
		{
			eventSink.onEvent(Events.assertionStart(
				state,
				assertion));

			try
			{
				AssertionResponse response = assertion.perform(instance);

				if (response.getResult())
				{
					eventSink.onEvent(Events.assertionComplete(
						state,
						assertion));
				}
				else
				{
					eventSink.onEvent(Events.assertionFailed(
						state,
						assertion,
						response.getMessage()));
				}

				result.add(new ImmutableAssertionResult(
					assertion.getAssertionId(),
					response.getResult(),
					response.getMessage()));
			}
			catch (Exception e)
			{
				throw new AssertionFaultException(
					assertion.getAssertionId(),
					e);
			}
		}

		return result;
	}

	public void state(
		Resource resource,
		Instance instance) throws
		IndeterminateStateException,
		AssertionFailedException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (instance == null) throw new ArgumentNullException("instance");

		State state = fetchState(resource, instance);

		if (state != null)
		{
			this.assertStateAndThrowIfFailed(
				resource,
				instance);
		}
	}

	public void migrate(
		Resource resource,
		Instance instance,
		String targetState) throws
		AssertionFailedException,
		MigrationFailedException,
		MigrationNotPossibleException,
		IndeterminateStateException,
		TargetNotSpecifiedException,
		UnknownStateSpecifiedException,
		InvalidReferenceException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (instance == null) throw new ArgumentNullException("instance");

		ResourcePlugin resourcePlugin = this.getResourcePlugin(
			resource.getType());

		// Resolve the target state
		String ts = targetState != null
			? targetState
			: resource.getDefaultTarget().orElse(null);

		if (ts == null)
		{
			throw new TargetNotSpecifiedException();
		}

		UUID targetStateId = null;
		try
		{
			targetStateId = ts == null
				? null
				: Wildebeest.findState(resource, ts).getStateId();
		}
		catch (InvalidReferenceException e)
		{
			throw new UnknownStateSpecifiedException(ts);
		}

		// Resolve the current state
		State currentState = resourcePlugin.currentState(
			resource,
			instance);

		UUID currentStateId = currentState == null
			? null
			: currentState.getStateId();

		// Are we already at the target state?
		if ((currentState == null && targetStateId == null) ||
			(currentStateId != null && targetStateId != null && currentStateId.equals(targetStateId)))
		{
			// Nothing to do
		}
		else
		{
			List<List<Migration>> paths = WildebeestApiImpl.findPaths(resource, currentStateId, targetStateId);

			if (paths.size() != 1)
			{
				throw new RuntimeException("multiple possible paths found");
			}

			List<Migration> path = paths.get(0);

			validateMigrationStates(resource);

			if (currentState != null)
			{
				this.assertStateAndThrowIfFailed(
					resource,
					instance);
			}

			for (Migration migration : path)
			{
				String migrationTypeUri = migration.getClass().getAnnotation(MigrationType.class).uri();
				MigrationPlugin migrationPlugin = this.getMigrationPlugin(migrationTypeUri);

				State fromState = migration.getFromState().isPresent()
					? Wildebeest.findState(resource, migration.getFromState().get())
					: null;
				State toState = migration.getToState().isPresent()
					? Wildebeest.findState(resource, migration.getToState().get())
					: null;

				// Migrate to the next state
				eventSink.onEvent(Events.migrationStart(migration));

				try
				{
					migrationPlugin.perform(
						eventSink,
						migration,
						instance);
				}
				catch (Exception ex)
				{
					eventSink.onEvent(Events.migrationFailed(
						migration,
						ex.getMessage()));
					throw ex;
				}

				eventSink.onEvent(Events.migrationComplete(migration));

				// Update the state
				try
				{
					// TODO: This will fail if toState is null (i.e. non-existant).  In this case the state-tracking record should be removed
					resourcePlugin.setStateId(
						eventSink,
						resource,
						instance,
						toState.getStateId());

					// Assert the new state
					this.assertStateAndThrowIfFailed(
						resource,
						instance);
				}
				catch (Exception ex)
				{
					throw ex;
				}
			}
		}
	}

	public void jumpstate(
		Resource resource,
		Instance instance,
		String targetState) throws
		AssertionFailedException,
		IndeterminateStateException,
		UnknownStateSpecifiedException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (instance == null) throw new ArgumentNullException("instance");
		if (targetState != null && "".equals(targetState.trim()))
			throw new ArgumentException("targetState", "targetState cannot be empty");

		ResourcePlugin resourcePlugin = this.getResourcePlugin(
			resource.getType());

		State state;
		try
		{
			state = Wildebeest.findState(resource, targetState);
		}
		catch (InvalidReferenceException e)
		{
			throw new UnknownStateSpecifiedException(targetState);
		}

		eventSink.onEvent(Events.jumpStateStart(state));

		resourcePlugin.setStateId(
			eventSink,
			resource,
			instance,
			state.getStateId()
		);

		// Assert the new state
		this.assertStateAndThrowIfFailed(
			resource,
			instance);

		eventSink.onEvent(Events.jumpStateComplete(state));
	}

	@Override
	public String describePlugins()
	{
		StringBuilder output = new StringBuilder();

		output.append("<manifest>");

		output.append("<groups>");

		this.getPluginGroups()
			.stream()
			.forEach(x -> output
				.append("<group ")
				.append("uri=\"").append(x.getUri()).append("\" ")
				.append("name=\"").append(x.getName()).append("\" ")
				.append("title=\"").append(x.getTitle()).append("\" ")
				.append(" />"));

		output.append("</groups>");

		output.append("<plugins>");

		// Migrations
		for (MigrationTypeInfo info : WildebeestApiImpl.getMigrationTypeInfos())
		{
			WildebeestApiImpl.pluginElement(
				output,
				"migration",
				info.getPluginGroupUri(),
				info.getUri(),
				info.getName(),
				info.getDescription(),
				info.getExample());
		}

		// Assertions
		for (AssertionType info : WildebeestApiImpl.getAssertionTypes())
		{
			WildebeestApiImpl.pluginElement(
				output,
				"assertion",
				info.pluginGroupUri(),
				info.uri(),
				Util.nameFromUri(info.uri()),
				info.description(),
				info.example());
		}

		output.append("</plugins>");

		output.append("</manifest>");

		return output.toString();
	}

	private static void pluginElement(
		StringBuilder output,
		String type,
		String groupUri,
		String uri,
		String name,
		String description,
		String example)
	{
		String exampleEsc = example
			.replaceAll("\\<", "&lt;")
			.replaceAll("\\>", "&gt;")
			.replaceAll("\"", "&quot;")
			.replaceAll("'", "&apos;");

		output
			.append("<plugin type=\"").append(type).append("\">")
			.append("<group>").append(groupUri).append("</group>")
			.append("<uri>").append(uri).append("</uri>")
			.append("<name>").append(name).append("</name>")
			.append("<description>");

		String[] descriptionLines = description.split("\\n");

		for (String descriptionLine : descriptionLines)
		{
			output.append("<line>").append(descriptionLine).append("</line>");
		}

		output
			.append("</description>")
			.append("<example>").append(exampleEsc).append("</example>")
			.append("</plugin>");
	}

	private static UUID stateIdForName(
		Resource resource,
		String name)
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (name == null) throw new ArgumentNullException("name");
		if ("".equals(name)) throw new IllegalArgumentException("name cannot be empty");

		State result = null;

		for (State check : resource.getStates())
		{
			if (check.getName().map(name::equals).orElse(false))
			{
				result = check;
			}
		}

		return result == null ? null : result.getStateId();
	}

	private static UUID getTargetStateId(
		Resource resource,
		String targetState) throws
		InvalidStateSpecifiedException,
		UnknownStateSpecifiedException
	{
		if (resource == null) throw new ArgumentNullException("resource");

		final String stateSpecificationRegex = "[a-zA-Z0-9][a-zA-Z0-9\\-\\_ ]+[a-zA-Z0-9]";
		if (targetState != null && !targetState.matches(stateSpecificationRegex))
		{
			throw new InvalidStateSpecifiedException(targetState);
		}

		UUID targetStateId = null;
		if (targetState != null)
		{
			try
			{
				targetStateId = UUID.fromString(targetState);
			}
			catch (IllegalArgumentException e)
			{
				targetStateId = WildebeestApiImpl.stateIdForName(
					resource,
					targetState);
			}

			// If we still could not find the specified state, then throw
			if (targetStateId == null)
			{
				throw new UnknownStateSpecifiedException(targetState);
			}
		}

		return targetStateId;
	}

	private static String readAllText(File file) throws
		IOException
	{
		if (file == null) throw new ArgumentNullException("file");

		if (!file.isFile())
		{
			throw new IllegalArgumentException(String.format(
				"%s is not a plain file",
				file.getAbsolutePath()));
		}

		String result;

		BufferedReader br = null;
		try
		{
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			result = sb.toString();
		}
		finally
		{
			if (br != null)
			{
				br.close();
			}
		}

		return result;
	}

	/**
	 * Looks up the ResourcePlugin for the supplied ResourceType.
	 *
	 * @param resourceType the ResourceType for which a plugin should be retrieved.
	 * @return the ResourcePlugin that corresponds to the supplied ResourceType.
	 * @since 4.0
	 */
	private ResourcePlugin getResourcePlugin(
		ResourceType resourceType)
	{
		if (resourceType == null) throw new ArgumentNullException("resourceType");

		ResourcePlugin resourcePlugin = this.getResourcePlugins().get(resourceType);

		if (resourcePlugin == null)
		{
			throw new RuntimeException(String.format(
				"resource plugin for resource type %s not found",
				resourceType.getUri()));
		}

		return resourcePlugin;
	}

	/**
	 * Looks up the MigrationPlugin for the supplied MigrationType URI.
	 *
	 * @param uri the URI identifying the MigrationType of interest.
	 * @return the MigrationPlugin for the supplied MigrationType URI.
	 * @since 4.0
	 */
	private MigrationPlugin getMigrationPlugin(
		String uri)
	{
		if (uri == null) throw new ArgumentNullException("uri");

		if (!this.getMigrationPlugins().containsKey(uri))
		{
			throw new RuntimeException(String.format("no MigrationPlugin found for uri: %s", uri));
		}

		return this.getMigrationPlugins().get(uri);
	}

	public static List<List<Migration>> findPaths(
		Resource resource,
		UUID fromState,
		UUID targetState) throws InvalidReferenceException
	{
		if (resource == null) throw new ArgumentNullException("resource");

		return findPaths(resource, fromState, targetState, new ArrayList<>());
	}

	/**
	 * @param resource      the resource that we are looking for a migration path through.
	 * @param fromStateId   the optional starting point for this recursive iteration of the path finding algorithm.  If
	 *                      this is null it indicates we are migrating from the non-existent state.
	 * @param targetStateId the optional target state for the migration.  If this is null it means we're migrating to
	 *                      the non-existent state.
	 * @param currPath      the path that we've built up so far on the current recursive search.
	 * @return
	 */
	private static List<List<Migration>> findPaths(
		Resource resource,
		UUID fromStateId,
		UUID targetStateId,
		List<Migration> currPath) throws InvalidReferenceException
	{
		if (resource == null) throw new ArgumentNullException("resource");
		if (currPath == null) throw new ArgumentNullException("currPath");

		List<List<Migration>> paths = new ArrayList<>();
		List<Migration> possibleMigrations = findPossibleMigrations(fromStateId, resource.getMigrations());

		for (Migration migration : possibleMigrations)
		{
			List<Migration> newPath = new ArrayList<>(currPath);

			// TODO: the toString() approach is not an optimal nor robust way to do this - find a better way
			if (migration.getToState().isPresent() && newPath.toString().contains(migration.getToState().get()))
			{
				// Circular path detected
				break;
			}

			newPath.add(migration);

			if (migration.getToState().isPresent())
			{
				UUID toStateId = Wildebeest.findState(resource, migration.getToState().get()).getStateId();

				// If we've arrived at the target, then this is a complete path
				if (targetStateId != null && targetStateId.equals(toStateId))
				{
					// Register the complete path
					paths.add(newPath);
				}

				// Otherwise recurse to look for more paths
				else
				{
					// Add all complete path found to paths
					paths.addAll(findPaths(
						resource,
						toStateId,
						targetStateId,
						newPath
					));
				}
			}
			else
			{
				if (targetStateId == null)
				{
					// A complete path found
					paths.add(newPath);
				}
			}
		}

		return paths;
	}

	private static List<Migration> findPossibleMigrations(
		UUID fromState,
		List<Migration> migrations)
	{
		return migrations.parallelStream().filter(m ->
		{
			if (fromState != null)
			{
				return m.getFromState().isPresent() && m.getFromState().get().equals(fromState.toString());
			}
			else
			{
				return !m.getFromState().isPresent();
			}
		}).collect(Collectors.toList());
	}

	private static void validateXml(
		String xml,
		String xsdResourceName) throws
		XmlValidationException
	{
		if (xml == null) throw new ArgumentNullException("xml");
		if (xsdResourceName == null) throw new ArgumentNullException("xsdResourceName");

		try
		{
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
			File xsdLocation = new File(WildebeestApiImpl.class.getResource(xsdResourceName).toURI());
			Schema schema = factory.newSchema(xsdLocation);

			Validator validator = schema.newValidator();
			Source source = new StreamSource(new StringReader(xml));
			validator.validate(source);
		}
		catch (URISyntaxException | IOException e)
		{
			throw new RuntimeException(e);
		}
		catch (SAXException e)
		{
			// Validation failed
			throw new XmlValidationException(e.getMessage());
		}
	}

	/**
	 * Retrives all migrations from plugin and throws an error if migration refers to state that does not exist
	 *
	 * @param resource Resource that is used to perform migration .
	 * @since 4.0
	 */
	private static void validateMigrationStates(
		Resource resource) throws MigrationNotPossibleException, InvalidReferenceException
	{
		if (resource == null) throw new ArgumentNullException("resource");

		List<Migration> migrations = resource.getMigrations();
		List<State> states = resource.getStates();

		for (Migration m : migrations)
		{
			// If neither terminals are present, then the migration is invalid because it is declaring that it will
			// migrate from non-existent to non-existent.
			if (!m.getFromState().isPresent() && !m.getToState().isPresent())
			{
				throw new MigrationNotPossibleException();
/*
				TODO: return a fully formed exception for this case with the following message
					String.format(
						"Migration \"%s\" from non-existent to non-existent is invalid",
						m.getMigrationId().toString()));
*/
			}

			// If either of the terminals are not set then they are valid
			boolean fromIsValid = !m.getFromState().isPresent();
			boolean toIsValid = !m.getToState().isPresent();

			// Search the states for those that match the non-empty references on the migration.
			for (State s : states)
			{
				fromIsValid |= s.matchesStateRef(m.getFromState().orElse(null));
				toIsValid |= s.matchesStateRef(m.getToState().orElse(null));

				if (fromIsValid && toIsValid)
				{
					break;
				}
			}

			// If after checking all states in the resource either the from or to references do not match a state, then
			// the migration  definition is invalid as it is referring to an unknown state.  Craft a message specifying
			// either or both of the invalid references
			if (!fromIsValid && !toIsValid)
			{
				throw InvalidReferenceException.twoReferences(
					EntityType.State,
					m.getFromState().get(),
					EntityType.State,
					m.getToState().get(),
					EntityType.Migration,
					m.getMigrationId().toString());
			}
			if (!fromIsValid)
			{
				// Note: no need to check m.getFromState().isPresent() because if its empty then fromIsValid is true
				throw InvalidReferenceException.oneReference(
					EntityType.State,
					m.getFromState().get(),
					EntityType.Migration,
					m.getMigrationId().toString());
			}
			else if (!toIsValid)
			{
				// Note: no need to check m.getToState().isPresent() because if its empty then toIsValid is true
				throw InvalidReferenceException.oneReference(
					EntityType.State,
					m.getToState().get(),
					EntityType.Migration,
					m.getMigrationId().toString());
			}
		}
	}

	private static List<MigrationTypeInfo> getMigrationTypeInfos()
	{
		Reflections reflections = new Reflections(Wildebeest.class.getPackage().getName());

		return reflections
			.getTypesAnnotatedWith(MigrationType.class)
			.stream()
			.map(
				migrationClass ->
				{
					MigrationType migrationType = migrationClass.getAnnotation(MigrationType.class);

					return new MigrationTypeInfo(
						migrationType.pluginGroupUri(),
						migrationType.uri(),
						Util.nameFromUri(migrationType.uri()),
						migrationType.description(),
						migrationType.example(),
						migrationClass);
				})
			.collect(Collectors.toList());
	}

	private static List<AssertionType> getAssertionTypes()
	{
		Reflections reflections = new Reflections(Wildebeest.class.getPackage().getName());

		return reflections
			.getTypesAnnotatedWith(AssertionType.class)
			.stream()
			.map(assertionClass -> assertionClass.getAnnotation(AssertionType.class))
			.collect(Collectors.toList());
	}
}
