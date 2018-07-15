package co.mv.wb.event;

public class JumpStateEvent extends Event
{
	/**
	 * Provides all events associated with JumpState Event.
	 *
	 * @since 4.0
	 */
	public enum Name
	{
		Start, Complete, Failed;
	}

	/**
	 * Constructs a new Event with the supplied details.
	 *
	 * @param name    the name of the event, this should be supplied from Event
	 * @param message a message of the event
	 * @since 4.0
	 */
	public JumpStateEvent(
		String name,
		String message)
	{
		super(name, message);
	}

	/**
	 * Creates an JumpStateEvent for Start Event.
	 *
	 * @return the JumpStateEvent created for Start Event
	 * @since 4.0
	 */
	public static JumpStateEvent start(String message)
	{
		return new JumpStateEvent(Name.Start.name(), message);
	}

	/**
	 * Creates an JumpStateEvent for Complete Event.
	 *
	 * @return the JumpStateEvent created for Complete Event
	 * @since 4.0
	 */
	public static JumpStateEvent complete(String message)
	{
		return new JumpStateEvent(Name.Complete.name(), message);
	}

	/**
	 * Creates an JumpStateEvent for Failed Event.
	 *
	 * @param message the message of the event
	 * @return the JumpStateEvent created for Failed Event
	 * @since 4.0
	 */
	public static JumpStateEvent failed(String message)
	{
		return new JumpStateEvent(Name.Failed.name(), message);
	}
}
