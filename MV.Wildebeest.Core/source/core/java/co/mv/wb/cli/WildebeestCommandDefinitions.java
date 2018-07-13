package co.mv.wb.cli;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;


public class WildebeestCommandParsing
{
	@Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
	boolean usageHelpRequested;

	@Option(names = {"-t", "--test"}, description = "test message")
	boolean test;


	@Parameters
	List<String> positional;

}

