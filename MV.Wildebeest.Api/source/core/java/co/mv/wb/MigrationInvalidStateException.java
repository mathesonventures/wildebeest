package co.mv.wb;

import co.mv.wb.framework.ArgumentNullException;

import java.util.UUID;

/**
 * Indicates that a Migration refers to state that does not exist.
 *
 * @since 4.0
 */
public class MigrationInvalidStateException extends Exception
{
	private final UUID migrationId;

	/**
	 * Creates a new MigrationInvalidStateException for the specified ID with the specified failure messages.
	 *
	 * @param message the failure message
	 * @since 4.0
	 */
	public MigrationInvalidStateException(
		UUID migrationId,
		String message)
	{
		super(message);

		if (migrationId == null) throw new ArgumentNullException("migrationId");

		this.migrationId = migrationId;
	}

	/**
	 * Gets the ID of the Migration that failed
	 *
	 * @return the ID of the Migration that failed
	 * @since 4.0
	 */
	public UUID getMigrationId()
	{
		return this.migrationId;
	}
}
