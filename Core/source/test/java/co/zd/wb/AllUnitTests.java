package co.zd.wb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author brendonm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.zd.wb.cli.WildebeestCommandTests.class,
	co.zd.wb.model.base.BaseResourceTests.class,
	co.zd.wb.model.database.RowDoesNotExistAssertionTests.class,
	co.zd.wb.model.database.RowExistsAssertionTests.class,
	co.zd.wb.model.mysql.MySqlCreateDatabaseMigrationTests.class,
	co.zd.wb.model.mysql.MySqlDatabaseDoesNotExistAssertionTests.class,
	co.zd.wb.model.mysql.MySqlDatabaseExistsAssertionTests.class,
	co.zd.wb.model.mysql.MySqlDatabaseResourceTests.class,
	co.zd.wb.model.mysql.MySqlTableDoesNotExistAssertionTests.class,
	co.zd.wb.model.mysql.MySqlTableExistsAssertionTests.class,
	co.zd.wb.service.dom.DomInstanceLoaderTests.class,
	co.zd.wb.service.dom.DomResourceLoaderTests.class
})
public class AllUnitTests
{
}