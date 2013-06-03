package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.BaseResource;
import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.model.AssertionFailedException;
import co.mv.stm.model.AssertionResult;
import co.mv.stm.model.IndeterminateStateException;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.Resource;
import co.mv.stm.model.ResourceInstance;
import co.mv.stm.model.ResourceType;
import co.mv.stm.model.FaultException;
import co.mv.stm.model.State;
import co.mv.stm.model.TransitionNotPossibleException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MySqlDatabaseResource extends BaseResource implements Resource
{

	//
	// Properties
	//
	
	// <editor-fold desc="ResourceId" defaultstate="collapsed">

	private UUID m_resourceId = null;
	private boolean m_resourceId_set = false;

	public UUID getResourceId() {
		if(!m_resourceId_set) {
			throw new IllegalStateException("resourceId not set.  Use the HasResourceId() method to check its state before accessing it.");
		}
		return m_resourceId;
	}

	private void setResourceId(
		UUID value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceId cannot be null");
		}
		boolean changing = !m_resourceId_set || m_resourceId != value;
		if(changing) {
			m_resourceId_set = true;
			m_resourceId = value;
		}
	}

	private void clearResourceId() {
		if(m_resourceId_set) {
			m_resourceId_set = true;
			m_resourceId = null;
		}
	}

	private boolean hasResourceId() {
		return m_resourceId_set;
	}

	// </editor-fold>

	// <editor-fold desc="Name" defaultstate="collapsed">

	private String m_name = null;
	private boolean m_name_set = false;

	public String getName() {
		if(!m_name_set) {
			throw new IllegalStateException("name not set.  Use the HasName() method to check its state before accessing it.");
		}
		return m_name;
	}

	private void setName(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		boolean changing = !m_name_set || m_name != value;
		if(changing) {
			m_name_set = true;
			m_name = value;
		}
	}

	private void clearName() {
		if(m_name_set) {
			m_name_set = true;
			m_name = null;
		}
	}

	private boolean hasName() {
		return m_name_set;
	}

	// </editor-fold>

	// <editor-fold desc="ResourceType" defaultstate="collapsed">

	private ResourceType m_resourceType = null;
	private boolean m_resourceType_set = false;

	public ResourceType getResourceType() {
		if(!m_resourceType_set) {
			throw new IllegalStateException("resourceType not set.  Use the HasResourceType() method to check its state before accessing it.");
		}
		return m_resourceType;
	}

	private void setResourceType(
		ResourceType value) {
		if(value == null) {
			throw new IllegalArgumentException("resourceType cannot be null");
		}
		boolean changing = !m_resourceType_set || m_resourceType != value;
		if(changing) {
			m_resourceType_set = true;
			m_resourceType = value;
		}
	}

	private void clearResourceType() {
		if(m_resourceType_set) {
			m_resourceType_set = true;
			m_resourceType = null;
		}
	}

	private boolean hasResourceType() {
		return m_resourceType_set;
	}

	// </editor-fold>
	
	//
	// Behaviour
	//

	@Override public UUID currentState(ResourceInstance instance) throws IndeterminateStateException
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		MySqlDatabaseResourceInstance db = ModelExtensions.As(instance, MySqlDatabaseResourceInstance.class);
		if (db == null)
		{
			throw new IllegalArgumentException("instance must be a DatabaseResourceInstance");
		}

		UUID declaredStateId = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (MySqlDatabaseHelper.schemaExists(db, db.getSchemaName()))
		{
			String stateTableName = db.hasStateTableName() ? db.getStateTableName() : Constants.DefaultStateTableName;

			try
			{
				conn = db.getAppDataSource().getConnection();
				ps = conn.prepareStatement("SELECT StateId FROM " + stateTableName + ";");
				rs = ps.executeQuery();

				if (rs.next())
				{
					declaredStateId = UUID.fromString(rs.getString(1));

					if (rs.next())
					{
						throw new IndeterminateStateException(String.format(
							"Multiple rows found in the state tracking table \"%s\"",
							stateTableName));
					}
				}
				else
				{
					throw new IndeterminateStateException(String.format(
						"The state tracking table \"%s\" was not found in the target schema",
						stateTableName));
				}
			}
			catch(SQLException e)
			{
				throw new FaultException(e);
			}
			finally
			{
				try
				{
					DatabaseHelper.release(rs);
					DatabaseHelper.release(ps);
					DatabaseHelper.release(conn);
				}
				catch(SQLException e)
				{
					throw new FaultException(e);
				}
			}
		}
		
		// If we found a declared state, check that the state is actually defined
		UUID result = null;
		if (declaredStateId != null)
		{
			for (State check : this.getStates())
			{
				if (declaredStateId.equals(check.getStateId()))
				{
					result = declaredStateId;
					break;
				}
			}

			// If the declared state ID is not known, throw
			if (result == null)
			{
				throw new IndeterminateStateException(String.format(
					"The resource is declared to be in state %s, but this state is not defined for this resource",
					declaredStateId.toString()));
			}
		}
		
		return result;
	}

	@Override public List<AssertionResult> assertState(ResourceInstance instance) throws IndeterminateStateException
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override public void transitionTo(
		ResourceInstance instance,
		UUID targetStateId) throws
			IndeterminateStateException,
			AssertionFailedException,
			TransitionNotPossibleException
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}