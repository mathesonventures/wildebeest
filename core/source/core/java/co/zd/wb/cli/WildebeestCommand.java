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

package co.zd.wb.cli;

import co.zd.wb.About;
import co.zd.wb.Instance;
import co.zd.wb.Interface;
import co.zd.wb.Resource;
import co.zd.wb.Logger;
import co.zd.wb.PrintStreamLogger;
import java.io.PrintStream;

/**
 * The Wildebeest command-line interface.  WildebeestCommand parses command-line invocations, and delegates to
 * {@link co.zd.wb.Interface} to carry out the command.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class WildebeestCommand
{
	/**
	 * The main entry point for the command-line interface.
	 * 
	 * @param       args                        the arguments supplied on the command-line invocation
	 * @since                                   1.0
	 */
	public static void main(String[] args)
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.run(args);
	}

	/**
	 * Creates a new WildebeestCommand instance.
	 * 
	 * @since                                   1.0
	 */
	public WildebeestCommand()
	{
		this.setLogger(new PrintStreamLogger(System.out));
	}

	// <editor-fold desc="Logger" defaultstate="collapsed">

	private Logger m_logger = null;
	private boolean m_logger_set = false;

	public Logger getLogger() {
		if(!m_logger_set) {
			throw new IllegalStateException("logger not set.  Use the HasLogger() method to check its state before accessing it.");
		}
		return m_logger;
	}

	public void setLogger(
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
	 * Runs the command using the supplied command-line arguments.
	 * 
	 * @param       args                        the arguments supplied on the command-line invocation
	 * @since                                   1.0
	 */
	public void run(String[] args)
	{
		if(args == null) { throw new IllegalArgumentException("args cannot be null"); }
		
		WildebeestCommand.printBanner(System.out);
		
		if (args.length == 0)
		{
			WildebeestCommand.printUsage(System.out);
		}
		
		else
		{
			String command = args[0];

			if ("about".equals(command))
			{
				About about = new About();
				this.getLogger().logLine(about.getProjectName() + " " + about.getVersionFullDotted());
				this.getLogger().logLine(about.getCopyrightAssertion());
			}

			else if ("state".equals(command))
			{
				String resourceFileName = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFileName = WildebeestCommand.getArg(args, "i", "instance");

				if (isNullOrWhiteSpace(resourceFileName) || isNullOrWhiteSpace(instanceFileName))
				{
					WildebeestCommand.printUsage(System.out);
				}
				else
				{
					Interface iface = new Interface(this.getLogger());
					iface.state(resourceFileName, instanceFileName);
				}
			}

			else if ("migrate".equals(command))
			{
				String resourceFileName = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFileName = WildebeestCommand.getArg(args, "i", "instance");
				String targetState = WildebeestCommand.getArg(args, "t", "targetState");

				if (isNullOrWhiteSpace(resourceFileName) || isNullOrWhiteSpace(instanceFileName) || isNull(targetState))
				{
					WildebeestCommand.printUsage(System.out);
				}
				else
				{
					Interface iface = new Interface(this.getLogger());

					Resource resource = iface.tryLoadResource(resourceFileName);
					Instance instance = iface.tryLoadInstance(instanceFileName);

					iface.migrate(resource, instance, targetState);
				}
			}

			else if (("jumpstate").equals(command))
			{
				String resourceFileName = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFileName = WildebeestCommand.getArg(args, "i", "instance");
				String targetState = WildebeestCommand.getArg(args, "t", "targetState");

				if (isNullOrWhiteSpace(resourceFileName) || isNullOrWhiteSpace(instanceFileName) || isNull(targetState))
				{
					WildebeestCommand.printUsage(System.out);
				}
				else
				{
					Interface iface = new Interface(this.getLogger());

					Resource resource = iface.tryLoadResource(resourceFileName);
					Instance instance = iface.tryLoadInstance(instanceFileName);

					iface.jumpstate(resource, instance, targetState);
				}
			}

			else
			{
				WildebeestCommand.printUsage(System.out);
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
	
    private static boolean isNull(String value)
    {
        return value == null;
    }
    
	private static boolean isNullOrWhiteSpace(String value)
	{
		return value == null ||	"".equals(value.trim());
	}
	
	private static void printBanner(PrintStream out)
	{
		if (out == null) { throw new IllegalArgumentException("out cannot be null"); }
		
		out.println("            _  _     _       _");
		out.println("           |_|| |   | |     | |                      _");
		out.println(" __      __ _ | | __| | ___ | |__   ___   ___  ___ _| |_");
		out.println(" \\ \\ /\\ / /| || |/ _` |/ _ \\| '_ \\ / _ \\ / _ \\/ __|_   _|");
		out.println("  \\ v  v / | || | (_| |  __/| |_) |  __/|  __/\\__ \\ | |");
		out.println("   \\_/\\_/  |_||_|\\__,_|\\___||_.__/ \\___| \\___||___/ |_|");
		out.println("");
		
		About about = new About();
		out.println("Version " + about.getVersionFullDotted() + ", " + about.getCopyrightAssertion());
		out.println("");
	}
	
	private static void printUsage(PrintStream out)
	{
		if (out == null) { throw new IllegalArgumentException("out"); }
		
		out.println("Usage: wb command [options]");
		out.println("");
		out.println("Valid commands: state; migrate; jumpstate;");
		out.println("");
	}
}