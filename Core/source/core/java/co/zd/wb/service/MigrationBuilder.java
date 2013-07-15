package co.zd.wb.service;

import co.zd.wb.model.Migration;
import java.util.UUID;

public interface MigrationBuilder
{
	Migration build(UUID migrationId, UUID fromStateId, UUID toStateId);
	
	void reset();
}