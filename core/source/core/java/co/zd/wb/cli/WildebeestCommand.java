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

import co.zd.wb.Interface;
import co.zd.wb.service.Logger;
import co.zd.wb.service.PrintStreamLogger;
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
		this.setInterface(new Interface(this.getLogger()));
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
	
	// <editor-fold desc="Interface" defaultstate="collapsed">

	private Interface m_interface = null;
	private boolean m_interface_set = false;

	private Interface getInterface() {
		if(!m_interface_set) {
			throw new IllegalStateException("interface not set.  Use the HasInterface() method to check its state before accessing it.");
		}
		return m_interface;
	}

	private void setInterface(
		Interface value) {
		if(value == null) {
			throw new IllegalArgumentException("interface cannot be null");
		}
		boolean changing = !m_interface_set || m_interface != value;
		if(changing) {
			m_interface_set = true;
			m_interface = value;
		}
	}

	private void clearInterface() {
		if(m_interface_set) {
			m_interface_set = true;
			m_interface = null;
		}
	}

	private boolean hasInterface() {
		return m_interface_set;
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
		
		if (args.length == 0)
		{
			WildebeestCommand.printUsage(System.out);
		}
		
		String command = args[0];
		
		if ("state".equals(command))
		{
			String resourceFileName = WildebeestCommand.getArg(args, "r", "resource");
			String instanceFileName = WildebeestCommand.getArg(args, "i", "instance");
			if (isNullOrWhiteSpace(resourceFileName) || isNullOrWhiteSpace(instanceFileName))
			{
				throw new RuntimeException("args missing");
			}

			this.getInterface().state(resourceFileName, instanceFileName);
		}
				
		if ("migrate".equals(command))
		{
			String resourceFileName = WildebeestCommand.getArg(args, "r", "resource");
			String instanceFileName = WildebeestCommand.getArg(args, "i", "instance");
			String targetState = WildebeestCommand.getArg(args, "t", "targetState");
			if (isNullOrWhiteSpace(resourceFileName) || isNullOrWhiteSpace(instanceFileName) ||
				isNullOrWhiteSpace(targetState))
			{
				throw new RuntimeException("args missing");
			}
				
			this.getInterface().migrate(resourceFileName, instanceFileName, targetState);
		}
		
		else
		{
				WildebeestCommand.printUsage(System.out);
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
	
	private static void printUsage(PrintStream out)
	{
		if (out == null) { throw new IllegalArgumentException("out"); }
		
		out.println("Usage: wb command [options]");
		out.println("");
		out.println("Valid commands: state; migrate;");
		out.println("");
	}
}
