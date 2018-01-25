// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
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

package co.mv.wb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author brendonm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
	co.mv.wb.ansisql.AllUnitTests.class,
	co.mv.wb.plugin.base.AllUnitTests.class,
	co.mv.wb.plugin.database.AllUnitTests.class,
	co.mv.wb.plugin.mysql.AllUnitTests.class,
	co.mv.wb.postgresql.AllUnitTests.class,
	co.mv.wb.plugin.sqlserver.AllUnitTests.class,
	co.mv.wb.service.dom.AllUnitTests.class
})
public class AllUnitTests
{
}
