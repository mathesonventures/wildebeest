package co.mv.stm.cli;

import java.util.UUID;
import junit.framework.Assert;
import org.junit.Test;

public class WildebeestCommandTests
{
	@Test public void parseForValidStateCommandWithLongArgsSucceeds()
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(new String[] { "state", "--instance=foo" });
		
		Assert.assertEquals("wb.command", CommandType.State, wb.getCommand());
		Assert.assertEquals("wb.instance", "foo", wb.getInstance());
	}

	@Test public void parseForValidStateCommandWithShortArgsSucceeds()
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(new String[] { "state", "-i=foo" });
		
		Assert.assertEquals("wb.command", CommandType.State, wb.getCommand());
		Assert.assertEquals("wb.instance", "foo", wb.getInstance());
	}
	
	@Test public void parseForValidTransitionCommandByLabelWithLongArgsSucceeds()
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(new String[] { "transition", "--instance=foo", "--targetState=created" });
		
		Assert.assertEquals("wb.command", CommandType.Transition, wb.getCommand());
		Assert.assertEquals("wb.instance", "foo", wb.getInstance());
		Assert.assertEquals("wb.targetStateLabel", "created", wb.getTargetStateLabel());
	}

	@Test public void parseForValidTransitionCommandByLabelWithShortArgsSucceeds()
	{
		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(new String[] { "transition", "-i=foo", "-t=created" });
		
		Assert.assertEquals("wb.command", CommandType.Transition, wb.getCommand());
		Assert.assertEquals("wb.instance", "foo", wb.getInstance());
		Assert.assertEquals("wb.targetStateLabel", "created", wb.getTargetStateLabel());
	}
	
	@Test public void parseForValidTransitionCommandByIdWithLongArgsSucceeds()
	{
		UUID targetStateId = UUID.randomUUID();

		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(new String[] { "transition", "--instance=foo", "--targetState=" + targetStateId.toString() });
		
		Assert.assertEquals("wb.command", CommandType.Transition, wb.getCommand());
		Assert.assertEquals("wb.instance", "foo", wb.getInstance());
		Assert.assertEquals("wb.targetStateId", targetStateId, wb.getTargetStateId());
	}

	@Test public void parseForValidTransitionCommandByIdWithShortArgsSucceeds()
	{
		UUID targetStateId = UUID.randomUUID();

		WildebeestCommand wb = new WildebeestCommand();
		wb.parse(new String[] { "transition", "-i=foo", "-t=" + targetStateId.toString() });
		
		Assert.assertEquals("wb.command", CommandType.Transition, wb.getCommand());
		Assert.assertEquals("wb.instance", "foo", wb.getInstance());
		Assert.assertEquals("wb.targetStateId", targetStateId, wb.getTargetStateId());
	}
}