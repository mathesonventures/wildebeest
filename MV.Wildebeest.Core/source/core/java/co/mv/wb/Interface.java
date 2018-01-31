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

import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.impl.ResourceHelper;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlDatabaseResourcePlugin;
import co.mv.wb.plugin.postgresql.PostgreSqlDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDatabaseResourcePlugin;
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
import java.util.HashMap;
import java.util.Map;
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
	public Interface(Logger logger)
	{
		this.setLogger(logger);
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

	/**
	 * Checks the state of an instance of a resource.
	 * 
	 * @param       logger                      the {@link Logger} instance to use.
	 * @param       resourceFileName            the filename of the descriptor for the resource
	 * @param       instanceFileName            the filename of the descriptor for the instance
	 * @since                                   1.0
	 */
	public void state(
		Logger logger,
		String resourceFileName,
		String instanceFileName)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
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
			logger,
			new File(resourceFileName),
			new File(instanceFileName));
	}
	
	/**
	 * Checks the state of an instance of a resource.
	 * 
	 * @param       logger                      the {@link Logger} instance to use.
	 * @param       resourceFile                the descriptor file for the resource
	 * @param       instanceFile                the descriptor file for the instance
	 * @since                                   1.0
	 */
	public void state(
		Logger logger,
		File resourceFile,
		File instanceFile)
	{
		if (logger == null) { throw new IllegalArgumentException("logger cannot be null"); }
		if (resourceFile == null) { throw new IllegalArgumentException("resourceFile cannot be null"); }
		if (instanceFile == null) { throw new IllegalArgumentException("instanceFile cannot be null"); }
		
		// Load Resource
		Resource resource = null;
		try
		{
			resource = loadResource(logger, resourceFile);
		}
		catch (MessagesException e)
		{
			this.getLogger().logLine("Unable to load the resource.");
			logMessages(this.getLogger(), e.getMessages());
		}

		ResourcePlugin resourcePlugin = Interface.getResourcePlugin(
			Interface.getResourcePlugins(),
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

					ResourceHelper.assertState(
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
	 * @param       targetState                 the name or unique ID of the state to which the instance should be
	 *                                          migrated
	 * @since                                   1.0
	 */
	public void migrate(
		Resource resource,
		Instance instance,
		String targetState)
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }

		Map<String, ResourcePlugin> resourcePlugins = Interface.getResourcePlugins();

		ResourcePlugin resourcePlugin = Interface.getResourcePlugin(
			resourcePlugins,
			resource.getType());

		// Perform migration
		try
		{
            UUID targetStateId = getTargetStateId(
            	resource,
				resourcePlugin,
				targetState);

			ResourceHelper.migrate(
				this.getLogger(),
				resource,
				resourcePlugin,
				instance,
				this.getMigrationPlugins(),
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

		ResourcePlugin resourcePlugin = Interface.getResourcePlugin(
			 Interface.getResourcePlugins(),
			resource.getType());

		try
		{
            UUID targetStateId = getTargetStateId(
            	resource,
				resourcePlugin,
				targetState);

			ResourceHelper.jumpstate(
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

	private static Map<String, ResourcePlugin> getResourcePlugins()
	{
		Map<String, ResourcePlugin> result = new HashMap<>();

		result.put(FactoryResourceTypes.MySqlDatabase.getUri(), new MySqlDatabaseResourcePlugin());
		result.put(FactoryResourceTypes.PostgreSqlDatabase.getUri(), new PostgreSqlDatabaseResourcePlugin());
		result.put(FactoryResourceTypes.SqlServerDatabase.getUri(), new SqlServerDatabaseResourcePlugin());

		return result;
	}

	private static ResourcePlugin getResourcePlugin(
		Map<String, ResourcePlugin> resourcePlugins,
		ResourceType resourceType)
	{
		if (resourcePlugins == null) { throw new IllegalArgumentException("resourcePlugins cannot be null"); }
		if (resourceType == null) { throw new IllegalArgumentException("resourceType cannot be null"); }

		ResourcePlugin resourcePlugin = resourcePlugins.get(resourceType.getUri());

		if (resourcePlugin == null)
		{
			throw new ResourceLoaderFault(String.format(
				"resource plugin for resource type %s not found",
				resourceType.getUri()));
		}

		return resourcePlugin;
	}

	private static Map<Class, MigrationPlugin> getMigrationPlugins()
	{
		Map<Class, MigrationPlugin> result = new HashMap<>();

		result.put(AnsiSqlCreateDatabaseMigration.class, new AnsiSqlCreateDatabaseMigrationPlugin());

		return result;
	}

	private static UUID getTargetStateId(
		Resource resource,
		ResourcePlugin resourcePlugin,
		String targetState) throws
            InvalidStateSpecifiedException,
            UnknownStateSpecifiedException
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		if (resourcePlugin == null) { throw new IllegalArgumentException("resourcePlugin cannot be null"); }

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
				targetStateId = ResourceHelper.stateIdForLabel(
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
	 * @param       logger                      the {@link Logger} instance to use.
	 * @param       resourceFileName            the descriptor file from which the Resource should be deserialized
	 * @return                                  a deserialized Resource object or null if the load request failed.
	 */
	public Resource tryLoadResource(
		Logger logger,
		String resourceFileName)
	{
		// Load the resource
		Resource resource = null;
		try
		{
			resource = Interface.loadResource(
				logger,
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
