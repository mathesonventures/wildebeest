package co.mv.stm.model;

public class ModelExtensions
{
	public static <T> T As(
		ResourceInstance instance,
		Class<T> type)
	{
		if (instance == null) { throw new IllegalArgumentException("instance"); }
		if (type == null) { throw new IllegalArgumentException("type"); }
		
		T result = null;
		
		if (type.isAssignableFrom(instance.getClass()))
		{
			result = (T)instance;
		}
		
		return result;
	}
}