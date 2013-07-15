package co.zd.wb.service.dom.mysql;

import co.zd.wb.model.Migration;
import co.zd.wb.model.mysql.MySqlCreateDatabaseMigration;
import co.zd.wb.service.dom.BaseDomMigrationBuilder;
import java.util.UUID;

public class MySqlCreateDatabaseDomMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(UUID migrationId, UUID fromStateId, UUID toStateId)
	{
		return new MySqlCreateDatabaseMigration(migrationId, fromStateId, toStateId);
	}
}