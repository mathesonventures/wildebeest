package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "jumpstate",
	description = " Tell Wildebeest that a resource instance is in a particular state",
	subcommands = CommandLine.HelpCommand.class)
public class JumpStateCommand extends SharedCommands
{

	@CommandLine.Option(names = {"-t", "--target-state"}, description = "Target state")
	String targetState;
}
