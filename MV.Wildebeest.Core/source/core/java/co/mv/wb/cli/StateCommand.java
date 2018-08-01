package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "state",
	description = "Check on the current state of a resource",
	subcommands = CommandLine.HelpCommand.class)
public class StateCommand extends SharedCommands
{
}
