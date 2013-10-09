package co.zd.wb.service;

import java.util.UUID;

/**
 * Provides validation helper methods for DOM-based builders of all types.
 * 
 * @author brendonm
 */
public class V
{
	public static void elementMissing(
		Messages messages,
		UUID itemId,
		String elementName,
		Class itemType)
	{
		if (messages == null) { throw new IllegalArgumentException("messages cannot be null"); }
		if (itemId == null) { throw new IllegalArgumentException("itemId cannot be null"); }
		if (elementName == null) { throw new IllegalArgumentException("elementName cannot be null"); }
		if ("".equals(elementName.trim())) { throw new IllegalArgumentException("elementName cannot be empty"); }
		if (itemType == null) { throw new IllegalArgumentException("itemType cannot be null"); }
		
		messages.addMessage("Element %s is missing from %s with ID %s", elementName, itemType.getName(), itemId);
	}
}
