// Wildebeest Migration Framework
// Copyright 2013 - 2014, Zen Digital Co Inc
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

package co.zd.wb.cli;

import co.mv.protium.data.Db;
import co.mv.protium.eventing.EventRouter;
import co.mv.protium.eventing.routers.NoOpEventRouter;
import co.zd.wb.FakeLogger;
import co.zd.wb.Interface;
import co.zd.wb.Instance;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.plugin.mysql.MySqlDatabaseInstance;
import co.zd.wb.plugin.mysql.MySqlUtil;
import co.zd.wb.plugin.postgresql.PostgreSqlDatabaseInstance;
import co.zd.wb.plugin.sqlserver.SqlServerDatabaseInstance;
import co.zd.wb.plugin.sqlserver.SqlServerUtil;
import co.zd.wb.service.MessagesException;
import java.io.File;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class CliIntegrationTests
{
	@Test public void about()
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"about"
		};

		//
		// Execute
		//

		wb.run(args);

	}
	
	@Test public void invokeWithNoCommand() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();
		wb.run(new String[] { });

	}
	
	//
	// MySql
	//
	
	@Test public void mySqlDatabaseMigrate() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"migrate",
			"--resource:MySqlDatabase/database.wbresource.xml",
			"--instance:MySqlDatabase/staging_db.wbinstance.xml",
			"--targetState:Core Schema Loaded"
		};

        Instance instance = null;
        
		try
		{
            instance = Interface.loadInstance(new File("MySqlDatabase/staging_db.wbinstance.xml"));

            //
            // Execute
            //

			wb.run(args);
		}
		finally
		{
            if (instance != null)
            {
                MySqlDatabaseInstance instanceT = (MySqlDatabaseInstance)instance;
                MySqlUtil.dropDatabase(instanceT, instanceT.getDatabaseName());
            }
		}

	}
	
	@Test public void mySqlDatabaseMigrateWithMissingInstanceArg() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();

		//
		// Execute
		//

		wb.run(new String[]
		{
			"migrate",
			"--resource:MySqlDatabase/database.wbresource.xml",
			"--targetState:Core Schema Loaded"
		});

	}
	
	@Test public void mySqlDatabaseMigrateWithMissingResourceArg() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();

		//
		// Execute
		//

		wb.run(new String[]
		{
			"migrate",
			"--instance:MySqlDatabase/staging_db.wbinstance.xml",
			"--targetState:Core Schema Loaded"
		});

	}
	
	@Test public void mySqlDatabaseJumpState() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//
		
		EventRouter er = new NoOpEventRouter();
		WildebeestCommand wb = new WildebeestCommand();
        MySqlDatabaseInstance instanceT = null;
        
		try
		{
            Instance instance = Interface.loadInstance(new File("MySqlDatabase/staging_db.wbinstance.xml"));
            instanceT = (MySqlDatabaseInstance)instance;

            // Create a database that is already in a state that matches a defined state in a Wildebeest resource.
            //
            // For the sake of simplicity, we will use Wildebeest to migrate to a state, then drop it's wb_state tracking
            // table, and then do the jumpstate.
            wb.run(new String[]
            {
                "migrate",
                "--resource:MySqlDatabase/database.wbresource.xml",
                "--instance:MySqlDatabase/staging_db.wbinstance.xml",
                "--targetState:Core Schema Loaded"
            });

            // Drop the wb_state table, so the database resource is now no longer tracked by Wildebeest
            Db.nonQuery(er, instanceT.getAppDataSource(), "DROP TABLE wb_state;", null);

            //
            // Execute
            //

			wb.run(new String[]
			{
				"jumpstate",
				"--resource:MySqlDatabase/database.wbresource.xml",
				"--instance:MySqlDatabase/staging_db.wbinstance.xml",
				"--targetState:Core Schema Loaded"
			});
		}
		finally
		{
            if (instanceT != null)
            {
    			MySqlUtil.dropDatabase(instanceT, instanceT.getDatabaseName());
            }
		}

	}
	
	@Test public void mySqlDatabaseState() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();

        Instance instance = null;
        
		try
		{
            instance = Interface.loadInstance(new File("MySqlDatabase/staging_db.wbinstance.xml"));

			wb.run(new String[]
			{
				"migrate",
				"--resource:MySqlDatabase/database.wbresource.xml",
				"--instance:MySqlDatabase/staging_db.wbinstance.xml",
				"--targetState:Database Created"
			});
			
            //
            // Execute
            //

			wb.run(new String[]
			{
				"state",
				"--resource:MySqlDatabase/database.wbresource.xml",
				"--instance:MySqlDatabase/staging_db.wbinstance.xml"
			});
		}
		finally
		{
            if (instance != null)
            {
                MySqlDatabaseInstance instanceT = (MySqlDatabaseInstance)instance;
                MySqlUtil.dropDatabase(instanceT, instanceT.getDatabaseName());
            }
		}

	}
	
	@Test public void mySqlDatabaseMigrateToInvalidStateLabel() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//
		
        FakeLogger logger = new FakeLogger();
		WildebeestCommand wb = new WildebeestCommand();
        wb.setLogger(logger);

        //
        // Execute
        //
        
        wb.run(new String[]
        {
            "migrate",
            "--resource:MySqlDatabase/database.wbresource.xml",
            "--instance:MySqlDatabase/staging_db.wbinstance.xml",
            "--targetState:   "
        });

        assertTrue(
            "InvalidStateSpecifiedException expected to be thrown and logged",
            logger.hasInvalidStateSpecifiedException());
        assertEquals(
            "e.specifiedState",
            "   ",
            logger.getInvalidStateSpecifiedException().getSpecifiedState());
        
	}
	
	@Test public void mySqlDatabaseMigrateToUnknownStateLabel() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//
		
        FakeLogger logger = new FakeLogger();
		WildebeestCommand wb = new WildebeestCommand();
        wb.setLogger(logger);

        // Create a database that is already in a state that matches a defined state in a Wildebeest resource.
        //
        // For the sake of simplicity, we will use Wildebeest to migrate to a state, then drop it's wb_state tracking
        // table, and then do the jumpstate.
        wb.run(new String[]
        {
            "migrate",
            "--resource:MySqlDatabase/database.wbresource.xml",
            "--instance:MySqlDatabase/staging_db.wbinstance.xml",
            "--targetState:Foo"
        });

        assertTrue(
            "UnknownStateSpecifiedException expected to be thrown and logged",
            logger.hasUnknownStateSpecifiedException());
        assertEquals(
            "e.specifiedState",
            "Foo",
            logger.getUnknownStateSpecifiedException().getSpecifiedState());
        
	}
	
	//
	// SqlServer
	//
	
	@Test public void sqlServerDatabaseMigrate() throws SQLException, MessagesException
	{
		
		//
		// Setup
		//
		
		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"migrate",
			"--resource:SqlServerDatabase/database.wbresource.xml",
			"--instance:SqlServerDatabase/staging_db.wbinstance.xml",
			"--targetState:Core Schema Loaded"
		};
		
        Instance instance = null;
        
		try
		{
            instance = Interface.loadInstance(new File("SqlServerDatabase/staging_db.wbinstance.xml"));

            //
            // Execute
            //

            wb.run(args);
		}
		finally
		{
            if (instance != null)
            {
                SqlServerDatabaseInstance instanceT = (SqlServerDatabaseInstance)instance;
                SqlServerUtil.tryDropDatabase(instanceT);
            }
		}

	}
	
	//
	// PostgreSql
	//
	
	@Test public void postgreSqlDatabaseMigrate() throws MessagesException
	{
		
		//
		// Setup
		//

		WildebeestCommand wb = new WildebeestCommand();
		String[] args = new String[]
		{
			"migrate",
			"--resource:PostgreSqlDatabase/database.wbresource.xml",
			"--instance:PostgreSqlDatabase/staging.wbinstance.xml",
			"--targetState:434fb1fd-e903-4b0f-a2b3-b1014360f799"
		};

        Instance instance = null;
        
		try
		{
            instance = Interface.loadInstance(new File("PostgreSqlDatabase/staging.wbinstance.xml"));

            //
            // Execute
            //

			wb.run(args);
		}
		finally
		{
            if (instance != null)
            {
				PostgreSqlDatabaseInstance instanceT = (PostgreSqlDatabaseInstance)instance;
				
				try
				{
					DatabaseHelper.execute(
						instanceT.getAdminDataSource(),
						String.format("DROP DATABASE %s", instanceT.getDatabaseName()));
				}
				catch (SQLException e)
				{
					throw new RuntimeException(e);
				}
            }
		}

	}
	
}