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
import co.mv.wb.InvalidReferenceException;
import co.mv.wb.JumpStateFailedException;
import co.mv.wb.LoaderFault;
import co.mv.wb.MigrationFailedException;
import co.mv.wb.MigrationNotPossibleException;
import co.mv.wb.OutputFormatter;
import co.mv.wb.PluginBuildException;
import co.mv.wb.PluginNotFoundException;
import co.mv.wb.Resource;
import co.mv.wb.StateResponse;
import co.mv.wb.TargetNotSpecifiedException;
import co.mv.wb.UnknownStateSpecifiedException;
import co.mv.wb.WildebeestApi;
import co.mv.wb.WildebeestApiBuilder;
import co.mv.wb.XmlValidationException;
import co.mv.wb.event.LoggingEventSink;
import co.mv.wb.event.MigrationLogEventSink;
import co.mv.wb.event.TeeEventSink;
import co.mv.wb.framework.ArgumentNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

/**
 * The Wildebeest command-line interface.  WildebeestCommand parses command-line invocations, and delegates to
 * {@link WildebeestApi} to carry out the command.
 *
 * @since 1.0
 */
@CommandLine.Command(name = "wb",
	description = "Some general description\n",
	synopsisHeading = "%nUsage:%n%n",
	descriptionHeading = "%nDescription:%n%n",
	parameterListHeading = "%nParameters:%n%n",
	subcommands = {
		MigrateCommand.class,
		JumpStateCommand.class,
		StateCommand.class,
		PluginsCommand.class,
		CommandLine.HelpCommand.class
	})
public class WildebeestCommand
{
	private final PrintStream output;
	private final WildebeestApi wildebeestApi;
	private static final Logger LOG = LoggerFactory.getLogger(WildebeestCommand.class);

	/**
	 * The main entry point for the command-line interface.
	 *
	 * @param args the arguments supplied on the command-line invocation
	 * @since 1.0
	 */
	public static void main(String[] args)
	{
		PrintStream output = System.out;
		TeeEventSink teeEventSink = new TeeEventSink(
			new LoggingEventSink(LOG),
			new MigrationLogEventSink(LOG),
			new PrintStreamEventSink(output));
		WildebeestApi wildebeestApi = WildebeestApiBuilder
			.create(teeEventSink)
			.withFactoryPluginGroups()
			.withPostgreSqlSupport()
			.withMySqlSupport()
			.withSqlServerSupport()
			.get();

		WildebeestCommand wb = new WildebeestCommand(
			output,
			wildebeestApi);

		wb.run(args);
	}

	/**
	 * Creates a new WildebeestCommand instance.
	 *
	 * @since 1.0
	 */
	public WildebeestCommand(
		PrintStream output,
		WildebeestApi wildebeestApi)
	{
		if (output == null) throw new ArgumentNullException("output");
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");

		this.output = output;
		this.wildebeestApi = wildebeestApi;
	}

	/**
	 * Runs the command using the supplied command-line arguments.
	 *
	 * @param args the arguments supplied on the command-line invocation
	 * @since 1.0
	 */
	public void run(String[] args)
	{
		if (args == null) throw new ArgumentNullException("args");

		if (args.length == 0)
		{
			WildebeestCommand.printBanner(this.output);
			CommandLine.usage(this, this.output);
		}
		else
		{
			try
			{
				CommandLine commandLine = new CommandLine(this);
				List<CommandLine> parsed = commandLine.parse(args);

				parseArguments(parsed);
			}
			catch (CommandLine.UnmatchedArgumentException | CommandLine.MissingParameterException e)
			{
				this.output.println(String.format("ERROR in command line \"%s\"", e.getMessage()));
				CommandLine.usage(this, this.output);
			}
		}

	}

