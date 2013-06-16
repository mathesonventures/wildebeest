/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mv.stm.model.mysql;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author brendonm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.mv.stm.model.mysql.MySqlCreateDatabaseTransitionTests.class,
	co.mv.stm.model.mysql.MySqlDatabaseDoesNotExistAssertionTests.class,
	co.mv.stm.model.mysql.MySqlDatabaseExistsAssertionTests.class,
	co.mv.stm.model.mysql.MySqlDatabaseResourceTests.class,
	co.mv.stm.model.mysql.MySqlTableDoesNotExistAssertionTests.class,
	co.mv.stm.model.mysql.MySqlTableExistsAssertionTests.class,
	co.mv.stm.model.database.RowDoesNotExistAssertionTests.class,
	co.mv.stm.model.mysql.SqlScriptTransitionTests.class,
})
public class UnitTestSuite
{
}