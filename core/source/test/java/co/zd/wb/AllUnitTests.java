// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

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