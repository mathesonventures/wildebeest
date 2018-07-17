package co.mv.wb.cli;

import picocli.CommandLine;

@CommandLine.Command(synopsisHeading = "%nUsage:%n%n",
	  descriptionHeading = "%nDescription:%n%n",
	  parameterListHeading = "%nParameters:%n%n",
	  optionListHeading = "%nOptions:%n%n",
	  commandListHeading = "%nCommands:%n%n",
	  separator = " ")
public class SharedCommands
{
	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
	boolean usageHelpRequested;

	@CommandLine.Option(names = {"-r", "--resource"}, description = "Resource file", required = true)
	String resource;

	@CommandLine.Option(names = {"-i", "--instance"}, description = "Instance file", required = true)
	String instance;

	@CommandLine.Option(names = {"-ts","--target-state"}, description = "Target state", required = true)
	String targetState;
}

