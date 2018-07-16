package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(synopsisHeading = "%nUsage:%n%n",
	  descriptionHeading = "%nDescription:%n%n",
	  parameterListHeading = "%nParameters:%n%n",
	  optionListHeading = "%nOptions:%n%n",
	  commandListHeading = "%nCommands:%n%n")
public class SharedCommands
{
	@CommandLine.Option(names = {"-r", "--resource"}, description = "Resource file")
	String resource;

	@CommandLine.Option(names = {"-i", "--instance"}, description = "Instance file")
	String instance;

	@CommandLine.Option(names = {"--targetState"}, description = "Target state")
	String targetState;
}

