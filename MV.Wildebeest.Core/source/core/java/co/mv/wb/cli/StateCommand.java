package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "state")
public class StateCommand
{
	@CommandLine.Option(names = {"-r", "--resource"}, description = "Resource file")
	String resource;

	@CommandLine.Option(names = {"-i", "--instance"}, description = "Instance file")
	String instance;
}
