package co.zd.wb.model.database;

public class Extensions
{
	public static String getStateTableName(
		DatabaseInstance instance)
	{
		if (instance == null) { throw new IllegalArgumentException("instance cannot be null"); }
		
		return instance.hasStateTableName() ? instance.getStateTableName() : Constants.DefaultStateTableName;
	}
}
