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

package co.zd.wb.plugin.postgresql;

import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Instance;
import co.zd.wb.Logger;
import co.zd.wb.State;
import co.zd.wb.plugin.base.BaseResource;
import java.util.UUID;

/**
 * Defines a PostgreSQL database resource.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class PostgreSqlDatabaseResource extends BaseResource
{
    public PostgreSqlDatabaseResource(
        UUID resourceId,
        String name)
    {
        super(resourceId, name);
    }

    @Override public State currentState(Instance instance) throws IndeterminateStateException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override public void setStateId(Logger logger, Instance instance, UUID stateId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}