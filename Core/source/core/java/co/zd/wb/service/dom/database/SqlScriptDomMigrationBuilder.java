package co.zd.wb.service.dom.database;

import co.zd.wb.model.Migration;
import co.zd.wb.model.database.SqlScriptMigration;
import co.zd.wb.service.ResourceLoaderFault;
import co.zd.wb.service.dom.BaseDomMigrationBuilder;
import java.util.UUID;

public class SqlScriptDomMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(UUID migrationId, UUID fromStateId, UUID toStateId)
	{
		Migration result = null;
		
		String sql = this.getString("sql");
		
		if (sql != null)
		{
			result = new SqlScriptMigration(migrationId, fromStateId, toStateId, sql);
		}
		
		if (result == null)
		{
			throw new ResourceLoaderFault("could not build instance due to missing data");
		}
		
		return result;
	}
}