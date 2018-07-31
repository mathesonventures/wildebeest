package co.mv.wb.plugin.generaldatabase;

import co.mv.wb.Assertion;
import co.mv.wb.AssertionFaultException;
import co.mv.wb.AssertionPlugin;
import co.mv.wb.AssertionResponse;
import co.mv.wb.Instance;
import co.mv.wb.ModelExtensions;
import co.mv.wb.PluginHandler;
import co.mv.wb.framework.ArgumentNullException;
import co.mv.wb.framework.DatabaseHelper;
import co.mv.wb.plugin.base.ImmutableAssertionResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handler for {@link RowExistsAssertion}.
 *
 * @since 4.0
 */
@PluginHandler(
	uri = "co.mv.wb.generaldatabase:RowExists"
)
public class RowExistsAssertionPlugin implements AssertionPlugin
{
	@Override public AssertionResponse perform(
		Assertion assertion,
		Instance instance)
	{
		if (assertion == null) throw new ArgumentNullException("assertion");
		if (instance == null) throw new ArgumentNullException("instance");

		RowExistsAssertion assertionT = (RowExistsAssertion)assertion;

		DatabaseInstance db = ModelExtensions.as(instance, DatabaseInstance.class);
		if (db == null)
		{
			throw new IllegalArgumentException("instance must be a DatabaseInstance");
		}

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
				ps = conn.prepareStatement(assertionT.getSql());
				rs = ps.executeQuery();

				int rowCount = 0;
				while (rs.next())
				{
					rowCount++;
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
		catch (SQLException e)
		{
			throw new AssertionFaultException(assertionT.getAssertionId(), e);
		}

		return result;
	}
}
