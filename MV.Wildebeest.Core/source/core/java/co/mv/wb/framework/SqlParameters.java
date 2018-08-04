package co.mv.wb.framework;

/**
 * Sql query parameters
 *
 * @since 4.0
 */
public class SqlParameters
{
	private String name;
	private Object value;

	public SqlParameters(
		String name,
		Object value)
	{
		if (name == null) throw new ArgumentNullException("name");
		if (value == null) throw new ArgumentNullException("value");

		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return this.name;
	}

	public Object getValue()
	{
		return this.value;
	}
}
