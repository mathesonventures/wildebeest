package co.mv.stm.impl.database.mysql;

import co.mv.stm.impl.BaseTransition;
import co.mv.stm.impl.database.DatabaseHelper;
import co.mv.stm.ModelExtensions;
import co.mv.stm.ResourceInstance;
import co.mv.stm.Transition;
import co.mv.stm.TransitionFailedException;
import co.mv.stm.TransitionFaultException;
import java.sql.SQLException;
import java.util.UUID;

public class MySqlCreateDatabaseTransition extends BaseTransition implements Transition
{
	public MySqlCreateDatabaseTransition(
		UUID transitionId,
		UUID toStateId)
	{
		super(transitionId, toStateId);
	}
	
	public MySqlCreateDatabaseTransition(
		UUID transitionId,
		UUID fromStateId,
		UUID toStateId)
	{
		super(transitionId, fromStateId, toStateId);
	}

	@Override public void perform(ResourceInstance instance) throws TransitionFailedException
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		MySqlDatabaseResourceInstance db = ModelExtensions.As(instance, MySqlDatabaseResourceInstance.class);
		if (db == null) { throw new IllegalArgumentException("instance must be a MySqlDatabaseResourceInstance"); }

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
