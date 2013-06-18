package co.mv.stm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.mv.stm.IntegrationTests.class,
	co.mv.stm.model.base.ResourceLoaderIntegrationTests.class,
	co.mv.stm.model.mysql.IntegrationTests.class
})
public class AllIntegrationTests
{
}