package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "migrate",
	description = "Migrate an instance of a resource to a new state",
	subcommands = CommandLine.HelpCommand.class)
public class MigrateCommand extends SharedCommands
{
	@CommandLine.Option(names = {"-t", "--target-state"}, description = "Target state")
	String targetState;
}
