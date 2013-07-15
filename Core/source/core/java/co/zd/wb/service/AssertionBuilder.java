package co.zd.wb.service;

import co.zd.wb.model.Assertion;
import java.util.UUID;

public interface AssertionBuilder
{
	Assertion build(
		UUID assertionId,
		String name,
		int seqNum);
	
	void reset();
}