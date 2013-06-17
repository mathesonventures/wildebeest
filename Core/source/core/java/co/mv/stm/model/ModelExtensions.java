package co.mv.stm.model;

public class ModelExtensions
{
	public static <T> T As(
		Object value,
		Class<T> type)
	{
		if (value == null) { throw new IllegalArgumentException("value"); }
		if (type == null) { throw new IllegalArgumentException("type"); }
		
		T result = null;
		
		if (type.isAssignableFrom(value.getClass()))
		{
			result = (T)value;
		}
		
		return result;
	}
}