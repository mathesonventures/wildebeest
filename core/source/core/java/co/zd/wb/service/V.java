// Wildebeest Migration Framework
// Copyright 2013, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb.service;

import java.util.UUID;

/**
 * Provides validation helper methods for DOM-based builders of all types.
 * 
 * @author                                      Brendon Matheson
 * @since                                       1.1
 */
public class V
{
	/**
	 * Adds a "required element" message to the messages collection.
	 * 
	 * @param       messages                    the Messages collection.
	 * @param       itemId                      the ID of the item that is missing a required element.
	 * @param       elementName                 the name of the required element that is missing.
	 * @param       itemType                    the type of the item that is missing a required element.
	 * @since                                   1.1
	 */
	public static void elementMissing(
		Messages messages,
		UUID itemId,
		String elementName,
		Class itemType)
	{
		if (messages == null) { throw new IllegalArgumentException("messages cannot be null"); }
		if (elementName == null) { throw new IllegalArgumentException("elementName cannot be null"); }
		if ("".equals(elementName.trim())) { throw new IllegalArgumentException("elementName cannot be empty"); }
		if (itemType == null) { throw new IllegalArgumentException("itemType cannot be null"); }
		
		if (itemId == null)
		{
			messages.addMessage("Element %s is missing from %s", elementName, itemType.getName());
		}
		else
		{
			messages.addMessage("Element %s is missing from %s with ID %s", elementName, itemType.getName(), itemId);
		}
	}
}
