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
import co.mv.wb.Instance;
import co.mv.wb.Interface;
import co.mv.wb.Logger;
import co.mv.wb.MigrationPlugin;
import co.mv.wb.PrintStreamLogger;
import co.mv.wb.Resource;
import co.mv.wb.ResourceHelper;
import co.mv.wb.ResourcePlugin;
import co.mv.wb.ResourceType;
import co.mv.wb.impl.FactoryResourceTypes;
import co.mv.wb.impl.ResourceHelperImpl;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.ansisql.AnsiSqlDropDatabaseMigration;
import co.mv.wb.plugin.ansisql.AnsiSqlDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.composite.ExternalResourceMigration;
import co.mv.wb.plugin.composite.ExternalResourceMigrationPlugin;
import co.mv.wb.plugin.database.SqlScriptMigration;
import co.mv.wb.plugin.database.SqlScriptMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlCreateDatabaseMigration;
import co.mv.wb.plugin.mysql.MySqlCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.mysql.MySqlDatabaseResourcePlugin;
import co.mv.wb.plugin.mysql.MySqlDropDatabaseMigration;
import co.mv.wb.plugin.postgresql.PostgreSqlDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerCreateDatabaseMigration;
import co.mv.wb.plugin.sqlserver.SqlServerCreateDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerCreateSchemaMigration;
import co.mv.wb.plugin.sqlserver.SqlServerCreateSchemaMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDatabaseResourcePlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropDatabaseMigrationPlugin;
import co.mv.wb.plugin.sqlserver.SqlServerDropSchemaMigration;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Wildebeest command-line interface.  WildebeestCommand parses command-line invocations, and delegates to
 * {@link co.mv.wb.Interface} to carry out the command.
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

	private Logger _logger = null;
	private boolean _logger_set = false;

	public final Logger getLogger() {
		if(!_logger_set) {
			throw new IllegalStateException("logger not set.  Use the HasLogger() method to check its state before accessing it.");
		}
		return _logger;
	}

	public final void setLogger(
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

			ResourceHelper resourceHelper = new ResourceHelperImpl();
			Map<ResourceType, ResourcePlugin> resourcePlugins = WildebeestCommand.getResourcePlugins(
				resourceHelper);
			Map<Class, MigrationPlugin> migrationPlugins = WildebeestCommand.getMigrationPlugins(
				resourceHelper);

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
					Interface iface = new Interface(
						this.getLogger(),
						resourcePlugins,
						migrationPlugins,
						resourceHelper);
					iface.state(resourceFileName, instanceFileName);
				}
			}

			else if ("migrate".equals(command))
			{
				String resourceFileName = WildebeestCommand.getArg(args, "r", "resource");
				String instanceFileName = WildebeestCommand.getArg(args, "i", "instance");
				Optional<String> targetState = WildebeestCommand.getOptionalArg(args, "t", "targetState");

				if (isNullOrWhiteSpace(resourceFileName) || isNullOrWhiteSpace(instanceFileName))
				{
					WildebeestCommand.printUsage(System.out);
				}
				else
				{
					Interface iface = new Interface(
						this.getLogger(),
						resourcePlugins,
						migrationPlugins,
						resourceHelper);

					Resource resource = iface.tryLoadResource(
						resourceFileName);

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
					Interface iface = new Interface(
						this.getLogger(),
						resourcePlugins,
						migrationPlugins,
						resourceHelper);

					Resource resource = iface.tryLoadResource(
						resourceFileName);
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

	private static Map<ResourceType, ResourcePlugin> getResourcePlugins(
		ResourceHelper resourceHelper)
	{
		if (resourceHelper == null) { throw new IllegalArgumentException("resourceHelper cannot be null"); }

		Map<ResourceType, ResourcePlugin> result = new HashMap<>();

		result.put(FactoryResourceTypes.MySqlDatabase, new MySqlDatabaseResourcePlugin(
			resourceHelper));
		result.put(FactoryResourceTypes.PostgreSqlDatabase, new PostgreSqlDatabaseResourcePlugin(
			resourceHelper));
		result.put(FactoryResourceTypes.SqlServerDatabase, new SqlServerDatabaseResourcePlugin(
			resourceHelper));

		return result;
	}

	private static Map<Class, MigrationPlugin> getMigrationPlugins(
		ResourceHelper resourceHelper)
	{
		if (resourceHelper == null) { throw new IllegalArgumentException("resourceHelper cannot be null"); }

		Map<Class, MigrationPlugin> result = new HashMap<>();

		// ansisql
		result.put(AnsiSqlCreateDatabaseMigration.class, new AnsiSqlCreateDatabaseMigrationPlugin());
		result.put(AnsiSqlDropDatabaseMigration.class, new AnsiSqlDropDatabaseMigrationPlugin());

		// composite
		result.put(ExternalResourceMigration.class, new ExternalResourceMigrationPlugin(
			result,
			resourceHelper));

		// database
		result.put(SqlScriptMigration.class, new SqlScriptMigrationPlugin());

		// mysql
		result.put(MySqlCreateDatabaseMigration.class, new MySqlCreateDatabaseMigrationPlugin());
		result.put(MySqlDropDatabaseMigration.class, new MySqlCreateDatabaseMigrationPlugin());

		// sqlserver
		result.put(SqlServerCreateDatabaseMigration.class, new SqlServerCreateDatabaseMigrationPlugin());
		result.put(SqlServerCreateSchemaMigration.class, new SqlServerCreateSchemaMigrationPlugin());
		result.put(SqlServerDropSchemaMigration.class, new SqlServerDropDatabaseMigrationPlugin());

		return result;
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
