package co.mv.wb.fixture;

import co.mv.wb.Instance;
import co.mv.wb.Resource;
import co.mv.wb.WildebeestApi;
import co.mv.wb.cli.WildebeestCommand;
import co.mv.wb.event.EventSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

/**
 * A test context providing an instance of WildebeestCommand with a mocked WildebeestApi configured to return a fake
 * Resource from loadResource and a FakeInstance from loadInstance.
 *
 * @since 4.0
 */
public class TestContext_WildebeestCommandUnit
{
	public final EventSink eventSink;
	public final Resource fakeResource;
	public final Instance fakeInstance;
	public final WildebeestApi wildebeestApi;
	public final WildebeestCommand wildebeestCommand;
	private static final Logger LOG = LoggerFactory.getLogger("wildebeestCommandLogger");

	public static TestContext_WildebeestCommandUnit get()
	{
		return new TestContext_WildebeestCommandUnit();
	}

	private TestContext_WildebeestCommandUnit()
	{
		this.eventSink = (event) -> {if(event.getMessage().isPresent()) LOG.info(event.getMessage().get());};

		this.fakeResource = Fixtures.fakeResource();
		this.fakeInstance = Fixtures.fakeInstance();

		this.wildebeestApi = Fixtures
			.wildebeestApi()
			.loadResourceReturns(fakeResource)
			.loadInstanceReturns(fakeInstance)
			.get();

		this.wildebeestCommand = new WildebeestCommand(
			eventSink,
			wildebeestApi);
	}
}
