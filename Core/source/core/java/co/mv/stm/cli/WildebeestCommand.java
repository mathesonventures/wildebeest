package co.mv.stm.cli;

import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.Resource;
import co.mv.stm.model.Instance;
import co.mv.stm.model.State;
import co.mv.stm.model.MigrationFailedException;
import co.mv.stm.model.MigrationNotPossibleException;
import co.mv.stm.service.InstanceLoaderFault;
import co.mv.stm.service.Logger;
import co.mv.stm.service.PrintStreamLogger;
import co.mv.stm.service.ResourceLoaderFault;
import co.mv.stm.service.dom.DomInstanceLoader;
import co.mv.stm.service.dom.DomPlugins;
import co.mv.stm.service.dom.DomResourceLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.UUID;

public class WildebeestCommand
{
	public static void main(String[] args)
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(args);
		wb.run(new PrintStreamLogger(System.out));
	}
	
	// <editor-fold desc="Command" defaultstate="collapsed">

	private CommandType m_command = null;
	private boolean m_command_set = false;

	public CommandType getCommand() {
		if(!m_command_set) {
			throw new IllegalStateException("command not set.  Use the HasCommand() method to check its state before accessing it.");
		}
		return m_command;
	}

	private void setCommand(
		CommandType value) {
		if(value == null) {
			throw new IllegalArgumentException("command cannot be null");
		}
		boolean changing = !m_command_set || m_command != value;
		if(changing) {
			m_command_set = true;
			m_command = value;
		}
	}

	private void clearCommand() {
		if(m_command_set) {
			m_command_set = true;
			m_command = null;
		}
	}

	private boolean hasCommand() {
		return m_command_set;
	}

	// </editor-fold>

	// <editor-fold desc="Resource" defaultstate="collapsed">

	private String m_resource = null;
	private boolean m_resource_set = false;

	public String getResource() {
		if(!m_resource_set) {
			throw new IllegalStateException("resource not set.  Use the HasResource() method to check its state before accessing it.");
		}
		return m_resource;
	}

	private void setResource(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("resource cannot be null");
		}
		boolean changing = !m_resource_set || m_resource != value;
		if(changing) {
			m_resource_set = true;
			m_resource = value;
		}
	}

	private void clearResource() {
		if(m_resource_set) {
			m_resource_set = true;
			m_resource = null;
		}
	}

	private boolean hasResource() {
		return m_resource_set;
	}

	// </editor-fold>

	// <editor-fold desc="Instance" defaultstate="collapsed">

	private String m_instance = null;
	private boolean m_instance_set = false;

	public String getInstance() {
		if(!m_instance_set) {
			throw new IllegalStateException("instance not set.  Use the HasInstance() method to check its state before accessing it.");
		}
		return m_instance;
	}

	private void setInstance(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("instance cannot be null");
		}
		boolean changing = !m_instance_set || m_instance != value;
		if(changing) {
			m_instance_set = true;
			m_instance = value;
		}
	}

	private void clearInstance() {
		if(m_instance_set) {
			m_instance_set = true;
			m_instance = null;
		}
	}

	private boolean hasInstance() {
		return m_instance_set;
	}

	// </editor-fold>

	// <editor-fold desc="TargetStateLabel" defaultstate="collapsed">

	private String m_targetStateLabel = null;
	private boolean m_targetStateLabel_set = false;

	public String getTargetStateLabel() {
		if(!m_targetStateLabel_set) {
			throw new IllegalStateException("targetStateLabel not set.  Use the HasTargetStateLabel() method to check its state before accessing it.");
		}
		return m_targetStateLabel;
	}

	private void setTargetStateLabel(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("targetStateLabel cannot be null");
		}
		boolean changing = !m_targetStateLabel_set || m_targetStateLabel != value;
		if(changing) {
			m_targetStateLabel_set = true;
			m_targetStateLabel = value;
		}
	}

	private void clearTargetStateLabel() {
		if(m_targetStateLabel_set) {
			m_targetStateLabel_set = true;
			m_targetStateLabel = null;
		}
	}

	private boolean hasTargetStateLabel() {
		return m_targetStateLabel_set;
	}

	// </editor-fold>

	// <editor-fold desc="TargetStateId" defaultstate="collapsed">

	private UUID m_targetStateId = null;
	private boolean m_targetStateId_set = false;

	public UUID getTargetStateId() {
		if(!m_targetStateId_set) {
			throw new IllegalStateException("targetStateId not set.  Use the HasTargetStateId() method to check its state before accessing it.");
		}
		return m_targetStateId;
	}

	private void setTargetStateId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("targetStateId cannot be null");
		}
		boolean changing = !m_targetStateId_set || m_targetStateId != value;
		if(changing) {
			m_targetStateId_set = true;
			m_targetStateId = value;
		}
	}

	private void clearTargetStateId() {
		if(m_targetStateId_set) {
			m_targetStateId_set = true;
			m_targetStateId = null;
		}
	}

	private boolean hasTargetStateId() {
		return m_targetStateId_set;
	}

	// </editor-fold>

	public void parse(String[] args)
	{
		if(args == null) { throw new IllegalArgumentException("args cannot be null"); }
		
		if (args.length == 0)
		{
			WildebeestCommand.printUsage(System.out);
		}
		
		String command = args[0];
		
		if ("state".equals(command))
		{
			this.setCommand(CommandType.State);
			String resource = WildebeestCommand.getArg(args, "r", "resource");
			String instance = WildebeestCommand.getArg(args, "i", "instance");

			if (isNullOrWhiteSpace(resource) || isNullOrWhiteSpace(instance))
			{
				throw new RuntimeException("args missing");
			}
			
			this.setResource(resource);
			this.setInstance(instance);
		}
		
		else if ("migrate".equals(command))
		{
			this.setCommand(CommandType.Migration);
			String resource = WildebeestCommand.getArg(args, "r", "resource");
			String instance = WildebeestCommand.getArg(args, "i", "instance");
			String targetState = WildebeestCommand.getArg(args, "t", "targetState");

			if (isNullOrWhiteSpace(resource) || isNullOrWhiteSpace(instance) || isNullOrWhiteSpace(targetState))
			{
				throw new RuntimeException("args missing");
			}
			
			this.setResource(resource);
			this.setInstance(instance);
			
			UUID targetStateId = null;
			try
			{
				targetStateId = UUID.fromString(targetState);
				this.setTargetStateId(targetStateId);
			}
			catch(IllegalArgumentException e)
			{
				this.setTargetStateLabel(targetState);
			}
		}
	}
	
	public Resource loadResource()
	{
		// Load Resource
		File resourceFile = new File(this.getResource());
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
	
	public Instance loadInstance()
	{
		// Load Instance
		File instanceFile = new File(this.getInstance());
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
	
	public void run(Logger logger)
	{
		if (logger == null) { throw new IllegalArgumentException("logger"); }

		if (this.getCommand() == CommandType.State)
		{
			Resource resource = this.loadResource();
			Instance instance = this.loadInstance();
			
			// Perform migration
			try
			{
				State state = resource.currentState(instance);
				
				if (state == null)
				{
					System.out.println("Current state: non-existent");
				}
				else
				{
					System.out.println("Current state: " + state.getStateId().toString());
				}
			}
			catch (IndeterminateStateException ex)
			{
				// TODO: Write to stream
			}
		}
		
		if (this.getCommand() == CommandType.Migration)
		{
			Resource resource = this.loadResource();
			Instance instance = this.loadInstance();
			UUID targetStateId = this.hasTargetStateId() ? this.getTargetStateId() :
				resource.stateIdForLabel(this.getTargetStateLabel());
			
			if (targetStateId == null)
			{
				throw new RuntimeException("no target state identified");
			}
			
			if (resource != null && instance != null && targetStateId != null)
			{
				// Perform migration
				try
				{
					resource.migrate(logger, instance, targetStateId);
				}
				catch (IndeterminateStateException ex)
				{
					// TODO: Write to stream
				}
				catch (AssertionFailedException ex)
				{
					// TODO: Write to stream
				}
				catch (MigrationNotPossibleException ex)
				{
					// TODO: Write to stream
				}
				catch (MigrationFailedException ex)
				{
					// TODO: Write to stream
				}
			}
		}
	}
	
	private static String getArg(
		String[] args,
		String shortName,
		String longName)
	{
		if (args == null) { throw new IllegalArgumentException("args"); }
		if (shortName == null) { throw new IllegalArgumentException("shortName cannot be null"); }
		if ("".equals(shortName)) { throw new IllegalArgumentException("shortName cannot be empty"); }
		if (longName == null) { throw new IllegalArgumentException("longName cannot be null"); }
		if ("".equals(longName)) { throw new IllegalArgumentException("longName cannot be empty"); }
		
		shortName = "-" + shortName + ":";
		longName = "--" + longName + ":";
		
		String result = null;
		
		for(String arg : args)
		{
			if (arg.startsWith(shortName))
			{
				result = arg.substring(shortName.length());
				break;
			}
			if (arg.startsWith(longName))
			{
				result = arg.substring(longName.length());
				break;
			}
		}
		
		return result;
	}
	
	private static boolean isNullOrWhiteSpace(String value)
	{
		return value == null ||	"".equals(value.trim());
	}
	
	private static String readAllText(File file) throws FileNotFoundException, IOException
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
	
	private static void printUsage(PrintStream out)
	{
		if (out == null) { throw new IllegalArgumentException("out"); }
		
		out.println("Usage: wb command [options]");
		out.println("");
		out.println("Valid commands: state; migrate;");
		out.println("");
	}
}
