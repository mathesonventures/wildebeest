package co.zd.wb.service.dom;

import co.zd.wb.service.dom.BaseDomMigrationBuilder;
import co.zd.wb.model.Migration;
import co.zd.wb.model.base.FakeMigration;
import java.util.UUID;

public class DomFakeMigrationBuilder extends BaseDomMigrationBuilder
{
	@Override public Migration build(UUID migrationId, UUID fromStateId, UUID toStateId)
	{
		String tag = this.getElement().getChildNodes().item(0).getTextContent();
		
		return new FakeMigration(migrationId, fromStateId, toStateId, tag);
	}
}