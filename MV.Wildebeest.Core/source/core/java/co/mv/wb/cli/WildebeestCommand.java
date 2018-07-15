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
import co.mv.wb.Wildebeest;
import co.mv.wb.WildebeestApi;
import co.mv.wb.XmlValidationException;
import co.mv.wb.event.CommandLineEvent;
import co.mv.wb.event.EventSink;
import co.mv.wb.framework.ArgumentNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

/**
 * The Wildebeest command-line interface.  WildebeestCommand parses command-line invocations, and delegates to
 * {@link WildebeestApi} to carry out the command.
 *
 * @since 1.0
 */
public class WildebeestCommand
{
	private final WildebeestApi wildebeestApi;
	private static final Logger LOG = LoggerFactory.getLogger("wildebeestCommandLogger");
	private final EventSink eventSink;

	/**
	 * The main entry point for the command-line interface.
	 *
	 * @param args the arguments supplied on the command-line invocation
	 * @since 1.0
	 */
	public static void main(String[] args)
	{
		EventSink eventSink = (event) -> {if(event.getMessage().isPresent()) LOG.info(event.getMessage().get());};

		WildebeestApi wildebeestApi = Wildebeest
			.wildebeestApi(eventSink)
			.withFactoryPluginGroups()
			.withFactoryResourcePlugins()
			.withFactoryMigrationPlugins()
			.get();

		WildebeestCommand wb = new WildebeestCommand(
			eventSink,
			wildebeestApi);

		wb.run(args);
	}

	/**
	 * Creates a new WildebeestCommand instance.
	 *
	 * @since 1.0
	 */
	public WildebeestCommand(
		EventSink eventSink,
		WildebeestApi wildebeestApi)
	{
		if (eventSink == null) throw new ArgumentNullException("eventSink");
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");

		this.eventSink = eventSink;
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
			eventSink.onEvent(CommandLineEvent.invalidArgument(getBanner()));
		}

		else
		{
			String command = args[0];

			if ("about".equals(command))
			{
				eventSink.onEvent(CommandLineEvent.aboutStart(Optional.empty()));

				About about = new About();
				String aboutMsg = about.getProjectName() + " " + about.getVersionFullDotted() + "\n" +
				about.getCopyrightAssertion();

				eventSink.onEvent(CommandLineEvent.aboutFinish(aboutMsg));
			}

			else if ("state".equals(command))
			{
				String resourceFilename = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFilename = WildebeestCommand.getArg(args, "i", "instance");

				if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename))
				{
					eventSink.onEvent(CommandLineEvent.invalidArgument(getBanner()));
				}
				else
				{
					Optional<Resource> resource = WildebeestCommand.tryLoadResource(
						this.wildebeestApi,
						resourceFilename,
						this.eventSink);

					Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
						this.wildebeestApi,
						instanceFilename,
						this.eventSink);

					if (resource.isPresent() && instance.isPresent())
					{
						try
						{
							this.wildebeestApi.state(
								resource.get(),
								instance.get());
						}
						catch (IndeterminateStateException | AssertionFailedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.stateCheckingFailed(e.getMessage()));
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
					eventSink.onEvent(CommandLineEvent.invalidArgument(getBanner()));
				}
				else
				{
					Optional<Resource> resource = WildebeestCommand.tryLoadResource(
						this.wildebeestApi,
						resourceFilename,
						this.eventSink);

					Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
						this.wildebeestApi,
						instanceFilename,
						this.eventSink);

