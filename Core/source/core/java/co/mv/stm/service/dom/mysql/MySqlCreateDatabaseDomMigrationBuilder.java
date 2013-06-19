package co.mv.stm.service.dom.mysql;

import co.mv.stm.model.Migration;
import co.mv.stm.model.mysql.MySqlCreateDatabaseMigration;
import co.mv.stm.service.dom.BaseDomMigrationBuilder;
import java.util.UUID;

public class MySqlCreateDatabaseDomMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(UUID migrationId, UUID fromStateId, UUID toStateId)
	{
		return new MySqlCreateDatabaseMigration(migrationId, fromStateId, toStateId);
	}
}