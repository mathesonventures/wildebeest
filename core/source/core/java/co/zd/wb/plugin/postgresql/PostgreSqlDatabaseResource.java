package co.zd.wb.plugin.postgresql;

import co.zd.wb.IndeterminateStateException;
import co.zd.wb.Instance;
import co.zd.wb.Logger;
import co.zd.wb.State;
import co.zd.wb.plugin.base.BaseResource;
import java.util.UUID;

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