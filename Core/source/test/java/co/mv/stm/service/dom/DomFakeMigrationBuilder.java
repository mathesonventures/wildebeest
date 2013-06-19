package co.mv.stm.service.dom;

import co.mv.stm.model.Migration;
import co.mv.stm.model.base.FakeMigration;
import java.util.UUID;

public class DomFakeMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(UUID migrationId, UUID fromStateId, UUID toStateId)
	{
		String tag = this.getElement().getChildNodes().item(0).getTextContent();
		
		return new FakeMigration(migrationId, fromStateId, toStateId, tag);
	}
}