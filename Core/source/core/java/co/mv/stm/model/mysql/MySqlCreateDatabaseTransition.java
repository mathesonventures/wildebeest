package co.mv.stm.model.mysql;

import co.mv.stm.model.base.BaseTransition;
import co.mv.stm.model.database.DatabaseHelper;
import co.mv.stm.model.ModelExtensions;
import co.mv.stm.model.Instance;
import co.mv.stm.model.Transition;
import co.mv.stm.model.TransitionFailedException;
import co.mv.stm.model.TransitionFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class MySqlCreateDatabaseTransition extends BaseTransition implements Transition
{
	public MySqlCreateDatabaseTransition(
		UUID transitionId,
		UUID fromStateId,
		UUID toStateId)
	{
		super(transitionId, fromStateId, toStateId);
	}

	@Override public void perform(Instance instance) throws TransitionFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		MySqlDatabaseInstance db = ModelExtensions.As(instance, MySqlDatabaseInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseInstance"); }

		if (MySqlDatabaseHelper.schemaExists(db, db.getSchemaName()))
		{
			throw new TransitionFailedException(
				this.getTransitionId(),
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
			throw new TransitionFaultException(e);
		}
	}
}