					if (resource.isPresent() && instance.isPresent())
					{
						try
						{
							this.wildebeestApi.migrate(
								resource.get(),
								instance.get(),
								targetState);
						}
						catch (TargetNotSpecifiedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.targetNotSpecified(e)));
						}
						catch (UnknownStateSpecifiedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.unknownStateSpecified(e)));
						}
						catch (InvalidStateSpecifiedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.invalidStateSpecified(e)));
						}
						catch (MigrationNotPossibleException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.migrationNotPossible(e)));
						}
						catch (IndeterminateStateException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.indeterminateState(e)));
						}
						catch (MigrationFailedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.migrationFailed(e)));
						}
						catch (AssertionFailedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.assertionFailed(e)));
						}
						catch (InvalidReferenceException e)
						{
							this.eventSink.onEvent(CommandLineEvent.migrateFailed(OutputFormatter.invalidReferenceException(e)));
						}
					}
				}
			}

			else if ("jumpstate".equals(command))
			{
				String resourceFilename = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFilename = WildebeestCommand.getArg(args, "i", "instance");
				String targetState = WildebeestCommand.getArg(args, "t", "targetState");

				if (isNullOrWhiteSpace(resourceFilename) || isNullOrWhiteSpace(instanceFilename) ||
					isNull(targetState))
				{
					eventSink.onEvent(CommandLineEvent.invalidArgument(getBanner()));
				}
				else
				{
					Optional<Resource> resource = WildebeestCommand.tryLoadResource(
						this.wildebeestApi,
						resourceFilename,
						this.eventSink);

					Optional<Instance> instance = WildebeestCommand.tryLoadInstance(
						this.wildebeestApi,
						instanceFilename,
						this.eventSink);

					if (resource.isPresent() && instance.isPresent())
					{
						try
						{
							this.wildebeestApi.jumpstate(
								resource.get(),
								instance.get(),
								targetState);
						}
						catch (UnknownStateSpecifiedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.jumpStateFailed(OutputFormatter.unknownStateSpecified(e)));
						}
						catch (IndeterminateStateException e)
						{
							this.eventSink.onEvent(CommandLineEvent.jumpStateFailed(OutputFormatter.indeterminateState(e)));
						}
						catch (InvalidStateSpecifiedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.jumpStateFailed(OutputFormatter.invalidStateSpecified(e)));
						}
						catch (AssertionFailedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.jumpStateFailed(OutputFormatter.assertionFailed(e)));
						}
						catch (JumpStateFailedException e)
						{
							this.eventSink.onEvent(CommandLineEvent.jumpStateFailed(OutputFormatter.jumpStateFailed(e)));
						}
					}
				}
			}

			else if ("plugins".equals(command))
			{
				eventSink.onEvent(CommandLineEvent.pluginsStart(Optional.empty()));
				String xml = this.wildebeestApi.describePlugins();
				this.eventSink.onEvent(CommandLineEvent.pluginsFinish(xml));
			}

			else
			{
				eventSink.onEvent(CommandLineEvent.invalidArgument(getBanner()));
			}
		}
	}

	private static Optional<Resource> tryLoadResource(
		WildebeestApi wildebeestApi,
		String resourceFilename,
		EventSink eventSink)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");
		if (resourceFilename == null) throw new ArgumentNullException("resourceFilename");
		if (eventSink == null) throw new ArgumentNullException("eventSink");

		File resourceFile = new File(resourceFilename);

		Resource resource = null;

		try
		{
			resource = wildebeestApi.loadResource(resourceFile);
		}
		catch (FileLoadException e)
		{
			eventSink.onEvent(CommandLineEvent.loadResourceFailed(OutputFormatter.fileLoad(e, "resource")));
		}
		catch (LoaderFault e)
		{
			eventSink.onEvent(CommandLineEvent.loadResourceFailed(OutputFormatter.loaderFault(e, "resource")));
		}
		catch (PluginBuildException e)
		{
			eventSink.onEvent(CommandLineEvent.loadResourceFailed(OutputFormatter.pluginBuild(e)));
		}
		catch (XmlValidationException e)
		{
			eventSink.onEvent(CommandLineEvent.loadResourceFailed(OutputFormatter.resourceValidation(e, "resource")));
		}
		catch (InvalidReferenceException e)
		{
			eventSink.onEvent(CommandLineEvent.loadResourceFailed(OutputFormatter.missingReference(e)));
		}

		return Optional.ofNullable(resource);
	}

	private static Optional<Instance> tryLoadInstance(
		WildebeestApi wildebeestApi,
		String instanceFilename,
		EventSink eventSink)
	{
		if (wildebeestApi == null) throw new ArgumentNullException("wildebeestApi");
		if (instanceFilename == null) throw new ArgumentNullException("instanceFilename");
		if (eventSink == null) throw new ArgumentNullException("eventSink");

		File instanceFile = new File(instanceFilename);

		Instance instance = null;

		try
		{
			instance = wildebeestApi.loadInstance(instanceFile);
		}
		catch (FileLoadException e)
		{
			eventSink.onEvent(CommandLineEvent.loadInstanceFailed(OutputFormatter.fileLoad(e, "instance")));
		}
		catch (LoaderFault e)
		{
			eventSink.onEvent(CommandLineEvent.loadInstanceFailed(OutputFormatter.loaderFault(e, "instance")));
		}
		catch (PluginBuildException e)
		{
			eventSink.onEvent(CommandLineEvent.loadInstanceFailed(OutputFormatter.pluginBuild(e)));
		}
		catch (XmlValidationException e)
		{
			eventSink.onEvent(CommandLineEvent.loadInstanceFailed(OutputFormatter.resourceValidation(e, "instance")));
		}

		return Optional.ofNullable(instance);
	}

	private static String getArg(
		String[] args,
		String shortName,
		String longName)
	{
		if (args == null) throw new ArgumentNullException("args");
		if (shortName == null) throw new ArgumentNullException("shortName");
		if ("".equals(shortName)) throw new IllegalArgumentException("shortName cannot be empty");
		if (longName == null) throw new ArgumentNullException("longName");
		if ("".equals(longName)) throw new IllegalArgumentException("longName cannot be empty");

		shortName = "-" + shortName + ":";
		longName = "--" + longName + ":";

		String result = null;

		for (String arg : args)
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
		if (args == null) throw new ArgumentNullException("args");
		if (shortName == null) throw new ArgumentNullException("shortName");
		if ("".equals(shortName)) throw new IllegalArgumentException("shortName cannot be empty");
		if (longName == null) throw new ArgumentNullException("longName");
		if ("".equals(longName)) throw new IllegalArgumentException("longName cannot be empty");

		shortName = "-" + shortName + ":";
		longName = "--" + longName + ":";

		Optional<String> result = Optional.empty();

		for (String arg : args)
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
		return value == null || "".equals(value.trim());
	}

	private static String getBanner()
	{
		String banner =
		"            _  _     _       _\n" +
		"           |_|| |   | |     | |                      _\n"+
		" __      __ _ | | __| | ___ | |__   ___   ___  ___ _| |_\n"+
		" \\ \\ /\\ / /| || |/ _` |/ _ \\| '_ \\ / _ \\ / _ \\/ __|_   _|\n"+
		"  \\ v  v / | || | (_| |  __/| |_) |  __/|  __/\\__ \\ | |\n"+
		"   \\_/\\_/  |_||_|\\__,_|\\___||_.__/ \\___| \\___||___/ |_|\n"+
		"\n";

		About about = new About();
		banner += "Version " + about.getVersionFullDotted() + ", " + about.getCopyrightAssertion() + "\n"+
		"\n";

		banner += "Usage: wb command [options]\n"+
			"\n"+
			"Valid commands: state; migrate; jumpstate;\n"+
			"\n";

		return banner;
	}
}
