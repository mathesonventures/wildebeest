package co.zd.wb.plugin.ansisql;

import co.zd.wb.Instance;
import co.zd.wb.MigrationFailedException;
import co.zd.wb.MigrationFaultException;
import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.plugin.base.BaseMigration;
import co.zd.wb.plugin.database.DatabaseHelper;
import co.zd.wb.plugin.database.DatabaseInstance;
import java.sql.SQLException;
import java.util.UUID;

public class AnsiSqlCreateDatabaseMigration extends BaseMigration
{
    public AnsiSqlCreateDatabaseMigration(
        UUID migrationId,
        UUID fromStateId,
        UUID toStateId)
    {
        super(migrationId, fromStateId, toStateId);
    }

    @Override public boolean canPerformOn(Resource resource)
    {
		if (resource == null) { throw new IllegalArgumentException("resource cannot be null"); }
		
		return ModelExtensions.As(resource, DatabaseInstance.class) != null;
    }

    @Override public void perform(Instance instance) throws MigrationFailedException
    {
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		DatabaseInstance db = ModelExtensions.As(instance, DatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a AnsiSqlDatabaseInstance"); }

		if (db.databaseExists())
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				String.format("database \"%s\" already exists",	db.getDatabaseName()));
		}
		
		try
		{
			DatabaseHelper.execute(
				db.getAdminDataSource(),
				String.format("CREATE DATABASE %s;", db.getDatabaseName()));
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
    }
}