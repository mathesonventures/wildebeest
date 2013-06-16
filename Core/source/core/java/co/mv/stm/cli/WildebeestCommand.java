package co.mv.stm.cli;

import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.Resource;
import co.mv.stm.model.Instance;
import co.mv.stm.service.ResourceLoaderFault;
import co.mv.stm.service.dom.DomPlugins;
import co.mv.stm.service.dom.DomResourceLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class WildebeestCommand
{
	public static void main(String[] args)
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(args);
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
		
		String command = args[0];
		
		if ("state".equals(command))
		{
			this.setCommand(CommandType.State);
			String instance = WildebeestCommand.getArg(args, "i", "instance");

			if (isNullOrWhiteSpace(instance))
			{
				throw new RuntimeException("args missing");
			}
			
			this.setInstance(instance);
		}
		
		else if ("transition".equals(command))
		{
			this.setCommand(CommandType.Transition);
			String instance = WildebeestCommand.getArg(args, "i", "instance");
			String targetState = WildebeestCommand.getArg(args, "t", "targetState");

			if (isNullOrWhiteSpace(instance) || isNullOrWhiteSpace(targetState))
			{
				throw new RuntimeException("args missing");
			}
			
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
	
	public void run()
	{
		if (this.getCommand() == CommandType.State)
		{
			File resourceFile = new File(this.getInstance());
			String resourceXml = readAllText(resourceFile);
			
			DomResourceLoader loader = new DomResourceLoader(
				DomPlugins.resourceBuilders(),
				DomPlugins.assertionBuilders(),
				DomPlugins.transitionBuilders(),
				resourceXml);
			Resource resource = loader.load();
			
			Instance instance = null;
			try
			{
				resource.currentState(instance);
			}
			catch (IndeterminateStateException ex)
			{
				// TODO: Write to stream
			}
		}
		
		if (this.getCommand() == CommandType.Transition)
		{
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
		
		shortName = "-" + shortName + "=";
		longName = "--" + longName + "=";
		
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
	
	private static String readAllText(File file)
	{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("file.txt"));
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
		}
		catch(FileNotFoundException e)
		{
			throw new ResourceLoaderFault(e);
		}
		catch (IOException e)
		{
			throw new ResourceLoaderFault(e);
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
		
		return sb.toString();
	}
}
