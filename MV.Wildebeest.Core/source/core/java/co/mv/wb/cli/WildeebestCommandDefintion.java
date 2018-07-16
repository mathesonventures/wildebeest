package co.mv.wb.cli;


import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(subcommands = {
	  MigrateCommand.class,
	  JumpStateCommand.class,
	  StateCommand.class
})
public class WildeebestCommandDefintion
{
	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
	boolean usageHelpRequested;
	
	public void parseArguments()
	{
		if (this.usageHelpRequested)
		{
			CommandLine.usage(new WildeebestCommandDefintion(), System.out);
			return;
		}
	}
}
