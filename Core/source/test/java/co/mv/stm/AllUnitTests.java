package co.mv.stm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author brendonm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.mv.stm.cli.WildebeestCommandTests.class,
	co.mv.stm.model.base.BaseResourceTests.class,
	co.mv.stm.model.database.RowDoesNotExistAssertionTests.class,
	co.mv.stm.model.database.RowExistsAssertionTests.class,
	co.mv.stm.model.mysql.MySqlCreateDatabaseMigrationTests.class,
	co.mv.stm.model.mysql.MySqlDatabaseDoesNotExistAssertionTests.class,
	co.mv.stm.model.mysql.MySqlDatabaseExistsAssertionTests.class,
	co.mv.stm.model.mysql.MySqlDatabaseResourceTests.class,
	co.mv.stm.model.mysql.MySqlTableDoesNotExistAssertionTests.class,
	co.mv.stm.model.mysql.MySqlTableExistsAssertionTests.class,
	co.mv.stm.service.dom.DomInstanceLoaderTests.class,
	co.mv.stm.service.dom.DomResourceLoaderTests.class
})
public class AllUnitTests
{
}