	private void parseArguments(List<CommandLine> parsed)
	{
		if (parsed.size() > 1)
		{
			if (parsed.get(1).getCommand().getClass() == CommandLine.HelpCommand.class)
			{
				CommandLine.usage(this, this.output);
				return;
			}
			else if (parsed.get(1).getCommand().getClass() == PluginsCommand.class)
			{
				pluginsCommand();
			}
			else if (parsed.get(1).getCommand().getClass() == MigrateCommand.class)
			{
				migrateCommand(parsed);
			}
			else if (parsed.get(1).getCommand().getClass() == JumpStateCommand.class)
			{
				jumpstateCommand(parsed);
			}
			else if (parsed.get(1).getCommand().getClass() == StateCommand.class)
			{
				StateCommand stateCommand = (StateCommand)parsed.get(1).getCommand();
				stateCommand(stateCommand);
			}
			else
			{
				WildebeestCommand.printBanner(this.output);
				CommandLine.usage(this, this.output);
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
		catch (InvalidReferenceException e)
		{
			out.println(OutputFormatter.missingReference(e));
		}
		catch (LoaderFault e)
		{
			out.println(OutputFormatter.loaderFault("resource"));
		}
		catch (PluginBuildException e)
		{
			out.println(OutputFormatter.pluginBuild(e));
		}
		catch (XmlValidationException e)
		{
			out.println(OutputFormatter.resourceValidation(e, "resource"));
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
			out.println(OutputFormatter.loaderFault("instance"));
		}
		catch (PluginBuildException e)
		{
			out.println(OutputFormatter.pluginBuild(e));
		}
		catch (XmlValidationException e)
		{
			out.println(OutputFormatter.resourceValidation(e, "instance"));
		}

		return Optional.ofNullable(instance);
	}

	private static boolean isNull(String value)
	{
		return value == null;
	}

	private static boolean isNullOrWhiteSpace(String value)
	{
		return value == null || "".equals(value.trim());
	}

	private static void printBanner(PrintStream out)
	{
		if (out == null) throw new ArgumentNullException("out");

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

	private void migrateCommand(List<CommandLine> parsed)
	{

		//check is help requested
		for (CommandLine c : parsed
		)
		{
			if (c.getCommand().getClass() == CommandLine.HelpCommand.class)
			{
				CommandLine.usage(new MigrateCommand(), this.output);
				return;
			}
		}

		String resourceFilename = parsed.get(1).getParseResult().matchedOption("--resource").getValue();
		String instanceFilename = parsed.get(1).getParseResult().matchedOption("--instance").getValue();
		String targetState = "";

		if (parsed.get(1).getParseResult().hasMatchedOption("--target-state"))
		{
			targetState = parsed.get(1).getParseResult().matchedOption("--target-state").getValue();
		}

		if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename))
		{
			WildebeestCommand.printBanner(this.output);
			CommandLine.usage(this, this.output);
		}
		else
		{
			Optional<Resource> resource = WildebeestCommand.tryLoadResource(
				this.wildebeestApi,
				resourceFilename,
				this.output);

			Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
				this.wildebeestApi,
				instanceFilename,
				this.output);

			if (resource.isPresent() && instance.isPresent())
			{
				try
				{
					this.wildebeestApi.migrate(
						resource.get(),
						instance.get(),
						targetState);
				}
				catch (AssertionFailedException e)
				{
					this.output.println(OutputFormatter.assertionFailed(e));
				}
				catch (IndeterminateStateException e)
				{
					this.output.println(OutputFormatter.indeterminateState(e));
				}
				catch (InvalidReferenceException e)
				{
					this.output.print(OutputFormatter.invalidReferenceException(e));
				}
				catch (MigrationFailedException e)
				{
					this.output.print(OutputFormatter.migrationFailed(e));
				}
				catch (MigrationNotPossibleException e)
				{
					this.output.println(OutputFormatter.migrationNotPossible(e));
				}
				catch (PluginNotFoundException e)
				{
					this.output.println(OutputFormatter.pluginNotFound(e));
				}
				catch (TargetNotSpecifiedException e)
				{
					this.output.println(OutputFormatter.targetNotSpecified());
				}
				catch (UnknownStateSpecifiedException e)
				{
					this.output.println(OutputFormatter.unknownStateSpecified(e));
				}
			}
		}
	}

	private void jumpstateCommand(List<CommandLine> parsed)
	{
		//check is help requested
		for (CommandLine c : parsed
		)
		{
			if (c.getCommand().getClass() == CommandLine.HelpCommand.class)
			{
				CommandLine.usage(new MigrateCommand(), this.output);
				return;
			}
		}

		String resourceFilename = parsed.get(1).getParseResult().matchedOption("--resource").getValue();
		String instanceFilename = parsed.get(1).getParseResult().matchedOption("--instance").getValue();
		String targetState = "";

		if (parsed.get(1).getParseResult().hasMatchedOption("--target-state"))
		{
			targetState = parsed.get(1).getParseResult().matchedOption("--target-state").getValue();
		}

		if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename) ||
			isNull(targetState))
		{
			WildebeestCommand.printBanner(this.output);
			CommandLine.usage(this, this.output);
		}
		else
		{
			Optional<Resource> resource = WildebeestCommand.tryLoadResource(
				this.wildebeestApi,
				resourceFilename,
				this.output);

			Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
				this.wildebeestApi,
				instanceFilename,
				this.output);

			if (resource.isPresent() && instance.isPresent())
			{
				try
				{
					this.wildebeestApi.jumpstate(
						resource.get(),
						instance.get(),
						targetState);
				}
				catch (AssertionFailedException e)
				{
					this.output.println(OutputFormatter.assertionFailed(e));
				}
				catch (IndeterminateStateException e)
				{
					this.output.println(OutputFormatter.indeterminateState(e));
				}
				catch (JumpStateFailedException e)
				{
					this.output.println(OutputFormatter.jumpStateFailed(e));
				}
				catch (PluginNotFoundException e)
				{
					this.output.println(OutputFormatter.pluginNotFound(e));
				}
				catch (UnknownStateSpecifiedException e)
				{
					this.output.println(OutputFormatter.unknownStateSpecified(e));
				}
			}
		}
	}

	private void stateCommand(StateCommand stateCommand)
	{
		if (stateCommand == null) throw new ArgumentNullException("stateCommand");

		if (isNullOrWhiteSpace(stateCommand.resource) || isNullOrWhiteSpace(stateCommand.instance))
		{
			WildebeestCommand.printBanner(this.output);
			CommandLine.usage(this, this.output);
		}
		else
		{
			Optional<Resource> resource = WildebeestCommand.tryLoadResource(
				this.wildebeestApi,
				stateCommand.resource,
				this.output);

			Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
				this.wildebeestApi,
				stateCommand.instance,
				this.output);

			if (resource.isPresent() && instance.isPresent())
			{
				try
				{
					StateResponse response = this.wildebeestApi.state(
						resource.get(),
						instance.get());

					if (response.getState().isPresent())
					{
						this.output.println(String.format(
							"Resource %s is at state %s",
							resource.get().getResourceId(),
							response.getState().get().getDisplayName()));
					}
					else
					{
						this.output.println((String.format(
							"Resource %s is non-existant",
							resource.get().getResourceId())));
					}
				}
				catch (AssertionFailedException | IndeterminateStateException e)
				{
					this.output.println(e.getMessage());
				}
				catch (PluginNotFoundException e)
				{
					this.output.println(OutputFormatter.pluginNotFound(e));
				}
			}
		}
	}

	private void pluginsCommand()
	{
		String xml = this.wildebeestApi.describePlugins();
		this.output.println(xml);
		return;
	}
}
