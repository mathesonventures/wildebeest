package co.mv.wb.cli;

import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(synopsisHeading = "%nUsage:%n%n",
	  descriptionHeading = "%nDescription:%n%n",
	  parameterListHeading = "%nParameters:%n%n",
	  optionListHeading = "%nOptions:%n%n",
	  separator = " ")
public class SharedCommands
{

	@CommandLine.Option(names = {"-r", "--resource"}, description = "Resource file", required = true)
	String resource;

	@CommandLine.Option(names = {"-i", "--instance"}, description = "Instance file", required = true)
	String instance;

}

