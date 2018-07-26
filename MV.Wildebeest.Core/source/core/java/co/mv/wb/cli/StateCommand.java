package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "state",
	description = "state description",
	subcommands = CommandLine.HelpCommand.class)
public class StateCommand extends SharedCommands
{
}
