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

package co.mv.wb.cli;

import co.mv.wb.About;
import co.mv.wb.AssertionFailedException;
import co.mv.wb.FileLoadException;
import co.mv.wb.IndeterminateStateException;
import co.mv.wb.Instance;
import co.mv.wb.InvalidStateSpecifiedException;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.OutputFormatter;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.WildebeestApi;
import co.mv.wb.WildebeestFactory;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.impl.WildebeestApiImpl;

import java.io.File;
import java.io.PrintStream;
import java.util.Optional;

/**
 * The Wildebeest command-line interface.  WildebeestCommand parses command-line invocations, and delegates to
 * {@link WildebeestApiImpl} to carry out the command.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class WildebeestCommand
{
	// <editor-fold desc="Output" defaultstate="collapsed">

	private PrintStream _output = null;
	private boolean _output_set = false;

	private PrintStream getOutput() {
		if(!_output_set) {
			throw new IllegalStateException("output not set.");
		}
		if(_output == null) {
			throw new IllegalStateException("output should not be null");
		}
		return _output;
	}

	private void setOutput(
		PrintStream value) {
		if(value == null) {
			throw new IllegalArgumentException("output cannot be null");
		}
		boolean changing = !_output_set || _output != value;
		if(changing) {
			_output_set = true;
			_output = value;
		}
	}

	private void clearOutput() {
		if(_output_set) {
			_output_set = true;
			_output = null;
		}
	}

	private boolean hasOutput() {
		return _output_set;
	}

	// </editor-fold>

	// <editor-fold desc="WildebeestApi" defaultstate="collapsed">

	private WildebeestApi _wildebeestApi = null;
	private boolean _wildebeestApi_set = false;

	private WildebeestApi getWildebeestApi() {
		if(!_wildebeestApi_set) {
			throw new IllegalStateException("wildebeestApi not set.");
		}
		if(_wildebeestApi == null) {
			throw new IllegalStateException("wildebeestApi should not be null");
		}
		return _wildebeestApi;
	}

	private void setWildebeestApi(
		WildebeestApi value) {
		if(value == null) {
			throw new IllegalArgumentException("wildebeestApi cannot be null");
		}
		boolean changing = !_wildebeestApi_set || _wildebeestApi != value;
		if(changing) {
			_wildebeestApi_set = true;
			_wildebeestApi = value;
		}
	}

	private void clearWildebeestApi() {
		if(_wildebeestApi_set) {
			_wildebeestApi_set = true;
			_wildebeestApi = null;
		}
	}

	private boolean hasWildebeestApi() {
		return _wildebeestApi_set;
	}

	// </editor-fold>

	/**
	 * The main entry point for the command-line interface.
	 * 
	 * @param       args                        the arguments supplied on the command-line invocation
	 * @since                                   1.0
	 */
	public static void main(String[] args)
	{
		PrintStream output = System.out;

		WildebeestCommand wb = new WildebeestCommand(
			output,
			WildebeestFactory.wildebeestApi(output));

		wb.run(args);
	}

	/**
	 * Creates a new WildebeestCommand instance.
	 * 
	 * @since                                   1.0
	 */
	public WildebeestCommand(
		PrintStream output,
		WildebeestApi wildebeestApi)
	{
		this.setOutput(output);
		this.setWildebeestApi(wildebeestApi);
	}

	/**
	 * Runs the command using the supplied command-line arguments.
	 * 
	 * @param       args                        the arguments supplied on the command-line invocation
	 * @since                                   1.0
	 */
	public void run(String[] args)
	{
		if(args == null) { throw new IllegalArgumentException("args cannot be null"); }

		WildebeestCommand.printBanner(this.getOutput());

		if (args.length == 0)
		{
			WildebeestCommand.printUsage(this.getOutput());
		}
		
		else
		{
			String command = args[0];

			if ("about".equals(command))
			{
				About about = new About();
				this.getOutput().println(about.getProjectName() + " " + about.getVersionFullDotted());
				this.getOutput().println(about.getCopyrightAssertion());
			}

			else if ("state".equals(command))
			{
				String resourceFilename = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFilename = WildebeestCommand.getArg(args, "i", "instance");

				if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename))
				{
					WildebeestCommand.printUsage(this.getOutput());
				}
				else
				{
					Optional<Resource> resource = WildebeestCommand.tryLoadResource(
						this.getWildebeestApi(),
						resourceFilename,
						this.getOutput());

					Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
						this.getWildebeestApi(),
						instanceFilename,
						this.getOutput());

					if (resource.isPresent() && instance.isPresent())
					{
						try
						{
							this.getWildebeestApi().state(
								resource.get(),
								instance.get());
						}
						catch (IndeterminateStateException e)
						{
							this.getOutput().println(e.getMessage());
						}
					}
				}
			}

			else if ("migrate".equals(command))
			{
				String resourceFilename = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFilename = WildebeestCommand.getArg(args, "i", "instance");
				Optional<String> targetState = WildebeestCommand.getOptionalArg(args, "t", "targetState");

				if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename))
				{
					WildebeestCommand.printUsage(System.out);
				}
				else
				{
					Optional<Resource> resource = WildebeestCommand.tryLoadResource(
						this.getWildebeestApi(),
						resourceFilename,
						this.getOutput());

					Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
						this.getWildebeestApi(),
						instanceFilename,
						this.getOutput());

					if (resource.isPresent() && instance.isPresent())
					{
						try
						{
							this.getWildebeestApi().migrate(
								resource.get(),
								instance.get(),
								targetState);
						}
						catch (TargetNotSpecifiedException e)
						{
							this.getOutput().println(OutputFormatter.targetNotSpecified(e));
						}
						catch (UnknownStateSpecifiedException e)
						{
							this.getOutput().println(OutputFormatter.unknownStateSpecified(e));
						}
						catch (InvalidStateSpecifiedException e)
						{
							this.getOutput().println(OutputFormatter.invalidStateSpecified(e));
						}
						catch (MigrationNotPossibleException e)
						{
							this.getOutput().println(OutputFormatter.migrationNotPossible(e));
						}
						catch (IndeterminateStateException e)
						{
							this.getOutput().println(OutputFormatter.indeterminateState(e));
						}
						catch (MigrationFailedException e)
						{
							this.getOutput().print(OutputFormatter.migrationFailed(e));
						}
						catch (AssertionFailedException e)
						{
							this.getOutput().println(OutputFormatter.assertionFailed(e));
						}
					}
				}
			}

			else if (("jumpstate").equals(command))
			{
				String resourceFilename = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFilename = WildebeestCommand.getArg(args, "i", "instance");
				String targetState = WildebeestCommand.getArg(args, "t", "targetState");

				if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename) || isNull(targetState))
				{
					WildebeestCommand.printUsage(System.out);
				}
				else
				{
					Optional<Resource> resource = WildebeestCommand.tryLoadResource(
						this.getWildebeestApi(),
						resourceFilename,
						this.getOutput());

					Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
						this.getWildebeestApi(),
						instanceFilename,
						this.getOutput());

					if (resource.isPresent() && instance.isPresent())
					{
						try
						{
							this.getWildebeestApi().jumpstate(
								resource.get(),
								instance.get(),
								targetState);
						}
						catch (UnknownStateSpecifiedException e)
						{
							this.getOutput().println(OutputFormatter.unknownStateSpecified(e));
						}
						catch (IndeterminateStateException e)
						{
							this.getOutput().println(OutputFormatter.indeterminateState(e));
						}
						catch (InvalidStateSpecifiedException e)
						{
							this.getOutput().println(OutputFormatter.invalidStateSpecified(e));
						}
						catch (AssertionFailedException e)
						{
							this.getOutput().println(OutputFormatter.assertionFailed(e));
						}
						catch (JumpStateFailedException e)
						{
							this.getOutput().println(OutputFormatter.jumpStateFailed(e));
						}
					}
				}
			}

			else
			{
				WildebeestCommand.printUsage(System.out);
			}
		}
	}

	private static Optional<Resource> tryLoadResource(
		WildebeestApi wildebeestApi,
		String resourceFilename,
		PrintStream out)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");
		if (resourceFilename == null) throw new ArgumentNullException("resourceFilename");
		if (out == null) throw new ArgumentNullException("out");

		File resourceFile = new File(resourceFilename);

		Resource resource = null;

		try
		{
			resource = wildebeestApi.loadResource(resourceFile);
		}
		catch (FileLoadException e)
		{
			out.println(OutputFormatter.fileLoad(e, "resource"));
		}
		catch (LoaderFault e)
		{
			out.println(OutputFormatter.loaderFault(e, "resource"));
		}
		catch (PluginBuildException e)
		{
			out.println(OutputFormatter.pluginBuild(e));
		}

		return Optional.ofNullable(resource);
	}

	private static Optional<Instance> tryLoadInstance(
		WildebeestApi wildebeestApi,
		String instanceFilename,
		PrintStream out)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");
		if (instanceFilename == null) throw new ArgumentNullException("instanceFilename");
		if (out == null) throw new ArgumentNullException("out");

		File instanceFile = new File(instanceFilename);

		Instance instance = null;

		try
		{
			instance = wildebeestApi.loadInstance(instanceFile);
		}
		catch (FileLoadException e)
		{
			out.println(OutputFormatter.fileLoad(e, "instance"));
		}
		catch (LoaderFault e)
		{
			out.println(OutputFormatter.loaderFault(e, "instance"));
		}
		catch (PluginBuildException e)
		{
			out.println(OutputFormatter.pluginBuild(e));
		}

		return Optional.ofNullable(instance);
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

	private static Optional<String> getOptionalArg(
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

		Optional<String> result = Optional.empty();

		for(String arg : args)
		{
			if (arg.startsWith(shortName))
			{
				result = Optional.of(arg.substring(shortName.length()));
				break;
			}
			if (arg.startsWith(longName))
			{
				result = Optional.of(arg.substring(longName.length()));
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
