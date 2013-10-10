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

package co.zd.wb.plugin.database;

import co.zd.wb.plugin.base.BaseAssertion;
import co.zd.wb.Assertion;
import co.zd.wb.AssertionFaultException;
import co.zd.wb.AssertionResponse;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Instance;
import co.zd.wb.plugin.base.ImmutableAssertionResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

/**
 * An {@link Assertion} that verifies that a given SQL query yields a single row.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.0
 */
public class RowExistsAssertion extends BaseAssertion implements Assertion
{
	/**
	 * Creates a new RowDoesNotExistAssertion.
	 * 
	 * @param       assertionId                 the ID of the assertion
	 * @param       description                 the description of the query that is being asserted
	 * @param       seqNum                      the ordinal index of the assertion within it's containing set
	 * @param       sql                         the query to be evaluated
	 */
	public RowExistsAssertion(
		UUID assertionId,
		String description,
		int seqNum,
		String sql)
	{
		super(assertionId, seqNum);
		this.setDescription(description);
		this.setSql(sql);
	}
	
	// <editor-fold desc="Description" defaultstate="collapsed">

	private String _description = null;
	private boolean _description_set = false;

	@Override public String getDescription() {
		if(!_description_set) {
			throw new IllegalStateException("description not set.  Use the HasDescription() method to check its state before accessing it.");
		}
		return _description;
	}

	private void setDescription(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("description cannot be null");
		}
		boolean changing = !_description_set || _description != value;
		if(changing) {
			_description_set = true;
			_description = value;
		}
	}

	private void clearDescription() {
		if(_description_set) {
			_description_set = true;
			_description = null;
		}
	}

	private boolean hasDescription() {
		return _description_set;
	}

	// </editor-fold>
	
	// <editor-fold desc="Sql" defaultstate="collapsed">

	private String m_sql = null;
	private boolean m_sql_set = false;

	private String getSql() {
		if(!m_sql_set) {
			throw new IllegalStateException("sql not set.  Use the HasSql() method to check its state before accessing it.");
		}
		return m_sql;
	}

	private void setSql(
		String value) {
		if(value == null) {
			throw new IllegalArgumentException("sql cannot be null");
		}
		boolean changing = !m_sql_set || m_sql != value;
		if(changing) {
			m_sql_set = true;
			m_sql = value;
		}
	}

	private void clearSql() {
		if(m_sql_set) {
			m_sql_set = true;
			m_sql = null;
		}
	}

	private boolean hasSql() {
		return m_sql_set;
	}

	// </editor-fold>
	
	@Override public AssertionResponse apply(Instance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a DatabaseInstance"); }

		AssertionResponse result = null;
		
		DataSource ds = db.getAppDataSource();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			try
			{
				conn = ds.getConnection();
				ps = conn.prepareStatement(this.getSql());
				rs = ps.executeQuery();

				int rowCount = 0;
				while(rs.next())
				{
					rowCount ++;
				}

				if (rowCount == 1)
				{
					result = new ImmutableAssertionResponse(true, "Exactly one row exists, as expected");
				}
				else
				{
					result = new ImmutableAssertionResponse(
						false,
						String.format("Expected to find exactly one row, but found %d", rowCount));
				}
			}
			finally
			{
				DatabaseHelper.release(rs);
				DatabaseHelper.release(ps);
				DatabaseHelper.release(conn);
			}
		}
		catch(SQLException e)
		{
			throw new AssertionFaultException(this.getAssertionId(), e);
		}
		
		return result;
	}
}