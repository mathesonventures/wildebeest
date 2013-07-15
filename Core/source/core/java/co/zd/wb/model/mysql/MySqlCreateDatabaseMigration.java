package co.zd.wb.model.mysql;

import co.zd.wb.model.base.BaseMigration;
import co.zd.wb.model.database.DatabaseHelper;
import co.zd.wb.model.ModelExtensions;
import co.zd.wb.model.Instance;
import co.zd.wb.model.Migration;
import co.zd.wb.model.MigrationFailedException;
import co.zd.wb.model.MigrationFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class MySqlCreateDatabaseMigration extends BaseMigration implements Migration
{
	public MySqlCreateDatabaseMigration(
		UUID migrationId,
		UUID fromStateId,
		UUID toStateId)
	{
		super(migrationId, fromStateId, toStateId);
	}

	@Override public void perform(Instance instance) throws MigrationFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }

		if (MySqlDatabaseHelper.schemaExists(db, db.getSchemaName()))
		{
			throw new MigrationFailedException(
				this.getMigrationId(),
				String.format("database \"%s\" already exists",	db.getSchemaName()));
		}
		
		try
		{
			DatabaseHelper.execute(db.getInfoDataSource(), new StringBuilder()
				.append("CREATE DATABASE `").append(db.getSchemaName()).append("`;").toString());
			
			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("CREATE TABLE `StmState`(`StateId` char(36) NOT NULL, PRIMARY KEY (`StateId`));").toString());
			
			DatabaseHelper.execute(db.getAppDataSource(), new StringBuilder()
				.append("INSERT INTO `StmState`(StateId) VALUES('").append(this.getToStateId().toString())
				.append("');").toString());
		}
		catch (SQLException e)
		{
			throw new MigrationFaultException(e);
		}
	}
}
