package co.mv.stm.service.dom.database;

import co.mv.stm.model.Migration;
import co.mv.stm.model.database.SqlScriptMigration;
import co.mv.stm.service.ResourceLoaderFault;
import co.mv.stm.service.dom.BaseDomMigrationBuilder;
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