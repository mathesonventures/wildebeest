package co.mv.stm.service;

import co.mv.stm.model.Migration;
import java.util.UUID;

public interface MigrationBuilder
{
	Migration build(UUID migrationId, UUID fromStateId, UUID toStateId);
	
	void reset();
}