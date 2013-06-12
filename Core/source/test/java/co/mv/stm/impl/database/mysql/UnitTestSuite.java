/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mv.stm.impl.database.mysql;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author brendonm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.mv.stm.impl.database.mysql.MySqlCreateDatabaseTransitionTests.class,
	co.mv.stm.impl.database.mysql.MySqlDatabaseResourceTests.class,
	co.mv.stm.impl.database.mysql.RowDoesNotExistAssertionTests.class,
	co.mv.stm.impl.database.mysql.MySqlTableDoesNotExistAssertionTests.class,
	co.mv.stm.impl.database.mysql.MySqlDatabaseDoesNotExistAssertionTests.class,
	co.mv.stm.impl.database.mysql.MySqlCreateDatabaseTransitionTests.class,
	co.mv.stm.impl.database.mysql.MySqlDatabaseExistsAssertionTests.class,
	co.mv.stm.impl.database.mysql.MySqlTableExistsAssertionTests.class,
	co.mv.stm.impl.database.mysql.IntegrationTests.class,
	co.mv.stm.impl.database.mysql.SqlScriptTransitionTests.class,
})
public class UnitTestSuite
{
}