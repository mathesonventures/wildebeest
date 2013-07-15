package co.zd.wb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.zd.wb.IntegrationTests.class,
	co.zd.wb.model.base.ResourceLoaderIntegrationTests.class,
	co.zd.wb.model.mysql.IntegrationTests.class
})
public class AllIntegrationTests
{
}