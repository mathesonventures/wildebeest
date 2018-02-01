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

import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.service.InstanceLoaderFault;
import co.mv.wb.service.Messages;
import co.mv.wb.service.MessagesException;
import co.mv.wb.service.ResourceLoaderFault;
import co.mv.wb.service.dom.DomInstanceLoader;
import co.mv.wb.service.dom.DomPlugins;
import co.mv.wb.service.dom.DomResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides a generic interface that can be adapted to different environments.  For example the WildebeestCommand
 * command-line interface delegates to Interface to drive commands.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class Interface
{
	/**
	 * Creates a new Interface using the supplied {@link Logger}.
	 * 
	 * @param       logger                      the Logger that this Interface should use.
	 * @since                                   1.0
	 */
	public Interface(
		Logger logger,
		Map<ResourceType, ResourcePlugin> resourcePlugins,
		Map<Class, MigrationPlugin> migrationPlugins,
		ResourceHelper resourceHelper)
	{
		this.setLogger(logger);
		this.setResourcePlugins(resourcePlugins);
		this.setMigrationPlugins(migrationPlugins);
		this.setResourceHelper(resourceHelper);
	}
	
	// <editor-fold desc="Logger" defaultstate="collapsed">

	private Logger _logger = null;
	private boolean _logger_set = false;

	private Logger getLogger() {
		if(!_logger_set) {
			throw new IllegalStateException("logger not set.  Use the HasLogger() method to check its state before accessing it.");
		}
		return _logger;
	}

	private void setLogger(
		Logger value) {
		if(value == null) {
			throw new IllegalArgumentException("logger cannot be null");
		}
		boolean changing = !_logger_set || _logger != value;
		if(changing) {
			_logger_set = true;
			_logger = value;
		}
	}

	private void clearLogger() {
		if(_logger_set) {
			_logger_set = true;
			_logger = null;
		}
	}

	private boolean hasLogger() {
		return _logger_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourcePlugins" defaultstate="collapsed">

	private Map<ResourceType, ResourcePlugin> _resourcePlugins = null;
	private boolean _resourcePlugins_set = false;

	private Map<ResourceType, ResourcePlugin> getResourcePlugins() {
		if(!_resourcePlugins_set) {
			throw new IllegalStateException("resourcePlugins not set.");
		}
		if(_resourcePlugins == null) {
			throw new IllegalStateException("resourcePlugins should not be null");
		}
		return _resourcePlugins;
	}

	private void setResourcePlugins(Map<ResourceType, ResourcePlugin> value) {
		if(value == null) {
			throw new IllegalArgumentException("resourcePlugins cannot be null");
		}
		boolean changing = !_resourcePlugins_set || _resourcePlugins != value;
		if(changing) {
			_resourcePlugins_set = true;
			_resourcePlugins = value;
		}
	}

	private void clearResourcePlugins() {
		if(_resourcePlugins_set) {
			_resourcePlugins_set = true;
			_resourcePlugins = null;
		}
	}

	private boolean hasResourcePlugins() {
		return _resourcePlugins_set;
	}

	// </editor-fold>

	// <editor-fold desc="MigrationPlugins" defaultstate="collapsed">

	private Map<Class, MigrationPlugin> _migrationPlugins = null;
	private boolean _migrationPlugins_set = false;

	private Map<Class, MigrationPlugin> getMigrationPlugins() {
		if(!_migrationPlugins_set) {
			throw new IllegalStateException("migrationPlugins not set.");
		}
		if(_migrationPlugins == null) {
			throw new IllegalStateException("migrationPlugins should not be null");
		}
		return _migrationPlugins;
	}

	private void setMigrationPlugins(Map<Class, MigrationPlugin> value) {
		if(value == null) {
			throw new IllegalArgumentException("migrationPlugins cannot be null");
		}
		boolean changing = !_migrationPlugins_set || _migrationPlugins != value;
		if(changing) {
			_migrationPlugins_set = true;
			_migrationPlugins = value;
		}
	}

	private void clearMigrationPlugins() {
		if(_migrationPlugins_set) {
			_migrationPlugins_set = true;
			_migrationPlugins = null;
		}
	}

	private boolean hasMigrationPlugins() {
		return _migrationPlugins_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourceHelper" defaultstate="collapsed">

	private ResourceHelper _resourceHelper = null;
	private boolean _resourceHelper_set = false;

	private ResourceHelper getResourceHelper() {
		if(!_resourceHelper_set) {
			throw new IllegalStateException("resourceHelper not set.");
		}
		if(_resourceHelper == null) {
			throw new IllegalStateException("resourceHelper should not be null");
		}
		return _resourceHelper;
	}

	private void setResourceHelper(
		ResourceHelper value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceHelper cannot be null");
		}
		boolean changing = !_resourceHelper_set || _resourceHelper != value;
		if(changing) {
			_resourceHelper_set = true;
			_resourceHelper = value;
		}
	}

	private void clearResourceHelper() {
		if(_resourceHelper_set) {
			_resourceHelper_set = true;
			_resourceHelper = null;
		}
	}

	private boolean hasResourceHelper() {
		return _resourceHelper_set;
	}

	// </editor-fold>

	/**
	 * Checks the state of an instance of a resource.
	 * 
	 * @param       resourceFileName            the filename of the descriptor for the resource
	 * @param       instanceFileName            the filename of the descriptor for the instance
	 * @since                                   1.0
	 */
	public void state(
		String resourceFileName,
		String instanceFileName)
	{
		if (resourceFileName == null) { throw new IllegalArgumentException("resourceFileName cannot be null"); }
		if ("".equals(resourceFileName.trim()))
		{
			throw new IllegalArgumentException("resourceFileName cannot be empty");
		}
		if (instanceFileName == null) { throw new IllegalArgumentException("instanceFileName cannot be null"); }
		if ("".equals(instanceFileName.trim()))
		{
			throw new IllegalArgumentException("instanceFileName cannot be empty");
		}

		this.state(
			new File(resourceFileName),
			new File(instanceFileName));
	}
	
	/**
	 * Checks the state of an instance of a resource.
	 * 
	 * @param       resourceFile                the descriptor file for the resource
	 * @param       instanceFile                the descriptor file for the instance
	 * @since                                   1.0
	 */
	public void state(
		File resourceFile,
		File instanceFile)
	{
		if (resourceFile == null) { throw new IllegalArgumentException("resourceFile cannot be null"); }
		if (instanceFile == null) { throw new IllegalArgumentException("instanceFile cannot be null"); }
		
		// Load Resource
		Resource resource = null;
		try
		{
			resource = loadResource(this.getLogger(), resourceFile);
		}
		catch (MessagesException e)
		{
			this.getLogger().logLine("Unable to load the resource.");
			logMessages(this.getLogger(), e.getMessages());
		}

		ResourcePlugin resourcePlugin = PluginHelper.getResourcePlugin(
			this.getResourcePlugins(),
			resource.getType());

		// Load Instance
		Instance instance = null;
		try
		{
			instance = loadInstance(instanceFile);
		}
		catch (MessagesException e)
		{
			this.getLogger().logLine("Unable to load the instance.");
			logMessages(this.getLogger(), e.getMessages());
		}

		if (resource != null && instance != null)
		{
			try
			{
				State state = resourcePlugin.currentState(
					resource,
					instance);

				if (state == null)
				{
					this.getLogger().logLine("Current state: non-existent");
				}
				else
				{
					if (state.getLabel().isPresent())
					{
						this.getLogger().logLine("Current state: " + state.getLabel());
					}
					else
					{
						this.getLogger().logLine("Current state: " + state.getStateId().toString());
					}

					this.getResourceHelper().assertState(
						this.getLogger(),
						resource,
						resourcePlugin,
						instance);
				}
			}
			catch (IndeterminateStateException ex)
			{
				this.getLogger().indeterminateState(ex);
			}
		}
	}

	/**
	 * Migrates an instance of a resource to a particular state.
	 * 
	 * @param       resource                    the resource
	 * @param       instance                    the instance
	 * @param       targetState                 the optional name or unique ID of the state to which the instance should
	 *                                          be migrated.  If none is supplied then Wildebeest will use the default
	 *                                          target if one is set on the REsource.  Otherwise a
	 *                                          NoTargetSpecifiedException is thrown.
	 * @since                                   1.0
	 */
	public void migrate(
		Resource resource,
		Instance instance,
		Optional<String> targetState)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (targetState == null) { throw new IllegalArgumentException("targetState cannot be null"); }

		ResourcePlugin resourcePlugin = PluginHelper.getResourcePlugin(
			this.getResourcePlugins(),
			resource.getType());

		// Resolve the target state
		Optional<String> ts = targetState.isPresent()
			? targetState
			: resource.getDefaultTarget();

		// Perform migration
		try
		{
			if (!ts.isPresent())
			{
				throw new TargetNotSpecifiedException();
			}

            UUID targetStateId = getTargetStateId(
				this.getResourceHelper(),
				resource,
				ts.get());

			this.getResourceHelper().migrate(
				this.getLogger(),
				resource,
				resourcePlugin,
				instance,
				this.getMigrationPlugins(),
				targetStateId);
		}
		catch(TargetNotSpecifiedException e)
		{
			this.getLogger().targetNotSpecified(e);
		}
        catch (InvalidStateSpecifiedException e)
        {
            this.getLogger().invalidStateSpecified(e);
        }
        catch(UnknownStateSpecifiedException e)
        {
            this.getLogger().unknownStateSpecified(e);
        }
		catch (IndeterminateStateException ex)
		{
			this.getLogger().indeterminateState(ex);
		}
		catch (AssertionFailedException ex)
		{
			this.getLogger().assertionFailed(ex);
		}
		catch (MigrationNotPossibleException ex)
		{
			this.getLogger().migrationNotPossible(ex);
		}
		catch (MigrationFailedException ex)
		{
			this.getLogger().migrationFailed(ex);
		}
	}
	
	public void jumpstate(
		Resource resource,
		Instance instance,
		String targetState)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		if (targetState != null && "".equals(targetState.trim()))
		{
			throw new IllegalArgumentException("targetState cannot be empty");
		}

		ResourcePlugin resourcePlugin = PluginHelper.getResourcePlugin(
			this.getResourcePlugins(),
			resource.getType());

		try
		{
            UUID targetStateId = getTargetStateId(
            	this.getResourceHelper(),
            	resource,
				targetState);

			this.getResourceHelper().jumpstate(
				this.getLogger(),
				resource,
				resourcePlugin,
				instance,
				targetStateId);
		}
        catch (InvalidStateSpecifiedException e)
        {
            this.getLogger().invalidStateSpecified(e);
        }
        catch(UnknownStateSpecifiedException e)
        {
            this.getLogger().unknownStateSpecified(e);
        }
		catch (AssertionFailedException e)
		{
			this.getLogger().assertionFailed(e);
		}
		catch (JumpStateFailedException e)
		{
			this.getLogger().jumpStateFailed(e);
		}
	}

	private static UUID getTargetStateId(
		ResourceHelper resourceHelper,
		Resource resource,
		String targetState) throws
            InvalidStateSpecifiedException,
            UnknownStateSpecifiedException
	{
		if (resourceHelper == null) { throw new IllegalArgumentException("resourceHelper cannot be null"); }
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }

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
			catch(IllegalArgumentException e)
			{
				targetStateId = resourceHelper.stateIdForLabel(
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
	
	/**
	 * Attempt to load the specified resource and return it if successful.  If not successful then null is returned.
	 * 
	 * @param       resourceFileName            the descriptor file from which the Resource should be deserialized
	 * @return                                  a deserialized Resource object or null if the load request failed.
	 */
	public Resource tryLoadResource(
		String resourceFileName)
	{
		// Load the resource
		Resource resource = null;
		try
		{
			resource = Interface.loadResource(
				this.getLogger(),
				new File(resourceFileName));
		}
		catch (MessagesException e)
		{
			this.getLogger().logLine("Unable to load the resource.");
			Interface.logMessages(this.getLogger(), e.getMessages());
		}

		return resource;
	}
	
	public Instance tryLoadInstance(
		String instanceFileName)
	{
		// Load the instance
		Instance instance = null;
		try
		{
			instance = Interface.loadInstance(new File(instanceFileName));
		}
		catch (MessagesException e)
		{
			this.getLogger().logLine("Unable to load the instance.");
			Interface.logMessages(this.getLogger(), e.getMessages());
		}

		return instance;
	}
	
	/**
	 * Deserializes a {@link Resource} from the specified descriptor file.
	 * 
	 * @param       logger                      the {@link Logger} instance to use.
	 * @param       resourceFileName            the descriptor file from which the Resource should be deserialized
	 * @return                                  a deserialized Resource object with it's plugin
	 * @throws      MessagesException           containing any validation errors encountered while the Resource is
	 *                                          being deserialized
	 * @since                                   1.0
	 */
	public static Resource loadResource(
		Logger logger,
		String resourceFileName) throws MessagesException
	{
		if (resourceFileName == null) { throw new IllegalArgumentException("resourceFileName"); }

		Resource resource = Interface.loadResource(
			logger,
			new File(resourceFileName));

		return resource;
	}

	/**
	 * Deserializes a {@link Resource} from the specified descriptor file.
	 * 
	 * @param       logger                      the {@link Logger} instance to use.
	 * @param       resourceFile                the descriptor file from which the Resource should be deserialized
	 * @return                                  a deserialized Resource object with it's plugin
	 * @throws      MessagesException           containing any validation errors encountered while the Resource is
	 *                                          being deserialized
	 * @since                                   1.0
	 */
	public static Resource loadResource(
		Logger logger,
		File resourceFile) throws MessagesException
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (resourceFile == null) { throw new IllegalArgumentException("resourceFile cannot be null"); }

		// Get the absolute file for this resource - this ensures that getParentFile works correctly
		resourceFile = resourceFile.getAbsoluteFile();
		
		// Load Resource
		String resourceXml;
		try
		{
			resourceXml = readAllText(resourceFile);
		}
		catch (FileNotFoundException ex)
		{
			throw new ResourceLoaderFault(String.format(
				"Resource file %s does not exist",
				resourceFile.getAbsolutePath()));
		}
		catch (IOException ex)
		{
			throw new ResourceLoaderFault(String.format(
				"There was a problem reading resource file %s",
				resourceFile.getAbsolutePath()));
		}

		Resource resource = null;
		if (resourceXml != null)
		{
			DomResourceLoader resourceLoader = DomPlugins.resourceLoader(
				ResourceTypeServiceBuilder
					.create()
					.withFactoryResourceTypes()
					.build(),
				logger,
				resourceXml);

			resource = resourceLoader.load(resourceFile.getParentFile());
		}

		return resource;
	}
	
	public static Instance loadInstance(
		String instanceFileName) throws MessagesException
	{
		if (instanceFileName == null) { throw new IllegalArgumentException("instanceFileName"); }
		
		Instance instance = Interface.loadInstance(new File(instanceFileName));

		return instance;
	}

	/**
	 * Deserializes an {@link Instance} from the specified descriptor file.
	 * 
	 * @param       instanceFile                the descriptor file from which the Instance should be deserialized
	 * @return                                  a deserialized Resource object
	 * @throws      MessagesException           containing any validation errors encountered while the Instance is
	 *                                          being deserialized
	 * @since                                   1.0
	 */
	public static Instance loadInstance(
		File instanceFile) throws MessagesException
	{
		if (instanceFile == null) { throw new IllegalArgumentException("instanceFile"); }

		// Load Instance
		String instanceXml;
		try
		{
			instanceXml = readAllText(instanceFile);
		}
		catch (FileNotFoundException ex)
		{
			throw new InstanceLoaderFault(String.format(
				"Instance file %s does not exist",
				instanceFile.getAbsolutePath()));
		}
		catch (IOException ex)
		{
			throw new ResourceLoaderFault(String.format(
				"There was a problem reading instance file %s",
				instanceFile.getAbsolutePath()));
		}

		Instance instance = null;
		if (instanceXml != null)
		{
			DomInstanceLoader instanceLoader = new DomInstanceLoader(
				DomPlugins.instanceBuilders(),
				instanceXml);
			instance = instanceLoader.load();
		}
		
		return instance;
	}
	
	private static String readAllText(File file) throws
		FileNotFoundException,
		IOException
	{
		if (file == null) { throw new IllegalArgumentException("file cannt be null"); }
		if (!file.isFile())
		{
			throw new IllegalArgumentException(String.format(
				"%s is not a plain file",
				file.getAbsolutePath()));
		}
	
		String result = null;
		
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
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					throw new ResourceLoaderFault(e);
				}
			}
		}
		
		return result;
	}
	
	public static void logMessages(
		Logger logger,
		Messages messages)
	{
		if (logger == null) { throw new IllegalArgumentException("logger"); }
		if (messages == null) { throw new IllegalArgumentException("messages"); }
		
		messages.getMessages().forEach(m -> logger.logLine(m));
	}
}
