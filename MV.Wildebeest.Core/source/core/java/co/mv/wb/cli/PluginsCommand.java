package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "plugins",
	description = "Plugins command description.",
	subcommands = CommandLine.HelpCommand.class,
	hidden = true)
public class PluginsCommand
{
}

