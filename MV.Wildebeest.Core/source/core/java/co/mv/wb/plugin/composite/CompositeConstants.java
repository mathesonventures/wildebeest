package co.mv.wb.plugin.composite;

import co.mv.wb.PluginGroup;

public class CompositeConstants
{
	private CompositeConstants()
	{
	}

	public static final PluginGroup CompositeResourcePluginGroup = new PluginGroup(
		"co.mv.wb:Composite",
		"Composite Resource",
		"Works with higher-order resources composed of multiple other Wildebeest resources");
}
