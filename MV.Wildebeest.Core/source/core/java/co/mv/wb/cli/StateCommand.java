package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "state",  description = "state description")
public class StateCommand
{
	@CommandLine.Option(names = {"-r", "--resource"}, description = "Resource file", required = true)
	String resource;

	@CommandLine.Option(names = {"-i", "--instance"}, description = "Instance file", required = true)
	String instance;
}
