package co.mv.stm.service;

import co.mv.stm.model.Assertion;
import java.util.UUID;

public interface AssertionBuilder
{
	Assertion build(
		UUID assertionId,
		String name,
		int seqNum);
	
	void reset();
}