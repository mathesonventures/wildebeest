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

package co.zd.wb;

import co.zd.wb.service.InstanceLoaderFault;
import co.zd.wb.service.Logger;
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
 */
public class Interface
{
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
	
	public void state(
		File resourceFile,
		File instanceFile)
	{
		if (resourceFile == null) { throw new IllegalArgumentException("resourceFile cannot be null"); }
		if (instanceFile == null) { throw new IllegalArgumentException("instanceFile cannot be null"); }
		
		Resource resource = loadResource(resourceFile);
		Instance instance = loadInstance(instanceFile);
		
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
	
	public void migrate(
		String resourceFileName,
		String instanceFileName,
		String targetState)
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
		if (targetState != null && "".equals(targetState.trim()))
		{
			throw new IllegalArgumentException("targetState cannot be empty");
		}
		
		this.migrate(
			new File(resourceFileName),
			new File(instanceFileName),
			targetState);
	}
	
	public void migrate(
		File resourceFile,
		File instanceFile,
		String targetState)
	{
		if (resourceFile == null) { throw new IllegalArgumentException("resourceFile cannot be null"); }
		if (instanceFile == null) { throw new IllegalArgumentException("instanceFile cannot be null"); }
		if (targetState != null && "".equals(targetState.trim()))
		{
			throw new IllegalArgumentException("targetState cannot be empty");
		}

		Resource resource = loadResource(resourceFile);
		Instance instance = loadInstance(instanceFile);

		migrate(resource, instance, targetState);
	}
	
	public void migrate(
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

		// Get the state
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
		}
		
		// Perform migration
		try
		{
			resource.migrate(this.getLogger(), instance, targetStateId);
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
	
	public static Resource loadResource(File resourceFile)
	{
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
	
	public static Instance loadInstance(File instanceFile)
	{
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
}
