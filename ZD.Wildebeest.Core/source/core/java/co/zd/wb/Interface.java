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

package co.zd.wb;

import co.zd.wb.service.InstanceLoaderFault;
import co.zd.wb.service.Messages;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.ResourceLoaderFault;
import co.zd.wb.service.dom.DomInstanceLoader;
import co.zd.wb.service.dom.DomPlugins;
import co.zd.wb.service.dom.DomResourceLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	private Logger m_logger = null;
	private boolean m_logger_set = false;

	private Logger getLogger() {
		if(!m_logger_set) {
			throw new IllegalStateException("logger not set.  Use the HasLogger() method to check its state before accessing it.");
		}
		return m_logger;
	}

	private void setLogger(
		Logger value) {
		if(value == null) {
			throw new IllegalArgumentException("logger cannot be null");
		}
		boolean changing = !m_logger_set || m_logger != value;
		if(changing) {
			m_logger_set = true;
			m_logger = value;
		}
	}

	private void clearLogger() {
		if(m_logger_set) {
			m_logger_set = true;
			m_logger = null;
		}
	}

	private boolean hasLogger() {
		return m_logger_set;
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
			resource = loadResource(resourceFile);
		}
		catch (MessagesException e)
		{
			this.getLogger().logLine("Unable to load the resource.");
			logMessages(this.getLogger(), e.getMessages());
		}
		
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
				State state = resource.currentState(instance);

				if (state == null)
				{
					this.getLogger().logLine("Current state: non-existent");
				}
				else
				{
					if (state.hasLabel())
					{
						this.getLogger().logLine("Current state: " + state.getLabel());
					}
					else
					{
						this.getLogger().logLine("Current state: " + state.getStateId().toString());
					}

					resource.assertState(this.getLogger(), instance);
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

		// Perform migration
		try
		{
            UUID targetStateId = getTargetStateId(resource, targetState);
			resource.migrate(this.getLogger(), instance, targetStateId);
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

		try
		{
            UUID targetStateId = getTargetStateId(resource, targetState);
			resource.jumpstate(this.getLogger(), instance, targetStateId);
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
		Resource resource,
		String targetState) throws
            InvalidStateSpecifiedException,
            UnknownStateSpecifiedException
	{
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }

		final String stateSpecificationRegex = "[a-zA-Z0-9][a-zA-Z0-9\\- ]+[a-zA-Z0-9]";
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
				targetStateId = resource.stateIdForLabel(targetState);
			}
            
            // If we still could not find the specified state, then throw
            if (targetStateId == null)
            {
                throw new UnknownStateSpecifiedException(targetState);
            }
		}
		
		return targetStateId;
	}
	
	public Resource tryLoadResource(
		String resourceFileName)
	{
		// Load the resource
		Resource resource = null;
		try
		{
			resource = Interface.loadResource(new File(resourceFileName));
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
	
	public static Resource loadResource(
		String resourceFileName) throws MessagesException
	{
		if (resourceFileName == null) { throw new IllegalArgumentException("resourceFileName"); }
		
		Resource resource = Interface.loadResource(new File(resourceFileName));

		return resource;
	}
	
	/**
	 * Deserializes a {@link Resource} from the specified descriptor file.
	 * 
	 * @param       resourceFile                the descriptor file from which the Resource should be deserialized
	 * @return                                  a deserialized Resource object
	 * @throws      MessagesException           containing any validation errors encountered while the Resource is
	 *                                          being deserialized
	 * @since                                   1.0
	 */
	public static Resource loadResource(
		File resourceFile) throws MessagesException
	{
		if (resourceFile == null) { throw new IllegalArgumentException("resourceFile cannot be null"); }
		
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
			DomResourceLoader resourceLoader = new DomResourceLoader(
				DomPlugins.resourceBuilders(),
				DomPlugins.assertionBuilders(),
				DomPlugins.migrationBuilders(),
				resourceXml);
			resource = resourceLoader.load();
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
		
		for (String message : messages.getMessages())
		{
			logger.logLine(message);
		}
	}
}
